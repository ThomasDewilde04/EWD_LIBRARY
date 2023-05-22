package validator;

import domein.Boek;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

public class BoekValidation implements Validator {

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
                // isbn?

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
