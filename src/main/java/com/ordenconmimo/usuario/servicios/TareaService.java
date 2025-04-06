package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    List<Tarea> findAll();
    
    Optional<Tarea> findById(Long id);
    
    Tarea save(Tarea tarea);
    
    void deleteById(Long id);
    
    List<Tarea> findByCategoriaMIMO(CategoriaMIMO categoria);
    
    List<Tarea> findByEspacioId(Long espacioId);
    
    List<Tarea> findByUsuarioId(Long usuarioId);
    
    boolean existsByTituloAndUsuarioId(String titulo, Long usuarioId);
    
    List<Tarea> findByCompletadaAndUsuarioId(boolean completada, Long usuarioId);
    
    void toggleCompletada(Long id);
}