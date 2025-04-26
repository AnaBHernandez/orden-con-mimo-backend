package com.ordenconmimo.usuario.modelos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ordenconmimo.espacio.modelos.Espacio;
import java.time.LocalDateTime;

class UsuarioTest {

    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan", "Pérez", "juanperez", "password", "juan@example.com");
        usuario.setId(1L);
    }
    
    @Test
    void testConstructorAndGetters() {
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("juanperez", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        assertEquals("juan@example.com", usuario.getEmail());
        assertEquals(1L, usuario.getId());
        assertNotNull(usuario.getFechaCreacion());
        assertNotNull(usuario.getFechaActualizacion());
    }
    
    @Test
    void testSetters() {
        usuario.setNombre("NuevoNombre");
        usuario.setApellido("NuevoApellido");
        usuario.setUsername("nuevousername");
        usuario.setPassword("nuevapassword");
        usuario.setEmail("nuevo@example.com");
        
        assertEquals("NuevoNombre", usuario.getNombre());
        assertEquals("NuevoApellido", usuario.getApellido());
        assertEquals("nuevousername", usuario.getUsername());
        assertEquals("nuevapassword", usuario.getPassword());
        assertEquals("nuevo@example.com", usuario.getEmail());
    }
    
    @Test
    void testToString() {
        String toStringResult = usuario.toString();
        
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("Juan"));
        assertTrue(toStringResult.contains("Pérez"));
        assertTrue(toStringResult.contains("juan@example.com"));
        assertTrue(toStringResult.contains("juanperez"));
    }
    
    @Test
    void testEquals() {
        Usuario mismoUsuario = new Usuario();
        mismoUsuario.setId(1L);
        
        Usuario usuarioDiferente = new Usuario();
        usuarioDiferente.setId(2L);
        
        assertEquals(usuario, mismoUsuario);
        assertNotEquals(usuario, usuarioDiferente);
        assertNotEquals(usuario, null);
        assertNotEquals(usuario, new Object());
    }
    
    @Test
    void testHashCode() {
        Usuario mismoUsuario = new Usuario();
        mismoUsuario.setId(1L);
        
        assertEquals(usuario.hashCode(), mismoUsuario.hashCode());
    }
    
    @Test
    void testEspaciosCollection() {
        assertNotNull(usuario.getEspacios());
        assertTrue(usuario.getEspacios().isEmpty());
        
        Espacio espacio1 = new Espacio("Espacio 1", "Descripción 1");
        espacio1.setId(1L);
        usuario.addEspacio(espacio1);
        
        assertEquals(1, usuario.getEspacios().size());
        assertEquals(usuario, espacio1.getUsuario());
        
        assertEquals(espacio1, usuario.getEspacios().get(0));
        
        usuario.removeEspacio(espacio1);
        assertEquals(0, usuario.getEspacios().size());
        assertNull(espacio1.getUsuario());
    }
    
    @Test
    void testFechaActualizacionCambiaAlModificar() {
        LocalDateTime fechaOriginal = usuario.getFechaActualizacion();
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        usuario.setNombre("NombreActualizado");
        
        assertNotEquals(fechaOriginal, usuario.getFechaActualizacion());
        assertTrue(usuario.getFechaActualizacion().isAfter(fechaOriginal));
    }
    
    @Test
    void testUsuarioVacio() {
        Usuario usuarioVacio = new Usuario();
        assertNull(usuarioVacio.getNombre());
        assertNull(usuarioVacio.getApellido());
        assertNull(usuarioVacio.getUsername());
        assertNull(usuarioVacio.getPassword());
        assertNull(usuarioVacio.getEmail());
        assertNull(usuarioVacio.getId());
        assertNotNull(usuarioVacio.getFechaCreacion());
        assertNotNull(usuarioVacio.getFechaActualizacion());
        assertNotNull(usuarioVacio.getEspacios());
        assertTrue(usuarioVacio.getEspacios().isEmpty());
    }
}