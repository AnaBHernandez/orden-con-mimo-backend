package com.ordenconmimo.usuario.controladores;

import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.servicios.TareaService;
import com.ordenconmimo.usuario.servicios.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void deberiaListarTareasDeUsuarioCuandoExistenUsuarios() throws Exception {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");
        usuario.setApellido("Apellido Test");
        usuario.setUsername("usuariotest");
        
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setTitulo("Tarea 1");
        tarea1.setUsuario(usuario);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setTitulo("Tarea 2");
        tarea2.setUsuario(usuario);
        
        List<Usuario> usuarios = Arrays.asList(usuario);
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(tareaService.findByUsuarioId(1L)).thenReturn(tareas);

        // When & Then
        mockMvc.perform(get("/tareas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tareas"))
                .andExpect(model().attributeExists("tareas"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("tareas", tareas))
                .andExpect(model().attribute("usuario", usuario));
        
        verify(usuarioService, times(1)).findAll();
        verify(tareaService, times(1)).findByUsuarioId(1L);
        verify(tareaService, never()).findAll();
    }

    @Test
    public void deberiaListarTodasLasTareasCuandoNoExistenUsuarios() throws Exception {
        // Given
        List<Usuario> usuarios = new ArrayList<>();
        
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setTitulo("Tarea 1");
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setTitulo("Tarea 2");
        
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(tareaService.findAll()).thenReturn(tareas);

        // When & Then
        mockMvc.perform(get("/tareas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tareas"))
                .andExpect(model().attributeExists("tareas"))
                .andExpect(model().attribute("tareas", tareas));
        
        verify(usuarioService, times(1)).findAll();
        verify(tareaService, never()).findByUsuarioId(any());
        verify(tareaService, times(1)).findAll();
    }

    @Test
    public void deberiaFuncionarConListaVaciaDeTareas() throws Exception {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");
        usuario.setApellido("Apellido Test");
        usuario.setUsername("usuariotest");
        
        List<Usuario> usuarios = Arrays.asList(usuario);
        List<Tarea> tareas = new ArrayList<>();
        
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(tareaService.findByUsuarioId(1L)).thenReturn(tareas);

        // When & Then
        mockMvc.perform(get("/tareas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tareas"))
                .andExpect(model().attributeExists("tareas"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("tareas", tareas))
                .andExpect(model().attribute("usuario", usuario));
        
        verify(usuarioService, times(1)).findAll();
        verify(tareaService, times(1)).findByUsuarioId(1L);
        verify(tareaService, never()).findAll();
    }

    @Test
    public void deberiaFuncionarConListaVaciaDeUsuariosYTareas() throws Exception {
        // Given
        List<Usuario> usuarios = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();
        
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(tareaService.findAll()).thenReturn(tareas);

        // When & Then
        mockMvc.perform(get("/tareas"))
                .andExpect(status().isOk())
                .andExpect(view().name("tareas"))
                .andExpect(model().attributeExists("tareas"))
                .andExpect(model().attribute("tareas", tareas));
        
        verify(usuarioService, times(1)).findAll();
        verify(tareaService, never()).findByUsuarioId(any());
        verify(tareaService, times(1)).findAll();
    }
}