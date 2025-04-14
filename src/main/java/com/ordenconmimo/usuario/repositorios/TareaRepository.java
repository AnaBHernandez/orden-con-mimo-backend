package com.ordenconmimo.usuario.repositorios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByCategoria(CategoriaMIMO categoria);
    List<Tarea> findByEspacioId(Long espacioId);
    List<Tarea> findByUsuarioId(Long usuarioId);
    List<Tarea> findByCompletada(boolean completada);
    List<Tarea> findByCompletadaAndUsuarioId(boolean completada, Long usuarioId);
}