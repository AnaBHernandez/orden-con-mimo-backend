package com.ordenconmimo.usuario.modelos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
public class TareaTest {
    @Test
    public void testCreacionTarea() {
        
        Tarea tarea = new Tarea("Limpiar escritorio", "Organizar papeles y documentos", CategoriaMIMO.ORDENA);
        
        assertEquals("Limpiar escritorio", tarea.getNombre());
        assertEquals("Organizar papeles y documentos", tarea.getDescripcion());
        assertEquals(CategoriaMIMO.ORDENA, tarea.getCategoria());
        assertEquals("Ordena", tarea.getCategoria().getDisplayName());
        assertFalse(tarea.isCompletada());
        assertNotNull(tarea.getFechaCreacion());
        assertNull(tarea.getFechaLimite());
        
        tarea.setCompletada(true);
        LocalDateTime fechaLimite = LocalDateTime.now().plusDays(1);
        tarea.setFechaLimite(fechaLimite);
        
        assertTrue(tarea.isCompletada());
        assertEquals(fechaLimite, tarea.getFechaLimite());
    }
    
    @Test
    public void testCategoriaMIMO() {
        
        assertEquals("Mírate", CategoriaMIMO.MIRATE.getDisplayName());
        assertEquals("Imagina", CategoriaMIMO.IMAGINA.getDisplayName());
        assertEquals("Muévete", CategoriaMIMO.MUEVETE.getDisplayName());
        assertEquals("Ordena", CategoriaMIMO.ORDENA.getDisplayName());
        
        assertEquals("Mírate", CategoriaMIMO.MIRATE.toString());
    }
}