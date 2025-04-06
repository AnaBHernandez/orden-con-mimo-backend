package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;
    
    @Mock
    private EspacioRepository espacioRepository;
    
    @InjectMocks
    private TareaServiceImpl tareaService;
    
    private Tarea tarea;
    private Usuario usuario;
    private Espacio espacio;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");
        
        espacio = new Espacio();
        espacio.setId(1L);
        espacio.setNombre("Espacio Test");
        espacio.setUsuario(usuario);
        
        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea Test");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setCategoria(CategoriaMIMO.MIRATE);
        tarea.setCompletada(false);
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setEspacio(espacio);
        tarea.setUsuario(usuario);
    }
    
    @Test
    void findAll_deberiaRetornarListaDeTareas() {
        List<Tarea> tareas = Arrays.asList(tarea);
        when(tareaRepository.findAll()).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.findAll();
        
        assertEquals(1, resultado.size());
        assertEquals("Tarea Test", resultado.get(0).getNombre());
        verify(tareaRepository).findAll();
    }
    
    @Test
    void findById_conIdExistente_deberiaRetornarTarea() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        
        Optional<Tarea> resultado = tareaService.findById(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals("Tarea Test", resultado.get().getNombre());
        verify(tareaRepository).findById(1L);
    }
    
    @Test
    void findById_conIdNoExistente_deberiaRetornarEmpty() {
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());
        
        Optional<Tarea> resultado = tareaService.findById(99L);
        
        assertFalse(resultado.isPresent());
        verify(tareaRepository).findById(99L);
    }
    
    @Test
    void findById_conIdNulo_deberiaLanzarException() {
        assertThrows(IllegalArgumentException.class, () -> tareaService.findById(null));
        verify(tareaRepository, never()).findById(any());
    }
    
    @Test
    void save_conTareaValida_deberiaGuardarTarea() {
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setNombre("Nueva Tarea");
        nuevaTarea.setCategoria(CategoriaMIMO.IMAGINA);
        nuevaTarea.setUsuario(usuario);
        
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(invocation -> {
            Tarea t = invocation.getArgument(0);
            t.setId(2L);
            return t;
        });
        
        Tarea resultado = tareaService.save(nuevaTarea);
        
        assertNotNull(resultado.getId());
        assertEquals("Nueva Tarea", resultado.getNombre());
        assertNotNull(resultado.getFechaCreacion());
        verify(tareaRepository).save(nuevaTarea);
    }
    
    @Test
    void save_conTareaSinNombre_deberiaLanzarException() {
        Tarea tareaSinNombre = new Tarea();
        tareaSinNombre.setCategoria(CategoriaMIMO.MUEVETE);
        
        assertThrows(IllegalArgumentException.class, () -> tareaService.save(tareaSinNombre));
        verify(tareaRepository, never()).save(any());
    }
    
    @Test
    void save_conTareaSinCategoria_deberiaLanzarException() {
        Tarea tareaSinCategoria = new Tarea();
        tareaSinCategoria.setNombre("Tarea sin categoría");
        
        assertThrows(IllegalArgumentException.class, () -> tareaService.save(tareaSinCategoria));
        verify(tareaRepository, never()).save(any());
    }
    
    @Test
    void save_conEspacioInexistente_deberiaLanzarException() {
        Tarea tareaConEspacioInvalido = new Tarea();
        tareaConEspacioInvalido.setNombre("Tarea con espacio inválido");
        tareaConEspacioInvalido.setCategoria(CategoriaMIMO.ORDENA);
        
        Espacio espacioInvalido = new Espacio();
        espacioInvalido.setId(99L);
        tareaConEspacioInvalido.setEspacio(espacioInvalido);
        
        when(espacioRepository.existsById(99L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> tareaService.save(tareaConEspacioInvalido));
        verify(tareaRepository, never()).save(any());
    }
    
    @Test
    void deleteById_conIdExistente_deberiaEliminarTarea() {
        when(tareaRepository.existsById(1L)).thenReturn(true);
        
        tareaService.deleteById(1L);
        
        verify(tareaRepository).deleteById(1L);
    }
    
    @Test
    void deleteById_conIdNoExistente_deberiaLanzarException() {
        when(tareaRepository.existsById(99L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> tareaService.deleteById(99L));
        verify(tareaRepository, never()).deleteById(any());
    }
    
    @Test
    void findByCategoriaMIMO_deberiaRetornarTareasPorCategoria() {
        List<Tarea> tareas = Arrays.asList(tarea);
        when(tareaRepository.findByCategoria(CategoriaMIMO.MIRATE)).thenReturn(tareas);
        
        List<Tarea> resultado = tareaService.findByCategoriaMIMO(CategoriaMIMO.MIRATE);
        
        assertEquals(1, resultado.size());
        assertEquals(CategoriaMIMO.MIRATE, resultado.get(0).getCategoria());
        verify(tareaRepository).findByCategoria(CategoriaMIMO.MIRATE);
    }
    
    @Test
    void toggleCompletada_conTareaIncompleta_deberiaMarcarlaComoCompletada() {
        tarea.setCompletada(false);
        
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        tareaService.toggleCompletada(1L);
        
        assertTrue(tarea.isCompletada());
        verify(tareaRepository).save(tarea);
    }
    
    @Test
    void toggleCompletada_conTareaCompletada_deberiaMarcarlaComoIncompleta() {
        tarea.setCompletada(true);
        
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        
        tareaService.toggleCompletada(1L);
        
        assertFalse(tarea.isCompletada());
        verify(tareaRepository).save(tarea);
    }
}