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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import repo.AuteurRepo;
import repo.BoekRepo;
import validator.BoekValidation;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/toevoegPage")
public class AddController {

    private static final Logger log = LoggerFactory.getLogger(AddController.class);


    @Autowired
    private BoekValidation boekValidation;
    @Autowired
    private AuteurRepo auteurRepo;
    @Autowired
    private BoekRepo boekRepo;


    @GetMapping
    public String toevoegPage(Model model, Authentication authentication) {
        model.addAttribute("boek", new Boek());
        model.addAttribute("user", authentication.getName());
        model.addAttribute("pl", new PropertyLoader());
        return "toevoegPage";
    }

    @PostMapping
    public String add(Boek boek, BindingResult result, Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getName());
        model.addAttribute("pl", new PropertyLoader());

        log.info("Posting new Boek with name: {}" , boek.getNaam());
        log.info("Posting new Boek with ISBN: {}", boek.getIsbn());
        log.info("Posting new Boek with price: {}", boek.getPrijs());
        log.info("Posting new Boek with ImgUrl: {}", boek.getImgUrl());
        for (Auteur a : boek.getAuteurs()) {
            log.info("Posting new Boek with author: {}", a.getAuteurNaam());
        }
        for (Locatie l : boek.getLocaties()) {
            log.info("Posting new Boek with code1: {}", l.getPlaatscode1());
            log.info("Posting new Boek with code2: {}", l.getPlaatscode2());
            log.info("Posting new Boek with plaats: {}", l.getPlaatsNaam());
        }

        boekValidation.validate(boek, result);

        if (result.hasErrors()) {
            return "toevoegPage";
        }

        //auteurs toevoegen
        List<Auteur> nieuweAuteurs = new ArrayList<>();
        for (Auteur a : boek.getAuteurs()) {
            if (a != null && a.getAuteurNaam() != null && !a.getAuteurNaam().isBlank()) {
                Auteur savedAuthor = auteurRepo.findByAuteurNaam(a.getAuteurNaam());
                if (savedAuthor == null)
                    auteurRepo.save(a);
                    nieuweAuteurs.add(a);
            }
        }
        boek.setAuteurs(nieuweAuteurs);
        //locatie toevoegen
        List<Locatie> savedLocations = new ArrayList<>();
        for (Locatie loc : boek.getLocaties()) {
            if (loc != null && !loc.getPlaatsNaam().isBlank() && !loc.getPlaatscode1().isBlank() && !loc.getPlaatscode2().isBlank()) {
                savedLocations.add(loc);
            }
        }
        boek.setLocaties(savedLocations);
        //boek toevoegen
        boekRepo.save(boek);
        log.info("Boek saved with id: {}", boek.getId());
        return "redirect:/library";
    }

}
