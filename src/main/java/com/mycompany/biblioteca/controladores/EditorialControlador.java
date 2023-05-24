
package com.mycompany.biblioteca.controladores;

import com.mycompany.biblioteca.excepciones.MiException;
import com.mycompany.biblioteca.servicios.EditorialServicio;
import java.util.List;
import com.mycompany.biblioteca.entidades.Editorial;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial") //localhost:8081/editorial
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar") //localhost:8081/editorial/registrar
    public String registrar(){
        
        return "editorialform.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Editorial registrada exitosamente.");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorialform.html";
        }
        return "redirect:../editorial/lista";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List <Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorialList.html";   
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("editorial", editorialServicio.getOne(id));
        
        return "editorialModificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito", "Editorial modificada exitosamente.");
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        
        return "editorialModificar.html";
    }
}
