package com.ordenconmimo.espacio.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

@ExtendWith(MockitoExtension.class)
class EspacioServiceTest {

    @Mock
    private EspacioRepository espacioRepository;
    
    @InjectMocks
    private EspacioService espacioService;
    
    private Espacio espacio1;
    private Espacio espacio2;
    
    @BeforeEach
    void setUp() {
        espacio1 = new Espacio();
        espacio1.setId(1L);
        espacio1.setNombre("Cocina");
        espacio1.setDescripcion("Espacio para cocinar");
        espacio1.setFechaCreacion(LocalDateTime.now());
        
        espacio2 = new Espacio();
        espacio2.setId(2L);
        espacio2.setNombre("Sala");
        espacio2.setDescripcion("Sala de estar");
        espacio2.setFechaCreacion(LocalDateTime.now());
    }
    
    @Test
    void deberiaObtenerTodosLosEspacios() {
        List<Espacio> espacios = Arrays.asList(espacio1, espacio2);
        when(espacioRepository.findAll()).thenReturn(espacios);
        
        List<Espacio> resultado = espacioService.obtenerTodosLosEspacios();
        
        assertEquals(2, resultado.size());
        assertEquals("Cocina", resultado.get(0).getNombre());
        assertEquals("Sala", resultado.get(1).getNombre());
        verify(espacioRepository).findAll();
    }
    
    @Test
    void deberiaObtenerEspacioPorId() {
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacio1));
        
        Optional<Espacio> resultado = espacioService.obtenerEspacioPorId(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals("Cocina", resultado.get().getNombre());
        verify(espacioRepository).findById(1L);
    }
    
    @Test
    void deberiaRetornarOptionalVacioSiNoExisteEspacio() {
        when(espacioRepository.findById(99L)).thenReturn(Optional.empty());
        
        Optional<Espacio> resultado = espacioService.obtenerEspacioPorId(99L);
        
        assertFalse(resultado.isPresent());
        verify(espacioRepository).findById(99L);
    }
    
    @Test
    void deberiaGuardarEspacio() {
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio1);
        
        Espacio resultado = espacioService.guardarEspacio(espacio1);
        
        assertEquals(1L, resultado.getId());
        assertEquals("Cocina", resultado.getNombre());
        verify(espacioRepository).save(espacio1);
    }
    
    @Test
    void deberiaEliminarEspacioSiExiste() {
        when(espacioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(espacioRepository).deleteById(1L);
        
        espacioService.eliminarEspacio(1L);
        
        verify(espacioRepository).existsById(1L);
        verify(espacioRepository).deleteById(1L);
    }
    
    @Test
    void noDeberiaEliminarEspacioSiNoExiste() {
        when(espacioRepository.existsById(99L)).thenReturn(false);
        
        espacioService.eliminarEspacio(99L);
        
        verify(espacioRepository).existsById(99L);
        verify(espacioRepository, never()).deleteById(anyLong());
    }
    
    @Test
    void deberiaObtenerEspaciosPorUsuario() {
        Long usuarioId = 1L;
        List<Espacio> espacios = Arrays.asList(espacio1, espacio2);
        when(espacioRepository.findByUsuarioId(usuarioId)).thenReturn(espacios);
        
        List<Espacio> resultado = espacioService.obtenerEspaciosPorUsuario(usuarioId);
        
        assertEquals(2, resultado.size());
        assertEquals("Cocina", resultado.get(0).getNombre());
        assertEquals("Sala", resultado.get(1).getNombre());
        verify(espacioRepository).findByUsuarioId(usuarioId);
    }
       
    
    @Test
    void deberiaActualizarEspacioExistente() {
        Long id = 1L;
        Espacio espacioExistente = new Espacio();
        espacioExistente.setId(id);
        espacioExistente.setNombre("Cocina");
        espacioExistente.setDescripcion("Espacio viejo");
        espacioExistente.setFechaCreacion(LocalDateTime.now().minusDays(1));
        
        Espacio espacioNuevo = new Espacio();
        espacioNuevo.setNombre("Cocina renovada");
        espacioNuevo.setDescripcion("Espacio renovado");
        
        when(espacioRepository.findById(id)).thenReturn(Optional.of(espacioExistente));
        when(espacioRepository.save(any(Espacio.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Optional<Espacio> resultado = espacioService.actualizarEspacio(id, espacioNuevo);
        
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Cocina renovada", resultado.get().getNombre());
        assertEquals("Espacio renovado", resultado.get().getDescripcion());
        assertNotNull(resultado.get().getFechaCreacion());
        verify(espacioRepository).findById(id);
        verify(espacioRepository).save(any(Espacio.class));
    }
    
    @Test
    void deberiaContarEspaciosPorUsuario() {
        Long usuarioId = 1L;
        when(espacioRepository.countByUsuarioId(usuarioId)).thenReturn(5L);
        
        long cantidad = espacioService.contarEspaciosPorUsuario(usuarioId);
        
        assertEquals(5L, cantidad);
        verify(espacioRepository).countByUsuarioId(usuarioId);
    }
    
    @Test
    void deberiaRetornarTrueParaExistsByIdCuandoExiste() {
        when(espacioRepository.existsById(1L)).thenReturn(true);
        
        boolean existe = espacioService.existeEspacio(1L);
        
        assertTrue(existe);
        verify(espacioRepository).existsById(1L);
    }
    
    @Test
    void deberiaRetornarFalseParaExistsByIdCuandoNoExiste() {
        when(espacioRepository.existsById(99L)).thenReturn(false);
        
        boolean existe = espacioService.existeEspacio(99L);
        
        assertFalse(existe);
        verify(espacioRepository).existsById(99L);
    }
    
    @Test
    void deberiaEncontrarEspacioPorNombre() {
        String nombre = "Cocina";
        when(espacioRepository.findByNombre(nombre)).thenReturn(Optional.of(espacio1));
        
        Optional<Espacio> resultado = espacioService.findByNombre(nombre);
        
        assertTrue(resultado.isPresent());
        assertEquals(nombre, resultado.get().getNombre());
        verify(espacioRepository).findByNombre(nombre);
    }
    
        
    @Test
    void deberiaRetornarListaVaciaSiNoExisteEspacioAlObtenerTareas() {
        when(espacioRepository.findById(99L)).thenReturn(Optional.empty());
        
        List<Tarea> resultado = espacioService.obtenerTareasDeEspacio(99L);
        
        assertTrue(resultado.isEmpty());
        verify(espacioRepository).findById(99L);
    }
       
    
    @Test
    void noDeberiaAgregarTareaSiNoExisteEspacio() {
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setNombre("Nueva tarea");  // Cambiado de setTitulo a setNombre
        
        when(espacioRepository.findById(99L)).thenReturn(Optional.empty());
        
        Optional<Espacio> resultado = espacioService.agregarTareaAEspacio(99L, nuevaTarea);
        
        assertFalse(resultado.isPresent());
        verify(espacioRepository).findById(99L);
        verify(espacioRepository, never()).save(any());
    }
    
}