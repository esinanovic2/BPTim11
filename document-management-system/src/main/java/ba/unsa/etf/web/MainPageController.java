package ba.unsa.etf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {
	
	@RequestMapping("/")
	public String main(Model model) {
		return "redirect:/navigation";
	}
	
	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
	public String wellcomePage(Model model) {

		return "navigation/main";
	}
	
	@RequestMapping(value ="/navigation/login")
	public String loginPage(Model model) {
		
		return "navigation/login";
	}
	
	

}
