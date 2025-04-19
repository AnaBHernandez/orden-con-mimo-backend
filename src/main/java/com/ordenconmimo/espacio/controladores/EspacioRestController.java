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
        List<Espacio> espacios = espacioService.obtenerTodosLosEspacios();
        return new ResponseEntity<>(espacios, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Espacio> obtenerEspacioPorId(@PathVariable Long id) {
        Optional<Espacio> espacioOpt = espacioService.obtenerEspacioPorId(id);
        if (espacioOpt.isPresent()) {
            return new ResponseEntity<>(espacioOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Espacio>> obtenerEspaciosPorUsuarioId(@PathVariable Long usuarioId) {
        List<Espacio> espacios = espacioService.obtenerEspaciosPorUsuarioId(usuarioId);
        return new ResponseEntity<>(espacios, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Espacio> crearEspacio(@RequestBody Espacio espacio) {
        Espacio nuevoEspacio = espacioService.guardarEspacio(espacio);
        return new ResponseEntity<>(nuevoEspacio, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Espacio> actualizarEspacio(@PathVariable Long id, @RequestBody Espacio espacio) {
        if (!espacioService.existeEspacio(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Espacio espacioActualizado = espacioService.actualizarEspacio(id, espacio);
        return new ResponseEntity<>(espacioActualizado, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspacio(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<Tarea>> obtenerTareasDeEspacio(@PathVariable Long id) {
        List<Tarea> tareas = espacioService.obtenerTareasDeEspacio(id);
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/tareas")
    public ResponseEntity<Tarea> agregarTareaAEspacio(@PathVariable Long id, @RequestBody Tarea tarea) {
        Tarea nuevaTarea = espacioService.agregarTareaAEspacio(id, tarea);
        if (nuevaTarea != null) {
            return new ResponseEntity<>(nuevaTarea, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<Long> contarEspaciosPorUsuario(@PathVariable Long usuarioId) {
        long count = espacioService.contarEspaciosPorUsuario(usuarioId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Espacio> buscarEspacioPorNombre(@PathVariable String nombre) {
        Espacio espacio = espacioService.findByNombre(nombre);
        if (espacio != null) {
            return new ResponseEntity<>(espacio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}