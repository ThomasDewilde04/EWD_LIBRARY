package com.thomas.EWD_PROJECT_LIBR;

import domein.Auteur;
import domein.Boek;
import domein.Locatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repo.AuteurRepo;
import repo.BoekRepo;
import validator.BoekValidation;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/updatePage")
public class UpdateController {

    private static final Logger log = LoggerFactory.getLogger(AddController.class);


    @Autowired
    private BoekValidation boekValidation;
    @Autowired
    private AuteurRepo auteurRepo;
    @Autowired
    private BoekRepo boekRepo;

    @GetMapping("/{id}")
    public String updatePage(@PathVariable int id, Model model, Authentication authentication) {
        Boek boek = boekRepo.findById(id).get();
        model.addAttribute("boek", boek );
        model.addAttribute("user", authentication.getName());
        model.addAttribute("pl", new PropertyLoader());

        log.info("Getting new Boek with name: {}" , boek.getNaam());
        log.info("Getting new Boek with ISBN: {}", boek.getIsbn());
        log.info("Getting new Boek with price: {}", boek.getPrijs());
        log.info("Getting new Boek with ImgUrl: {}", boek.getImgUrl());
        for (Auteur a : boek.getAuteurs()) {
            log.info("Getting new Boek with author: {}", a.getAuteurNaam());
        }
        for (Locatie l : boek.getLocaties()) {
            log.info("Getting new Boek with code1: {}", l.getPlaatscode1());
            log.info("Getting new Boek with code2: {}", l.getPlaatscode2());
            log.info("Getting new Boek with plaats: {}", l.getPlaatsNaam());
        }
        return "updatePage";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, Boek boek, BindingResult result, Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getName());
        model.addAttribute("pl", new PropertyLoader());

        Boek updatedBoek = boekRepo.findById(id).get();

        log.info("Updating new Boek with name: {}" , boek.getNaam());
        log.info("Updating new Boek with ISBN: {}", boek.getIsbn());
        log.info("Updating new Boek with price: {}", boek.getPrijs());
        log.info("Updating new Boek with ImgUrl: {}", boek.getImgUrl());
        for (Auteur a : boek.getAuteurs()) {
            log.info("Updating new Boek with author: {}", a.getAuteurNaam());
        }
        for (Locatie l : boek.getLocaties()) {
            log.info("Updating new Boek with code1: {}", l.getPlaatscode1());
            log.info("Updating new Boek with code2: {}", l.getPlaatscode2());
            log.info("Updating new Boek with plaats: {}", l.getPlaatsNaam());
        }

        if (result.hasErrors()) {
            return "updatePage/" + id;
        }

        boekValidation.validate(boek, result);

        for (Auteur a : boek.getAuteurs()) {
            System.out.println(a.getAuteurNaam());
        }
        for (Locatie l : boek.getLocaties()) {
            System.out.println(l.getPlaatscode1());
            System.out.println(l.getPlaatscode2());
            System.out.println(l.getPlaatsNaam());
        }

        updatedBoek.setNaam(boek.getNaam());
        updatedBoek.setIsbn(boek.getIsbn());
        updatedBoek.setPrijs(boek.getPrijs());
        updatedBoek.setImgUrl(boek.getImgUrl());
        //auteurs toevoegen
        for (Auteur a : boek.getAuteurs()) {
            if (a != null && a.getAuteurNaam() != null && !a.getAuteurNaam().isBlank()) {
                Auteur savedAuthor = auteurRepo.findByAuteurNaam(a.getAuteurNaam());
                if (savedAuthor == null)
                    auteurRepo.save(a);
                updatedBoek.addAuteur(a);
            }
        }
        //locatie toevoegen
        for (Locatie loc : boek.getLocaties()) {
            if (loc != null && !loc.getPlaatsNaam().isBlank() && !loc.getPlaatscode1().isBlank() && !loc.getPlaatscode2().isBlank()) {
                updatedBoek.addLocatie(loc);
            }
        }
        updatedBoek.removesNulls();
        //boek updaten
        boekRepo.delete(boekRepo.findById(id).get());
        boekRepo.save(updatedBoek);
        log.info("Boek updated with id: {}", updatedBoek.getId());
        return "redirect:/library";
    }

}
