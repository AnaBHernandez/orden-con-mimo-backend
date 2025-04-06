package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EspacioServiceImplTest {

    @Mock
    private EspacioRepository espacioRepository;
    
    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private EspacioServiceImpl espacioService;

    private Usuario usuario;
    private Espacio espacio;
    private Tarea tarea;

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
        tarea.setDescripcion("Tarea de prueba");
        tarea.setUsuario(usuario);
        tarea.setEspacio(espacio);
    }

    @Test
    void guardarEspacio_ConDatosValidos_DebeGuardarYRetornarEspacio() {
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio);
                
        Espacio resultado = espacioService.guardarEspacio(espacio);
        
        assertNotNull(resultado);
        assertEquals("Espacio Test", resultado.getNombre());
        verify(espacioRepository, times(1)).save(any(Espacio.class));
    }
    
    @Test
    void guardarEspacio_SinNombre_DebeLanzarExcepcion() {
        espacio.setNombre("");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            espacioService.guardarEspacio(espacio);
        });
        
        assertEquals("El nombre del espacio no puede estar vacío", exception.getMessage());
        verify(espacioRepository, never()).save(any(Espacio.class));
    }
    
    @Test
    void guardarEspacio_NombreDuplicado_DebeLanzarExcepcion() {
        espacio.setId(null); 
        when(espacioRepository.findByNombreAndUsuario("Espacio Test", usuario))
            .thenReturn(Optional.of(espacio));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            espacioService.guardarEspacio(espacio);
        });
        
        assertEquals("Ya existe un espacio con ese nombre para el usuario", exception.getMessage());
        verify(espacioRepository, never()).save(any(Espacio.class));
    }
    
    @Test
    void buscarPorId_ConIdExistente_DebeRetornarEspacio() {
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacio));
        
        Optional<Espacio> resultado = espacioService.buscarPorId(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(espacioRepository, times(1)).findById(1L);
    }
    
    @Test
    void obtenerEspaciosPorUsuario_ConUsuarioConEspacios_DebeRetornarListaEspacios() {
        List<Espacio> espacios = Arrays.asList(espacio);
        when(espacioRepository.findByUsuario(usuario)).thenReturn(espacios);
        
        List<Espacio> resultado = espacioService.obtenerEspaciosPorUsuario(usuario);
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Espacio Test", resultado.get(0).getNombre());
        verify(espacioRepository, times(1)).findByUsuario(usuario);
    }
    
    @Test
    void eliminarEspacio_ConIdExistente_DebeRetornarTrue() {
        when(espacioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(espacioRepository).deleteById(1L);
        
        boolean resultado = espacioService.eliminarEspacio(1L);
        
        assertTrue(resultado);
        verify(espacioRepository, times(1)).existsById(1L);
        verify(espacioRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void agregarTarea_ConDatosValidos_DebeAñadirTareaAlEspacio() {
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacio));
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio);
        
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setDescripcion("Nueva tarea");
        nuevaTarea.setUsuario(usuario);
        
        Espacio resultado = espacioService.agregarTarea(1L, nuevaTarea);
        assertNotNull(resultado);
        
        verify(espacioRepository, times(1)).findById(1L);
        verify(espacioRepository, times(1)).save(any(Espacio.class));
    }
    
    @Test
    void eliminarTarea_ConDatosValidos_DebeEliminarTareaDelEspacio() {
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacio));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(espacioRepository.save(any(Espacio.class))).thenReturn(espacio);
        
        Espacio resultado = espacioService.eliminarTarea(1L, 1L);
        assertNotNull(resultado);
        
        verify(espacioRepository, times(1)).findById(1L);
        verify(tareaRepository, times(1)).findById(1L);
        verify(espacioRepository, times(1)).save(any(Espacio.class));
    }
}