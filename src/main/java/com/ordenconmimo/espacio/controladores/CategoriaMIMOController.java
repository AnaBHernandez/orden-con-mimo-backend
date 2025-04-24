package com.ordenconmimo.espacio.controladores;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;

@RestController
@RequestMapping("/api")
public class CategoriaMIMOController {
    
    @GetMapping("/categorias")
    public ResponseEntity<List<Map<String, Object>>> obtenerCategorias() {
        List<Map<String, Object>> categorias = Arrays.stream(CategoriaMIMO.values())
                .map(categoria -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", categoria.name());
                    map.put("displayName", categoria.getDisplayName());
                    map.put("color", obtenerColorPorCategoria(categoria));
                    map.put("descripcion", obtenerDescripcionPorCategoria(categoria));
                    return map;
                })
                .toList();
        
        return ResponseEntity.ok(categorias);
    }
    
    private String obtenerColorPorCategoria(CategoriaMIMO categoria) {
        return switch (categoria) {
            case MIRATE -> "#614385";  // Púrpura
            case IMAGINA -> "#F1C40F"; // Dorado
            case MUEVETE -> "#E67E22"; // Naranja
            case ORDENA -> "#27AE60";  // Verde
        };
    }
    
    private String obtenerDescripcionPorCategoria(CategoriaMIMO categoria) {
        return switch (categoria) {
            case MIRATE -> "Autorreflexión y conocimiento personal";
            case IMAGINA -> "Visualización de metas y proyectos futuros";
            case MUEVETE -> "Acciones y tareas para lograr objetivos";
            case ORDENA -> "Organización y gestión de recursos";
        };
    }
}