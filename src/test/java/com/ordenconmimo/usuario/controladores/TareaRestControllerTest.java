package com.ordenconmimo.usuario.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.servicios.tareaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
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
    private tareaServiceImpl tareaService;

    @InjectMocks
    private TareaRestController tareaRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tareaRestController).build();
    }

    private String createTareaJson(Long id, String nombre, String descripcion,
            CategoriaMIMO categoria, boolean completada) throws Exception {
        Map<String, Object> tarea = new HashMap<>();
        if (id != null)
            tarea.put("id", id);
        tarea.put("nombre", nombre);
        tarea.put("descripcion", descripcion);
        tarea.put("categoria", categoria);
        tarea.put("completada", completada);
        return objectMapper.writeValueAsString(tarea);
    }

    @Test
    void deberiaActualizarTareaConFechaLimite() throws Exception {

        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Limpiar cocina");
        tareaExistente.setDescripcion("Limpieza general");
        tareaExistente.setCategoria(CategoriaMIMO.ORDENA);
        tareaExistente.setCompletada(false);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Limpiar cocina a fondo");
        tareaActualizada.setDescripcion("Limpieza profunda");
        tareaActualizada.setCategoria(CategoriaMIMO.ORDENA);
        tareaActualizada.setCompletada(true);
        tareaActualizada.setFechaLimite(LocalDate.parse("2025-04-30"));

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));

        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"nombre\":\"Limpiar cocina a fondo\"," +
                "\"descripcion\":\"Limpieza profunda\"," +
                "\"categoria\":\"ORDENA\"," +
                "\"completada\":true," +
                "\"fechaLimite\":\"2025-04-30\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Limpiar cocina a fondo")))
                .andExpect(jsonPath("$.descripcion", is("Limpieza profunda")))
                .andExpect(jsonPath("$.categoria", is("ORDENA")))
                .andExpect(jsonPath("$.completada", is(true)))
                .andExpect(jsonPath("$.fechaLimite").isArray())
                .andExpect(jsonPath("$.fechaLimite[0]", is(2025)))
                .andExpect(jsonPath("$.fechaLimite[1]", is(4)))
                .andExpect(jsonPath("$.fechaLimite[2]", is(30)));
    }

    @Test
    void deberiaActualizarTareaYEliminarFechaLimite() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Limpiar cocina");
        tareaExistente.setDescripcion("Limpieza general");
        tareaExistente.setCategoria(CategoriaMIMO.ORDENA);
        tareaExistente.setCompletada(false);
        tareaExistente.setFechaLimite(LocalDate.parse("2025-04-30"));

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Limpiar cocina a fondo");
        tareaActualizada.setDescripcion("Limpieza profunda");
        tareaActualizada.setCategoria(CategoriaMIMO.ORDENA);
        tareaActualizada.setCompletada(true);
        tareaActualizada.setFechaLimite(null);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));

        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"nombre\":\"Limpiar cocina a fondo\"," +
                "\"descripcion\":\"Limpieza profunda\"," +
                "\"categoria\":\"ORDENA\"," +
                "\"completada\":true," +
                "\"fechaLimite\":null}";

        // Ejecutar solicitud y verificar
        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

    }

    @Test
    void deberiaActualizarSoloNombre() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea original");
        tareaExistente.setCategoria(CategoriaMIMO.MIRATE);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea actualizada");
        tareaActualizada.setCategoria(CategoriaMIMO.MIRATE);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"nombre\":\"Tarea actualizada\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Tarea actualizada")));
    }

    @Test
    void deberiaActualizarSoloDescripcion() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea");
        tareaExistente.setDescripcion("Descripción original");

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea");
        tareaActualizada.setDescripcion("Descripción actualizada");

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"descripcion\":\"Descripción actualizada\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion", is("Descripción actualizada")));
    }

    @Test
    void deberiaActualizarSoloCategoria() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea");
        tareaExistente.setCategoria(CategoriaMIMO.MIRATE);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea");
        tareaActualizada.setCategoria(CategoriaMIMO.MUEVETE);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"categoria\":\"MUEVETE\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria", is("MUEVETE")));
    }

    @Test
    void deberiaActualizarSoloCompletada() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea");
        tareaExistente.setCompletada(false);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea");
        tareaActualizada.setCompletada(true);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"completada\":true}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completada", is(true)));
    }

    @Test
    void deberiaFallarCuandoLaFechaNoEsValida() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea");

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));

        String requestJson = "{\"fechaLimite\":\"fecha-invalida\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deberiaRetornarErrorCuandoElServicioFalla() throws Exception {
        when(tareaService.findById(1L)).thenThrow(new RuntimeException("Error de servicio"));

        String requestJson = "{\"nombre\":\"Tarea actualizada\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deberiaActualizarCamposMultiples() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea original");
        tareaExistente.setDescripcion("Descripción original");
        tareaExistente.setCategoria(CategoriaMIMO.MIRATE);
        tareaExistente.setCompletada(false);

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea actualizada");
        tareaActualizada.setDescripcion("Descripción actualizada");
        tareaActualizada.setCategoria(CategoriaMIMO.ORDENA);
        tareaActualizada.setCompletada(true);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"nombre\":\"Tarea actualizada\"," +
                "\"descripcion\":\"Descripción actualizada\"," +
                "\"categoria\":\"ORDENA\"," +
                "\"completada\":true}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Tarea actualizada")))
                .andExpect(jsonPath("$.descripcion", is("Descripción actualizada")))
                .andExpect(jsonPath("$.categoria", is("ORDENA")))
                .andExpect(jsonPath("$.completada", is(true)));
    }

    @Test
    void deberiaActualizarFechaVacia() throws Exception {
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setNombre("Tarea");
        tareaExistente.setFechaLimite(LocalDate.parse("2025-04-30"));

        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1L);
        tareaActualizada.setNombre("Tarea");
        tareaActualizada.setFechaLimite(null);

        when(tareaService.findById(1L)).thenReturn(Optional.of(tareaExistente));
        when(tareaService.save(any(Tarea.class))).thenReturn(tareaActualizada);

        String requestJson = "{\"fechaLimite\":\"\"}";

        mockMvc.perform(put("/api/tareas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaLimite").doesNotExist());
    }
}
