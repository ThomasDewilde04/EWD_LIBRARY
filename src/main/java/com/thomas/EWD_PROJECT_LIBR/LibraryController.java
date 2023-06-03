package com.thomas.EWD_PROJECT_LIBR;


import domein.Auteur;
import domein.Boek;
import domein.Locatie;
import domein.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import repo.AuteurRepo;
import repo.BoekRepo;
import repo.LocatieRepo;
import repo.UserRepo;
import validator.BoekValidation;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/library")
public class LibraryController {

    private static final Logger log = LoggerFactory.getLogger(AddController.class);

    @Autowired
    private BoekRepo boekRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BoekValidation boekValidation;
    @Autowired
    private AuteurRepo auteurRepo;

    @GetMapping("/detailPage/{id}")
    public String boek(@PathVariable Integer id, Model model, Authentication authentication) {
        Boek boek = boekRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        User user = userRepo.findByUsername(authentication.getName());
        model.addAttribute("boek", boek);
        model.addAttribute("user", user);
        return "detailPage";
    }

    @GetMapping
    public String showLibrary(Model model, Authentication authentication) {
        model.addAttribute("boeken", boekRepo.findAll());
        model.addAttribute("user", authentication.getName());
        model.addAttribute("pl", new PropertyLoader());
        return "library";
    }

    @PostMapping("/{id}/favoriet")
    public String addToFavorites(@PathVariable("id") Integer id, Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        User user = userRepo.findByUsername(authentication.getName());
        Boek boek = boekRepo.findById(id).orElse(null);

        if (boek == null) {
            return "error";
        }
        String boekTitel = boek.getNaam();
        if (user.getFavorieten().contains(boek)) {
            user.removeFavoriteBook(boek);
            redirectAttributes.addFlashAttribute("confirmation",  boekTitel + " is verwijderd uit favorieten");
        }else {
            try {
                user.addFavoriteBook(boek);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            redirectAttributes.addFlashAttribute("confirmation", boekTitel + " is toegevoegd aan favorieten");
        }
        userRepo.save(user);
        return "redirect:/library";
    }

    @GetMapping("/popularBoeks")
    public String showPopularBoeks(Model model, Authentication authentication) {
        model.addAttribute("boeken", boekRepo.findMostLikedBooks());
        model.addAttribute("user", authentication.getName());
        return "popularBoeks";
    }

}
