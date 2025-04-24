package com.ordenconmimo.usuario.repositorios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    
    List<Tarea> findByCompletada(boolean completada);
    
    List<Tarea> findByUsuarioId(Long usuarioId);
    
    List<Tarea> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
    
    long countByCategoria(CategoriaMIMO categoria);
    
    @Modifying
    @Transactional
    @Query("UPDATE Tarea t SET t.completada = ?1 WHERE t.categoria = ?2")
    int updateCompletadaByCategoria(boolean completada, CategoriaMIMO categoria);

    List<Tarea> findByEspacioId(long espacioId);
}