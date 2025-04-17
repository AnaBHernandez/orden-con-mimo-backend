package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Espacio guardarEspacio(Espacio espacio) {
        if (espacio.getFechaCreacion() == null) {
            espacio.setFechaCreacion(LocalDateTime.now());
        }
        return espacioRepository.save(espacio);
    }

    @Transactional
    public void eliminarEspacio(Long id) {
        espacioRepository.deleteById(id);
    }

    public boolean existeEspacio(Long id) {
        return espacioRepository.existsById(id);
    }

    public List<Tarea> obtenerTareasDeEspacio(Long id) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(id);
        return espacioOpt.map(Espacio::getTareas).orElse(null);
    }

    @Transactional
    public Tarea agregarTareaAEspacio(Long espacioId, Tarea tarea) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(espacioId);
        if (espacioOpt.isPresent()) {
            Espacio espacio = espacioOpt.get();
            tarea.setEspacio(espacio);
            espacio.getTareas().add(tarea);
            espacioRepository.save(espacio);
            return tarea;
        }
        return null;
    }

    @Transactional
    public void eliminarTareaDeEspacio(Long espacioId, Long tareaId) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(espacioId);
        if (espacioOpt.isPresent()) {
            Espacio espacio = espacioOpt.get();
            espacio.getTareas().removeIf(tarea -> tarea.getId().equals(tareaId));
            espacioRepository.save(espacio);
        }
    }

    public List<Espacio> obtenerEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Espacio actualizarEspacio(Long id, Espacio espacioActualizado) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(id);
        if (espacioOpt.isPresent()) {
            Espacio espacio = espacioOpt.get();
            espacio.setNombre(espacioActualizado.getNombre());
            espacio.setDescripcion(espacioActualizado.getDescripcion());
            return espacioRepository.save(espacio);
        }
        return null;
    }

    public long contarEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.countByUsuarioId(usuarioId);
    }

    public Espacio findByNombre(String nombre) {
        return espacioRepository.findByNombre(nombre);
    }
}