package com.ordenconmimo.espacio.controladores;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.servicios.EspacioService;
import com.ordenconmimo.usuario.modelos.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/espacios")
public class EspacioRestController {

    @Autowired
    private EspacioService espacioService;

    @GetMapping
    public ResponseEntity<List<Espacio>> obtenerTodosLosEspacios() {
        return ResponseEntity.ok(espacioService.obtenerTodosLosEspacios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espacio> obtenerEspacioPorId(@PathVariable Long id) {
        Optional<Espacio> espacio = espacioService.obtenerEspacioPorId(id);
        return espacio.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Espacio> crearEspacio(@RequestBody Espacio espacio) {
        return new ResponseEntity<>(espacioService.guardarEspacio(espacio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizarEspacio(@PathVariable Long id, @RequestBody Espacio espacio) {
        if (!espacioService.existeEspacio(id)) {
            return ResponseEntity.notFound().build();
        }
        espacio.setId(id);
        return ResponseEntity.ok(espacioService.guardarEspacio(espacio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspacio(@PathVariable Long id) {
        if (!espacioService.existeEspacio(id)) {
            return ResponseEntity.notFound().build();
        }
        espacioService.eliminarEspacio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<Tarea>> obtenerTareasDeEspacio(@PathVariable Long id) {
        if (!espacioService.existeEspacio(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(espacioService.obtenerTareasDeEspacio(id));
    }

    @PostMapping("/{id}/tareas")
    public ResponseEntity<Espacio> agregarTareaAEspacio(@PathVariable Long id, @RequestBody Tarea tarea) {
        Optional<Espacio> espacioActualizado = espacioService.agregarTareaAEspacio(id, tarea);
        return espacioActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{espacioId}/tareas/{tareaId}")
    public ResponseEntity<Espacio> eliminarTareaDeEspacio(@PathVariable Long espacioId, @PathVariable Long tareaId) {
        Optional<Espacio> espacioActualizado = espacioService.eliminarTareaDeEspacio(espacioId, tareaId);
        return espacioActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}