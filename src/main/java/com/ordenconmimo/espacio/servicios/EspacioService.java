package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

import java.util.List;
import java.util.Optional;

public interface EspacioService {
    
    Espacio guardarEspacio(Espacio espacio);
    
    Optional<Espacio> buscarPorId(Long id);
    
    List<Espacio> obtenerEspaciosPorUsuario(Usuario usuario);
    
    Optional<Espacio> buscarPorNombreYUsuario(String nombre, Usuario usuario);
    
    List<Espacio> buscarPorNombreParcial(String texto);
    
    boolean eliminarEspacio(Long id);
    
    Espacio agregarTarea(Long espacioId, Tarea tarea);
    
    Espacio eliminarTarea(Long espacioId, Long tareaId);
    
    Long contarEspaciosPorUsuario(Usuario usuario);
}