package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deberiaEncontrarTodosLosUsuarios() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        usuario1.setApellido("Pérez");
        usuario1.setUsername("juanperez");
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Ana");
        usuario2.setApellido("García");
        usuario2.setUsername("anagarcia");
        
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // When
        List<Usuario> resultado = usuarioService.findAll();

        // Then
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
        verify(usuarioRepository).findAll();
    }

    @Test
    void deberiaEncontrarUsuarioPorId() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setUsername("juanperez");
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When
        Optional<Usuario> resultado = usuarioService.findById(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void noDeberiaEncontrarUsuarioPorIdInexistente() {
        // Given
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Usuario> resultado = usuarioService.findById(99L);

        // Then
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void deberiaGuardarUsuario() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setUsername("juanperez");
        usuario.setPassword("password");
        
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Juan");
        usuarioGuardado.setApellido("Pérez");
        usuarioGuardado.setUsername("juanperez");
        usuarioGuardado.setPassword("encodedPassword");
        
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // When
        Usuario resultado = usuarioService.save(usuario);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("encodedPassword", resultado.getPassword());
        verify(passwordEncoder).encode("password");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deberiaEliminarUsuario() {
        // Given
        doNothing().when(usuarioRepository).deleteById(1L);

        // When
        usuarioService.deleteById(1L);

        // Then
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deberiaEncontrarUsuarioPorUsername() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setUsername("juanperez");
        
        when(usuarioRepository.findByUsername("juanperez")).thenReturn(Optional.of(usuario));

        // When
        Optional<Usuario> resultado = usuarioService.findByUsername("juanperez");

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(usuarioRepository).findByUsername("juanperez");
    }

    @Test
    void noDeberiaEncontrarUsuarioPorUsernameInexistente() {
        // Given
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        // When
        Optional<Usuario> resultado = usuarioService.findByUsername("noexiste");

        // Then
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findByUsername("noexiste");
    }

    @Test
    void deberiaVerificarSiExisteUsuarioPorUsername() {
        // Given
        when(usuarioRepository.existsByUsername("juanperez")).thenReturn(true);

        // When
        boolean resultado = usuarioService.existsByUsername("juanperez");

        // Then
        assertTrue(resultado);
        verify(usuarioRepository).existsByUsername("juanperez");
    }

    @Test
    void deberiaVerificarSiNoExisteUsuarioPorUsername() {
        // Given
        when(usuarioRepository.existsByUsername("noexiste")).thenReturn(false);

        // When
        boolean resultado = usuarioService.existsByUsername("noexiste");

        // Then
        assertFalse(resultado);
        verify(usuarioRepository).existsByUsername("noexiste");
    }

    @Test
    void deberiaActualizarUsuarioSinCambiarPassword() {
        // Given
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNombre("Juan");
        usuarioExistente.setApellido("Pérez");
        usuarioExistente.setUsername("juanperez");
        usuarioExistente.setPassword("encodedPassword");
        
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre("Juan Carlos");
        usuarioActualizado.setApellido("Pérez");
        usuarioActualizado.setUsername("juanperez");
        usuarioActualizado.setPassword(null); // No actualiza password
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Usuario resultado = usuarioService.update(1L, usuarioActualizado);

        // Then
        assertEquals("Juan Carlos", resultado.getNombre());
        assertEquals("encodedPassword", resultado.getPassword()); // Mantiene la password original
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void deberiaActualizarUsuarioConNuevaPassword() {
        // Given
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNombre("Juan");
        usuarioExistente.setApellido("Pérez");
        usuarioExistente.setUsername("juanperez");
        usuarioExistente.setPassword("oldEncodedPassword");
        
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre("Juan Carlos");
        usuarioActualizado.setApellido("Pérez");
        usuarioActualizado.setUsername("juanperez");
        usuarioActualizado.setPassword("newPassword");
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Usuario resultado = usuarioService.update(1L, usuarioActualizado);

        // Then
        assertEquals("Juan Carlos", resultado.getNombre());
        assertEquals("newEncodedPassword", resultado.getPassword());
        verify(usuarioRepository).findById(1L);
        verify(passwordEncoder).encode("newPassword");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarUsuarioInexistente() {
        // Given
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(99L);
        usuarioActualizado.setNombre("Juan Carlos");
        
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> usuarioService.update(99L, usuarioActualizado));
        verify(usuarioRepository).findById(99L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void deberiaEncontrarUsuariosPorCoincidenciaEnNombreOApellido() {
        // Given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan García");
        usuario1.setApellido("Pérez");
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Ana");
        usuario2.setApellido("García López");
        
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        
        when(usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase("García", "García"))
            .thenReturn(usuarios);

        // When
        List<Usuario> resultado = usuarioService.findByKeyword("García");

        // Then
        assertEquals(2, resultado.size());
        verify(usuarioRepository).findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase("García", "García");
    }

    @Test
    void deberiaContarTodosLosUsuarios() {
        // Given
        when(usuarioRepository.count()).thenReturn(5L);

        // When
        long resultado = usuarioService.count();

        // Then
        assertEquals(5L, resultado);
        verify(usuarioRepository).count();
    }
}