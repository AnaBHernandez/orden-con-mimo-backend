package com.ordenconmimo.usuario.servicios;

import com.ordenconmimo.usuario.modelos.CategoriaMIMO;
import com.ordenconmimo.usuario.modelos.Tarea;
import com.ordenconmimo.usuario.repositorios.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.ordenconmimo.usuario.servicios.TareaService;
@Service
public class TareaService {
    
    @Autowired
    private TareaRepository tareaRepository;
    
    public List<Tarea> findAll() {
        return tareaRepository.findAll();
    }
    
    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }
    
    public Tarea save(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    
    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }
    
    public List<Tarea> findByCategoriaMIMO(CategoriaMIMO categoria) {
   
        return tareaRepository.findAll().stream()
                        .filter(t -> t.getCategoria() == categoria)
                        .collect(Collectors.toList());
    }
    
    public Tarea toggleCompletada(Long id) {
        return tareaRepository.findById(id).map(tarea -> {
            tarea.setCompletada(!tarea.isCompletada());
            return tareaRepository.save(tarea);
        }).orElse(null);
    }
    
    public List<Tarea> findByEspacioId(Long espacioId) {
        return tareaRepository.findByEspacioId(espacioId);
    }
    
    public List<Tarea> findByUsuarioId(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }
    

    public List<Tarea> findByCompletadaAndUsuarioId(boolean completada, Long usuarioId) {   
        return tareaRepository.findAll().stream()
                .filter(t -> t.isCompletada() == completada && 
                             t.getUsuario() != null && 
                             t.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }
    
    
}