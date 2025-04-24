package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.controladores.TareaRestController;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private tareaServiceImpl tareaService;

    @Test
    void deberiaEncontrarTodasLasTareas() {
        List<Tarea> tareas = Arrays.asList(new Tarea(), new Tarea());
        when(tareaRepository.findAll()).thenReturn(tareas);

        List<Tarea> resultado = tareaService.findAll();

        assertEquals(2, resultado.size());
        verify(tareaRepository).findAll();
    }

    @Test
    void deberiaEncontrarTareaPorId() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        Optional<Tarea> resultado = tareaService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(tareaRepository).findById(1L);
    }

    @Test
    void deberiaGuardarTarea() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Nueva tarea");
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea resultado = tareaService.save(tarea);

        assertNotNull(resultado);
        assertEquals("Nueva tarea", resultado.getNombre());
        assertNotNull(tarea.getFechaCreacion());
        verify(tareaRepository).save(tarea);
    }

    @Test
    void deberiaEliminarTarea() {
        Long id = 1L;
        doNothing().when(tareaRepository).deleteById(id);

        tareaService.deleteById(id);

        verify(tareaRepository).deleteById(id);
    }

    @Test
    void deberiaToggleTareaCompletada() {
        Long id = 1L;
        Tarea tarea = new Tarea();
        tarea.setId(id);
        tarea.setCompletada(false);
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(i -> i.getArgument(0));

        Tarea resultado = tareaService.toggleCompletada(id);

        assertTrue(resultado.isCompletada());
        verify(tareaRepository).findById(id);
        verify(tareaRepository).save(tarea);
    }

    @Test
    void deberiaLanzarExcepcionAlToggleTareaInexistente() {
        Long id = 99L;
        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tareaService.toggleCompletada(id));
        verify(tareaRepository).findById(id);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void deberiaEncontrarTareasPorCategoria() {
        List<Tarea> tareas = Arrays.asList(new Tarea(), new Tarea());
        when(tareaRepository.findByCategoria(CategoriaMIMO.ORDENA)).thenReturn(tareas);

        List<Tarea> resultado = tareaService.findByCategoria(CategoriaMIMO.ORDENA);

        assertEquals(2, resultado.size());
        verify(tareaRepository).findByCategoria(CategoriaMIMO.ORDENA);
    }

    @Test
    void deberiaEncontrarTareasPorCompletada() {
        List<Tarea> tareas = Arrays.asList(new Tarea(), new Tarea());
        when(tareaRepository.findByCompletada(true)).thenReturn(tareas);

        List<Tarea> resultado = tareaService.findByCompletada(true);

        assertEquals(2, resultado.size());
        verify(tareaRepository).findByCompletada(true);
    }

    @Test
    void deberiaEncontrarTareasPorUsuarioId() {
        List<Tarea> tareas = Arrays.asList(new Tarea(), new Tarea());
        when(tareaRepository.findByUsuarioId(1L)).thenReturn(tareas);

        List<Tarea> resultado = tareaService.findByUsuarioId(1L);

        assertEquals(2, resultado.size());
        verify(tareaRepository).findByUsuarioId(1L);
    }

    @Test
    void deberiaActualizarTareaParcialmente() {
        Long id = 1L;
        Tarea tarea = new Tarea();
        tarea.setId(id);
        tarea.setNombre("Tarea original");
        tarea.setDescripcion("Descripción original");
        
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(i -> i.getArgument(0));
        
        Map<String, Object> campos = new HashMap<>();
        campos.put("descripcion", "Descripción actualizada");

        Tarea resultado = tareaService.actualizarParcialmente(id, campos);

        assertEquals("Tarea original", resultado.getNombre());
        assertEquals("Descripción actualizada", resultado.getDescripcion());
        verify(tareaRepository).findById(id);
        verify(tareaRepository).save(tarea);
    }

    @Test
    void deberiaBuscarTareasPorTexto() {
        List<Tarea> tareas = Arrays.asList(new Tarea(), new Tarea());
        when(tareaRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase("cocina", "cocina")).thenReturn(tareas);

        List<Tarea> resultado = tareaService.buscarPorTexto("cocina");

        assertEquals(2, resultado.size());
        verify(tareaRepository).findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase("cocina", "cocina");
    }

    @Test
    void deberiaContarTareasPorCategoria() {
        when(tareaRepository.countByCategoria(CategoriaMIMO.IMAGINA)).thenReturn(5L);

        long resultado = tareaService.contarPorCategoria(CategoriaMIMO.IMAGINA);

        assertEquals(5L, resultado);
        verify(tareaRepository).countByCategoria(CategoriaMIMO.IMAGINA);
    }

    @Test
    void deberiaMarcarTareasComoCompletadasPorCategoria() {
        when(tareaRepository.updateCompletadaByCategoria(true, CategoriaMIMO.MUEVETE)).thenReturn(3);

        int resultado = tareaService.marcarCompletadasPorCategoria(CategoriaMIMO.MUEVETE);

        assertEquals(3, resultado);
        verify(tareaRepository).updateCompletadaByCategoria(true, CategoriaMIMO.MUEVETE);
    }
    @Test
