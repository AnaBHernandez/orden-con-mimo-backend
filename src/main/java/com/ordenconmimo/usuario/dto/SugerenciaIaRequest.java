package com.ordenconmimo.usuario.dto;

public class SugerenciaIaRequest {
    private String titulo;
    private String descripcion;

    public SugerenciaIaRequest() {
    }

    public SugerenciaIaRequest(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}