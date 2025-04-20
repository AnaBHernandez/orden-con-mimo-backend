package com.ordenconmimo.usuario.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Estos métodos siguen la convención de nombres y no necesitan anotaciones especiales
    List<Tarea> findByEspacioId(Long espacioId);
    List<Tarea> findByUsuarioId(Long usuarioId);
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    List<Tarea> findByCompletada(boolean completada);
    List<Tarea> findByCategoriaAndCompletada(CategoriaMIMO categoria, boolean completada);
    long countByCategoria(CategoriaMIMO categoria);
    
    // Para el método problemático, usamos una query nativa que es más segura
    @Query(value = "SELECT * FROM tareas t WHERE LOWER(t.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))", nativeQuery = true)
    List<Tarea> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(@Param("texto") String texto, @Param("texto") String texto2);
    
    // También este método necesita implementación explícita
    @Query(value = "UPDATE tareas SET completada = :completada WHERE categoria_mimo = :categoria", nativeQuery = true)
    int updateCompletadaByCategoria(@Param("completada") boolean completada, @Param("categoria") String categoria);
}