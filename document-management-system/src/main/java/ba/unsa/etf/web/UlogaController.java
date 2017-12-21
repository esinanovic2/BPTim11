package ba.unsa.etf.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import ba.unsa.etf.model.Uloga;
import ba.unsa.etf.service.UlogaService;
import ba.unsa.etf.validator.UlogaFormValidator;

@Controller
public class UlogaController {
	private final Logger logger = LoggerFactory.getLogger(UlogaController.class);

	String loggedRole = "0";
	
	@Autowired
	UlogaFormValidator ulogaFormValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(ulogaFormValidator);
	}

	private UlogaService ulogaService;

	@Autowired
	public void setUlogaService(UlogaService ulogaService) {
		this.ulogaService = ulogaService;
	}

	@RequestMapping(value = "/uloge", method = RequestMethod.GET)
	public String prikaziSveUloge(Model model, HttpSession session) {

		logger.debug("showPrikaziSveUloge()");
		model.addAttribute("uloge", ulogaService.findAll());

		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);
		return "uloge/listauloga";
	}

	@RequestMapping(value = "/uloge", method = RequestMethod.POST)
	public String snimiIliIzmijeniUlogu(@ModelAttribute("ulogaForm") @Validated Uloga uloga,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		logger.debug("snimiIliIzmijeniUlogu() : {}", uloga);

		if (result.hasErrors()) {
			return "uloge/ulogaform";
		} else {
			redirectAttributes.addFlashAttribute("css", "success");
			if(uloga.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Uloga uspjesno dodana!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Uloga uspjesno izmjenjena!");
			}
				
			ulogaService.saveOrUpdate(uloga);
			
			return "redirect:/uloge/" + uloga.getId();
		}
	}

	@RequestMapping(value = "/uloge/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajUlogu(Model model, HttpSession session) {

		logger.debug("showDodajUloguForm()");

		Uloga uloga = new Uloga();
		model.addAttribute("ulogaForm", uloga);

		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);

		return "uloge/ulogaform";

	}

	@RequestMapping(value = "/uloge/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniUlogu(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("showPromijeniUloguForm() : {}", id);

		Uloga uloga = ulogaService.findById(id);
		model.addAttribute("ulogaForm", uloga);
		

		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);

		return "uloge/ulogaform";
	}


	@RequestMapping(value = "/uloge/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiUlogu(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUloga() : {}", id);

		ulogaService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Uloga je obrisana!");
		
		return "redirect:/uloge";
	}

	@RequestMapping(value = "/uloge/{id}", method = RequestMethod.GET)
	public String prikaziUlogu(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("prikaziUlogu() id: {}", id);

		Uloga uloga = ulogaService.findById(id);
		if (uloga == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Uloga nije pronadjena");
		}
		model.addAttribute("uloga", uloga);
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);

		return "uloge/prikazi";
	}
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("uloge/prikazi");
		model.addObject("msg", "Uloga nije pronadjena");

		return model;
	}
}
