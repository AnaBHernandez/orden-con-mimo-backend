package com.ordenconmimo.espacio.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.servicios.EspacioService;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class EspacioRestControllerTest {

    @Mock
    private EspacioService espacioService;

    @InjectMocks
    private EspacioRestController espacioRestController;

    private Espacio espacio;
    private List<Espacio> espacios;
    private Tarea tarea;
    private List<Tarea> tareas;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("Test", "User", "testuser", "password", "test@example.com");
        usuario.setId(1L);

        espacio = new Espacio("Espacio de prueba", "Descripción de prueba");
        espacio.setId(1L);
        espacio.setUsuario(usuario);

        espacios = new ArrayList<>();
        espacios.add(espacio);

        Espacio espacio2 = new Espacio("Espacio 2", "Descripción 2");
        espacio2.setId(2L);
        espacio2.setUsuario(usuario);
        espacios.add(espacio2);

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea de prueba");
        tarea.setEspacio(espacio);

        tareas = new ArrayList<>();
        tareas.add(tarea);
    }

    @Test
    public void testObtenerTodosLosEspacios() {
        when(espacioService.obtenerTodosLosEspacios()).thenReturn(espacios);

        ResponseEntity<List<Espacio>> response = espacioRestController.obtenerTodosLosEspacios();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Espacio> espaciosRespuesta = response.getBody();
        assertNotNull(espaciosRespuesta, "La respuesta no debería tener cuerpo null");
        assertEquals(2, espaciosRespuesta.size());

        verify(espacioService, times(1)).obtenerTodosLosEspacios();
    }

    @Test
    public void testObtenerEspacioPorIdExistente() {
        when(espacioService.obtenerEspacioPorId(1L)).thenReturn(Optional.of(espacio));

        ResponseEntity<Espacio> response = espacioRestController.obtenerEspacioPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "El cuerpo de la respuesta no debe ser null");
        assertEquals("Espacio de prueba", response.getBody().getNombre());
        verify(espacioService, times(1)).obtenerEspacioPorId(1L);
    }

    @Test
    public void testObtenerEspacioPorIdNoExistente() {
        when(espacioService.obtenerEspacioPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Espacio> response = espacioRestController.obtenerEspacioPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(espacioService, times(1)).obtenerEspacioPorId(99L);
    }

    @Test
    public void testCrearEspacio() {
        when(espacioService.guardarEspacio(any(Espacio.class))).thenReturn(espacio);

        Espacio nuevoEspacio = new Espacio("Nuevo Espacio", "Nueva Descripción");
        ResponseEntity<Espacio> response = espacioRestController.crearEspacio(nuevoEspacio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "El cuerpo de la respuesta no debe ser null");
        assertEquals("Espacio de prueba", response.getBody().getNombre());
        verify(espacioService, times(1)).guardarEspacio(any(Espacio.class));
    }

    @Test
    public void testActualizarEspacioExistente() {
        // Configurar mocks correctamente para los métodos que realmente se llaman
        when(espacioService.existeEspacio(1L)).thenReturn(true);
        when(espacioService.actualizarEspacio(eq(1L), any(Espacio.class))).thenReturn(espacio);

        Espacio espacioActualizado = new Espacio("Espacio Actualizado", "Descripción Actualizada");
        ResponseEntity<Espacio> response = espacioRestController.actualizarEspacio(1L, espacioActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(espacioService, times(1)).existeEspacio(1L);
        verify(espacioService, times(1)).actualizarEspacio(eq(1L), any(Espacio.class));
    }

    @Test
    public void testActualizarEspacioNoExistente() {
        // Cambia esto para usar existeEspacio en lugar de obtenerEspacioPorId
        when(espacioService.existeEspacio(99L)).thenReturn(false);

        Espacio espacioActualizado = new Espacio("Espacio Actualizado", "Descripción Actualizada");
        ResponseEntity<Espacio> response = espacioRestController.actualizarEspacio(99L, espacioActualizado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(espacioService, times(1)).existeEspacio(99L);
        verify(espacioService, never()).guardarEspacio(any(Espacio.class));
    }

    @Test
    public void testEliminarEspacioExistente() {
        when(espacioService.existeEspacio(1L)).thenReturn(true);
        doNothing().when(espacioService).eliminarEspacio(1L);

        ResponseEntity<Void> response = espacioRestController.eliminarEspacio(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(espacioService, times(1)).existeEspacio(1L);
        verify(espacioService, times(1)).eliminarEspacio(1L);
    }

    @Test
    public void testEliminarEspacioNoExistente() {
        when(espacioService.existeEspacio(99L)).thenReturn(false);

        ResponseEntity<Void> response = espacioRestController.eliminarEspacio(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(espacioService, times(1)).existeEspacio(99L);
        verify(espacioService, never()).eliminarEspacio(99L);
    }

    @Test
    public void testObtenerEspaciosPorUsuarioId() {
        when(espacioService.obtenerEspaciosPorUsuarioId(1L)).thenReturn(espacios);

        ResponseEntity<List<Espacio>> response = espacioRestController.obtenerEspaciosPorUsuarioId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(espacios.size(), response.getBody().size());
        verify(espacioService, times(1)).obtenerEspaciosPorUsuarioId(1L);
    }

    @Test
    public void testObtenerTareasDeEspacio() {
        List<Tarea> tareasLocales = new ArrayList<>();  // Cambiado "tareas" a "tareasLocales"
        tareasLocales.add(new Tarea());
        when(espacioService.obtenerTareasDeEspacio(1L)).thenReturn(tareasLocales);

        ResponseEntity<List<Tarea>> response = espacioRestController.obtenerTareasDeEspacio(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Tarea> tareasRespuesta = response.getBody();

        assertNotNull(tareasRespuesta, "El cuerpo de la respuesta no debería ser null");

        assertEquals(1, tareasRespuesta.size());

        verify(espacioService, times(1)).obtenerTareasDeEspacio(1L);
    }

    @Test
    public void testAgregarTareaAEspacioExistente() {
        Tarea tareaLocal = new Tarea();  // Renombrada
        tareaLocal.setNombre("Nueva Tarea");
        when(espacioService.agregarTareaAEspacio(eq(1L), any(Tarea.class))).thenReturn(tareaLocal);

        ResponseEntity<Tarea> response = espacioRestController.agregarTareaAEspacio(1L, tareaLocal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Tarea tareaRespuesta = response.getBody();
        assertNotNull(tareaRespuesta, "El cuerpo de la respuesta no debería ser null");
        assertEquals("Nueva Tarea", tareaRespuesta.getNombre());

        verify(espacioService, times(1)).agregarTareaAEspacio(eq(1L), any(Tarea.class));
    }

    @Test
    public void testAgregarTareaAEspacioNoExistente() {
        Tarea tareaLocal = new Tarea();  // Renombrada
        when(espacioService.agregarTareaAEspacio(eq(99L), any(Tarea.class))).thenReturn(null);

        ResponseEntity<Tarea> response = espacioRestController.agregarTareaAEspacio(99L, tareaLocal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(espacioService, times(1)).agregarTareaAEspacio(eq(99L), any(Tarea.class));
    }

    @Test
    public void testContarEspaciosPorUsuario() {
        when(espacioService.contarEspaciosPorUsuario(1L)).thenReturn(5L);

        ResponseEntity<Long> response = espacioRestController.contarEspaciosPorUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5L, response.getBody());
        verify(espacioService, times(1)).contarEspaciosPorUsuario(1L);
    }

    @Test
    public void testBuscarEspacioPorNombreExistente() {
        when(espacioService.findByNombre("Espacio Test")).thenReturn(espacio);

        ResponseEntity<Espacio> response = espacioRestController.buscarEspacioPorNombre("Espacio Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(espacio, response.getBody());
        verify(espacioService, times(1)).findByNombre("Espacio Test");
    }

    @Test
    public void testBuscarEspacioPorNombreNoExistente() {
        when(espacioService.findByNombre("Espacio Inexistente")).thenReturn(null);

        ResponseEntity<Espacio> response = espacioRestController.buscarEspacioPorNombre("Espacio Inexistente");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(espacioService, times(1)).findByNombre("Espacio Inexistente");
    }
}
