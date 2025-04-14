package com.ordenconmimo.usuario.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;
    
    @InjectMocks
    private TareaService tareaService;
    
    @Test
    @Disabled("El método obtenerTodasLasTareas no está implementado")
    void deberiaObtenerTodasLasTareas() {
    }
    
    @Test
    @Disabled("El método obtenerTareaPorId no está implementado")
    void deberiaObtenerTareaPorId() {
    }
    
    @Test
    @Disabled("El método guardarTarea no está implementado")
    void deberiaGuardarTarea() {
    }
    
    @Test
    void deberiaEncontrarTodasLasTareas() {
      
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setNombre("Limpiar cocina");
        tarea1.setCategoria(CategoriaMIMO.ORDENA);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setNombre("Hacer ejercicio");
        tarea2.setCategoria(CategoriaMIMO.MUEVETE);
        
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        when(tareaRepository.findAll()).thenReturn(tareas);
        
      
        List<Tarea> resultado = tareaService.findAll();
  
        assertEquals(2, resultado.size());
        assertEquals("Limpiar cocina", resultado.get(0).getNombre());
        verify(tareaRepository).findAll();
    }
    
    @Test
    void deberiaEncontrarTareaPorId() {
   
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Limpiar cocina");
        tarea.setCategoria(CategoriaMIMO.ORDENA);
        
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        
    
        Optional<Tarea> resultado = tareaService.findById(1L);
        
    
        assertTrue(resultado.isPresent());
        assertEquals("Limpiar cocina", resultado.get().getNombre());
        verify(tareaRepository).findById(1L);
    }
    
    @Test
    void deberiaSalvarTarea() {
    
        Tarea tarea = new Tarea();
        tarea.setNombre("Limpiar cocina");
        tarea.setCategoria(CategoriaMIMO.ORDENA);
        
        Tarea tareaGuardada = new Tarea();
        tareaGuardada.setId(1L);
        tareaGuardada.setNombre("Limpiar cocina");
        tareaGuardada.setCategoria(CategoriaMIMO.ORDENA);
        
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaGuardada);
        
    
        Tarea resultado = tareaService.save(tarea);
        
        assertEquals(1L, resultado.getId());
        assertEquals("Limpiar cocina", resultado.getNombre());
        verify(tareaRepository).save(tarea);
    }
    
    @Test
    void deberiaEliminarTareaPorId() {
        doNothing().when(tareaRepository).deleteById(1L);
        
        tareaService.deleteById(1L);
        
        verify(tareaRepository).deleteById(1L);
    }
    
    @Test
    void deberiaEncontrarTareasPorCategoria() {
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setNombre("Limpiar cocina");
        tarea1.setCategoria(CategoriaMIMO.ORDENA);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setNombre("Meditar");
        tarea2.setCategoria(CategoriaMIMO.MIRATE);
        
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        when(tareaRepository.findAll()).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.findByCategoriaMIMO(CategoriaMIMO.ORDENA);
        
        assertEquals(1, resultado.size());
        assertEquals("Limpiar cocina", resultado.get(0).getNombre());
        verify(tareaRepository).findAll();
    }
}