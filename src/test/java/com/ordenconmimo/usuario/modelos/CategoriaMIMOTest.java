package com.ordenconmimo.usuario.modelos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CategoriaMIMOTest {

    @Test
    void deberiaExistirValoresCorrectosEnEnum() {
        assertNotNull(CategoriaMIMO.MIRATE);
        assertNotNull(CategoriaMIMO.IMAGINA);
        assertNotNull(CategoriaMIMO.MUEVETE);
        assertNotNull(CategoriaMIMO.ORDENA);
    }
    
    @Test
    void deberiaTenerNombresCorrectos() {
        assertEquals("MIRATE", CategoriaMIMO.MIRATE.name());
        assertEquals("IMAGINA", CategoriaMIMO.IMAGINA.name());
        assertEquals("MUEVETE", CategoriaMIMO.MUEVETE.name());
        assertEquals("ORDENA", CategoriaMIMO.ORDENA.name());
    }
    
    @Test
    void deberiaPermitirComparacionDeValores() {
        assertEquals(CategoriaMIMO.MIRATE, CategoriaMIMO.MIRATE);
        assertNotEquals(CategoriaMIMO.MIRATE, CategoriaMIMO.IMAGINA);
    }
}