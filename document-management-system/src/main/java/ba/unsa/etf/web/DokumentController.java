package ba.unsa.etf.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.model.Vidljivost;
import ba.unsa.etf.service.DokumentService;
import ba.unsa.etf.service.KorisnikService;
import ba.unsa.etf.service.VidljivostService;
import ba.unsa.etf.validator.DokumentValidator;
import ba.unsa.etf.validator.KorisnikFormValidator;

@Controller
public class DokumentController {
	
	private final Logger logger = LoggerFactory.getLogger(DokumentController.class);
	
	private XWPFDocument xdoc=null;
	
	@Autowired
	DokumentValidator dokumentValidator;
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(dokumentValidator);
	}
	
//	@InitBinder("vlasnik")
//	protected void initUserBinder(WebDataBinder binder) {
//	    binder.setValidator(new KorisnikFormValidator());
//	}
	
	private DokumentService dokumentService;
	
	@Autowired
	public void setDokumentService(DokumentService dokumentService) {
		this.dokumentService = dokumentService;
	}
	
	private VidljivostService vidljivostService;
	
	@Autowired
	public void setVidljivostService (VidljivostService vidljivostService)
	{
		this.vidljivostService=vidljivostService;
	}
	
	private KorisnikService korisnikService;
	@Autowired
	public void setKorisnikService (KorisnikService korisnikService)
	{
		this.korisnikService=korisnikService;
	}
	
	@RequestMapping(value = "/dokumenti", method = RequestMethod.GET)
	public String prikaziSveDokumente(Model model) {
		List<Dokument> sviDokumenti = dokumentService.findAll();
		List<Korisnik> sviVlasnici = new ArrayList<>();
		for(int i = 0; i< sviDokumenti.size(); i++) {
			sviVlasnici.add(korisnikService.findById(sviDokumenti.get(i).getVlasnik()));
		}

		model.addAttribute("dokumenti", sviDokumenti);
		model.addAttribute("vlasnici", sviVlasnici);
		return "dokumenti/listadokumenata";
	}
	
	@RequestMapping(value = "/dokumenti", method = RequestMethod.POST)
	public String snimiIliIzmijeniDokument(@ModelAttribute("dokumentForm") @Validated Dokument dokument,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes, @RequestParam("naziv") String naziv,
			@RequestParam("vlasnik") Integer vlasnik, @RequestParam("vidljivost") Integer vidljivost, 
			@RequestParam(value="fajl",required=false) MultipartFile fileUpload) {

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

		logger.debug("snimiIliIzmijeniDokument() : {}", id + naziv + vlasnik + vidljivost + xdoc);
	
		Dokument dokument=dokumentService.findById(id);
		dokument.setId(id);
		dokument.setNaziv(naziv);
		dokument.setVlasnik(vlasnik);
		dokument.setVidljivost(vidljivost);
		if("docx".equals(dokument.getExtenzija())) {
			InputStream inputStream=replaceText(fajlcontent);
			dokument.setFajlDrugi(inputStream);		
		}else if("txt".equals(dokument.getExtenzija()))
			dokument.contentToInputStream(fajlcontent);
		
		dokumentService.saveOrUpdate(dokument);
			
		return "redirect:/dokumenti/";
	}
	
	private InputStream replaceText(String fajlcontent) {
		List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

	    for (XWPFParagraph paragraph : paragraphs){
	        for (int i = 0; i <= paragraph.getRuns().size(); i++){
	              paragraph.removeRun(i);
	           }
	    }
		
		// create Paragraph
		XWPFParagraph paragraph = paragraphs.get(0);
		paragraph.removeRun(0);
		XWPFRun run = paragraph.createRun();
		run.setText(fajlcontent);

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		
		try {
			xdoc.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream inputStream = new ByteArrayInputStream(b.toByteArray());
		return inputStream;
	}
	
	
	@RequestMapping(value = "/dokumenti/{id}/promijeni", method = RequestMethod.GET)
	public String prikaziFormuIzmijeniDokument(@PathVariable("id") int id, Model model) {

		logger.debug("showPromijeniDokumentForm() : {}", id);

		boolean showFileContentForm=false;
		Dokument dokument = dokumentService.findById(id);
		String extenzija= dokument.getExtenzija();
		String documentContent="";
		
		Map<String,String> documentMap=new HashMap<>();
		xdoc=null;
		if("docx".equals(extenzija)) {
			InputStream dokumentFile= dokument.getFajl();
			
			try {
				xdoc = new XWPFDocument(OPCPackage.open(dokumentFile));
				XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc); 
				
				System.out.println(extractor.getText());
				documentContent=extractor.getText();
				showFileContentForm=true;
				extractor.close();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
				 
//		Korisnik vlasnik = korisnikService.findById(dokument.getVlasnik());
		model.addAttribute("dokumentForm", dokument);
//		model.addAttribute("vlasnik",vlasnik);
		
		Vidljivost prva = vidljivostService.findById(dokument.getVidljivost());
		List<Vidljivost> sveVidljivosti = vidljivostService.findAll();
		List<Vidljivost> vidljivosti = new ArrayList<>();
		vidljivosti.add(prva);
		
		for(int i=0; i<sveVidljivosti.size(); i++){
			if(!(prva).equals(sveVidljivosti.get(i)))
				vidljivosti.add(sveVidljivosti.get(i));
		}
			
		model.addAttribute("vidljivosti", vidljivosti);
		
		if("txt".equals(extenzija)) {
			documentContent=dokument.getContent();
			showFileContentForm=true;
		}
		
		model.addAttribute("dokumetContent",documentContent);
		model.addAttribute("showTextArea",showFileContentForm);			
		return "dokumenti/dokumentedit";
	}
	
	
	@RequestMapping(value = "/editnocontent", method = RequestMethod.POST)
	public String izmijeniBezSadrzaja(@RequestParam("id") Integer id, @RequestParam("naziv") String naziv, @RequestParam("vlasnik") Integer vlasnik, 
			@RequestParam("vidljivost") Integer vidljivost) {

		logger.debug("snimiIliIzmijeniDokument() : {}", id + naziv + vlasnik + vidljivost);
		
		Dokument dokument=dokumentService.findById(id);
		dokument.setId(id);
		dokument.setNaziv(naziv);
		dokument.setVlasnik(vlasnik);
		dokument.setVidljivost(vidljivost);
		
		dokumentService.saveOrUpdate(dokument);
			
		return "redirect:/dokumenti/";
	}
	
	
	@RequestMapping(value = "/dokumenti/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajDokument(Model model) {

		logger.debug("showDodajDokumentForm()");
		List<Vidljivost> listaVidljivosti=vidljivostService.findAll();
		List<Korisnik> listaVlasnika = korisnikService.findAll();
		
		Dokument dokument= new Dokument();
		model.addAttribute("dokumentForm", dokument);
		model.addAttribute("vlasnici", listaVlasnika);
		model.addAttribute("vidljivosti",listaVidljivosti);
		return "dokumenti/dokumentform";
	}
		
	@RequestMapping(value = "/dokumenti/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiDokument(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {

		logger.debug("deleteDokument() : {}", id);

		dokumentService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Dokument je obrisan!");
		
		return "redirect:/dokumenti";
	}
	
	 @RequestMapping(value = "/dokumenti/{id}/prikazi/", method = RequestMethod.GET)
	 protected String prikaziDokument(@PathVariable("id") int id,HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
	     try {
	    	 Dokument dokument = dokumentService.findById(id);
	    	 
	    	 String extenzija=dokument.getExtenzija();
	    	 
	    	 logger.debug("prikaz dokumenta ekstenzija: " + extenzija, extenzija);
	    	 
	         byte[] dokumentBytes = IOUtils.toByteArray(dokument.getFajl());  
	        
	         response.setHeader("Content-Disposition", "inline; filename=\""+dokument.getNaziv()+""+"."+dokument.getExtenzija()+"\"");
	         response.setContentType(dokument.getContentType());
	         response.setDateHeader("Expires", -1);
	         response.setContentLength(dokumentBytes.length);
	         response.getOutputStream().write(dokumentBytes);
	         
	     } catch (Exception ioe) {
	     } finally {
	     }
	     return null;
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
