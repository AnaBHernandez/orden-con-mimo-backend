package com.ordenconmimo.usuario.repositorios;

import com.ordenconmimo.usuario.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Cambiamos findByUsername a findByEmail
    Optional<Usuario> findByEmail(String email);
}