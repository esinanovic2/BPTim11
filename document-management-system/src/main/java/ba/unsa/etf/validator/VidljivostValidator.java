package ba.unsa.etf.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.unsa.etf.model.Vidljivost;
import ba.unsa.etf.service.VidljivostService;

@Component
public class VidljivostValidator implements Validator{
	
	@Autowired
	VidljivostService vidljivostService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Vidljivost.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv", "NotEmpty.vidljivostForm.naziv");	
	}

}
