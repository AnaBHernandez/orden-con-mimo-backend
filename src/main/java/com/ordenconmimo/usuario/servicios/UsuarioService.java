package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Optional que contiene el usuario si existe
     */
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un usuario nuevo o actualiza uno existente
     * @param usuario Usuario a guardar
     * @return Usuario guardado
     */
    @Transactional
    public Usuario save(Usuario usuario) {
        // Codificar la contraseña antes de guardar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     */
    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Optional que contiene el usuario si existe
     */
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Verifica si existe un usuario con el nombre de usuario dado
     * @param username Nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param usuarioActualizado Datos actualizados del usuario
     * @return Usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    @Transactional
    public Usuario update(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                    usuarioExistente.setEmail(usuarioActualizado.getEmail());
                    
                    // Solo actualizar el username si se proporciona uno nuevo
                    if (usuarioActualizado.getUsername() != null && !usuarioActualizado.getUsername().isEmpty()) {
                        usuarioExistente.setUsername(usuarioActualizado.getUsername());
                    }
                    
                    // Solo actualizar la contraseña si se proporciona una nueva
                    if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                        usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
                    }
                    
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    /**
     * Busca usuarios que contengan la palabra clave en su nombre o apellido
     * @param keyword Palabra clave para la búsqueda
     * @return Lista de usuarios que coinciden con la búsqueda
     */
    public List<Usuario> findByKeyword(String keyword) {
        return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(keyword, keyword);
    }

    /**
     * Cuenta el número total de usuarios
     * @return Número total de usuarios
     */
    public long count() {
        return usuarioRepository.count();
    }
}