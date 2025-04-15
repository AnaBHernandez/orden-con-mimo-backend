package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private TareaService tareaService;

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
}