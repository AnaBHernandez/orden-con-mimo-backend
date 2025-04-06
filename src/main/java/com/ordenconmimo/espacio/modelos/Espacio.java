package com.ordenconmimo.espacio.modelos;

import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

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
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();
    
    // Constructor por defecto (necesario para JPA)
    public Espacio() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor con nombre y descripción
    public Espacio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    // Métodos helper para gestionar la relación bidireccional
    
    public void addTarea(Tarea tarea) {
        tareas.add(tarea);
        tarea.setEspacio(this);
    }
    
    public void removeTarea(Tarea tarea) {
        tareas.remove(tarea);
        tarea.setEspacio(null);
    }
    
    // Equals y hashCode basados en id
    
    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Espacio espacio = (Espacio) o;
    
    // Si ambos IDs son nulos, comparar por nombre y descripción
    if (id == null && espacio.id == null) {
        return Objects.equals(nombre, espacio.nombre) &&
               Objects.equals(descripcion, espacio.descripcion);
    }
    
    // Si solo uno de los IDs es nulo, los objetos son diferentes
    if (id == null || espacio.id == null) {
        return false;
    }
    
    // Si ambos tienen ID, comparar por ID
    return Objects.equals(id, espacio.id);
}
    @Override
    public int hashCode() {
    // Si id es nulo, usar nombre y descripción para hashCode
    return id != null ? Objects.hash(id) : Objects.hash(nombre, descripcion);
}
    
    @Override
    public String toString() {
        return "Espacio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}