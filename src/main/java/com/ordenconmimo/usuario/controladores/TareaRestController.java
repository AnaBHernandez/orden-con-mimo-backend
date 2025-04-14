// Archivo: src/main/java/com/ordenconmimo/usuario/controladores/TareaRestController.java
package com.ordenconmimo.usuario.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.servicios.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> getAllTareas() {
        return tareaService.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        return tareaService.findById(id) 
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> createTarea(@RequestBody Tarea tarea) {
        if (tarea.getUsuario() == null) {
            Usuario usuarioPredeterminado = new Usuario();
            usuarioPredeterminado.setId(1L); 
            tarea.setUsuario(usuarioPredeterminado);
        }

        Tarea nuevaTarea = tareaService.save(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        return tareaService.findById(id)
                .map(tareaExistente -> {
                    tarea.setId(id);
                    return ResponseEntity.ok(tareaService.save(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        return tareaService.findById(id)
                .map(tarea -> {
                    tareaService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/toggle-completada")
    public ResponseEntity<Tarea> toggleCompletada(@PathVariable Long id) {
        return tareaService.findById(id)
                .map(tarea -> {
                    Tarea tareaActualizada = tareaService.toggleCompletada(id);
                    return ResponseEntity.ok(tareaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}