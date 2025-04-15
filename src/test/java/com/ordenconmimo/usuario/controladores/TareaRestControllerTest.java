package com.ordenconmimo.usuario.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.servicios.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TareaRestControllerTest {

    @Mock
    private TareaService tareaService;

    @InjectMocks
    private TareaRestController tareaRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tareaRestController).build();
    }

    // Método para evitar instanciar Tarea directamente
    private String createTareaJson(Long id, String nombre, String descripcion, 
                                CategoriaMIMO categoria, boolean completada) throws Exception {
        Map<String, Object> tarea = new HashMap<>();
        if (id != null) tarea.put("id", id);
        tarea.put("nombre", nombre);
        tarea.put("descripcion", descripcion);
        tarea.put("categoria", categoria);
        tarea.put("completada", completada);
        return objectMapper.writeValueAsString(tarea);
    }

    @Test
    void deberiaListarTodasLasTareas() throws Exception {
        // Configuración para evitar usar Tarea directamente
        List<Map<String, Object>> tareas = new ArrayList<>();
        
        Map<String, Object> tarea1 = new HashMap<>();
        tarea1.put("id", 1L);
        tarea1.put("nombre", "Limpiar cocina");
        tarea1.put("descripcion", "Limpieza general");
        tarea1.put("categoria", CategoriaMIMO.ORDENA);
        tarea1.put("completada", false);
        
        Map<String, Object> tarea2 = new HashMap<>();
        tarea2.put("id", 2L);
        tarea2.put("nombre", "Hacer ejercicio");
        tarea2.put("descripcion", "30 minutos de cardio");
        tarea2.put("categoria", CategoriaMIMO.MUEVETE);
        tarea2.put("completada", false);
        
        tareas.add(tarea1);
        tareas.add(tarea2);
        
        // Mock con una técnica diferente
        doAnswer(inv -> tareas).when(tareaService).findAll();

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Limpiar cocina")))
                .andExpect(jsonPath("$[1].nombre", is("Hacer ejercicio")));
    }
    
    @Test
    void deberiaListarListaVacia() throws Exception {
        doAnswer(inv -> Collections.emptyList()).when(tareaService).findAll();

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deberiaEncontrarTareaPorId() throws Exception {
        // Preparar datos sin usar Tarea
        Map<String, Object> tarea = new HashMap<>();
        tarea.put("id", 1L);
        tarea.put("nombre", "Limpiar cocina");
        tarea.put("descripcion", "Limpieza general");
        tarea.put("categoria", CategoriaMIMO.ORDENA);
        tarea.put("completada", false);
        
        doAnswer(inv -> Optional.of(tarea)).when(tareaService).findById(1L);

        mockMvc.perform(get("/api/tareas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Limpiar cocina")));
    }

    @Test
    void deberiaRetornarNotFoundCuandoTareaNoExiste() throws Exception {
        doAnswer(inv -> Optional.empty()).when(tareaService).findById(99L);

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaSalvarTarea() throws Exception {
        // Preparar datos sin usar Tarea
        Map<String, Object> tareaGuardada = new HashMap<>();
        tareaGuardada.put("id", 1L);
        tareaGuardada.put("nombre", "Limpiar cocina");
        tareaGuardada.put("descripcion", "Limpieza general");
        tareaGuardada.put("categoria", CategoriaMIMO.ORDENA);
        tareaGuardada.put("completada", false);
        
        doAnswer(inv -> tareaGuardada).when(tareaService).save(any());

        mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTareaJson(null, "Limpiar cocina", "Limpieza general", CategoriaMIMO.ORDENA, false)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Limpiar cocina")));
    }

    @Test
    void deberiaActualizarTarea() throws Exception {
        // Preparar datos sin usar Tarea
        Map<String, Object> tareaExistente = new HashMap<>();
        tareaExistente.put("id", 1L);
        tareaExistente.put("nombre", "Limpiar cocina");
        tareaExistente.put("descripcion", "Limpieza general");
        tareaExistente.put("categoria", CategoriaMIMO.ORDENA);
        tareaExistente.put("completada", false);
        
        Map<String, Object> tareaActualizada = new HashMap<>();
        tareaActualizada.put("id", 1L);
        tareaActualizada.put("nombre", "Limpiar cocina a fondo");
        tareaActualizada.put("descripcion", "Limpieza profunda");
        tareaActualizada.put("categoria", CategoriaMIMO.ORDENA);
        tareaActualizada.put("completada", true);
        
        doAnswer(inv -> Optional.of(tareaExistente)).when(tareaService).findById(1L);
        doAnswer(inv -> tareaActualizada).when(tareaService).save(any());

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTareaJson(1L, "Limpiar cocina a fondo", "Limpieza profunda", CategoriaMIMO.ORDENA, true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Limpiar cocina a fondo")))
                .andExpect(jsonPath("$.completada", is(true)));
    }

    @Test
    void deberiaRetornarNotFoundAlActualizarTareaInexistente() throws Exception {
        doAnswer(inv -> Optional.empty()).when(tareaService).findById(99L);

        mockMvc.perform(put("/api/tareas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createTareaJson(99L, "Tarea inexistente", "No existe", null, false)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaEliminarTarea() throws Exception {
        // Preparar datos sin usar Tarea
        Map<String, Object> tarea = new HashMap<>();
        tarea.put("id", 1L);
        tarea.put("nombre", "Tarea a eliminar");
        
        doAnswer(inv -> Optional.of(tarea)).when(tareaService).findById(1L);
        doNothing().when(tareaService).deleteById(1L);

        mockMvc.perform(delete("/api/tareas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deberiaRetornarNotFoundAlEliminarTareaInexistente() throws Exception {
        doAnswer(inv -> Optional.empty()).when(tareaService).findById(99L);

        mockMvc.perform(delete("/api/tareas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaToggleTareaCompletada() throws Exception {
        // Preparar datos sin usar Tarea
        Map<String, Object> tareaOriginal = new HashMap<>();
        tareaOriginal.put("id", 1L);
        tareaOriginal.put("nombre", "Limpiar cocina");
        tareaOriginal.put("completada", false);
        
        Map<String, Object> tareaActualizada = new HashMap<>();
        tareaActualizada.put("id", 1L);
        tareaActualizada.put("nombre", "Limpiar cocina");
        tareaActualizada.put("completada", true);
        
        doAnswer(inv -> Optional.of(tareaOriginal)).when(tareaService).findById(1L);
        doAnswer(inv -> tareaActualizada).when(tareaService).toggleCompletada(1L);

        mockMvc.perform(put("/api/tareas/1/toggle-completada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completada", is(true)));
    }

    @Test
    void deberiaRetornarNotFoundAlToggleCompletadaTareaInexistente() throws Exception {
        doAnswer(inv -> Optional.empty()).when(tareaService).findById(99L);

        mockMvc.perform(put("/api/tareas/99/toggle-completada"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deberiaEncontrarTareasPorCategoria() throws Exception {
        // Preparar datos sin usar Tarea
        List<Map<String, Object>> tareas = new ArrayList<>();
        
        Map<String, Object> tarea1 = new HashMap<>();
        tarea1.put("id", 1L);
        tarea1.put("nombre", "Limpiar cocina");
        tarea1.put("categoria", CategoriaMIMO.ORDENA);
        
        Map<String, Object> tarea2 = new HashMap<>();
        tarea2.put("id", 2L);
        tarea2.put("nombre", "Organizar armario");
        tarea2.put("categoria", CategoriaMIMO.ORDENA);
        
        tareas.add(tarea1);
        tareas.add(tarea2);
        
        doAnswer(inv -> tareas).when(tareaService).findByCategoria(CategoriaMIMO.ORDENA);

        mockMvc.perform(get("/api/tareas/categoria/ORDENA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Limpiar cocina")))
                .andExpect(jsonPath("$[1].nombre", is("Organizar armario")));
    }
    
    @Test
    void deberiaRetornarListaVaciaPorCategoriaSinTareas() throws Exception {
        doAnswer(inv -> Collections.emptyList()).when(tareaService).findByCategoria(CategoriaMIMO.IMAGINA);

        mockMvc.perform(get("/api/tareas/categoria/IMAGINA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deberiaContarTareasPorCategoria() throws Exception {
        doAnswer(inv -> 5L).when(tareaService).contarPorCategoria(CategoriaMIMO.ORDENA);

        mockMvc.perform(get("/api/tareas/contar/categoria/ORDENA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }
    
    @Test
    void deberiaMarcarTareasComoCompletadasPorCategoria() throws Exception {
        doAnswer(inv -> 3).when(tareaService).marcarCompletadasPorCategoria(CategoriaMIMO.MUEVETE);

        mockMvc.perform(put("/api/tareas/completar/categoria/MUEVETE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }
}
