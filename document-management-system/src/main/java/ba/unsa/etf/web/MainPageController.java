package ba.unsa.etf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.model.Login;
import ba.unsa.etf.service.KorisnikService;

@Controller
public class MainPageController {
	
	private final Logger logger = LoggerFactory.getLogger(MainPageController.class);
	
	private String loginAccess="false";
	
	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}
	
	@RequestMapping("/")
	public String main(Model model) {
		return "redirect:/navigation";
	}
	
	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
	public String wellcomePage(Model model) {
		
		model.addAttribute("loginAcces", loginAccess);
		return "navigation/main";
	}
	
//	@RequestMapping(value ="/navigation/login", method = RequestMethod.GET)
//	public String loginPage(Model model) {
//		
//		loginAccess="true";
//		return "navigation/login";
//	}
	
	
	@RequestMapping(value ="/navigation/login", method = RequestMethod.GET)
	public String loginPageGet(Model model) {
		
		Login login = new Login();
		
		model.addAttribute("loginForm", login);
		return "navigation/login";
	}
	
	
	@RequestMapping(value ="/navigation/login", method = RequestMethod.POST)
	public String loginPagePost(@ModelAttribute("loginForm") Login login, Model model) {
		
		logger.debug("login() : {}", login.getKorisnickoIme() + login.getSifra());
		
		if(login.getKorisnickoIme().equals("nnovi") && login.getSifra().equals("1234")) {
			logger.debug("login IF() : {}", login.getKorisnickoIme() + login.getSifra());
			loginAccess="true";
		}
			
		
//		Login login = new Login();
//			
//		model.addAttribute("loginForm", login);
		return "navigation/main";
	}
	
	

}
