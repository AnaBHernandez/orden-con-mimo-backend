package com.ordenconmimo.usuario.controladores;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.servicios.tareaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {

    @Autowired
    private tareaServiceImpl tareaService;

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
public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Map<String, Object> tareaData) {
    try {
        Optional<Tarea> tareaOpt = tareaService.findById(id);
        if (tareaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Tarea tarea = tareaOpt.get();
        
        if (tareaData.containsKey("nombre")) {
            tarea.setNombre((String) tareaData.get("nombre"));
        }
        
        if (tareaData.containsKey("descripcion")) {
            tarea.setDescripcion((String) tareaData.get("descripcion"));
        }
        
        if (tareaData.containsKey("categoria")) {
            tarea.setCategoria(CategoriaMIMO.valueOf((String) tareaData.get("categoria")));
        }
        
        if (tareaData.containsKey("completada")) {
            tarea.setCompletada((Boolean) tareaData.get("completada"));
        }
        
        if (tareaData.containsKey("fechaLimite")) {
            if (tareaData.get("fechaLimite") != null && !tareaData.get("fechaLimite").toString().isEmpty()) {
                String fechaStr = tareaData.get("fechaLimite").toString();
                try {
                    LocalDate fecha = LocalDate.parse(fechaStr);
                    tarea.setFechaLimite(fecha);
                    System.out.println("Fecha límite actualizada: " + fecha);
                } catch (Exception e) {
                    System.err.println("Error al parsear fecha límite: " + fechaStr);
                    e.printStackTrace();
                }
            } else {
                tarea.setFechaLimite(null);
            }
        }
        
        Tarea tareaActualizada = tareaService.save(tarea);
        return ResponseEntity.ok(tareaActualizada);
    } catch (Exception e) {
        System.err.println("Error al actualizar tarea: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
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
    public ResponseEntity<Tarea> actualizarParcialmente(@PathVariable Long id,
            @RequestBody Map<String, Object> campos) {
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