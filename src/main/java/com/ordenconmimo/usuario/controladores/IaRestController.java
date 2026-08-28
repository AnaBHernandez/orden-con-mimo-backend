package com.ordenconmimo.usuario.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenconmimo.usuario.dto.SugerenciaIaRequest;
import com.ordenconmimo.usuario.dto.SugerenciaIaResponse;
import com.ordenconmimo.usuario.servicios.GeminiService;

@RestController
@RequestMapping("/api/ia")
public class IaRestController {

    private final GeminiService geminiService;

    public IaRestController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/clasificar")
    public ResponseEntity<SugerenciaIaResponse> clasificarTarea(@RequestBody SugerenciaIaRequest request) {
        if (request.getTitulo() == null || request.getTitulo().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        SugerenciaIaResponse respuesta = geminiService.sugerirCategoria(
                request.getTitulo(),
                request.getDescripcion());

        return ResponseEntity.ok(respuesta);
    }
}