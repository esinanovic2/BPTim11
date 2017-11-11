package ba.unsa.etf.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.service.KorisnikService;
import ba.unsa.etf.validator.KorisnikFormValidator;

@Controller
public class KorisnikController {
	private final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

	@Autowired
	KorisnikFormValidator korisnikFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(korisnikFormValidator);
	}

	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}

	@RequestMapping(value = "/korisnici", method = RequestMethod.GET)
	public String prikaziSveKorisnike(Model model) {

		logger.debug("showPrikaziSveKorisnike()");
		model.addAttribute("korisnici", korisnikService.findAll());
		return "korisnici/listakorisnika";
	}

	@RequestMapping(value = "/korisnici", method = RequestMethod.POST)
	public String snimiIliIzmijeniKorisnika(@ModelAttribute("korisnikForm") @Validated Korisnik korisnik,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("snimiIliIzmijeniKorisnika() : {}", korisnik);

		if (result.hasErrors()) {
			return "korisnici/korisnikform";
		} else {
			redirectAttributes.addFlashAttribute("css", "success");
			if(korisnik.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Korisnik uspjesno dodan!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Korisnik uspjesno izmjenjen!");
			}
			
			korisnikService.saveOrUpdate(korisnik);
			
			return "redirect:/korisnici/" + korisnik.getId();
		}

	}

	@RequestMapping(value = "/korisnici/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajKorisnika(Model model) {

		logger.debug("showDodajKorisnikaForm()");

		Korisnik korisnik = new Korisnik();
//
//		korisnik.setIme("Korisnik1");
//		korisnik.setPrezime("Korisnikovic");
//		korisnik.setKorisnickoIme("kkorisnik");
//		korisnik.setSifra("1234");
//		korisnik.setUloga(2);
//
		model.addAttribute("korisnikForm", korisnik);

		return "korisnici/korisnikform";

	}

	@RequestMapping(value = "/korisnici/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniKorisnika(@PathVariable("id") int id, Model model) {

		logger.debug("showPromijeniKorisnikaForm() : {}", id);

		Korisnik korisnik = korisnikService.findById(id);
		model.addAttribute("korisnikForm", korisnik);

		return "korisnici/korisnikform";
	}


	@RequestMapping(value = "/korisnici/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiKorisnika(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteKorisnik() : {}", id);

		korisnikService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Korisnik je obrisan!");
		
		return "redirect:/korisnici";
	}

	@RequestMapping(value = "/korisnici/{id}", method = RequestMethod.GET)
	public String prikaziKorisnika(@PathVariable("id") int id, Model model) {

		logger.debug("prikaziKorisnika() id: {}", id);

		Korisnik korisnik = korisnikService.findById(id);
		if (korisnik == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Korisnik nije pronadjen");
		}
		model.addAttribute("korisnik", korisnik);

		return "korisnici/prikazi";
	}
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("korisnik/prikazi");
		model.addObject("msg", "Korisnik nije pronadjen");

		return model;
	}

}