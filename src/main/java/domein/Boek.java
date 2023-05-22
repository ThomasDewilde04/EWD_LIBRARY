package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
	
	@Getter @Setter private String naam;

	@Getter @Setter private String IsbnNummer;
	@Getter @Setter private double prijs;
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

}
