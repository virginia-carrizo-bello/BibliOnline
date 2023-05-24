
package com.mycompany.biblioteca.servicios;

import org.springframework.stereotype.Service;
import com.mycompany.biblioteca.entidades.Libro;
import com.mycompany.biblioteca.entidades.Autor;
import com.mycompany.biblioteca.entidades.Editorial;
import com.mycompany.biblioteca.excepciones.MiException;
import com.mycompany.biblioteca.repositorio.AutorRepositorio;
import com.mycompany.biblioteca.repositorio.EditorialRepositorio;
import com.mycompany.biblioteca.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Autor autor = autorRepositorio.findById(idAutor).get();
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    public List listarLibros(){
     
        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)throws MiException{
//        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        System.out.println("LIBRO ISBN " + isbn + "LIBRO TITULO " + titulo + "LIBRO EJEMPLARES " +ejemplares+ "LIBRO AUTOR " + idAutor + "LIBRO EDITORIAL " + idEditorial);
        
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Libro respuestaLibro = libroRepositorio.findByISBN(isbn);
        
        System.out.println("LIBRO ISBN " + respuestaLibro.getIsbn() + "LIBRO TITULO " + respuestaLibro.getTitulo() + "LIBRO EJEMPLARES " + respuestaLibro.getEjemplares() + "LIBRO AUTOR " + respuestaLibro.getAutor()+ "LIBRO EDITORIAL " + respuestaLibro.getEditorial());
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if(respuestaAutor.isPresent()){
            
            autor = respuestaAutor.get();
        }
        
        if(respuestaEditorial.isPresent()){
            
            editorial = respuestaEditorial.get();
        };

        if(respuestaLibro.getIsbn()!=null){
            System.out.println("libro:" + respuestaLibro);
            Libro libro = respuestaLibro;

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            System.out.println("LIBRO ISBN " + respuestaLibro.getIsbn() + "LIBRO TITULO " + respuestaLibro.getTitulo() + "LIBRO EJEMPLARES " + respuestaLibro.getEjemplares() + "LIBRO AUTOR " + respuestaLibro.getAutor()+ "LIBRO EDITORIAL " + respuestaLibro.getEditorial());
        
            libroRepositorio.save(libro);
        }

    }
    
    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        if (isbn == null){
            throw new MiException("El isbn no puede ser nulo");
        }
        if (titulo == null || titulo.isEmpty()){
            throw new MiException("El titulo no puede ser nulo o estar vacío");
        }
        if (ejemplares == null){
            throw new MiException("El ejemplar no puede ser nulo");
        }
        if (idAutor == null || idAutor.isEmpty()){
            throw new MiException("El idAutor no puede ser nulo o estar vacío");
        }
        if (idEditorial == null || idEditorial.isEmpty()){
            throw new MiException("El idEditorial no puede ser nulo o estar vacío");
        } 
    }
    
}
