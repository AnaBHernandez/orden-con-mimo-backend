// Archivo: src/test/java/com/ordenconmimo/usuario/controladores/TareaRestControllerTest.java
package com.ordenconmimo.usuario.controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.servicios.TareaService;

@WebMvcTest(TareaRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TareaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private TareaService tareaService;
    
    @Test
    void deberiaListarTodasLasTareas() throws Exception {
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setNombre("Limpiar cocina");
        tarea1.setDescripcion("Limpieza general");
        tarea1.setCategoria(CategoriaMIMO.ORDENA);
        tarea1.setCompletada(false);
        
        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setNombre("Hacer ejercicio");
        tarea2.setDescripcion("30 minutos de cardio");
        tarea2.setCategoria(CategoriaMIMO.MUEVETE);
        tarea2.setCompletada(false);
        
        List<Tarea> tareas = Arrays.asList(tarea1, tarea2);
        when(tareaService.findAll()).thenReturn(tareas);
        
        mockMvc.perform(get("/api/tareas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nombre", is("Limpiar cocina")))
            .andExpect(jsonPath("$[1].nombre", is("Hacer ejercicio")));
    }
    
    @Test
    void deberiaEncontrarTareaPorId() throws Exception {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Limpiar cocina");
        tarea.setDescripcion("Limpieza general");
        tarea.setCategoria(CategoriaMIMO.ORDENA);
        tarea.setCompletada(false);
        
        when(tareaService.findById(1L)).thenReturn(Optional.of(tarea));
        
        mockMvc.perform(get("/api/tareas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre", is("Limpiar cocina")));
    }
    
    @Test
    void deberiaRetornarNotFoundCuandoTareaNoExiste() throws Exception {
        when(tareaService.findById(99L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/tareas/99"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void deberiaSalvarTarea() throws Exception {
        Tarea tarea = new Tarea();
        tarea.setNombre("Limpiar cocina");
        tarea.setDescripcion("Limpieza general");
        tarea.setCategoria(CategoriaMIMO.ORDENA);
        tarea.setCompletada(false);
        
        Tarea tareaGuardada = new Tarea();
        tareaGuardada.setId(1L);
        tareaGuardada.setNombre("Limpiar cocina");
        tareaGuardada.setDescripcion("Limpieza general");
        tareaGuardada.setCategoria(CategoriaMIMO.ORDENA);
        tareaGuardada.setCompletada(false);
        
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaGuardada);
        
        mockMvc.perform(post("/api/tareas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tarea)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nombre", is("Limpiar cocina")));
    }
}