package repo;

import domein.Locatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatieRepo extends JpaRepository<Locatie, Integer> {
}
