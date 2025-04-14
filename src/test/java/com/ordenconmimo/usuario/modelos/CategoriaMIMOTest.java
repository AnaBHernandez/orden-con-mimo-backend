package com.ordenconmimo.usuario.modelos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CategoriaMIMOTest {

    @Test
    void deberiaExistirValoresCorrectosEnEnum() {
        // Verificar que existen los valores esperados
        assertNotNull(CategoriaMIMO.MIRATE);
        assertNotNull(CategoriaMIMO.IMAGINA);
        assertNotNull(CategoriaMIMO.MUEVETE);
        assertNotNull(CategoriaMIMO.ORDENA);
    }
    
    @Test
    void deberiaTenerNombresCorrectos() {
        // Verificar los nombres de los enum
        assertEquals("MIRATE", CategoriaMIMO.MIRATE.name());
        assertEquals("IMAGINA", CategoriaMIMO.IMAGINA.name());
        assertEquals("MUEVETE", CategoriaMIMO.MUEVETE.name());
        assertEquals("ORDENA", CategoriaMIMO.ORDENA.name());
    }
    
    @Test
    void deberiaPermitirComparacionDeValores() {
        // Verificar comparación de valores
        assertEquals(CategoriaMIMO.MIRATE, CategoriaMIMO.MIRATE);
        assertNotEquals(CategoriaMIMO.MIRATE, CategoriaMIMO.IMAGINA);
    }
}