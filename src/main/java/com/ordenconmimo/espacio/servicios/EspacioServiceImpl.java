package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspacioServiceImpl implements EspacioService {
    
    private final EspacioRepository espacioRepository;
    private final TareaRepository tareaRepository;
    
    @Autowired
    public EspacioServiceImpl(EspacioRepository espacioRepository, TareaRepository tareaRepository) {
        this.espacioRepository = espacioRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    @Transactional
    public Espacio guardarEspacio(Espacio espacio) {
        if (espacio.getNombre() == null || espacio.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del espacio no puede estar vacío");
        }
        
        if (espacio.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio para crear un espacio");
        }
        
        if (espacio.getId() == null) {
            Optional<Espacio> espacioExistente = 
                espacioRepository.findByNombreAndUsuario(espacio.getNombre(), espacio.getUsuario());
            
            if (espacioExistente.isPresent()) {
                throw new IllegalArgumentException("Ya existe un espacio con ese nombre para el usuario");
            }
        }
        
        return espacioRepository.save(espacio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Espacio> buscarPorId(Long id) {
        return espacioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Espacio> obtenerEspaciosPorUsuario(Usuario usuario) {
        return espacioRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Espacio> buscarPorNombreYUsuario(String nombre, Usuario usuario) {
        return espacioRepository.findByNombreAndUsuario(nombre, usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Espacio> buscarPorNombreParcial(String texto) {
        return espacioRepository.findByNombreContainingIgnoreCase(texto);
    }

    @Override
    @Transactional
    public boolean eliminarEspacio(Long id) {
        if (espacioRepository.existsById(id)) {
            espacioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Espacio agregarTarea(Long espacioId, Tarea tarea) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(espacioId);
        if (espacioOpt.isPresent()) {
            Espacio espacio = espacioOpt.get();
            
            if (tarea.getId() != null) {
                Optional<Tarea> tareaExistente = tareaRepository.findById(tarea.getId());
                if (tareaExistente.isPresent()) {
                    tarea = tareaExistente.get();
                }
            }
            
            if (tarea.getUsuario() == null) {
                tarea.setUsuario(espacio.getUsuario());
            } else if (!tarea.getUsuario().equals(espacio.getUsuario())) {
                throw new IllegalArgumentException("La tarea debe pertenecer al mismo usuario que el espacio");
            }
            
            espacio.addTarea(tarea);
            return espacioRepository.save(espacio);
        } else {
            throw new IllegalArgumentException("No existe un espacio con el ID proporcionado");
        }
    }

    @Override
    @Transactional
    public Espacio eliminarTarea(Long espacioId, Long tareaId) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(espacioId);
        if (!espacioOpt.isPresent()) {
            throw new IllegalArgumentException("No existe un espacio con el ID proporcionado");
        }
        
        Espacio espacio = espacioOpt.get();
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        
        if (!tareaOpt.isPresent()) {
            throw new IllegalArgumentException("No existe una tarea con el ID proporcionado");
        }
        
        Tarea tarea = tareaOpt.get();
        
        if (tarea.getEspacio() == null || !tarea.getEspacio().getId().equals(espacioId)) {
            throw new IllegalArgumentException("La tarea no pertenece al espacio especificado");
        }
        
        espacio.removeTarea(tarea);
        return espacioRepository.save(espacio);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarEspaciosPorUsuario(Usuario usuario) {
        return espacioRepository.countByUsuario(usuario);
    }
}