package com.ordenconmimo.usuario.controladores;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    
    public String home() {
        return "index";
    }

    @GetMapping("/presentacion")
    public String presentacion() {
        return "presentacion";
    }
}



