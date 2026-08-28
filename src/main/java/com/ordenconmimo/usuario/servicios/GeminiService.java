package com.ordenconmimo.usuario.servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ordenconmimo.usuario.dto.SugerenciaIaResponse;
import com.ordenconmimo.usuario.modelos.CategoriaMIMO;

@Service
@SuppressWarnings("unchecked")
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public SugerenciaIaResponse sugerirCategoria(String titulo, String descripcion) {
        try {
            String urlCompleta = apiUrl + "?key=" + apiKey;

            String prompt = String.format(
                    "Analiza la siguiente tarea para un sistema de organización personal llamado MIMO.%n" +
                            "Título: '%s'%n" +
                            "Descripción: '%s'%n%n" +
                            "Clasifícala ESTRICTAMENTE en una de estas 4 categorías:%n" +
                            "- MIRATE (autoconocimiento, hábitos, reflexión)%n" +
                            "- IMAGINA (creatividad, diseño, planificación, metas)%n" +
                            "- MUEVETE (acción física directa, llamadas, compras)%n" +
                            "- ORDENA (organización del entorno, sistemas, limpieza)%n%n" +
                            "Responde en una única línea con el formato EXACTO:%n" +
                            "CATEGORIA|Explicación breve de 1 frase.",
                    titulo, (descripcion != null ? descripcion : ""));

            // Construcción del JSON esperado por Google AI Studio
            Map<String, Object> part = Map.of("text", prompt);
            Map<String, Object> content = Map.of("parts", List.of(part));
            Map<String, Object> requestBody = Map.of("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(urlCompleta, entity, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidateContent = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) candidateContent.get("parts");
                    String textoRespuesta = (String) parts.get(0).get("text");

                    return procesarRespuestaIa(textoRespuesta);
                }
            }

            return new SugerenciaIaResponse(CategoriaMIMO.ORDENA,
                    "No se pudo obtener sugerencia, asignada por defecto.");
        } catch (Exception e) {
            System.err.println("Error llamando a Gemini API: " + e.getMessage());
            return new SugerenciaIaResponse(CategoriaMIMO.ORDENA, "Error de comunicación con IA.");
        }
    }

    private SugerenciaIaResponse procesarRespuestaIa(String textoRespuesta) {
        String textoLimpio = textoRespuesta.trim();
        String categoriaStr = "ORDENA";
        String explicacion = "Sugerencia generada por el reino MIMO.";

        if (textoLimpio.contains("|")) {
            String[] partes = textoLimpio.split("\\|", 2);
            categoriaStr = partes[0].trim().toUpperCase();
            explicacion = partes[1].trim();
        } else {
            categoriaStr = textoLimpio.toUpperCase();
        }

        CategoriaMIMO categoriaFinal;
        try {
            categoriaFinal = CategoriaMIMO.valueOf(categoriaStr);
        } catch (IllegalArgumentException e) {
            categoriaFinal = CategoriaMIMO.ORDENA;
        }

        return new SugerenciaIaResponse(categoriaFinal, explicacion);
    }
}