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

import ba.unsa.etf.model.Vidljivost;
import ba.unsa.etf.service.VidljivostService;
import ba.unsa.etf.validator.VidljivostValidator;

@Controller
public class VidljivostController {

	private final Logger logger = LoggerFactory.getLogger(VidljivostController.class);
	
	@Autowired
	VidljivostValidator vidljivostFormValidator;
	
	@InitBinder
	protected void initBinder (WebDataBinder binder)
	{
		binder.setValidator(vidljivostFormValidator);
	}
	
	private VidljivostService vidljivostService;
	
	@Autowired
	public void setVidljivostService (VidljivostService vidljivostService)
	{
		this.vidljivostService=vidljivostService;
	}
	
	@RequestMapping(value = "/vidljivosti", method = RequestMethod.GET)
	public String prikaziSveVidljivosti(Model model) {

		logger.debug("showPrikaziSveVidljivosti()");
		model.addAttribute("vidljivosti", vidljivostService.findAll());
		return "vidljivosti/listavidljivosti";
	}
	
	@RequestMapping(value="/vidljivosti", method = RequestMethod.POST)
	public String snimiIliIzmijeniVidljivost(@ModelAttribute("vidljivostForm") @Validated Vidljivost vidljivost,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes)
	{
		logger.debug("snimiIliIzmijeniVidljivost() : {}", vidljivost);
		if (result.hasErrors()) {
			return "vidljivosti/vidljivostform";
		} else {
			redirectAttributes.addFlashAttribute("css", "success");
			if(vidljivost.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Vidljivost uspjesno dodana!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Vidljivost uspjesno izmjenjena!");
			}
			
			vidljivostService.saveOrUpdate(vidljivost);
			
			return "redirect:/vidljivosti/" + vidljivost.getId();
		}
	}
	
	@RequestMapping(value = "/vidljivosti/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajVidljivost(Model model) {

		logger.debug("showDodajVidljivostForm()");
		
		Vidljivost vidljivost=new Vidljivost();
		model.addAttribute("vidljivostForm", vidljivost);

		return "vidljivosti/vidljivostform";
	}
	
	@RequestMapping(value = "/vidljivosti/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniVidljivosti(@PathVariable("id") int id, Model model) {

		logger.debug("showPromijeniVidljivostForm() : {}", id);
		
		Vidljivost vidljivost = vidljivostService.findById(id);
		model.addAttribute("vidljivostForm", vidljivost);

		return "vidljivosti/vidljivostform";
	}
	
	@RequestMapping(value = "/vidljivosti/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiKorisnika(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteVidljivost() : {}", id);

		vidljivostService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Vidljivost je obrisana!");
		
		return "redirect:/vidljivosti";
	}
	
	@RequestMapping(value = "/vidljivosti/{id}", method = RequestMethod.GET)
	public String prikaziKorisnika(@PathVariable("id") int id, Model model) {

		logger.debug("prikaziVidljivost() id: {}", id);

		Vidljivost vidljivost = vidljivostService.findById(id);
		if (vidljivost == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Vidljivost nije pronadjena");
		}
		model.addAttribute("vidljivost", vidljivost);

		return "vidljivosti/prikazi";
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("vidljivost/prikazi");
		model.addObject("msg", "Vidljivost nije pronadjena");

		return model;
	}

}
