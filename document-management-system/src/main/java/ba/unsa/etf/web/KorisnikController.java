package ba.unsa.etf.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.model.Uloga;
import ba.unsa.etf.service.KorisnikService;
import ba.unsa.etf.service.UlogaService;
import ba.unsa.etf.validator.KorisnikFormValidator;

@Controller
public class KorisnikController {
	private final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

	String loggedRole = "0";

	
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
	
	private UlogaService ulogaService;
	@Autowired
	public void setUlogaService (UlogaService ulogaService){
		this.ulogaService=ulogaService;
	}

	/*@RequestMapping("/korisniciandroid")
	public ResponseEntity<List<Korisnik>> sviKorisnici(){
		List<Korisnik> korisnici = new ArrayList<>();
		korisnici = korisnikService.findAll();
		return new ResponseEntity<List<Korisnik>>(korisnici,HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/korisnici", method = RequestMethod.GET)
	public String prikaziSveKorisnike(Model model, HttpSession session) {
		logger.debug("prikaziSveKorisnike(): " +String.valueOf(session.getAttribute("roleid")));
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		
		if(loggedRole.equals("3")){
			logger.debug("ELSE" + "korisnici/"+ String.valueOf(session.getAttribute("userid")) +"/promijeni");

			String redirect = "redirect:/korisnici/"+ String.valueOf(session.getAttribute("userid")) +"/promijeni";
			return redirect;
		}
		else if(!loggedRole.equals("0")){
			
			List<Korisnik> sviKorisnici = korisnikService.findAll();
			
			if(loggedRole.equals("4")){
				logger.debug("Studentska(): " +String.valueOf(session.getAttribute("roleid")));
			
				sviKorisnici = korisnikService.findUsersWithRole(Integer.valueOf(4));
				List<Korisnik> studenti = korisnikService.findUsersWithRole(Integer.valueOf(3));
				sviKorisnici.addAll(studenti);
			}
		
			List<Uloga> sveUloge = new ArrayList<>();
			for(int i = 0; i< sviKorisnici.size(); i++) {
				sveUloge.add(ulogaService.findById(sviKorisnici.get(i).getUloga()));
			}
		
			logger.debug("showPrikaziSveKorisnike()");
			model.addAttribute("korisnici", sviKorisnici);
			
			
			model.addAttribute("loggedRole", loggedRole);
		
			model.addAttribute("uloge", sveUloge);
			return "korisnici/listakorisnika";
		}
			
		return null;	
		
	}

	@RequestMapping(value = "/korisnici", method = RequestMethod.POST)
	public String snimiIliIzmijeniKorisnika(@ModelAttribute("korisnikForm") @Validated Korisnik korisnik, BindingResult result, 
			@RequestParam("uloga") Integer uloga,  Model model, final RedirectAttributes redirectAttributes, HttpSession session) {
		
		if(Integer.valueOf(String.valueOf(session.getAttribute("userid"))) == 3){
			logger.debug("ULOGAAA():" );
		}
		logger.debug("snimiIliIzmijeniKorisnika():" + korisnik.getId() + "dddd "+ uloga);
		
		if(korisnik.isNew() && korisnikService.findByUsername(korisnik.getKorisnickoIme()) != null) {
			result.rejectValue("korisnickoIme", "AlreadyExists.korisnikForm.exists", "Korisnik sa tim korisnickim imenom vec postoji!");
		}
		
		if(!korisnik.getSifra().equals(korisnik.getPotvrdisifru())) {
			result.rejectValue("potvrdisifru", "NotEmpty.korisnikForm.notsame", "Sifra i ponovi sifru moraju biti iste!");
		}
	
		if (result.hasErrors()) {
			List<Uloga> sveUloge = ulogaService.findAll();
			model.addAttribute("korisnikForm", korisnik);
			model.addAttribute("uloge", sveUloge);
			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
			return "korisnici/korisnikform";
		} else {
			
			redirectAttributes.addFlashAttribute("css", "success");
			if(korisnik.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Korisnik uspjesno dodan!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Korisnik uspjesno izmjenjen!");
			}
			
			korisnikService.saveOrUpdate(korisnik);
			logger.debug("snimi ili izmijeni saveor update");

			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
			
			return "redirect:/korisnici/" + korisnik.getId();
		}

	}
	

	@RequestMapping(value = "/korisnici/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajKorisnika(Model model, HttpSession session) {
		logger.debug("showDodajKorisnikaForm()");
		List<Uloga> sveUloge = ulogaService.findAll();
		
		Korisnik korisnik = new Korisnik();
		model.addAttribute("korisnikForm", korisnik);
		model.addAttribute("uloge", sveUloge);
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);
		
		
		return "korisnici/korisnikform";
	}

	@RequestMapping(value = "/korisnici/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniKorisnika(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("showPromijeniKorisnikaForm() : {}", id);

		if(session.getAttribute("roleid").toString().equals("3")){
	
			Korisnik korisnik = korisnikService.findById(Integer.valueOf(String.valueOf(session.getAttribute("userid"))));
			model.addAttribute("korisnikForm", korisnik);
			
			List<Uloga> sveUloge = ulogaService.findAll();
			Uloga uloga = new Uloga();
			for(int i= 0; i<sveUloge.size(); i++){
				if(sveUloge.get(i).getId() == (korisnik.getUloga())){
						uloga = sveUloge.get(i);	
				}
			}
				
			model.addAttribute("uloga",uloga.getId());
			
			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
		}
		else{  
			List<Uloga> sveUloge = ulogaService.findAll();
			Korisnik korisnik = korisnikService.findById(id);
			model.addAttribute("korisnikForm", korisnik);
			model.addAttribute("uloge",sveUloge);
			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
		
		}	
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
	public String prikaziKorisnika(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("prikaziKorisnika() id: {}", id);
	
		Korisnik korisnik = korisnikService.findById(id);
		if (korisnik == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Korisnik nije pronadjen");
		}
		logger.debug("prikaziKorisnika() korisnik: {}", korisnik.getKorisnickoIme() + korisnik.getUloga());
		
		Uloga uloga = ulogaService.findById(korisnik.getUloga());

		logger.debug("prikaziKorisnika() aaaaa: {}", uloga);
		
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);
		model.addAttribute("korisnik", korisnik);
		model.addAttribute("uloga", uloga.getNaziv());

		return "korisnici/prikazi";
	}
	
	 @RequestMapping(value = "/korisnici/{id}/dokumenti/", method = RequestMethod.GET)
	 protected ModelAndView prikaziDokument(@PathVariable("id") int id,HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) {
	     try {	 
	    	 logger.debug("prikaziDokument korisnika " + korisnikService.findById(id));
	    	 loggedRole = String.valueOf(session.getAttribute("roleid"));
	    	 if("0".equals(loggedRole) || "3".equals(loggedRole)){
	    		 //
	    		 return new ModelAndView("forward:/dokumenti");
	    	 }
	    	 else{
	    		 //TODO NE moze admine da vidi
	    		 if(!loggedRole.equals("1")){
	    			 if(String.valueOf(korisnikService.findById(id).getUloga()).equals("1")){
	    				 session.setAttribute("docUserID",  "null");
	    				 return new ModelAndView("forward:/dokumenti");
	    			 }
	    	}
	    	session.setAttribute("docUserID",  String.valueOf(id));
	        return new ModelAndView("forward:/dokumenti");
	    	 }
	     } catch (Exception ioe) {
	     } finally {
	     }
	     return null;
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