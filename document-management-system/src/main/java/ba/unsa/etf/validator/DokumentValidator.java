package ba.unsa.etf.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.unsa.etf.model.Dokument;
import ba.unsa.etf.service.DokumentService;

@Component
public class DokumentValidator implements Validator{	
	@Autowired
	DokumentService dokumentService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Dokument.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Dokument dokument = (Dokument) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv", "NotEmpty.dokumentForm.naziv");
		
		if(dokument.getFajl()==null){
			errors.rejectValue("fajl", "NotEmpty.dokumentForm.fajl");
		}
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fajl", "NotEmpty.dokumentForm.fajl");
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vlasnik", "NotEmpty.dokumentForm.vlasnik");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vidljivost", "NotEmpty.vlasnik.vidljivost");
	}

}
