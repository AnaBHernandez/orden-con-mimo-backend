package com.ordenconmimo.espacio.modelos;

import com.ordenconmimo.usuario.modelos.Tarea;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Espacio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private String descripcion;
    
    private LocalDateTime fechaCreacion;
    
    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();
    
    protected Espacio() {
    }
    
    public Espacio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void addTarea(Tarea tarea) {
        tareas.add(tarea);
        tarea.setEspacio(this);
    }
    
    public void removeTarea(Tarea tarea) {
        tareas.remove(tarea);
        tarea.setEspacio(null);
    }
    
    
    public Long getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Espacio espacio = (Espacio) o;
    
    if (id != null && espacio.id != null) {
        return Objects.equals(id, espacio.id);
    }
    
    return Objects.equals(nombre, espacio.nombre) &&
        Objects.equals(descripcion, espacio.descripcion);
    }
    @Override
    public int hashCode() {
    
    if (id != null) {
        return Objects.hash(id);
    }
    return Objects.hash(nombre, descripcion);
    }
        
    @Override
    public String toString() {
        return "Espacio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", tareas=" + tareas.size() +
                '}';
    }
}