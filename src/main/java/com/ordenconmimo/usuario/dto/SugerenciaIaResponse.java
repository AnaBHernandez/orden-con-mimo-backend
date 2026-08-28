package com.ordenconmimo.usuario.dto;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;

public class SugerenciaIaResponse {
    private CategoriaMIMO categoriaSugerida;
    private String explicacion;

    public SugerenciaIaResponse() {
    }

    public SugerenciaIaResponse(CategoriaMIMO categoriaSugerida, String explicacion) {
        this.categoriaSugerida = categoriaSugerida;
        this.explicacion = explicacion;
    }

    public CategoriaMIMO getCategoriaSugerida() {
        return categoriaSugerida;
    }

    public void setCategoriaSugerida(CategoriaMIMO categoriaSugerida) {
        this.categoriaSugerida = categoriaSugerida;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }
}