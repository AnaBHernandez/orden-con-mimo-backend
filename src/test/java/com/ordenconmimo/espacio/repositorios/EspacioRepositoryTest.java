package com.ordenconmimo.espacio.repositorios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Usuario;

@DataJpaTest
class EspacioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EspacioRepository espacioRepository;

    @Test
    void deberiaEncontrarEspacioPorUsuarioId() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setEmail("test@example.com");
        entityManager.persist(usuario);

        Espacio espacio1 = new Espacio();
        espacio1.setNombre("Cocina");
        espacio1.setDescripcion("Espacio para cocinar");
        espacio1.setFechaCreacion(LocalDateTime.now());
        espacio1.setUsuario(usuario);
        entityManager.persist(espacio1);

        Espacio espacio2 = new Espacio();
        espacio2.setNombre("Sala");
        espacio2.setDescripcion("Sala de estar");
        espacio2.setFechaCreacion(LocalDateTime.now());
        espacio2.setUsuario(usuario);
        entityManager.persist(espacio2);

        entityManager.flush();

        // When
        List<Espacio> espacios = espacioRepository.findByUsuarioId(usuario.getId());

        // Then
        assertEquals(2, espacios.size());
        assertTrue(espacios.stream().anyMatch(e -> e.getNombre().equals("Cocina")));
        assertTrue(espacios.stream().anyMatch(e -> e.getNombre().equals("Sala")));
    }

    @Test
    void deberiaEncontrarEspacioPorNombreYUsuarioId() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setEmail("test@example.com");
        entityManager.persist(usuario);

        Espacio espacio = new Espacio();
        espacio.setNombre("Cocina");
        espacio.setDescripcion("Espacio para cocinar");
        espacio.setFechaCreacion(LocalDateTime.now());
        espacio.setUsuario(usuario);
        entityManager.persist(espacio);

        entityManager.flush();

        // When
        Optional<Espacio> resultado = espacioRepository.findByNombreAndUsuarioId("Cocina", usuario.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Cocina", resultado.get().getNombre());
        assertEquals(usuario.getId(), resultado.get().getUsuario().getId());
    }

    @Test
    void deberiaEncontrarEspacioPorNombreConteniendoTexto() {
        // Given
        Espacio espacio1 = new Espacio();
        espacio1.setNombre("Cocina grande");
        espacio1.setDescripcion("Espacio para cocinar");
        espacio1.setFechaCreacion(LocalDateTime.now());
        entityManager.persist(espacio1);

        Espacio espacio2 = new Espacio();
        espacio2.setNombre("Mini cocina");
        espacio2.setDescripcion("Espacio pequeño para cocinar");
        espacio2.setFechaCreacion(LocalDateTime.now());
        entityManager.persist(espacio2);

        Espacio espacio3 = new Espacio();
        espacio3.setNombre("Sala");
        espacio3.setDescripcion("Sala de estar");
        espacio3.setFechaCreacion(LocalDateTime.now());
        entityManager.persist(espacio3);

        entityManager.flush();

        // When
        List<Espacio> resultados = espacioRepository.findByNombreContainingIgnoreCase("cocina");

        // Then
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().anyMatch(e -> e.getNombre().equals("Cocina grande")));
        assertTrue(resultados.stream().anyMatch(e -> e.getNombre().equals("Mini cocina")));
    }

    @Test
    void deberiaContarEspaciosPorUsuarioId() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Usuario 1");
        usuario1.setEmail("usuario1@example.com");
        entityManager.persist(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario 2");
        usuario2.setEmail("usuario2@example.com");
        entityManager.persist(usuario2);

        Espacio espacio1 = new Espacio();
        espacio1.setNombre("Cocina");
        espacio1.setUsuario(usuario1);
        entityManager.persist(espacio1);

        Espacio espacio2 = new Espacio();
        espacio2.setNombre("Sala");
        espacio2.setUsuario(usuario1);
        entityManager.persist(espacio2);

        Espacio espacio3 = new Espacio();
        espacio3.setNombre("Dormitorio");
        espacio3.setUsuario(usuario2);
        entityManager.persist(espacio3);

        entityManager.flush();

        // When
        Long countUsuario1 = espacioRepository.countByUsuarioId(usuario1.getId());
        Long countUsuario2 = espacioRepository.countByUsuarioId(usuario2.getId());

        // Then
        assertEquals(2, countUsuario1);
        assertEquals(1, countUsuario2);
    }

    @Test
    void deberiaEncontrarEspacioPorNombre() {
        // Given
        Espacio espacio = new Espacio();
        espacio.setNombre("Biblioteca");
        espacio.setDescripcion("Espacio para leer");
        espacio.setFechaCreacion(LocalDateTime.now());
        entityManager.persist(espacio);

        entityManager.flush();

        Optional<Espacio> resultado = espacioRepository.findByNombre("Biblioteca");

        assertTrue(resultado.isPresent());
        assertEquals("Biblioteca", resultado.get().getNombre());
    }
}