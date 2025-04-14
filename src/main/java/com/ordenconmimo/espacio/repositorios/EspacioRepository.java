package com.ordenconmimo.espacio.repositorios;
import com.ordenconmimo.espacio.modelos.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    List<Espacio> findByUsuarioId(Long usuarioId);
    Optional<Espacio> findByNombreAndUsuarioId(String nombre, Long usuarioId);
    List<Espacio> findByNombreContainingIgnoreCase(String texto);
    Long countByUsuarioId(Long usuarioId);
    Optional<Espacio> findByNombre(String nombre);
}