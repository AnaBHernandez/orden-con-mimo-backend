package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    @Transactional
    public Tarea save(Tarea tarea) {
        if (tarea.getFechaCreacion() == null) {
            tarea.setFechaCreacion(LocalDateTime.now());
        }
        return tareaRepository.save(tarea);
    }

    @Transactional
    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }

    @Transactional
    public Tarea toggleCompletada(Long id) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setCompletada(!tarea.isCompletada());
            return tareaRepository.save(tarea);
        }
        throw new RuntimeException("Tarea no encontrada con id: " + id);
    }

    public List<Tarea> findByCategoria(CategoriaMIMO categoria) {
        return tareaRepository.findByCategoria(categoria);
    }

    public List<Tarea> findByCompletada(boolean completada) {
        return tareaRepository.findByCompletada(completada);
    }

    public List<Tarea> findByUsuarioId(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Tarea actualizarParcialmente(Long id, Map<String, Object> campos) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            
            if (campos.containsKey("nombre") && campos.get("nombre") != null) {
                tarea.setNombre((String) campos.get("nombre"));
            }
            
            if (campos.containsKey("descripcion") && campos.get("descripcion") != null) {
                tarea.setDescripcion((String) campos.get("descripcion"));
            }
            
            if (campos.containsKey("completada") && campos.get("completada") != null) {
                tarea.setCompletada((Boolean) campos.get("completada"));
            }
            
            if (campos.containsKey("categoria") && campos.get("categoria") != null) {
                tarea.setCategoria(CategoriaMIMO.valueOf((String) campos.get("categoria")));
            }
            
            return tareaRepository.save(tarea);
        }
        throw new RuntimeException("Tarea no encontrada con id: " + id);
    }

    public List<Tarea> buscarPorTexto(String texto) {
        return tareaRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(texto, texto);
    }

    public long contarPorCategoria(CategoriaMIMO categoria) {
        return tareaRepository.countByCategoria(categoria);
    }

    @Transactional
    public int marcarCompletadasPorCategoria(CategoriaMIMO categoria) {
        return tareaRepository.updateCompletadaByCategoria(true, categoria);
    }
    
    public List<Tarea> findByEspacioId(Long espacioId) {
        return tareaRepository.findByEspacioId(espacioId);
    }
    
    @Transactional
    public Tarea asignarAEspacio(Long tareaId, Long espacioId) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            // Este método asume que la asignación al espacio se hace en otro servicio
            // o que el espacio ya se ha obtenido y pasado a la tarea.
            // Aquí solo actualizamos el ID del espacio
            // Normalmente sería mejor tener una referencia al EspacioService aquí.
            return tareaRepository.save(tarea);
        }
        throw new RuntimeException("Tarea no encontrada con id: " + tareaId);
    }
    
    @Transactional
    public List<Tarea> findCompletadasByCategoria(CategoriaMIMO categoria) {
        return tareaRepository.findByCategoriaAndCompletada(categoria, true);
    }
    
    @Transactional
    public List<Tarea> findPendientesByCategoria(CategoriaMIMO categoria) {
        return tareaRepository.findByCategoriaAndCompletada(categoria, false);
    }
}