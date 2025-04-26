package com.ordenconmimo.espacio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

@ExtendWith(MockitoExtension.class)
public class EspacioServiceTest {

    @Mock
    private EspacioRepository espacioRepository;
    
    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private EspacioService espacioService;
    
    private Espacio espacio;
    private Usuario usuario;
    private List<Espacio> espacios;
    
    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
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
    }

    @Test
    public void testObtenerTodosLosEspacios() {
        when(espacioRepository.findAll()).thenReturn(espacios);
        
        List<Espacio> resultado = espacioService.obtenerTodosLosEspacios();
        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(espacioRepository).findAll();
    }

    @Test
    public void testObtenerEspacioPorId() {
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacio));
        
        Optional<Espacio> resultado = espacioService.obtenerEspacioPorId(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(espacioRepository).findById(1L);
    }
    
    @Test
    public void testExisteEspacio() {
        when(espacioRepository.existsById(1L)).thenReturn(true);
        when(espacioRepository.existsById(999L)).thenReturn(false);
        
        assertTrue(espacioService.existeEspacio(1L));
        assertFalse(espacioService.existeEspacio(999L));
    }
    
    @Test
    public void testObtenerEspaciosPorUsuarioId() {
        when(espacioRepository.findByUsuarioId(1L)).thenReturn(espacios);
        
        List<Espacio> resultado = espacioService.obtenerEspaciosPorUsuarioId(1L);
        
        assertEquals(2, resultado.size());
        verify(espacioRepository).findByUsuarioId(1L);
    }
    
    @Test
    public void testGuardarEspacio() {
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio);
        
        Espacio resultado = espacioService.guardarEspacio(espacio);
        
        assertNotNull(resultado);
        assertEquals("Espacio de prueba", resultado.getNombre());
        verify(espacioRepository).save(espacio);
    }

    @Test
    public void testEliminarEspacio() {
        doNothing().when(espacioRepository).deleteById(1L);
        when(espacioRepository.existsById(1L)).thenReturn(true);
        
        espacioService.eliminarEspacio(1L);
        
        verify(espacioRepository).deleteById(1L);
    }
    
    @Test
    public void testObtenerEspaciosPorUsuarioIdVacio() {
        when(espacioRepository.findByUsuarioId(999L)).thenReturn(new ArrayList<>());
        
        List<Espacio> resultado = espacioService.obtenerEspaciosPorUsuarioId(999L);
        
        assertTrue(resultado.isEmpty());
        verify(espacioRepository).findByUsuarioId(999L);
    }
    
    @Test
    public void testObtenerTareasDeEspacio() {
        List<Tarea> tareas = new ArrayList<>();
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tareas.add(tarea);
        
        when(espacioRepository.existsById(1L)).thenReturn(true);
        when(tareaRepository.findByEspacioId(1L)).thenReturn(tareas);
        
        List<Tarea> resultado = espacioService.obtenerTareasDeEspacio(1L);
        
        assertEquals(1, resultado.size());
    }
    
    @Test
    public void testObtenerTareasDeEspacioNoExistente() {
        when(espacioRepository.existsById(999L)).thenReturn(false);
        
        List<Tarea> resultado = espacioService.obtenerTareasDeEspacio(999L);
        
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    public void testActualizarEspacio() {
        when(espacioRepository.existsById(1L)).thenReturn(true);
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio);
        
        Espacio espacioActualizado = new Espacio("Espacio actualizado", "Descripción actualizada");
        espacioActualizado.setId(1L);
        
        Espacio resultado = espacioService.actualizarEspacio(1L, espacioActualizado);
        
        assertNotNull(resultado);
        assertEquals("Espacio de prueba", resultado.getNombre());
    }
    
    @Test
    public void testActualizarEspacioNoExistente() {
        when(espacioRepository.existsById(999L)).thenReturn(false);
        
        Espacio espacioActualizado = new Espacio("Espacio actualizado", "Descripción actualizada");
        
        Espacio resultado = espacioService.actualizarEspacio(999L, espacioActualizado);
        
        assertNull(resultado);
    }
}