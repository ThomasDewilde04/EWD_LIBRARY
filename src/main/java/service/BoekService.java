package service;

import domein.Auteur;
import domein.Boek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.BoekRepo;

import java.util.List;

@Service
public class BoekService {

    @Autowired
    private BoekRepo boekRepo;

    public List<Boek> findByAuteurs(Auteur a) {
        return boekRepo.findByAuteurs(a);
    }

    public Boek findByIsbn(String isbn) {
        return boekRepo.findByIsbn(isbn);
    }
}
