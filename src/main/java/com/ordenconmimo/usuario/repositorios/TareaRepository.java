package com.ordenconmimo.usuario.repositorios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    
    List<Tarea> findByCompletada(boolean completada);
    
    List<Tarea> findByUsuarioId(Long usuarioId);
    
    List<Tarea> findByEspacioId(Long espacioId);
    
    List<Tarea> findByCategoriaAndCompletada(CategoriaMIMO categoria, boolean completada);
    
    List<Tarea> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
    
    Long countByCategoria(CategoriaMIMO categoria);
    
    @Modifying
    @Query("UPDATE Tarea t SET t.completada = :completada WHERE t.categoria = :categoria")
    int updateCompletadaByCategoria(@Param("completada") boolean completada, @Param("categoria") CategoriaMIMO categoria);
}