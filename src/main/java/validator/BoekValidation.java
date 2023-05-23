package validator;

import domein.Boek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import repo.BoekRepo;

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

        }
}
