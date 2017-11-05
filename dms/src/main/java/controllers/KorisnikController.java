package controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ba.unsa.etf.baze.tim11.dao.Korisnik;
import ba.unsa.etf.baze.tim11.dao.KorisnikJDBCTemplate;

import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/")
public class KorisnikController {
	 ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	   KorisnikJDBCTemplate korisnikJDBCTemplate = 
		         (KorisnikJDBCTemplate) context.getBean("korisnikJDBCTemplate");
	   @RequestMapping(value = "/korisnik", method = RequestMethod.GET)
	   public ModelAndView korisnik() {
	      return new ModelAndView("korisnik", "command", new Korisnik());
	   }
	   
	   @RequestMapping(value = "/addKorisnik", method = RequestMethod.POST)
	      public String addKorisnik(@ModelAttribute("korisnik")Korisnik korisnik, ModelMap model) {
		  korisnikJDBCTemplate.create(korisnik);
		  System.out.println("Poslije unosa korisnika!!!");
		  model.addAttribute("ime", korisnik.getIme());
	      model.addAttribute("prezime", korisnik.getPrezime());
	      model.addAttribute("uloga", korisnik.getUloga());
	      model.addAttribute("id", korisnik.getId());
	      return "result";
	      
	      //korisnikJDBCTemplate.create(korisnik);
	      //System.out.println("Poslije unosa korisnika!!!");
	      
	     
	   }
	   

	   
}
