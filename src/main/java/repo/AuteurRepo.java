package repo;

import domein.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuteurRepo extends JpaRepository<Auteur, Integer> {
    Auteur findByAuteurNaam(String auteurNaam);
}
