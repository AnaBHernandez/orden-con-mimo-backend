package com.ordenconmimo.usuario.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.servicios.TareaServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TareaRestControllerTest {

    @Mock
    private TareaServiceImpl tareaService;
    
    @InjectMocks
    private TareaRestController tareaRestController;
    
    private Tarea tarea;
    private List<Tarea> tareas;
    
    @BeforeEach
    public void setUp() {
        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea de prueba");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setCategoria(CategoriaMIMO.MIRATE);
        tarea.setCompletada(false);
        
        tareas = new ArrayList<>();
        tareas.add(tarea);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setNombre("Otra tarea");
        tarea2.setDescripcion("Otra descripción");
        tarea2.setCategoria(CategoriaMIMO.IMAGINA);
        tarea2.setCompletada(true);
        
        tareas.add(tarea2);
    }
    
    @Test
    public void testObtenerTodasLasTareas() {
        when(tareaService.findAll()).thenReturn(tareas);
        
        ResponseEntity<List<Tarea>> response = tareaRestController.obtenerTodasLasTareas();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    
    @Test
    public void testObtenerTareaPorId() {
        when(tareaService.obtenerTareaPorId(1L)).thenReturn(Optional.of(tarea));
        
        ResponseEntity<Tarea> response = tareaRestController.obtenerTareaPorId(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarea de prueba", response.getBody().getNombre());
    }
    
    @Test
    public void testObtenerTareaPorIdNoEncontrada() {
        when(tareaService.obtenerTareaPorId(999L)).thenReturn(Optional.empty());
        
        ResponseEntity<Tarea> response = tareaRestController.obtenerTareaPorId(999L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testCrearTarea() {
        when(tareaService.guardarTarea(any(Tarea.class))).thenReturn(tarea);
        
        ResponseEntity<Tarea> response = tareaRestController.crearTarea(new Tarea());
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tarea de prueba", response.getBody().getNombre());
    }
    
    @Test
    public void testActualizarTarea() {
        when(tareaService.obtenerTareaPorId(1L)).thenReturn(Optional.of(tarea));
        when(tareaService.guardarTarea(any(Tarea.class))).thenReturn(tarea);
        
        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setNombre("Tarea actualizada");
        
        ResponseEntity<Tarea> response = tareaRestController.actualizarTarea(1L, tareaActualizada);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarea de prueba", response.getBody().getNombre());
    }
    
    @Test
    public void testActualizarTareaNoEncontrada() {
        when(tareaService.obtenerTareaPorId(999L)).thenReturn(Optional.empty());
        
        ResponseEntity<Tarea> response = tareaRestController.actualizarTarea(999L, new Tarea());
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testEliminarTarea() {
        when(tareaService.existeTarea(1L)).thenReturn(true);
        doNothing().when(tareaService).eliminarTarea(1L);
        
        ResponseEntity<Void> response = tareaRestController.eliminarTarea(1L);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tareaService, times(1)).eliminarTarea(1L);
    }
    
    @Test
    public void testEliminarTareaNoEncontrada() {
        when(tareaService.existeTarea(999L)).thenReturn(false);
        
        ResponseEntity<Void> response = tareaRestController.eliminarTarea(999L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testObtenerTareasPorCategoria() {
        when(tareaService.findByCategoria(CategoriaMIMO.MIRATE)).thenReturn(List.of(tarea));
        
        ResponseEntity<List<Tarea>> response = tareaRestController.obtenerTareasPorCategoria("MIRATE");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(CategoriaMIMO.MIRATE, response.getBody().get(0).getCategoria());
    }
    
    @Test
    public void testObtenerTareasPorCompletada() {
        when(tareaService.findByCompletada(true)).thenReturn(List.of(tareas.get(1)));
        
        ResponseEntity<List<Tarea>> response = tareaRestController.obtenerTareasPorCompletada(true);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(true, response.getBody().get(0).isCompletada());
    }
    
    @Test
    public void testObtenerTareasPorUsuarioId() {
        when(tareaService.findByUsuarioId(1L)).thenReturn(tareas);
        
        ResponseEntity<List<Tarea>> response = tareaRestController.obtenerTareasPorUsuarioId(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    
    @Test
    public void testActualizarTareaParcialmente() {
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Tarea actualizada");
        campos.put("completada", true);
        
        when(tareaService.actualizarParcialmente(1L, campos)).thenReturn(Optional.of(tarea));
        
        ResponseEntity<Tarea> response = tareaRestController.actualizarTareaParcialmente(1L, campos);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarea de prueba", response.getBody().getNombre());
    }
    
    @Test
    public void testActualizarTareaParcialmenteNoEncontrada() {
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Tarea actualizada");
        
        when(tareaService.actualizarParcialmente(999L, campos)).thenReturn(Optional.empty());
        
        ResponseEntity<Tarea> response = tareaRestController.actualizarTareaParcialmente(999L, campos);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}