package com.ordenconmimo.usuario.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public Tarea save(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public Tarea toggleCompletada(Long id) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setCompletada(!tarea.isCompletada());
            return tareaRepository.save(tarea);
        }
        throw new RuntimeException("Tarea no encontrada con ID: " + id);
    }

    @Override
    public List<Tarea> findByCategoria(CategoriaMIMO categoria) {
        return tareaRepository.findByCategoria(categoria);
    }

    @Override
    public List<Tarea> findByCompletada(boolean completada) {
        return tareaRepository.findByCompletada(completada);
    }

    @Override
    public List<Tarea> findByUsuarioId(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Tarea> actualizarParcialmente(Long id, Map<String, Object> campos) {
        Optional<Tarea> tareaOptional = obtenerTareaPorId(id);

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();

            if (campos.containsKey("nombre")) {
                tarea.setNombre((String) campos.get("nombre"));
            }
            if (campos.containsKey("descripcion")) {
                tarea.setDescripcion((String) campos.get("descripcion"));
            }
            if (campos.containsKey("completada")) {
                tarea.setCompletada((Boolean) campos.get("completada"));
            }
            if (campos.containsKey("categoria")) {
                try {
                    if (campos.get("categoria") instanceof String) {
                        tarea.setCategoria(CategoriaMIMO.valueOf((String) campos.get("categoria")));
                    }
                } catch (IllegalArgumentException e) {
                }
            }

            return Optional.of(guardarTarea(tarea));
        }

        return Optional.empty();
    }

    @Override
    public List<Tarea> buscarPorTexto(String texto) {
        return tareaRepository.findByNombreContainingOrDescripcionContaining(texto, texto);
    }
    
    @Override
    public Long contarPorCategoria(CategoriaMIMO categoria) {
        return tareaRepository.countByCategoria(categoria);
    }

    @Override
    public Integer marcarCompletadasPorCategoria(CategoriaMIMO categoria) {
        List<Tarea> tareas = tareaRepository.findByCategoriaAndCompletada(categoria, false);
        tareas.forEach(tarea -> tarea.setCompletada(true));
        tareaRepository.saveAll(tareas);
        return tareas.size();
    }

    @Override
    public List<Tarea> findByEspacioId(Long espacioId) {
        return tareaRepository.findByEspacioId(espacioId);
    }

    @Override
    public boolean existeTarea(Long id) {
        return tareaRepository.existsById(id);
    }

    @Override
    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }
}