void testActualizarTareaCompleto() {
    // Setup del mock para la tarea existente
    Tarea tareaExistente = new Tarea();
    tareaExistente.setId(1L);
    tareaExistente.setNombre("Tarea original");
    tareaExistente.setDescripcion("Descripción original");
    tareaExistente.setCategoria(CategoriaMIMO.MIRATE);
    tareaExistente.setCompletada(false);
    tareaExistente.setFechaLimite(null);
    
    // Mock para el servicio
    tareaServiceImpl servicioMock = Mockito.mock(tareaServiceImpl.class);
    
    // Configuramos el controlador con el mock
    TareaRestController controlador = new TareaRestController();
    ReflectionTestUtils.setField(controlador, "tareaService", servicioMock);
    
    // Datos para la actualización
    Map<String, Object> datosTarea = new HashMap<>();
    datosTarea.put("nombre", "Tarea actualizada");
    datosTarea.put("descripcion", "Descripción actualizada");
    datosTarea.put("categoria", "ORDENA");
    datosTarea.put("completada", true);
    datosTarea.put("fechaLimite", "2025-05-15");
    
    // Tarea que esperamos recibir después de la actualización
    Tarea tareaActualizada = new Tarea();
    tareaActualizada.setId(1L);
    tareaActualizada.setNombre("Tarea actualizada");
    tareaActualizada.setDescripcion("Descripción actualizada");
    tareaActualizada.setCategoria(CategoriaMIMO.ORDENA);
    tareaActualizada.setCompletada(true);
    tareaActualizada.setFechaLimite(LocalDate.parse("2025-05-15"));
    
    // Configuración de comportamiento del mock
    when(servicioMock.findById(1L)).thenReturn(Optional.of(tareaExistente));
    when(servicioMock.save(any(Tarea.class))).thenReturn(tareaActualizada);
    
    // Ejecutar el método bajo prueba
    ResponseEntity<Tarea> respuesta = controlador.actualizarTarea(1L, datosTarea);
    
    // Verificar que el status code es 200 OK
    assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    
    // Verificar que el cuerpo de la respuesta contiene los datos actualizados
    Tarea tareaResultado = respuesta.getBody();
    assertNotNull(tareaResultado);
    assertEquals("Tarea actualizada", tareaResultado.getNombre());
    assertEquals("Descripción actualizada", tareaResultado.getDescripcion());
    assertEquals(CategoriaMIMO.ORDENA, tareaResultado.getCategoria());
    assertTrue(tareaResultado.isCompletada());
    assertEquals(LocalDate.parse("2025-05-15"), tareaResultado.getFechaLimite());
    
    // Verificar que se llamó a los métodos del servicio
    verify(servicioMock).findById(1L);
    verify(servicioMock).save(any(Tarea.class));
}

