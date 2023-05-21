package domein;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Locatie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String plaatscode1;
	private String plaatscode2;
	private String plaatsNaam;


	public Locatie(String plaatscode1, String plaatscode2, String plaatsNaam) {
		this.plaatscode1 = plaatscode1;
		this.plaatscode2 = plaatscode2;
		this.plaatsNaam = plaatsNaam;
	}


}
