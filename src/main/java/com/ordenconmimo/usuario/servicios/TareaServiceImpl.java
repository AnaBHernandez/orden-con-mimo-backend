package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final EspacioRepository espacioRepository;

    @Autowired
    public TareaServiceImpl(TareaRepository tareaRepository, EspacioRepository espacioRepository) {
        this.tareaRepository = tareaRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la tarea no puede ser nulo");
        }
        return tareaRepository.findById(id);
    }

    @Override
    public Tarea save(Tarea tarea) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }
        
        if (tarea.getNombre() == null || tarea.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tarea no puede estar vacío");
        }
        
        if (tarea.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría MIMO es obligatoria");
        }
        
        if (tarea.getEspacio() != null && tarea.getEspacio().getId() != null) {
            if (!espacioRepository.existsById(tarea.getEspacio().getId())) {
                throw new EntityNotFoundException("El espacio asociado no existe");
            }
        }
        
        if (tarea.getId() == null) {
            tarea.setFechaCreacion(LocalDateTime.now());
        }
        
        return tareaRepository.save(tarea);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la tarea no puede ser nulo");
        }
        
        if (!tareaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró la tarea con ID: " + id);
        }
        
        tareaRepository.deleteById(id);
    }

    @Override
    public List<Tarea> findByCategoriaMIMO(CategoriaMIMO categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría MIMO no puede ser nula");
        }
        return tareaRepository.findByCategoria(categoria);
    }

    @Override
    public List<Tarea> findByEspacioId(Long espacioId) {
        if (espacioId == null) {
            throw new IllegalArgumentException("El ID del espacio no puede ser nulo");
        }
        
        Optional<Espacio> espacio = espacioRepository.findById(espacioId);
        if (espacio.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el espacio con ID: " + espacioId);
        }
        
        return tareaRepository.findByEspacio(espacio.get());
    }

    @Override
    public List<Tarea> findByUsuarioId(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        
        throw new UnsupportedOperationException("findByUsuarioId aún no está implementado");
    }

    @Override
    public boolean existsByTituloAndUsuarioId(String titulo, Long usuarioId) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        
        throw new UnsupportedOperationException("existsByTituloAndUsuarioId aún no está implementado");
    }

    @Override
    public List<Tarea> findByCompletadaAndUsuarioId(boolean completada, Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        
        throw new UnsupportedOperationException("findByCompletadaAndUsuarioId aún no está implementado");
    }

    @Override
    public void toggleCompletada(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la tarea no puede ser nulo");
        }
        
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la tarea con ID: " + id));
        
        tarea.setCompletada(!tarea.isCompletada());
        
        tareaRepository.save(tarea);
    }
}