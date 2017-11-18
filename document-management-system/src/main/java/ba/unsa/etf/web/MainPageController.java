package ba.unsa.etf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.model.Login;
import ba.unsa.etf.service.KorisnikService;
import ba.unsa.etf.validator.KorisnikFormValidator;
import ba.unsa.etf.validator.LoginFormValidator;

@Controller
public class MainPageController {
	
	private final Logger logger = LoggerFactory.getLogger(MainPageController.class);
	
	@Autowired
	LoginFormValidator loginFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(loginFormValidator);
	}
	
	private String loginAccess="false";
	
	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}
	
	@RequestMapping("/")
	public String main(Model model) {
		return "redirect:/navigation/login";
	}
	
//	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
//	public String wellcomePage(Model model) {
//		
//		model.addAttribute("loginAcces", loginAccess);
//		return "redirect:navigation/login";
//	}
		
	@RequestMapping(value ="/navigation/login", method = RequestMethod.GET)
	public String loginPageGet(Model model) {
		
		loginAccess="false";
		Login login = new Login();
		
		model.addAttribute("loginForm", login);
		return "navigation/login";
	}
	
	@RequestMapping(value = "navigation/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		
		loginAccess="false";
		model.addAttribute("loginAcces", loginAccess);
		
		return "redirect:/navigation/login";
	}
	
	
	@RequestMapping(value ="/navigation/login", method = RequestMethod.POST)
	public String loginPagePost(@ModelAttribute("loginForm")@Validated Login login, BindingResult result, Model model) {
		
		logger.debug("login() : {}", login.getKorisnickoIme() + login.getSifra());
		
		Korisnik korisnik=null;
		
		if (result.hasErrors()) {
			return "navigation/login";
		} else {
			korisnik=korisnikService.findByUsernameAndPassword(login.getKorisnickoIme(), login.getSifra());
			if(korisnik!=null && korisnik.getUloga()==1) {
				logger.debug("login IF() : {}", login.getKorisnickoIme() + login.getSifra());
				loginAccess="true";
			}
			else {
				model.addAttribute("loginError","Pogresna sifra ili korisnicko ime");
				return "navigation/login";
			}
			model.addAttribute("msg", korisnik.getKorisnickoIme());
			model.addAttribute("loginAcces", loginAccess);			
			return "navigation/loginsuccess";
		}
	}
}
