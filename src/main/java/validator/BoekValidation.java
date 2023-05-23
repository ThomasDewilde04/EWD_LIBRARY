package validator;

import domein.Auteur;
import domein.Boek;
import domein.Locatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import repo.BoekRepo;

import java.util.List;

public class BoekValidation implements Validator {


        @Autowired
        private BoekRepo bookRepository;
        @Override
        public boolean supports(Class<?> klass) { return Boek.class.isAssignableFrom(klass); }

        @Override
        public void validate(Object target, Errors errors) {
            Boek boek = (Boek) target;

            // titel validation
            String titel = boek.getNaam();
            if (titel.isBlank() || titel.length() == 0 || titel.isEmpty()) {
                errors.rejectValue("naam",
                        "lengthOfNaam.boek.naam",
                        "Titel mag niet leeg zijn.");
            }

            // isbn validation
            String isbn = boek.getIsbnNummer();
            if (isbn == null) {
                errors.rejectValue("isbnNummer",
                        "lengthOfIsbnNummer.boek.isbnNummer",
                        "ISBN mag niet leeg zijn.");
            }
            if (isbn.length() != 13) {
                errors.rejectValue("isbnNummer", "lengthOfIsbn.adminPage.isbn",
                        "ISBN moet 13 karakters lang zijn.");
                return;
            } else {
                int sum = 0;
                for (int i = 0; i < 12; i++) {
                    int digit = Character.getNumericValue(isbn.charAt(i));
                    sum += (i % 2 == 0) ? digit : digit * 3;
                }
                int checkDigit = (10 - (sum % 10)) % 10;
                if (checkDigit != Character.getNumericValue(isbn.charAt(12))) {
                    errors.rejectValue("isbnNummer", "invalidIsbn.adminPage.isbn", "Ongeldig ISBN!");
                }
            }
            for (Boek b : bookRepository.findAll()) {
                if (b.getIsbnNummer().equals(isbn))
                    errors.rejectValue("isbnNummer", "notUniqueISBN.adminPage.isbn", "ISBN moet uniek zijn!");
            }

            // prijs validation
            Double prijs = boek.getPrijs();
            if (prijs != null) {
                if (prijs <= 0 || prijs > 100) {
                    errors.rejectValue("prijs",
                            "lengthOfPrijs.boek.prijs",
                            "Prijs moet tussen 0 en 100 zijn.");
                }
            }

            //auteur validation
            List<Auteur> auteurs = boek.getAuteurs();
            Auteur auteur = auteurs.get(0);
            if (auteur == null || auteur.getAuteurNaam().isEmpty()) {
                errors.rejectValue("auteurs[0].auteurNaam",
                        "lengthOfAuteurs.boek.auteurs",
                        "Boek moet minstens 1 auteur hebben.");
            }

            //locatie validation
            List<Locatie> locaties = boek.getLocaties();
            Locatie locatie1 = locaties.get(0);
            if (locatie1 == null || locatie1.getPlaatscode1().isEmpty() ||
                    locatie1.getPlaatscode2().isEmpty() || locatie1.getPlaatsNaam().isEmpty()) {
                errors.rejectValue("locaties[0].plaatscode1",
                        "lengthOfLocaties.boek.locaties",
                        "Boek moet minstens 1 locatie hebben.");
            }
            for (Locatie loc : boek.getLocaties()) {
                if (loc != null && !loc.getPlaatscode1().isBlank() &&
                        !loc.getPlaatscode2().isBlank() && !loc.getPlaatsNaam().isBlank()) {
                    int locCode1 = Integer.parseInt(loc.getPlaatscode1());
                    int locCode2 = Integer.parseInt(loc.getPlaatscode2());
                    if (locCode1 < 50 || locCode1 > 300 || locCode2 < 50 || locCode2 > 300) {
                        errors.rejectValue("locaties[0].plaatscode1",
                                "invalidLocatie.boek.locaties",
                                "Plaatscodes moeten tussen 50 en 300 liggen.");
                    }
                    if (locCode1 - locCode2 < 50) {
                        errors.rejectValue("locaties[0].plaatscode1",
                                "invalidLocatie.boek.locaties",
                                "Verschil plaatscode1 & plaatscode2 moet min. 50 zijn.");
                    }
                    if (!loc.getPlaatsNaam().matches("[a-zA-Z]+")) {
                        errors.rejectValue("locaties[0].plaatscode1",
                                "invalidPlaatsNaam.boek.locaties",
                                "Plaatsnaam mag enkel letters bevatten.");
                    }
                }
            }

        }
}
