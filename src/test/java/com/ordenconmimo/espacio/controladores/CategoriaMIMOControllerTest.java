package com.ordenconmimo.espacio.controladores;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CategoriaMIMOControllerTest {

    @InjectMocks
    private final CategoriaMIMOController controller = new CategoriaMIMOController();

    @Test
public void testObtenerCategorias() {
    ResponseEntity<List<Map<String, Object>>> response = controller.obtenerCategorias();

    assertEquals(200, response.getStatusCode().value());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getStatusCode().is2xxSuccessful());

    List<Map<String, Object>> categorias = response.getBody();
    assertNotNull(categorias);
    assertEquals(4, categorias.size());

    for (Map<String, Object> categoria : categorias) {
        assertTrue(categoria.containsKey("nombre"));
        assertTrue(categoria.containsKey("displayName"));
        assertTrue(categoria.containsKey("color"));
        assertTrue(categoria.containsKey("descripcion"));
    }
}
}
