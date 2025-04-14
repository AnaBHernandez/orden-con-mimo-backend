package com.ordenconmimo.usuario.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ordenconmimo.espacio.modelos.Espacio;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.ordenconmimo.usuario.modelos.Usuario;

@Entity
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_mimo", nullable = false)
    private CategoriaMIMO categoria;

    @Column(name = "completada", nullable = false)
    private boolean completada = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_limite")
    private java.time.LocalDate fechaLimite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({ "tareas", "espacios" })
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "espacio_id")
    @JsonIgnoreProperties({ "tareas", "usuario" })
    private Espacio espacio;

    public Tarea() {
    }

    public Tarea(String titulo, String descripcion, CategoriaMIMO categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.completada = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public java.time.LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(java.time.LocalDate fechaLimite) {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre() {
        return getTitulo();
    }

    public void setNombre(String nombre) {
        setTitulo(nombre);
    }
}