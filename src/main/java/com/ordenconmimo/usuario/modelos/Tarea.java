package com.ordenconmimo.usuario.modelos;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.ordenconmimo.espacio.modelos.Espacio;

@Entity
@Table(name = "tareas")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false)
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_mimo", nullable = false)
    private CategoriaMIMO categoria;
    
    @Column(name = "completada", nullable = false)
    private boolean completada = false;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;
    
    public Tarea() {
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

    public CategoriaMIMO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaMIMO categoria) {
        this.categoria = categoria;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public void setTitulo(String titulo) {
        this.nombre = titulo; 
    }
    
    public String getTitulo() {
        return this.nombre; 
    }
}