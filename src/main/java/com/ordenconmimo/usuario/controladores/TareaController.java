package com.ordenconmimo.usuario.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ordenconmimo.usuario.servicios.TareaService;
import com.ordenconmimo.usuario.servicios.UsuarioService;

@Controller
@RequestMapping("/tareas")
public class TareaController {
    
    @Autowired
    private TareaService tareaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listarTareas(Model model) {
        // En esta versión simplificada, mostramos todas las tareas
        // o las del primer usuario si existe
        var usuarios = usuarioService.findAll();
        if (!usuarios.isEmpty()) {
            model.addAttribute("tareas", tareaService.findByUsuarioId(usuarios.get(0).getId()));
            model.addAttribute("usuario", usuarios.get(0));
        } else {
            model.addAttribute("tareas", tareaService.findAll());
        }
        return "tareas"; // Nombre de la plantilla Thymeleaf
    }
}