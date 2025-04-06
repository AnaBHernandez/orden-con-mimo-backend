package com.ordenconmimo.usuario.repositorios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByNombre(String nombre);
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    List<Tarea> findByCompletada(boolean completada);
    List<Tarea> findByEspacio(Espacio espacio);
    List<Tarea> findByUsuario(Usuario usuario);
    List<Tarea> findByCategoriaAndUsuario(CategoriaMIMO categoria, Usuario usuario);
    List<Tarea> findByDescripcionContainingIgnoreCaseAndUsuario(String texto, Usuario usuario);
    
    @Query("SELECT COUNT(t) FROM Tarea t WHERE t.categoria = :categoria")
    Long countByCategoria(@Param("categoria") CategoriaMIMO categoria);
}