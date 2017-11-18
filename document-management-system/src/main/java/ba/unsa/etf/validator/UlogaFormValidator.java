package ba.unsa.etf.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.unsa.etf.model.Uloga;
import ba.unsa.etf.service.UlogaService;

@Component
public class UlogaFormValidator implements Validator{

	@Autowired
	UlogaService ulogaService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Uloga.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv", "NotEmpty.ulogaForm.naziv");
	}

}
