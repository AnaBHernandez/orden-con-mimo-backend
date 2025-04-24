package com.ordenconmimo.usuario.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class tareaServiceImpl {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea save(Tarea tarea) {
        if (tarea.getFechaCreacion() == null) {
            tarea.setFechaCreacion(LocalDateTime.now());
        }
        return tareaRepository.save(tarea);
    }

    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }
    
    public Tarea toggleCompletada(Long id) {
        Optional<Tarea> tareaOpt = findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setCompletada(!tarea.isCompletada());
            return save(tarea);
        } else {
            throw new RuntimeException("Tarea no encontrada con id: " + id);
        }
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
    
    public Tarea actualizarParcialmente(Long id, Map<String, Object> campos) {
        Optional<Tarea> tareaOpt = findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            
            campos.forEach((key, value) -> {
                switch(key) {
                    case "nombre":
                        tarea.setNombre((String) value);
                        break;
                    case "descripcion":
                        tarea.setDescripcion((String) value);
                        break;
                    case "categoria":
                        if (value instanceof String) {
                            tarea.setCategoria(CategoriaMIMO.valueOf((String) value));
                        } else if (value instanceof CategoriaMIMO) {
                            tarea.setCategoria((CategoriaMIMO) value);
                        }
                        break;
                    case "completada":
                        tarea.setCompletada((Boolean) value);
                        break;
                    case "fechaLimite":
                        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                            tarea.setFechaLimite(null);
                        } else if (value instanceof String) {
                            tarea.setFechaLimite(LocalDate.parse((String) value));
                        } else if (value instanceof LocalDate) {
                            tarea.setFechaLimite((LocalDate) value);
                        }
                        break;
                }
            });
            
            return save(tarea);
        } else {
            throw new RuntimeException("Tarea no encontrada con id: " + id);
        }
    }
    
    public List<Tarea> buscarPorTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return findAll();
        }
        return tareaRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(texto, texto);
    }
    
    public long contarPorCategoria(CategoriaMIMO categoria) {
        return tareaRepository.countByCategoria(categoria);
    }
    
    public int marcarCompletadasPorCategoria(CategoriaMIMO categoria) {
        return tareaRepository.updateCompletadaByCategoria(true, categoria);
    }
    
    public List<Tarea> obtenerTodasLasTareas() {
        return findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return findById(id);
    }

    public Tarea guardarTarea(Tarea tarea) {
        return save(tarea);
    }

    public void eliminarTarea(Long id) {
        deleteById(id);
    }
    
    public List<Tarea> obtenerTareasPorEspacio(Long espacioId) {
        return tareaRepository.findAll().stream()
            .filter(tarea -> tarea.getEspacio() != null && ((Espacio)tarea.getEspacio()).getId().equals(espacioId))
            .collect(Collectors.toList());
    }
    
    public List<Tarea> obtenerTareasPorUsuario(Long usuarioId) {
        return findByUsuarioId(usuarioId);
    }
}