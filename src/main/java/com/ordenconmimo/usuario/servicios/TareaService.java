package com.ordenconmimo.usuario.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;

public interface TareaService {
    List<Tarea> obtenerTodasLasTareas();
    Optional<Tarea> obtenerTareaPorId(Long id);
    Tarea guardarTarea(Tarea tarea);
    void eliminarTarea(Long id);
    boolean existeTarea(Long id);
    List<Tarea> findAll();
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    List<Tarea> findByCompletada(boolean completada);
    List<Tarea> findByUsuarioId(Long usuarioId);
    List<Tarea> findByEspacioId(Long espacioId);
    Optional<Tarea> actualizarParcialmente(Long id, Map<String, Object> campos);
    Optional<Tarea> findById(Long id);
    Tarea save(Tarea tarea);
    void deleteById(Long id);
    Tarea toggleCompletada(Long id);
    List<Tarea> buscarPorTexto(String texto);
    Long contarPorCategoria(CategoriaMIMO categoria);
    Integer marcarCompletadasPorCategoria(CategoriaMIMO categoria);
}