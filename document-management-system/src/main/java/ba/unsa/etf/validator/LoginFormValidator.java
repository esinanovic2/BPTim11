package ba.unsa.etf.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.unsa.etf.model.Login;

@Component
public class LoginFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "korisnickoIme", "NotEmpty.loginForm.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sifra", "NotEmpty.loginForm.password");
	}

}
