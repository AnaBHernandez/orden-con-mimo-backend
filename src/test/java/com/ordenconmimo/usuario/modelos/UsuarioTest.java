package com.ordenconmimo.usuario.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ordenconmimo.espacio.modelos.Espacio;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;
    private Espacio espacio;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setUsername("juanperez");
        usuario.setPassword("password");

        espacio = new Espacio();
        espacio.setId(1L);
        espacio.setNombre("Espacio de prueba");
        espacio.setDescripcion("Descripción del espacio");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("juan@example.com", usuario.getEmail());
        assertEquals("juanperez", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        
        usuario.setId(2L);
        usuario.setNombre("Pedro");
        usuario.setApellido("González");
        usuario.setEmail("pedro@example.com");
        usuario.setUsername("pedrogonzalez");
        usuario.setPassword("newpassword");
        
        assertEquals(2L, usuario.getId());
        assertEquals("Pedro", usuario.getNombre());
        assertEquals("González", usuario.getApellido());
        assertEquals("pedro@example.com", usuario.getEmail());
        assertEquals("pedrogonzalez", usuario.getUsername());
        assertEquals("newpassword", usuario.getPassword());
    }

    @Test
    void testConstructorWithArgs() {
        Usuario usuarioConArgs = new Usuario("Pedro", "González", "pedro@example.com", "pedrogonzalez", "password");
        
        assertEquals("Pedro", usuarioConArgs.getNombre());
        assertEquals("González", usuarioConArgs.getApellido());
        assertEquals("pedro@example.com", usuarioConArgs.getEmail());
        assertEquals("pedrogonzalez", usuarioConArgs.getUsername());
        assertEquals("password", usuarioConArgs.getPassword());
    }

    @Test
    void testAddEspacio() {
        assertTrue(usuario.getEspacios().isEmpty());
        
        usuario.addEspacio(espacio);
        
        assertEquals(1, usuario.getEspacios().size());
        assertTrue(usuario.getEspacios().contains(espacio));
        assertEquals(usuario, espacio.getUsuario());
    }

    @Test
    void testRemoveEspacio() {
        usuario.addEspacio(espacio);
        assertEquals(1, usuario.getEspacios().size());
        
        usuario.removeEspacio(espacio);
        
        assertTrue(usuario.getEspacios().isEmpty());
        assertNull(espacio.getUsuario());
    }

    @Test
    void testSetEspacios() {
        Set<Espacio> espacios = new HashSet<>();
        
        Espacio espacio1 = new Espacio();
        espacio1.setId(1L);
        espacio1.setNombre("Espacio 1");
        
        Espacio espacio2 = new Espacio();
        espacio2.setId(2L);
        espacio2.setNombre("Espacio 2");
        
        espacios.add(espacio1);
        espacios.add(espacio2);
        
        usuario.setEspacios(espacios);
        
        assertEquals(2, usuario.getEspacios().size());
        assertTrue(usuario.getEspacios().contains(espacio1));
        assertTrue(usuario.getEspacios().contains(espacio2));
    }

    @Test
    void testToString() {
        String expectedString = "Usuario{id=1, nombre='Juan', apellido='Pérez', email='juan@example.com', username='juanperez'}";
        assertEquals(expectedString, usuario.toString());
    }

    @Test
    void testEquals() {
        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        
        Usuario usuario3 = new Usuario();
        usuario3.setId(2L);
        
        Usuario usuario4 = new Usuario();
        usuario4.setId(null);
        
        Usuario usuario5 = new Usuario();
        usuario5.setId(null);
        
        assertEquals(usuario, usuario);
        
        assertEquals(usuario, usuario2);
        assertEquals(usuario2, usuario);
        
        assertNotEquals(usuario, usuario3);
        assertNotEquals(usuario2, usuario3);
        
        assertNotEquals(null, usuario);
        
        assertEquals(usuario4, usuario5);
        
        assertNotEquals(usuario, "String");
    }

    @Test
    void testHashCode() {
        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        
        Usuario usuario3 = new Usuario();
        usuario3.setId(2L);
        
        Usuario usuario4 = new Usuario();
        usuario4.setId(null);
        
        assertEquals(usuario.hashCode(), usuario2.hashCode());
        
        assertNotEquals(usuario.hashCode(), usuario3.hashCode());
        
        assertEquals(0, usuario4.hashCode());
    }
}