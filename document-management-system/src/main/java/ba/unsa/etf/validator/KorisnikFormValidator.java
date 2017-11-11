package ba.unsa.etf.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.service.KorisnikService;

@Component
public class KorisnikFormValidator implements Validator {

	@Autowired
	KorisnikService korisnikService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Korisnik.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ime", "NotEmpty.korisnikForm.ime");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prezime", "NotEmpty.korisnikForm.prezime");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "korisnickoIme", "NotEmpty.korisnikForm.korisnickoIme");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sifra", "NotEmpty.korisnikForm.sifra");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "potvrdisifru","NotEmpty.korisnikForm.potvrdisifru");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uloga", "NotEmpty.korisnikForm.uloga");
	}
}