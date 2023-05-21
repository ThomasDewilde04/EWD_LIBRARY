package repo;

import domein.Boek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoekRepo extends JpaRepository<Boek, Integer> {

    @Query("SELECT b FROM Boek b WHERE size(b.favoritedByUsers) > 0 ORDER BY size(b.favoritedByUsers) DESC, b.naam ASC")
    List<Boek> findMostLikedBooks();

}
