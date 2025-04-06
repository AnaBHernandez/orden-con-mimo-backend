package com.ordenconmimo.usuario.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/presentacion")
    public String presentacion() {
        return "presentacion";
    }

    @GetMapping("/gamma")
    public String gamma() {
        return "gamma";
    }
}