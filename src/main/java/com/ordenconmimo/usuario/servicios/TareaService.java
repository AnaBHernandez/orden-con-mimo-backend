package com.ordenconmimo.usuario.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
    
    // Métodos simulados que usan findAll() y filtrado en memoria
    
    public List<Tarea> obtenerTareasPorEspacio(Long espacioId) {
        return tareaRepository.findAll().stream()
            .filter(tarea -> tarea.getEspacio() != null && tarea.getEspacio().getId().equals(espacioId))
            .collect(Collectors.toList());
    }
    
    public List<Tarea> obtenerTareasPorUsuario(Long usuarioId) {
        return tareaRepository.findAll().stream()
            .filter(tarea -> tarea.getUsuario() != null && tarea.getUsuario().getId().equals(usuarioId))
            .collect(Collectors.toList());
    }
    
    public List<Tarea> buscarPorTexto(String texto) {
        if (texto == null) return tareaRepository.findAll();
        
        String textoLower = texto.toLowerCase();
        return tareaRepository.findAll().stream()
            .filter(tarea -> 
                (tarea.getTitulo() != null && tarea.getTitulo().toLowerCase().contains(textoLower)) ||
                (tarea.getDescripcion() != null && tarea.getDescripcion().toLowerCase().contains(textoLower))
            )
            .collect(Collectors.toList());
    }

    
}

