package com.ordenconmimo.usuario.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordenconmimo.config.PasswordEncoder;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    @Transactional
    public Usuario update(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                    usuarioExistente.setEmail(usuarioActualizado.getEmail());
                    
                    if (usuarioActualizado.getUsername() != null && !usuarioActualizado.getUsername().isEmpty()) {
                        usuarioExistente.setUsername(usuarioActualizado.getUsername());
                    }
                    
                    if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                        usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
                    }
                    
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public List<Usuario> findByKeyword(String keyword) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(keyword, keyword);
    }
    
    public long count() {
        return usuarioRepository.count();
    }
}