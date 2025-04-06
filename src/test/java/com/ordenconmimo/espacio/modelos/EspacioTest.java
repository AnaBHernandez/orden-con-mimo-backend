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
    
    // Con IDs nulos, comparamos solo por referencia, no por contenido
    // Así que dos objetos distintos nunca son iguales si tienen IDs nulos
    assertNotEquals(espacio1, espacio2);
    
    // Pero un objeto siempre es igual a sí mismo
    assertEquals(espacio1, espacio1);
    
    // Asignar el mismo ID a ambos espacios
    espacio1.setId(1L);
    espacio2.setId(1L);
    
    // Ahora deberían ser iguales porque tienen el mismo ID
    assertEquals(espacio1, espacio2);
    
    // Asignar IDs diferentes
    espacio2.setId(2L);
    
    // Ahora son diferentes de nuevo
    assertNotEquals(espacio1, espacio2);
    
    // Validar contra null y otros tipos
    assertNotEquals(null, espacio1);
    assertNotEquals(espacio1, new Object());
}
}