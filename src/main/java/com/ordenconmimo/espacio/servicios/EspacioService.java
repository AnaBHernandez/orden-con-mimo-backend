package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;
    
    public List<Espacio> obtenerTodosLosEspacios() {
        return espacioRepository.findAll();
    }
    
    public Optional<Espacio> obtenerEspacioPorId(Long id) {
        return espacioRepository.findById(id);
    }
    
    public Espacio guardarEspacio(Espacio espacio) {
        return espacioRepository.save(espacio);
    }
    
    public void eliminarEspacio(Long id) {
        if (espacioRepository.existsById(id)) {
            espacioRepository.deleteById(id);
        }
    }

    public boolean existeEspacio(Long id) {
        return espacioRepository.existsById(id);
    }
    
    public Optional<Espacio> actualizarEspacio(Long id, Espacio espacioActualizado) {
        return obtenerEspacioPorId(id)
                .map(espacioExistente -> {
                    espacioActualizado.setId(id);
                    if (espacioExistente.getFechaCreacion() != null) {
                        espacioActualizado.setFechaCreacion(espacioExistente.getFechaCreacion());
                    }
                    if (espacioExistente.getTareas() != null && !espacioExistente.getTareas().isEmpty()) {
                        espacioActualizado.setTareas(espacioExistente.getTareas());
                    }
                    if (espacioExistente.getUsuario() != null) {
                        espacioActualizado.setUsuario(espacioExistente.getUsuario());
                    }
                    return espacioRepository.save(espacioActualizado);
                });
    }

    public Optional<Espacio> asignarUsuarioAEspacio(Long espacioId, Usuario usuario) {
        return obtenerEspacioPorId(espacioId)
                .map(espacio -> {
                    espacio.setUsuario(usuario);
                    return espacioRepository.save(espacio);
                });
    }

    public List<Espacio> obtenerEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.findByUsuarioId(usuarioId);
    }

    public long contarEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.countByUsuarioId(usuarioId);
    }

    public Optional<Espacio> findByNombre(String nombre) {
        return espacioRepository.findByNombre(nombre);
    }
    
    public List<Tarea> obtenerTareasDeEspacio(Long espacioId) {
        return obtenerEspacioPorId(espacioId)
                .map(Espacio::getTareas)
                .orElse(Collections.emptyList());
    }

    public Optional<Espacio> agregarTareaAEspacio(Long espacioId, Tarea tarea) {
        return obtenerEspacioPorId(espacioId)
                .map(espacio -> {
                    espacio.addTarea(tarea);
                    return espacioRepository.save(espacio);
                });
    }

    public Optional<Espacio> eliminarTareaDeEspacio(Long espacioId, Long tareaId) {
        return obtenerEspacioPorId(espacioId)
                .map(espacio -> {
                    espacio.getTareas().stream()
                        .filter(tarea -> tarea.getId().equals(tareaId))
                        .findFirst()
                        .ifPresent(espacio::removeTarea);
                    return espacioRepository.save(espacio);
                });
    }
}