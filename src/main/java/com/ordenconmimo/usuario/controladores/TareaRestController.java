package com.ordenconmimo.usuario.controladores;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.servicios.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        return tareaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        return new ResponseEntity<>(tareaService.save(tarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        return tareaService.findById(id)
                .map(tareaExistente -> {
                    tarea.setId(id);
                    return ResponseEntity.ok(tareaService.save(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        return tareaService.findById(id)
                .map(tarea -> {
                    tareaService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/toggle-completada")
    public ResponseEntity<Tarea> toggleCompletada(@PathVariable Long id) {
        return tareaService.findById(id)
                .map(tarea -> ResponseEntity.ok(tareaService.toggleCompletada(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorCategoria(@PathVariable CategoriaMIMO categoria) {
        return ResponseEntity.ok(tareaService.findByCategoria(categoria));
    }

    @GetMapping("/completadas/{completada}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorEstadoCompletado(@PathVariable boolean completada) {
        return ResponseEntity.ok(tareaService.findByCompletada(completada));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tareaService.findByUsuarioId(usuarioId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> actualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        try {
            return ResponseEntity.ok(tareaService.actualizarParcialmente(id, campos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Tarea>> buscarPorTexto(@RequestParam String texto) {
        return ResponseEntity.ok(tareaService.buscarPorTexto(texto));
    }
    
    @GetMapping("/contar/categoria/{categoria}")
    public ResponseEntity<Long> contarPorCategoria(@PathVariable CategoriaMIMO categoria) {
        return ResponseEntity.ok(tareaService.contarPorCategoria(categoria));
    }
    
    @PutMapping("/completar/categoria/{categoria}")
    public ResponseEntity<Integer> marcarCompletadasPorCategoria(@PathVariable CategoriaMIMO categoria) {
        return ResponseEntity.ok(tareaService.marcarCompletadasPorCategoria(categoria));
    }
}