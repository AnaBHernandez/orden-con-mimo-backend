package com.ordenconmimo.espacio.modelos;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EspacioTest {

    @Test
    public void testCreacionEspacio() {
        Espacio espacio = new Espacio("Trabajo", "Tareas relacionadas con el trabajo");
        
        assertEquals("Trabajo", espacio.getNombre());
        assertEquals("Tareas relacionadas con el trabajo", espacio.getDescripcion());
        assertNotNull(espacio.getFechaCreacion());
        assertTrue(espacio.getTareas().isEmpty());
    }
    
    @Test
    public void testRelacionConTareas() {
        
        Espacio espacio = new Espacio("Hogar", "Tareas del hogar");
        
        
        Tarea tarea1 = new Tarea("Limpiar cocina", "Limpiar y ordenar la cocina", CategoriaMIMO.ORDENA);
        Tarea tarea2 = new Tarea("Comprar alimentos", "Hacer la compra semanal", CategoriaMIMO.MUEVETE);
        
        
        espacio.addTarea(tarea1);
        espacio.addTarea(tarea2);
        
        
        assertEquals(2, espacio.getTareas().size());
        assertEquals(espacio, tarea1.getEspacio());
        assertEquals(espacio, tarea2.getEspacio());
        
        espacio.removeTarea(tarea1);
        
        
        assertEquals(1, espacio.getTareas().size());
        assertNull(tarea1.getEspacio());
        assertEquals(espacio, tarea2.getEspacio());
    }
    
    @Test
public void testEqualsHashCode() {
    Espacio espacio1 = new Espacio("Espacio 1", "Descripción 1");
    Espacio espacio2 = new Espacio("Espacio 2", "Descripción 2");
    
    assertNotEquals(espacio1, espacio2);
    
    assertEquals(espacio1, espacio1);
    
    espacio1.setId(1L);
    espacio2.setId(1L);
    
    assertEquals(espacio1, espacio2);    
  
    espacio2.setId(2L);    
    
    assertNotEquals(espacio1, espacio2);    
    
    assertNotEquals(null, espacio1);
    assertNotEquals(espacio1, new Object());
}
}