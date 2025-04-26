package com.ordenconmimo.usuario.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

@ExtendWith(MockitoExtension.class)
public class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;
    
    @InjectMocks
    private TareaServiceImpl tareaService;
    
    private Tarea tarea;
    private Usuario usuario;
    private Espacio espacio;
    private List<Tarea> tareas;
    
    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        
        espacio = new Espacio("Espacio de prueba", "Descripción de prueba");
        espacio.setId(1L);
        espacio.setUsuario(usuario);
        
        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea de prueba");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setCategoria(CategoriaMIMO.MIRATE);
        tarea.setCompletada(false);
        tarea.setEspacio(espacio);
        tarea.setUsuario(usuario);
        
        tareas = new ArrayList<>();
        tareas.add(tarea);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setNombre("Tarea 2");
        tarea2.setDescripcion("Descripción 2");
        tarea2.setCategoria(CategoriaMIMO.IMAGINA);
        tarea2.setCompletada(true);
        tarea2.setEspacio(espacio);
        tarea2.setUsuario(usuario);
        tareas.add(tarea2);
    }
    
    @Test
    public void testObtenerTodasLasTareas() {
        when(tareaRepository.findAll()).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.obtenerTodasLasTareas();
        
        assertEquals(2, resultado.size());
        verify(tareaRepository).findAll();
    }
    
    @Test
    public void testObtenerTareaPorId() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        
        Optional<Tarea> resultado = tareaService.obtenerTareaPorId(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals("Tarea de prueba", resultado.get().getNombre());
        verify(tareaRepository).findById(1L);
    }
    
    @Test
    public void testObtenerTareaPorIdNoExistente() {
        when(tareaRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Tarea> resultado = tareaService.obtenerTareaPorId(999L);
        
        assertFalse(resultado.isPresent());
        verify(tareaRepository).findById(999L);
    }
    
    @Test
    public void testGuardarTarea() {
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        Tarea resultado = tareaService.guardarTarea(tarea);
        
        assertEquals("Tarea de prueba", resultado.getNombre());
        verify(tareaRepository).save(tarea);
    }
    
    @Test
    public void testEliminarTarea() {
        doNothing().when(tareaRepository).deleteById(1L);
        
        tareaService.eliminarTarea(1L);
        
        verify(tareaRepository).deleteById(1L);
    }
    
    @Test
    public void testExisteTarea() {
        when(tareaRepository.existsById(1L)).thenReturn(true);
        when(tareaRepository.existsById(999L)).thenReturn(false);
        
        assertTrue(tareaService.existeTarea(1L));
        assertFalse(tareaService.existeTarea(999L));
    }
    
    @Test
    public void testActualizarParcialmente() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Tarea actualizada");
        campos.put("completada", true);
        
        Optional<Tarea> resultado = tareaService.actualizarParcialmente(1L, campos);
        
        assertTrue(resultado.isPresent());
        assertEquals("Tarea actualizada", resultado.get().getNombre());
        assertTrue(resultado.get().isCompletada());
    }
    
    @Test
    public void testActualizarParcialmenteConCategoria() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        Map<String, Object> campos = new HashMap<>();
        campos.put("categoria", "ORDENA");
        
        Optional<Tarea> resultado = tareaService.actualizarParcialmente(1L, campos);
        
        assertTrue(resultado.isPresent());
        assertEquals(CategoriaMIMO.ORDENA, resultado.get().getCategoria());
    }
    
    @Test
    public void testActualizarParcialmenteConCategoriaInvalida() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        Map<String, Object> campos = new HashMap<>();
        campos.put("categoria", "CATEGORIA_INVALIDA");
        
        Optional<Tarea> resultado = tareaService.actualizarParcialmente(1L, campos);
        
        assertTrue(resultado.isPresent());
        // La categoría debería permanecer igual
        assertEquals(CategoriaMIMO.MIRATE, resultado.get().getCategoria());
    }
    
    @Test
    public void testActualizarParcialmenteTareaNoExistente() {
        when(tareaRepository.findById(999L)).thenReturn(Optional.empty());
        
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Tarea actualizada");
        
        Optional<Tarea> resultado = tareaService.actualizarParcialmente(999L, campos);
        
        assertFalse(resultado.isPresent());
    }
    
    @Test
    public void testFindByCategoria() {
        when(tareaRepository.findByCategoria(CategoriaMIMO.MIRATE)).thenReturn(List.of(tarea));
        
        List<Tarea> resultado = tareaService.findByCategoria(CategoriaMIMO.MIRATE);
        
        assertEquals(1, resultado.size());
        assertEquals("Tarea de prueba", resultado.get(0).getNombre());
    }
    
    @Test
    public void testFindByCompletada() {
        when(tareaRepository.findByCompletada(true)).thenReturn(List.of(tareas.get(1)));
        
        List<Tarea> resultado = tareaService.findByCompletada(true);
        
        assertEquals(1, resultado.size());
        assertEquals("Tarea 2", resultado.get(0).getNombre());
    }
    
    @Test
    public void testFindByUsuarioId() {
        when(tareaRepository.findByUsuarioId(1L)).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.findByUsuarioId(1L);
        
        assertEquals(2, resultado.size());
    }
    
    @Test
    public void testFindByEspacioId() {
        when(tareaRepository.findByEspacioId(1L)).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.findByEspacioId(1L);
        
        assertEquals(2, resultado.size());
    }
}