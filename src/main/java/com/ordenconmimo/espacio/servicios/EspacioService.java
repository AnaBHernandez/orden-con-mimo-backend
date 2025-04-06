package com.ordenconmimo.espacio.servicios;

import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los espacios.
 */
public interface EspacioService {
    
    /**
     * Guarda un nuevo espacio o actualiza uno existente.
     * @param espacio El espacio a guardar o actualizar
     * @return El espacio guardado con su ID generado
     */
    Espacio guardarEspacio(Espacio espacio);
    
    /**
     * Busca un espacio por su ID.
     * @param id ID del espacio a buscar
     * @return Optional con el espacio si existe
     */
    Optional<Espacio> buscarPorId(Long id);
    
    /**
     * Obtiene todos los espacios asociados a un usuario.
     * @param usuario El usuario cuyos espacios se quieren obtener
     * @return Lista de espacios del usuario
     */
    List<Espacio> obtenerEspaciosPorUsuario(Usuario usuario);
    
    /**
     * Busca un espacio por su nombre y usuario.
     * @param nombre Nombre del espacio
     * @param usuario Usuario propietario
     * @return Optional con el espacio si existe
     */
    Optional<Espacio> buscarPorNombreYUsuario(String nombre, Usuario usuario);
    
    /**
     * Busca espacios cuyo nombre contenga el texto especificado.
     * @param texto El texto a buscar
     * @return Lista de espacios que coinciden con la búsqueda
     */
    List<Espacio> buscarPorNombreParcial(String texto);
    
    /**
     * Elimina un espacio por su ID.
     * @param id ID del espacio a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean eliminarEspacio(Long id);
    
    /**
     * Añade una tarea a un espacio existente.
     * @param espacioId ID del espacio
     * @param tarea Tarea a añadir
     * @return El espacio actualizado
     * @throws IllegalArgumentException si el espacio no existe
     */
    Espacio agregarTarea(Long espacioId, Tarea tarea);
    
    /**
     * Elimina una tarea de un espacio.
     * @param espacioId ID del espacio
     * @param tareaId ID de la tarea
     * @return El espacio actualizado
     * @throws IllegalArgumentException si el espacio o la tarea no existen
     */
    Espacio eliminarTarea(Long espacioId, Long tareaId);
    
    /**
     * Obtiene el número de espacios de un usuario.
     * @param usuario El usuario
     * @return Número de espacios
     */
    Long contarEspaciosPorUsuario(Usuario usuario);
}