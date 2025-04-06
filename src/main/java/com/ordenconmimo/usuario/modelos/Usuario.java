package com.ordenconmimo.usuario.modelos;

import com.ordenconmimo.espacio.modelos.Espacio;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String email;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Espacio> espacios = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();
    
    public Usuario() {
    }
    
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Espacio> getEspacios() {
        return espacios;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }   

    
    public Espacio crearEspacio(String nombre, String descripcion) {
        Espacio nuevoEspacio = new Espacio(nombre, descripcion);
        addEspacio(nuevoEspacio);
        return nuevoEspacio;
    }
    
    private void addEspacio(Espacio espacio) {
        espacios.add(espacio);
        espacio.setUsuario(this);
    }
    
        
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}