@Test
void testActualizarTareaEliminarFechaLimite() {
    // Setup del mock para la tarea existente
    Tarea tareaExistente = new Tarea();
    tareaExistente.setId(1L);
    tareaExistente.setNombre("Tarea original");
    tareaExistente.setFechaLimite(LocalDate.parse("2025-05-15"));
    
    // Mock para el servicio
    tareaServiceImpl servicioMock = Mockito.mock(tareaServiceImpl.class);
    
    // Configuramos el controlador con el mock
    TareaRestController controlador = new TareaRestController();
    ReflectionTestUtils.setField(controlador, "tareaService", servicioMock);
    
    // Datos para la actualización (con fechaLimite = null)
    Map<String, Object> datosTarea = new HashMap<>();
    datosTarea.put("nombre", "Tarea actualizada");
    datosTarea.put("fechaLimite", null);
    
    // Tarea que esperamos recibir después de la actualización
    Tarea tareaActualizada = new Tarea();
    tareaActualizada.setId(1L);
    tareaActualizada.setNombre("Tarea actualizada");
    tareaActualizada.setFechaLimite(null);
    
    // Configuración de comportamiento del mock
    when(servicioMock.findById(1L)).thenReturn(Optional.of(tareaExistente));
    when(servicioMock.save(any(Tarea.class))).thenReturn(tareaActualizada);
    
    // Ejecutar el método bajo prueba
    ResponseEntity<Tarea> respuesta = controlador.actualizarTarea(1L, datosTarea);
    
    // Verificar que el status code es 200 OK
    assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    
    // Verificar que el cuerpo de la respuesta tiene fechaLimite = null
    Tarea tareaResultado = respuesta.getBody();
    assertNotNull(tareaResultado);
    assertEquals("Tarea actualizada", tareaResultado.getNombre());
    assertNull(tareaResultado.getFechaLimite());
    
    // Verificar que se llamó a los métodos del servicio
    verify(servicioMock).findById(1L);
    
    // Capturar el argumento pasado a save para verificar que fechaLimite es null
    ArgumentCaptor<Tarea> tareaCaptor = ArgumentCaptor.forClass(Tarea.class);
    verify(servicioMock).save(tareaCaptor.capture());
    Tarea tareaGuardada = tareaCaptor.getValue();
    assertNull(tareaGuardada.getFechaLimite());
}

@Test
void testErrorParseandoFechaLimite() {
    // Setup del mock para la tarea existente
    Tarea tareaExistente = new Tarea();
    tareaExistente.setId(1L);
    tareaExistente.setNombre("Tarea original");
    
    // Mock para el servicio
    tareaServiceImpl servicioMock = Mockito.mock(tareaServiceImpl.class);
    
    // Configuramos el controlador con el mock
    TareaRestController controlador = new TareaRestController();
    ReflectionTestUtils.setField(controlador, "tareaService", servicioMock);
    
    // Datos para la actualización (con fechaLimite inválida)
    Map<String, Object> datosTarea = new HashMap<>();
    datosTarea.put("fechaLimite", "fecha-invalida");
    
    // Configuración de comportamiento del mock
    when(servicioMock.findById(1L)).thenReturn(Optional.of(tareaExistente));
    
    // Ejecutar el método bajo prueba
    ResponseEntity<Tarea> respuesta = controlador.actualizarTarea(1L, datosTarea);
    
    // Verificar que el status code es 500 INTERNAL_SERVER_ERROR
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
    
    // Verificar que se llamó a findById pero no a save
    verify(servicioMock).findById(1L);
    verify(servicioMock, never()).save(any(Tarea.class));
}

@Test
void testTareaNoEncontrada() {
    // Mock para el servicio
    tareaServiceImpl servicioMock = Mockito.mock(tareaServiceImpl.class);
    
    // Configuramos el controlador con el mock
    TareaRestController controlador = new TareaRestController();
    ReflectionTestUtils.setField(controlador, "tareaService", servicioMock);
    
    // Datos para la actualización
    Map<String, Object> datosTarea = new HashMap<>();
    datosTarea.put("nombre", "Tarea actualizada");
    
    // Configuración de comportamiento del mock
    when(servicioMock.findById(999L)).thenReturn(Optional.empty());
    
    // Ejecutar el método bajo prueba
    ResponseEntity<Tarea> respuesta = controlador.actualizarTarea(999L, datosTarea);
    
    // Verificar que el status code es 404 NOT_FOUND
    assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    
    // Verificar que se llamó a findById pero no a save
    verify(servicioMock).findById(999L);
    verify(servicioMock, never()).save(any(Tarea.class));
}

@Test
void testErrorEnServicio() {
    // Mock para el servicio
    tareaServiceImpl servicioMock = Mockito.mock(tareaServiceImpl.class);
    
    // Configuramos el controlador con el mock
    TareaRestController controlador = new TareaRestController();
    ReflectionTestUtils.setField(controlador, "tareaService", servicioMock);
    
    // Datos para la actualización
    Map<String, Object> datosTarea = new HashMap<>();
    datosTarea.put("nombre", "Tarea actualizada");
    
    // Configuración de comportamiento del mock
    when(servicioMock.findById(1L)).thenThrow(new RuntimeException("Error simulado"));
    
    // Ejecutar el método bajo prueba
    ResponseEntity<Tarea> respuesta = controlador.actualizarTarea(1L, datosTarea);
    
    // Verificar que el status code es 500 INTERNAL_SERVER_ERROR
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
    
    // Verificar que se llamó a findById pero no a save
    verify(servicioMock).findById(1L);
    verify(servicioMock, never()).save(any(Tarea.class));
}
}