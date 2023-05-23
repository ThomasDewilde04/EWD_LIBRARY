package com.thomas.EWD_PROJECT_LIBR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domein.Auteur;
import domein.Boek;
import service.AuteurService;
import service.BoekService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest")
public class BoekRestController {

    @Autowired
    private BoekService boekService;

    @Autowired
    private AuteurService auteurService;

    @GetMapping(value = "/books/auteur/{id}")
    public List<Boek> getBooks(@PathVariable("id") String id) {
        System.out.println("Getting books for author with id " + id);
        Auteur a = auteurService.findById(id);
        if (a == null) {
            System.out.println("Author with id " + id + " not found");
            return null;
        }
        return boekService.findByAuteurs(a);
    }

    @GetMapping(value = "/books/isbn/{isbn}")
    public Boek getBook(@PathVariable("isbn") String isbn) {
        System.out.println("Getting book with isbn " + isbn);
        return boekService.findByIsbn(isbn);
    }
}

