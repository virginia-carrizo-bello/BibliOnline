
package com.mycompany.biblioteca.servicios;

import com.mycompany.biblioteca.entidades.Editorial;
import com.mycompany.biblioteca.excepciones.MiException;
import com.mycompany.biblioteca.repositorio.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
   @Autowired
   EditorialRepositorio editorialRepositorio;

   @Transactional
   public void crearEditorial(String nombre)throws MiException{
       
       validar(nombre);
       
       Editorial editorial = new Editorial();
       
       editorial.setNombre(nombre);
       
       editorialRepositorio.save(editorial);
   }

   public List<Editorial> listarEditoriales(){
       
       List<Editorial> editoriales = new ArrayList();
       
       editoriales = editorialRepositorio.findAll();
       
       return editoriales;
   }
   
   public void modificarEditorial(String id, String nombre) throws MiException{
       
       validar(nombre);
       
       Optional<Editorial> respuesta = editorialRepositorio.findById(id);
       
       if(respuesta.isPresent()){
           
           Editorial editorial = respuesta.get();
           
           editorial.setNombre(nombre);
           
           editorialRepositorio.save(editorial);
       }
   }
   
   public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    } 
   
    public void validar(String nombre)throws MiException{
     
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
 
    }
}
