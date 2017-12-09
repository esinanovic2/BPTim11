package ba.unsa.etf.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ba.unsa.etf.model.Dokument;
import ba.unsa.etf.service.DokumentService;
import ba.unsa.etf.validator.DokumentValidator;

@Controller
public class DokumentController {
	
	private final Logger logger = LoggerFactory.getLogger(DokumentController.class);
	
	@Autowired
	DokumentValidator dokumentValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(dokumentValidator);
	}
	
	private DokumentService dokumentService;
	
	@Autowired
	public void setDokumentService(DokumentService dokumentService) {
		this.dokumentService = dokumentService;
	}
	
	@RequestMapping(value = "/dokumenti", method = RequestMethod.GET)
	public String prikaziSveDokumente(Model model) {
		List<Dokument> sviDokumenti = dokumentService.findAll();
		model.addAttribute("dokumenti", sviDokumenti);
		return "dokumenti/listadokumenata";
	}
	
	@RequestMapping(value = "/dokumenti", method = RequestMethod.POST)
	public String snimiIliIzmijeniDokument(@ModelAttribute("dokumentForm") @Validated Dokument dokument,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes, @RequestParam("naziv") String naziv, @RequestParam("vlasnik") Integer vlasnik, 
			@RequestParam("vidljivost") Integer vidljivost, @RequestParam(value="fajl",required=false) MultipartFile fileUpload) {

			logger.debug("snimiIliIzmijeniDokument():", naziv + vlasnik + vidljivost + fileUpload.getOriginalFilename());
		
			String contentType=fileUpload.getContentType();
			
			logger.debug("Content type before substring : {}",contentType);
			
			String extenzija=fileUpload.getOriginalFilename();
			logger.debug("Extenzija before substring : {}",extenzija);
			extenzija=extenzija.substring(extenzija.lastIndexOf(".")+1, extenzija.length());
			
			logger.debug("Extenzija after substring : {}",extenzija);
			
			
			 	 		
		
		if (result.hasErrors()) {
			logger.debug("snimi ili izmijeni if has errors");
			return "dokumenti/dokumentform";
		} else {	
			redirectAttributes.addFlashAttribute("css", "success");
			if(dokument.isNew()){
				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno dodan!");
			}else{
				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno izmjenjen!");
			}
			dokument.setContentType(contentType);
			dokument.setExtenzija(extenzija);
			dokumentService.saveOrUpdate(dokument);
			logger.debug("snimi ili izmijeni saveor update");
			return "redirect:/dokumenti/" + dokument.getId();
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String izmijeniDokument(@RequestParam("id") Integer id, @RequestParam("naziv") String naziv, @RequestParam("vlasnik") Integer vlasnik, 
			@RequestParam("vidljivost") Integer vidljivost, @RequestParam("fajlcontent") String fajlcontent) {

		logger.debug("snimiIliIzmijeniDokument() : {}", id + naziv + vlasnik + vidljivost + fajlcontent);
		
		Dokument dokument=new Dokument();
		dokument.setId(id);
		dokument.setNaziv(naziv);
		dokument.setVlasnik(vlasnik);
		dokument.setVidljivost(vidljivost);
		dokument.contentToInputStream(fajlcontent);
		
		dokumentService.saveOrUpdate(dokument);
			
		return "redirect:/dokumenti/";
	}
	
			
	@RequestMapping(value = "/dokumenti/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajDokument(Model model) {

		logger.debug("showDodajDokumentForm()");

		Dokument dokument= new Dokument();
		model.addAttribute("dokumentForm", dokument);

		return "dokumenti/dokumentform";

	}
	
	@RequestMapping(value = "/dokumenti/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniDokument(@PathVariable("id") int id, Model model) {

		logger.debug("showPromijeniDokumentForm() : {}", id);

		Dokument dokument = dokumentService.findById(id);
		model.addAttribute("dokumentForm", dokument);
		
		model.addAttribute("dokumetContent",dokument.getContent());

		return "dokumenti/dokumentedit";
	}
	
	@RequestMapping(value = "/dokumenti/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiDokument(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteDokument() : {}", id);

		dokumentService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Dokument je obrisan!");
		
		return "redirect:/dokumenti";
	}
	
	 @RequestMapping(value = "/dokumenti/{id}", method = RequestMethod.GET)
	 protected String preivewSection(@PathVariable("id") int id,HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
	     try {
	    	 Dokument dokument = dokumentService.findById(id);
	    	 
	    	 String extenzija=dokument.getExtenzija();
	    	 
	    	 logger.debug("prikaz dokumenta ekstenzija: " + extenzija, extenzija);
	    	 
	         byte[] documentInBytes = IOUtils.toByteArray(dokument.getFajl());  
	         
	          
	         response.setHeader("Content-Disposition", "inline; filename=\""+dokument.getNaziv()+""+dokument.getExtenzija()+"\"");
	         response.setContentType(dokument.getContentType());

	         //response.setHeader("Content-Disposition", "inline; filename=\""+dokument.getNaziv()+".pdf\"");
	         response.setDateHeader("Expires", -1);
	         //response.setContentType("application/pdf");
	         response.setContentLength(documentInBytes.length);
	         response.getOutputStream().write(documentInBytes);
	     } catch (Exception ioe) {
	     } finally {
	     }
	     return null;
	 }
	
//	@RequestMapping(value = "/dokumenti/{id}", method = RequestMethod.GET)
//	public String prikaziDokument(@PathVariable("id") int id, Model model) {
//
//		logger.debug("prikaziDokument() id: {}", id);
//
//		Dokument dokument = dokumentService.findById(id);
//		if (dokument == null) {
//			model.addAttribute("css", "danger");
//			model.addAttribute("msg", "Dokument nije pronadjen");
//		}
//		model.addAttribute("dokument", dokument);
//
//		return "dokumenti/prikazi";
//	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		logger.debug("handleEmptyData()");
		logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("dokumenti/prikazi");
		model.addObject("msg", "Dokument nije pronadjen");

		return model;
	}


}
