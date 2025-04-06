// src/main/java/com/ordenconmimo/usuario/servicios/TareaService.java
package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.espacio.modelos.Espacio;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con las tareas.
 */
public interface TareaService {
    
    /**
     * Guarda una nueva tarea o actualiza una existente.
     * @param tarea La tarea a guardar o actualizar
     * @return La tarea guardada con su ID generado
     */
    Tarea guardarTarea(Tarea tarea);
    
    /**
     * Busca una tarea por su ID.
     * @param id ID de la tarea a buscar
     * @return Optional con la tarea si existe
     */
    Optional<Tarea> buscarPorId(Long id);
    
    /**
     * Obtiene todas las tareas asociadas a un usuario.
     * @param usuario El usuario cuyas tareas se quieren obtener
     * @return Lista de tareas del usuario
     */
    List<Tarea> obtenerTareasPorUsuario(Usuario usuario);
    
    /**
     * Obtiene todas las tareas asociadas a un espacio.
     * @param espacio El espacio cuyas tareas se quieren obtener
     * @return Lista de tareas del espacio
     */
    List<Tarea> obtenerTareasPorEspacio(Espacio espacio);
    
    /**
     * Obtiene las tareas filtradas por categoría MIMO.
     * @param categoria La categoría MIMO para filtrar
     * @param usuario El usuario propietario de las tareas
     * @return Lista de tareas que corresponden a la categoría
     */
    List<Tarea> obtenerTareasPorCategoria(CategoriaMIMO categoria, Usuario usuario);
    
    /**
     * Elimina una tarea por su ID.
     * @param id ID de la tarea a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    boolean eliminarTarea(Long id);
    
    /**
     * Marca una tarea como completada.
     * @param id ID de la tarea a marcar
     * @return La tarea actualizada
     * @throws IllegalArgumentException si la tarea no existe
     */
    Tarea marcarComoCompletada(Long id);
    
    /**
     * Busca tareas cuya descripción contenga el texto especificado.
     * @param texto El texto a buscar
     * @param usuario El usuario propietario de las tareas
     * @return Lista de tareas que coinciden con la búsqueda
     */
    List<Tarea> buscarPorDescripcion(String texto, Usuario usuario);
}