package service;

import domein.Auteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.AuteurRepo;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepo auteurRepo;

    public Auteur findById(String id) {
        int aId = Integer.parseInt(id);
        return auteurRepo.findById(aId).orElse(null);
    }
}
