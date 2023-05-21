package domein;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import lombok.Getter;
import lombok.Setter;
@NoArgsConstructor
@Getter @Setter 
@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String username;
	private String password;
	private int maxLikes;
	private Enum role;

	@ManyToMany
	@JoinTable(name = "user_favorite_books",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "boek_id"))
	@Getter public List<Boek> favorieten;

	public void addFavoriteBook(Boek boek) {
		if (favorieten.size() >= maxLikes) {
			throw new IllegalStateException("Maximum aantal likes bereikt");
		}
		favorieten.add(boek);
	}

	public int getFavoriteBooksSize() {
		return favorieten.size();
	}

	public void removeFavoriteBook(Boek boek) {
		favorieten.remove(boek);
	}

	public boolean isBookFavorited(Boek boek) {
		return favorieten.contains(boek);
	}

	public int getMax_fav() {
		return this.maxLikes;
	}

}
