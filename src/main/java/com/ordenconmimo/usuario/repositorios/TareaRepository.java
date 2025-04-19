package com.ordenconmimo.usuario.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByEspacioId(long espacioId);

    List<Tarea> findByCompletada(boolean completada);

    List<Tarea> findByCategoria(CategoriaMIMO categoria);

    List<Tarea> findByUsuarioId(Long usuarioId);

    List<Tarea> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String texto, String texto2);

    long countByCategoria(CategoriaMIMO categoria);

    int updateCompletadaByCategoria(boolean b, CategoriaMIMO categoria);

    List<Tarea> findByCategoriaAndCompletada(CategoriaMIMO categoria, boolean b);
}