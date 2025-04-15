package com.ordenconmimo.espacio.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.UsuarioRepository;

@DataJpaTest
@ActiveProfiles("test")
public class EspacioRepositoryTest {

    @Autowired
    private EspacioRepository espacioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Test
    public void testSaveEspacio() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setUsername("testuser");
        usuario.setPassword("password");
        usuarioRepository.save(usuario);
        
        Espacio espacio = new Espacio();
        espacio.setNombre("Espacio de prueba");
        espacio.setDescripcion("Descripción del espacio de prueba");
        espacio.setUsuario(usuario);
        
        Espacio savedEspacio = espacioRepository.save(espacio);
        
        assertNotNull(savedEspacio.getId());
        assertEquals("Espacio de prueba", savedEspacio.getNombre());
    }
    
    @Test
    public void testFindByUsuarioId() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setUsername("testuser2");
        usuario.setPassword("password");
        Usuario savedUsuario = usuarioRepository.save(usuario);
        
        Espacio espacio = new Espacio();
        espacio.setNombre("Espacio de prueba");
        espacio.setDescripcion("Descripción del espacio de prueba");
        espacio.setUsuario(savedUsuario);
        espacioRepository.save(espacio);
        
        List<Espacio> espacios = espacioRepository.findByUsuarioId(savedUsuario.getId());
        
        assertEquals(1, espacios.size());
        assertEquals("Espacio de prueba", espacios.get(0).getNombre());
    }
    
    @Test
    public void testFindByNombre() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setUsername("testuser3");
        usuario.setPassword("password");
        usuarioRepository.save(usuario);
        
        Espacio espacio = new Espacio();
        espacio.setNombre("Espacio único");
        espacio.setDescripcion("Descripción del espacio único");
        espacio.setUsuario(usuario);
        espacioRepository.save(espacio);
        
        Espacio foundEspacio = espacioRepository.findByNombre("Espacio único");
        
        assertNotNull(foundEspacio);
        assertEquals("Espacio único", foundEspacio.getNombre());
    }
}