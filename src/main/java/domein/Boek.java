package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class Boek implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonProperty("boek_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;

	@JsonProperty("boek_naam")
	@Getter @Setter private String naam;

	@JsonProperty("boek_isbn")
	@Getter @Setter private String isbn;

	@JsonProperty("boek_prijs")
	@Getter @Setter private double prijs;

	@JsonProperty("boek_img_url")
	@Getter @Setter private String imgUrl;

	@ManyToMany(mappedBy = "favorieten")
	private List<User> favoritedByUsers;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@Getter @Setter private List<Auteur> auteurs = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@Getter @Setter private List<Locatie> locaties = new ArrayList<>();

	public void addAuteur(Auteur auteur) {
		auteurs.add(auteur);
	}

	public void addLocatie(Locatie locatie) {
		locaties.add(locatie);
	}

	public Object auteursToString() {

		return auteurs.stream().map(auteur -> String.format("%s", auteur.getAuteurNaam()))
				.collect(Collectors.joining(", "));
	}

	public Object locatiesToString() {
		return locaties.stream().map(locatie ->
			String.format("%s %s %s", locatie.getPlaatscode1(), locatie.getPlaatscode2(), locatie.getPlaatsNaam()))
				.collect(Collectors.joining(", "));
	}

	public String prijsToString() {
		return String.format("%.2f", prijs);
	}

	public int getLikes() {
		return favoritedByUsers != null ? favoritedByUsers.size() : 0;
	}

	public void removesNulls() {
		auteurs.remove(auteurs.size() - 1);
		locaties.remove(locaties.size() - 1);
	}

}
