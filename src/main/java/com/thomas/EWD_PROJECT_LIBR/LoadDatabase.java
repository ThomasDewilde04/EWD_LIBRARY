package com.thomas.EWD_PROJECT_LIBR;

import domein.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import repo.AuteurRepo;
import repo.BoekRepo;
import repo.LocatieRepo;
import repo.UserRepo;

@Component
@RequiredArgsConstructor
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(BoekRepo boekRepo, UserRepo userRepo, AuteurRepo auteurRepo, LocatieRepo locatieRepo) {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(12)));
        admin.setMaxLikes(2);
        admin.setRole(Roles.ADMIN);

        User user = new User();
        user.setUsername("user");
        user.setPassword(BCrypt.hashpw("user", BCrypt.gensalt(12)));
        user.setMaxLikes(3);
        user.setRole(Roles.USER);

        Auteur auteur1 = new Auteur("John Flanagan");
        Auteur auteur2 = new Auteur("Elisabetta Dami");
        Auteur auteur3 = new Auteur("Annie M.G. Schmidt");
        Auteur auteur4 = new Auteur("Fiep Westendorp");
        Auteur auteur5 = new Auteur("Jeff Kinney");

        Boek boek1 = new Boek();
        boek1.setNaam("De Grijze Jager");
        boek1.addAuteur(auteur1);
        boek1.setIsbn("9789025742843");
        boek1.setPrijs(12.99);
        Locatie locatie1 = new Locatie("50", "150", "A");
        boek1.addLocatie(locatie1);
        boek1.setImgUrl("https://media.s-bol.com/RKV3m0EmJLnR/3VPRqR/790x1200.jpg");

        Boek boek2 = new Boek();
        boek2.setNaam("Geronimo Stilton");
        boek2.addAuteur(auteur2);
        boek2.setIsbn("9788408191858");
        boek2.setPrijs(8.99);
        Locatie locatie2 = new Locatie("150", "50", "B");
        Locatie locatie6 = new Locatie("120", "10", "G");
        boek2.addLocatie(locatie2);
        boek2.addLocatie(locatie6);
        boek2.setImgUrl("https://media.s-bol.com/9L3M4VKZQQMz/Jq78zNJ/902x1200.jpg");

        Boek boek3 = new Boek();
        boek3.setNaam("Pluk van de Petteflet");
        boek3.addAuteur(auteur3);
        boek3.addAuteur(auteur4);
        boek3.setIsbn("9789045110950");
        boek3.setPrijs(20.99);
        Locatie locatie3 = new Locatie("20", "80", "C");
        boek3.addLocatie(locatie3);
        boek3.setImgUrl("https://media.s-bol.com/NK6Z8NDNnNZ8/O6z96R/897x1200.jpg");

        Boek boek4 = new Boek();
        boek4.setNaam("Het leven van een loser");
        boek4.addAuteur(auteur5);
        boek4.setIsbn("9789026125690");
        boek4.setPrijs(17.99);
        Locatie locatie4 = new Locatie("80", "20", "D");
        boek4.addLocatie(locatie4);
        boek4.setImgUrl("https://media.s-bol.com/R889QOg2xQgV/P1vGB76/550x816.jpg");

        Boek boek5 = new Boek();
        boek5.setNaam("The Hunger Games 1");
        boek5.addAuteur(new Auteur("Suzanne Collins"));
        boek5.setIsbn("9782266260770");
        boek5.setPrijs(8.29);
        Locatie locatie5 = new Locatie("30", "70", "S");
        boek5.addLocatie(locatie5);
        boek5.setImgUrl("https://media.s-bol.com/R2L4RR6oowY/733x1200.jpg");

        return args -> {
            log.info("Preloading " + userRepo.save(admin));
            log.info("Preloading " + userRepo.save(user));

            log.info("Preloading " + boekRepo.save(boek1));
            log.info("Preloading " + boekRepo.save(boek2));
            log.info("Preloading " + boekRepo.save(boek3));
            log.info("Preloading " + boekRepo.save(boek4));
            log.info("Preloading " + boekRepo.save(boek5));
        };
    }
}
