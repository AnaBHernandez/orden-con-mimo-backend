package com.ordenconmimo.espacio.servicios;
import com.ordenconmimo.espacio.modelos.Espacio;
import com.ordenconmimo.espacio.repositorios.EspacioRepository;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.modelos.Usuario;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final TareaRepository tareaRepository;
    
    @Autowired
    public EspacioService(EspacioRepository espacioRepository, TareaRepository tareaRepository) {
        this.espacioRepository = espacioRepository;
        this.tareaRepository = tareaRepository;
    }
    
    public List<Espacio> obtenerTodosLosEspacios() {
        return espacioRepository.findAll();
    }
    
    public Optional<Espacio> obtenerEspacioPorId(Long id) {
        return espacioRepository.findById(id);
    }
    
    public List<Espacio> obtenerEspaciosPorUsuario(Usuario usuario) {
        return espacioRepository.findByUsuario(usuario);
    }
    
    public List<Espacio> obtenerEspaciosPorUsuarioId(Long usuarioId) {
        return espacioRepository.findByUsuarioId(usuarioId);
    }
    
    public Espacio guardarEspacio(Espacio espacio) {
        return espacioRepository.save(espacio);
    }
    
    public void eliminarEspacio(Long id) {
        if (existeEspacio(id)) {
            espacioRepository.deleteById(id);
        }
    }
    
    public boolean existeEspacio(Long id) {
        return espacioRepository.existsById(id);
    }
    
    public long contarEspaciosPorUsuarioId(Long usuarioId) {
        return espacioRepository.countByUsuarioId(usuarioId);
    }
    
    public long contarEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.countByUsuarioId(usuarioId);
    }
    
    public Espacio buscarEspacioPorNombre(String nombre) {
        return espacioRepository.findByNombre(nombre);
    }
    
    public Espacio findByNombre(String nombre) {
        return espacioRepository.findByNombre(nombre);
    }
    
    public Espacio actualizarEspacio(Long id, Espacio espacio) {
        if (!existeEspacio(id)) {
            return null;
        }
        
        espacio.setId(id);
        return guardarEspacio(espacio);
    }
    
    public List<Tarea> obtenerTareasDeEspacio(long espacioId) {
        if (!espacioRepository.existsById(espacioId)) {
            return Collections.emptyList();
        }
        return tareaRepository.findByEspacioId(espacioId);
    }
    
    public Tarea agregarTareaAEspacio(long espacioId, Tarea tarea) {
        Optional<Espacio> espacioOpt = espacioRepository.findById(espacioId);
        if (espacioOpt.isEmpty()) {
            return null;
        }
        
        Espacio espacio = espacioOpt.get();
        tarea.setEspacio(espacio);
        
        return tareaRepository.save(tarea);
    }

    public List<Espacio> obtenerEspaciosPorUsuario(Long usuarioId) {
        return espacioRepository.findByUsuarioId(usuarioId);
    }
}