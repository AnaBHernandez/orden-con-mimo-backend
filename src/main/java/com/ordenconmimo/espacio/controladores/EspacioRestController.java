package com.ordenconmimo.espacio.controladores;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.servicios.EspacioService;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espacios")
public class EspacioRestController {

    @Autowired
    private EspacioService espacioService;

    @GetMapping
    public ResponseEntity<List<Espacio>> obtenerTodosLosEspacios() {
        List<Espacio> espacios = espacioService.obtenerTodosLosEspacios();
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espacio> obtenerEspacioPorId(@PathVariable Long id) {
        return espacioService.obtenerEspacioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Espacio> guardarEspacio(@RequestBody Espacio espacio) {
        Espacio espacioGuardado = espacioService.guardarEspacio(espacio);
        return ResponseEntity.status(HttpStatus.CREATED).body(espacioGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizarEspacio(@PathVariable Long id, @RequestBody Espacio espacio) {
        if (!espacioService.existeEspacio(id)) {
            return ResponseEntity.notFound().build();
        }
        espacio.setId(id);
        Espacio espacioActualizado = espacioService.guardarEspacio(espacio);
        return ResponseEntity.ok(espacioActualizado);
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
        List<Tarea> tareas = espacioService.obtenerTareasDeEspacio(id);
        return ResponseEntity.ok(tareas);
    }

    @PostMapping("/{id}/tareas")
    public ResponseEntity<Tarea> agregarTareaAEspacio(@PathVariable Long id, @RequestBody Tarea tarea) {
        if (!espacioService.existeEspacio(id)) {
            return ResponseEntity.notFound().build();
        }
        Tarea tareaAgregada = espacioService.agregarTareaAEspacio(id, tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaAgregada);
    }

    @DeleteMapping("/{espacioId}/tareas/{tareaId}")
    public ResponseEntity<Void> eliminarTareaDeEspacio(@PathVariable Long espacioId, @PathVariable Long tareaId) {
        if (!espacioService.existeEspacio(espacioId)) {
            return ResponseEntity.notFound().build();
        }
        espacioService.eliminarTareaDeEspacio(espacioId, tareaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Espacio>> obtenerEspaciosPorUsuario(@PathVariable Long usuarioId) {
        List<Espacio> espacios = espacioService.obtenerEspaciosPorUsuario(usuarioId);
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/count/usuario/{usuarioId}")
    public ResponseEntity<Long> contarEspaciosPorUsuario(@PathVariable Long usuarioId) {
        long count = espacioService.contarEspaciosPorUsuario(usuarioId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}/usuario")
    public ResponseEntity<Espacio> asignarUsuarioAEspacio(@PathVariable Long id, @RequestBody Usuario usuario) {
        return espacioService.obtenerEspacioPorId(id)
                .map(espacio -> {
                    espacio.setUsuario(usuario);
                    return ResponseEntity.ok(espacioService.guardarEspacio(espacio));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}