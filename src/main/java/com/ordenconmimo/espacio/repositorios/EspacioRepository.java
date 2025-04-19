package com.ordenconmimo.espacio.repositorios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    List<Espacio> findByUsuario(Usuario usuario);
    List<Espacio> findByUsuarioId(Long usuarioId);
    long countByUsuarioId(Long usuarioId);
    Espacio findByNombre(String nombre);
}
