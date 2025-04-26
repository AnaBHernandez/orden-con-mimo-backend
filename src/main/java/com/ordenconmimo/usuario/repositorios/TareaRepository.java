package com.ordenconmimo.usuario.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    
    List<Tarea> findByCompletada(boolean completada);
    
    List<Tarea> findByUsuarioId(Long usuarioId);
    
    List<Tarea> findByNombreContainingOrDescripcionContaining(String nombre, String descripcion);
    
    Long countByCategoria(CategoriaMIMO categoria);
    
    List<Tarea> findByCategoriaAndCompletada(CategoriaMIMO categoria, boolean completada);
    
    List<Tarea> findByEspacioId(Long espacioId);
}