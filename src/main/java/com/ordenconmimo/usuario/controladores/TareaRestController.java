package com.ordenconmimo.usuario.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.servicios.TareaServiceImpl;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {

    @Autowired
    private TareaServiceImpl tareaService;

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        return tareaService.obtenerTareaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        return new ResponseEntity<>(tareaService.guardarTarea(tarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        return tareaService.obtenerTareaPorId(id)
                .map(tareaExistente -> {
                    tarea.setId(id);
                    return ResponseEntity.ok(tareaService.guardarTarea(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        if (!tareaService.existeTarea(id)) {
            return ResponseEntity.notFound().build();
        }
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/toggle-completada")
    public ResponseEntity<Tarea> toggleCompletada(@PathVariable Long id) {
        return tareaService.obtenerTareaPorId(id)
                .map(tarea -> {
                    tarea.setCompletada(!tarea.isCompletada());
                    return ResponseEntity.ok(tareaService.guardarTarea(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorCategoria(@PathVariable String categoria) {
        try {
            CategoriaMIMO categoriaMIMO = CategoriaMIMO.valueOf(categoria);
            return ResponseEntity.ok(tareaService.findByCategoria(categoriaMIMO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/completada")
    public ResponseEntity<List<Tarea>> obtenerTareasPorCompletada(@RequestParam boolean completada) {
        return ResponseEntity.ok(tareaService.findByCompletada(completada));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tareaService.findByUsuarioId(usuarioId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTareaParcialmente(@PathVariable Long id,
            @RequestBody Map<String, Object> campos) {
        return tareaService.actualizarParcialmente(id, campos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Tarea>> buscarPorTexto(@RequestParam String texto) {
        // Implementar si es necesario
        return ResponseEntity.ok(List.of());
    }
    
    @GetMapping("/contar/categoria/{categoria}")
    public ResponseEntity<Long> contarPorCategoria(@PathVariable CategoriaMIMO categoria) {
        // Implementar si es necesario
        return ResponseEntity.ok(0L);
    }
    
    @PutMapping("/completar/categoria/{categoria}")
    public ResponseEntity<Integer> marcarCompletadasPorCategoria(@PathVariable CategoriaMIMO categoria) {
        // Implementar si es necesario
        return ResponseEntity.ok(0);
    }
}