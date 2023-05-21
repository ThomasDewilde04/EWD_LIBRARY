package com.thomas.EWD_PROJECT_LIBR;

import domein.Auteur;
import domein.Boek;
import domein.Locatie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repo.AuteurRepo;
import repo.BoekRepo;
import repo.LocatieRepo;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/toevoegPage")
public class AddController {

    @Autowired
    private AuteurRepo auteurRepo;
    @Autowired
    private BoekRepo boekRepo;
    @Autowired
    private LocatieRepo locatieRepo;


    @GetMapping
    public String toevoegPage(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getName());
        return "toevoegPage";
    }

    @PostMapping
    public String add(Boek boek) {

        System.out.println("------------------");
        System.out.println(boek.getNaam());
        System.out.println(boek.getIsbnNummer());
        System.out.println(boek.getPrijs());
        System.out.println(boek.getImgUrl());
        System.out.println(boek.auteursToString());
        System.out.println(boek.locatiesToString());
        System.out.println("------------------");

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
        return "redirect:/library";
    }

}
