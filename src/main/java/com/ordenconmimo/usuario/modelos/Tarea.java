package com.ordenconmimo.usuario.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;    
    private String nombre;    
    private String descripcion;    
    private LocalDateTime fechaCreacion;    
    private LocalDateTime fechaLimite;    
    private boolean completada;
    
    @Enumerated(EnumType.STRING)
    private CategoriaMIMO categoria;
    
    public Tarea() {
        this.fechaCreacion = LocalDateTime.now();
        this.completada = false;
    }
    
    public Tarea(String nombre, String descripcion, CategoriaMIMO categoria) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }
    
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
    
    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }
    
    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    
    public boolean isCompletada() {
        return completada;
    }
    
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
    
    public CategoriaMIMO getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaMIMO categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "Tarea [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + 
        ", completada=" + completada + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}