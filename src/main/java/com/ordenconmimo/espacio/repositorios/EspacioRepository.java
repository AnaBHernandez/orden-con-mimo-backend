package com.ordenconmimo.espacio.repositorios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    List<Espacio> findByNombre(String nombre);
    List<Espacio> findByUsuario(Usuario usuario);
    Optional<Espacio> findByNombreAndUsuario(String nombre, Usuario usuario);
    List<Espacio> findByNombreContainingIgnoreCase(String nombre);
    Long countByUsuario(Usuario usuario);
    
    @Query("SELECT COUNT(t) FROM Tarea t WHERE t.espacio.id = :espacioId")
    Long countTareasByEspacioId(@Param("espacioId") Long espacioId);

    @Query("SELECT COUNT(t) FROM Tarea t WHERE t.espacio.id = :espacioId AND t.completada = true")
    Long countCompletedTareasByEspacioId(@Param("espacioId") Long espacioId);
}