package ba.unsa.etf.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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
	public String snimiIliIzmijeniDokument(@RequestParam("id") Integer id, @RequestParam("naziv") String naziv, @RequestParam("vlasnik") Integer vlasnik, 
			@RequestParam("vidljivost") Integer vidljivost, @RequestParam("fajl") MultipartFile fileUpload ) {

		logger.debug("snimiIliIzmijeniDokument() : {}", id + naziv + vlasnik + vidljivost + fileUpload.getOriginalFilename());

		Dokument uploadovanDokument=new Dokument();
		uploadovanDokument.setId(id);
		uploadovanDokument.setNaziv(naziv);
		uploadovanDokument.setVlasnik(vlasnik);
		uploadovanDokument.setVidljivost(vidljivost);
		
		InputStream is=null;
		try {
			is= new ByteArrayInputStream(fileUpload.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uploadovanDokument.setFajl(is);
		
		
		
//		if (result.hasErrors()) {
//			return "dokumenti/dokumentform";
//		} else {
//			redirectAttributes.addFlashAttribute("css", "success");
//			if(dokument.isNew()){
//				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno dodan!");
//			}else{
//				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno izmjenjen!");
//			}
//			
			dokumentService.saveOrUpdate(uploadovanDokument);
//			
			return "redirect:dokumenti/";
//		}
	}
	
	
	public File convert(MultipartFile file)
	{    
		try {
	    File convFile = new File(file.getOriginalFilename());
	    
			convFile.createNewFile();

	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 	
	}
	
	
//	@RequestMapping(value = "/dokumenti", method = RequestMethod.POST)
//	public String snimiIliIzmijeniDokument(@ModelAttribute("dokumentForm") @Validated Dokument dokument,
//			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
//
//		logger.debug("snimiIliIzmijeniDokument() : {}", dokument);
//
//		if (result.hasErrors()) {
//			return "dokumenti/dokumentform";
//		} else {
//			redirectAttributes.addFlashAttribute("css", "success");
//			if(dokument.isNew()){
//				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno dodan!");
//			}else{
//				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno izmjenjen!");
//			}
//			
//			dokumentService.saveOrUpdate(dokument);
//			
//			return "redirect:/dokumenti/" + dokument.getId();
//		}
//	}
	
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
	public String prikaziDokument(@PathVariable("id") int id, Model model) {

		logger.debug("prikaziDokument() id: {}", id);

		Dokument dokument = dokumentService.findById(id);
		if (dokument == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Dokument nije pronadjen");
		}
		model.addAttribute("dokument", dokument);

		return "dokumenti/prikazi";
	}
	
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
