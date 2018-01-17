package ba.unsa.etf.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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

@Controller
public class DokumentController {

	private final Logger logger = LoggerFactory.getLogger(DokumentController.class);

	private XWPFDocument xdoc = null;

	String loggedRole = "0";

	String userID = "null";

	Integer vidljivost = 1;

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

	private VidljivostService vidljivostService;

	@Autowired
	public void setVidljivostService(VidljivostService vidljivostService) {
		this.vidljivostService = vidljivostService;
	}

	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}

	@RequestMapping(value = "/dokumenti", method = RequestMethod.GET)
	public String prikaziSveDokumente(Model model, HttpSession session) {
		logger.debug("prikaziSveDokumente");
		loggedRole = String.valueOf(session.getAttribute("roleid"));

		List<Dokument> sviDokumenti = new ArrayList<>();
		sviDokumenti = dokumentService.findAll();
		String loggedID = String.valueOf(session.getAttribute("userid"));

		if (loggedRole.equals("2")) {
			sviDokumenti = prikaziDokumenteVlasnikaIPrivilegije(loggedRole, loggedID, sviDokumenti);
		} else if (loggedRole.equals("3")) {
			// ako je sam on. ako je poslao id.
			String userID = String.valueOf(session.getAttribute("docUserID"));

			logger.debug("prikaziSveDokumente STUDENTSKA ");
			if ("null".equals(userID)) {
				sviDokumenti = prikaziDokumenteVlasnikaIPrivilegije(loggedRole, loggedID, sviDokumenti);
			} else if (String.valueOf(korisnikService.findById(Integer.valueOf(userID)).getUloga()).equals("1")) {
				// Admin nono
				sviDokumenti = prikaziDokumenteVlasnikaIPrivilegije("3", userID, sviDokumenti);
			} else {
				// Pokazi od tog korisnika
				loggedRole = "43";
				session.setAttribute("docUserID", "null");
				sviDokumenti = prikaziDokumenteVlasnikaIPrivilegije("3", userID, sviDokumenti);
			}

		} else if (loggedRole.equals("1")) {

			// ako je sam on. ako je poslao id.
			userID = String.valueOf(session.getAttribute("docUserID"));
			// Ako je taj userID ADMIN prikazi samo od tog admina dokumente
			sviDokumenti = dokumentService.findDocumentsByUserId(Integer.valueOf(loggedID));

			logger.debug("prikaziSveDokumente ADMIN " + userID);
			if (!"null".equals(userID)) {
				// //Prikazuje od tog
				loggedRole = "13";
				sviDokumenti = dokumentService.findDocumentsByUserId(Integer.valueOf(userID));
				logger.debug("ADMIN 13 user: " + userID + " dokumenti size: " + sviDokumenti.size());
				session.setAttribute("docUserID", "null");
			}
		}

		List<Integer> idEvi=new ArrayList<>();
		List<Korisnik> sviVlasnici = new ArrayList<>();
		for (int i = 0; i < sviDokumenti.size(); i++) {
			idEvi.add(i+1);
			sviVlasnici.add(korisnikService.findById(sviDokumenti.get(i).getVlasnik()));
		}

		model.addAttribute("loggedRole", loggedRole);
		model.addAttribute("dokumenti", sviDokumenti);
		model.addAttribute("vlasnici", sviVlasnici);
		model.addAttribute("idevi", idEvi);
		return "dokumenti/listadokumenata";
	}

	private List<Dokument> prikaziDokumenteVlasnikaIPrivilegije(String uloga, String ID, List<Dokument> sviDokumenti) {
		List<Dokument> noviDokumenti = new ArrayList<>();
		// ako je uloga 4 onda moze vidjeti i vlasnike 3
		// TODO find dokumente sa idem korisnika tim i tim QUE
		for (int i = 0; i < sviDokumenti.size(); i++) {
			// Vlasnici dokumenta prema privilegiji
			if (ID.equals(sviDokumenti.get(i).getVlasnik().toString())) {
				noviDokumenti.add(sviDokumenti.get(i));
			}
			// if(uloga.equals("4")){//ako je uloga 4 onda moze vidjeti i vlasnike 3
			// //Vlasnike ciji je ID ULOGE 3
			// String ulogaVlasnika =
			// String.valueOf(korisnikService.findById(sviDokumenti.get(i).getVlasnik()).getUloga());
			// if(ulogaVlasnika.equals("3")){
			// noviDokumenti.add(sviDokumenti.get(i));
			// }
			// }
		}
		return noviDokumenti;
	}

	@RequestMapping(value = "/dokumenti", method = RequestMethod.POST)
	public String snimiIliIzmijeniDokument(@ModelAttribute("dokumentForm") @Validated Dokument dokument,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes,
			@RequestParam("naziv") String naziv, @RequestParam(value = "fajl", required = false) MultipartFile fileUpload, HttpSession session) {

		logger.debug("snimiIliIzmijeniDokument():" + naziv + "filename: " + fileUpload.isEmpty(), naziv + "filename: " + fileUpload.getOriginalFilename());

		String contentType = fileUpload.getContentType();
		String extenzija = fileUpload.getOriginalFilename();
		extenzija = extenzija.substring(extenzija.lastIndexOf(".") + 1, extenzija.length());
		
		if(fileUpload.isEmpty())
			result.rejectValue("fajl", "NotEmpty.dokumentForm.fajl", "Fajl je obavezan!");
		
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);

		if (result.hasErrors()) {
			logger.debug("snimi ili izmijeni if has errors: " + dokument.getFajl());
			
			List<Korisnik> listaVlasnika = korisnikService.findAll();
			model.addAttribute("staticVlasnik", "0");
			if (loggedRole.equals("1")) {
				if (userID.equals("null")) {
					model.addAttribute("vlasnici", listaVlasnika);
				} else {
					model.addAttribute("staticVlasnik", "1");
					model.addAttribute("vlasnik", userID);
				}
			} else {
				model.addAttribute("staticVlasnik", "1");
				Integer id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
				model.addAttribute("vlasnik", id);
			}
			model.addAttribute("vidljivost", vidljivost);
			
			
			return "dokumenti/dokumentform";
		} else {
			redirectAttributes.addFlashAttribute("css", "success");
			if (dokument.isNew()) {
				redirectAttributes.addFlashAttribute("msg", "Dokument uspjesno dodan!");
			} else {
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
	public String izmijeniDokument(@ModelAttribute("dokumentForm") Dokument dokument,
			BindingResult result, Model model, @RequestParam("id") Integer id, @RequestParam("naziv") String naziv,
			@RequestParam("vidljivost") Integer vidljivost, @RequestParam("fajlcontent") String fajlcontent,
			 HttpSession session) {

		logger.debug("snimiIliIzmijeniDokument() : {}" + id + naziv + vidljivost + xdoc);
		
		if(naziv==null || naziv.isEmpty() || "".equals(naziv)) {
			logger.debug("Naziv prazan : " + naziv);
			result.rejectValue("naziv", "NotEmpty.dokumentForm.naziv", "Ne moze biti prazan");
		}
		
		if(fajlcontent==null || fajlcontent.isEmpty() || "".equals(fajlcontent)) {
			logger.debug("Fajl prazan : " + naziv);
			result.rejectValue("naziv", "NotEmpty.dokumentForm.content", "Sadrzaj ne moze biti prazan");
		}
			
		if(result.hasErrors()) {
			logger.debug("Has errors() : " + "id: " + id + " naziv: " + naziv + " vidljivost: " +  vidljivost + " xdoc: " +  xdoc);
						
			boolean showFileContentForm = false;
			dokument = dokumentService.findById(id);
			String extenzija = dokument.getExtenzija();
			String documentContent = "";

			xdoc = null;
			if ("docx".equals(extenzija)) {
				InputStream dokumentFile = dokument.getFajl();
				try {
					xdoc = new XWPFDocument(OPCPackage.open(dokumentFile));
					XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);

					System.out.println(extractor.getText());
					documentContent = extractor.getText();
					showFileContentForm = true;
					extractor.close();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//model.addAttribute("dokumentForm", dokument);
			Korisnik vlasnik = korisnikService.findById(dokument.getVlasnik());
			model.addAttribute("vlasnik", vlasnik.getKorisnickoIme());

			Vidljivost prva = vidljivostService.findById(dokument.getVidljivost());
			List<Vidljivost> sveVidljivosti = vidljivostService.findAll();
			List<Vidljivost> vidljivosti = new ArrayList<>();
			vidljivosti.add(prva);

			for (int i = 0; i < sveVidljivosti.size(); i++) {
				if (!(prva.getNaziv()).equals(sveVidljivosti.get(i).getNaziv()))
					vidljivosti.add(sveVidljivosti.get(i));
			}
			model.addAttribute("vlasnik", vlasnik.getKorisnickoIme());
			model.addAttribute("vidljivost", vidljivost);

			if ("txt".equals(extenzija)) {
				documentContent = dokument.getContent();
				showFileContentForm = true;
			}

			model.addAttribute("dokumetContent", documentContent.trim());
			model.addAttribute("showTextArea", showFileContentForm);
			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
			
			return "dokumenti/dokumentedit";
		}else {
			dokument = dokumentService.findById(id);
			dokument.setId(id);
			dokument.setNaziv(naziv);
			dokument.setVidljivost(vidljivost);
			if ("docx".equals(dokument.getExtenzija())) {
				InputStream inputStream = replaceText(fajlcontent);
				dokument.setFajlDrugi(inputStream);
			} else if ("txt".equals(dokument.getExtenzija()))
				dokument.contentToInputStream(fajlcontent);

			loggedRole = String.valueOf(session.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);

			dokumentService.saveOrUpdate(dokument);

			return "redirect:/dokumenti/";
		}
	}

	private InputStream replaceText(String fajlcontent) {
		List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

		for (XWPFParagraph paragraph : paragraphs) {
			for (int i = 0; i <= paragraph.getRuns().size(); i++) {
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
	public String prikaziFormuIzmijeniDokument(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("showPromijeniDokumentForm() : {}", id);
		boolean showFileContentForm = false;
		Dokument dokument = dokumentService.findById(id);
		String extenzija = dokument.getExtenzija();
		String documentContent = "";

		xdoc = null;
		if ("docx".equals(extenzija)) {
			InputStream dokumentFile = dokument.getFajl();
			try {
				xdoc = new XWPFDocument(OPCPackage.open(dokumentFile));
				XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);

				System.out.println(extractor.getText());
				documentContent = extractor.getText();
				showFileContentForm = true;
				extractor.close();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		model.addAttribute("dokumentForm", dokument);
		Korisnik vlasnik = korisnikService.findById(dokument.getVlasnik());
		model.addAttribute("vlasnik", vlasnik.getKorisnickoIme());

		Vidljivost prva = vidljivostService.findById(dokument.getVidljivost());
		List<Vidljivost> sveVidljivosti = vidljivostService.findAll();
		List<Vidljivost> vidljivosti = new ArrayList<>();
		vidljivosti.add(prva);

		for (int i = 0; i < sveVidljivosti.size(); i++) {
			if (!(prva.getNaziv()).equals(sveVidljivosti.get(i).getNaziv()))
				vidljivosti.add(sveVidljivosti.get(i));
		}

		model.addAttribute("vidljivosti", vidljivosti);
		model.addAttribute("vlasnik", vlasnik.getKorisnickoIme());
		model.addAttribute("vidljivost", vidljivost);

		if ("txt".equals(extenzija)) {
			documentContent = dokument.getContent();
			showFileContentForm = true;
		}

		model.addAttribute("dokumetContent", documentContent.trim());
		model.addAttribute("showTextArea", showFileContentForm);
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);
		return "dokumenti/dokumentedit";

	}

	@RequestMapping(value = "/editnocontent", method = RequestMethod.POST)
	public String izmijeniBezSadrzaja(@ModelAttribute("dokumentForm") Dokument dokument,Model model,
			BindingResult result,@RequestParam("id") Integer id, @RequestParam("naziv") String naziv,
			@RequestParam("vidljivost") Integer vidljivost) {

		logger.debug("snimiIliIzmijeniDokument() : {}", id + naziv + vidljivost);
		
		if(naziv==null || naziv.isEmpty() || "".equals(naziv)) {
			logger.debug("Naziv prazan : " + naziv);
			result.rejectValue("naziv", "NotEmpty.dokumentForm.naziv", "Ne moze biti prazan");
		}

		if (result.hasErrors()) {
			
			dokument = dokumentService.findById(id);

			Korisnik vlasnik = korisnikService.findById(dokument.getVlasnik());
			model.addAttribute("vlasnik", vlasnik.getKorisnickoIme());

			Vidljivost prva = vidljivostService.findById(dokument.getVidljivost());
			List<Vidljivost> sveVidljivosti = vidljivostService.findAll();
			List<Vidljivost> vidljivosti = new ArrayList<>();
			vidljivosti.add(prva);

			for (int i = 0; i < sveVidljivosti.size(); i++) {
				if (!(prva.getNaziv()).equals(sveVidljivosti.get(i).getNaziv()))
					vidljivosti.add(sveVidljivosti.get(i));
			}

			return "dokumenti/dokumentedit";
		} else {
			dokument = dokumentService.findById(id);
			dokument.setId(id);
			dokument.setNaziv(naziv);
			dokument.setVidljivost(vidljivost);
			dokumentService.saveOrUpdate(dokument);
			return "redirect:/dokumenti/";
		}
	}

	@RequestMapping(value = "/dokumenti/dodaj", method = RequestMethod.GET)
	public String prikaziFormuDodajDokument(Model model, HttpSession session) {
		logger.debug("showDodajDokumentForm()");
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		logger.debug("USER SAD " + loggedRole + " userID " + userID);

		// List<Vidljivost> listaVidljivosti=vidljivostService.findAll();
		List<Korisnik> listaVlasnika = korisnikService.findAll();
		Dokument dokument = new Dokument();
		model.addAttribute("dokumentForm", dokument);
		model.addAttribute("staticVlasnik", "0");
		if (loggedRole.equals("1")) {
			if (userID.equals("null")) {
				model.addAttribute("vlasnici", listaVlasnika);
			} else {
				model.addAttribute("staticVlasnik", "1");
				model.addAttribute("vlasnik", userID);
			}
		} else {
			model.addAttribute("staticVlasnik", "1");
			Integer id = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
			model.addAttribute("vlasnik", id);
		}
		// model.addAttribute("vidljivosti",listaVidljivosti);
		model.addAttribute("vidljivost", vidljivost);
		model.addAttribute("loggedRole", loggedRole);

		return "dokumenti/dokumentform";
	}

	@RequestMapping(value = "/dokumenti/{id}/obrisi", method = RequestMethod.POST)
	public String obrisiDokument(@PathVariable("id") int id, final RedirectAttributes redirectAttributes,
			HttpSession session) {

		logger.debug("deleteDokument() : {}", id);

		dokumentService.delete(id);

		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Dokument je obrisan!");

		return "redirect:/dokumenti";
	}

	@RequestMapping(value = "/dokumenti/{id}/prikazi/", method = RequestMethod.GET)
	protected String prikaziDokument(@PathVariable("id") int id, HttpServletRequest request, Model model,
			HttpSession httpSession, HttpServletResponse response) {
		try {
			Dokument dokument = dokumentService.findById(id);
			String extenzija = dokument.getExtenzija();
			InputStream dokumentFile = dokument.getFajl();

			model.addAttribute("showcontent", "true");
			xdoc = null;
			if ("txt".equals(dokument.getExtenzija())) {
				model.addAttribute("naziv", dokument.getNaziv());
				String content = dokument.getContent();
				model.addAttribute("content", content);
				return "dokumenti/prikazi";
			} else if ("docx".equals(dokument.getExtenzija())) {
				try {
					model.addAttribute("naziv", dokument.getNaziv());
					xdoc = new XWPFDocument(OPCPackage.open(dokumentFile));
					XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
					String content = extractor.getText();
					model.addAttribute("content", content.trim());
					extractor.close();
					return "dokumenti/prikazi";
				} catch (InvalidFormatException e) {
					logger.debug("prikaz dokumenta exception: " + e.getLocalizedMessage(), e.getLocalizedMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.debug("prikaz dokumenta exception: " + e.getLocalizedMessage(), e.getLocalizedMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.debug("prikaz dokumenta exception: " + e.getLocalizedMessage(), e.getLocalizedMessage());
					e.printStackTrace();
				}
			} else if("jpg".equalsIgnoreCase(dokument.getExtenzija())||"jpeg".equalsIgnoreCase(dokument.getExtenzija()) || "png".equalsIgnoreCase(dokument.getExtenzija())) {
//				BufferedImage image = ImageIO.read(dokument.getFajl());
//				Integer visina = image.getHeight();
//				Integer sirina = image.getWidth();
//				logger.debug("prikaz dokumenta visina i sirina: " + visina + sirina);
////				model.addAttribute("visina",String.valueOf(visina));
////				model.addAttribute("sirina",String.valueOf(sirina));
				byte[] dokumentBytes = IOUtils.toByteArray(dokument.getFajl());
				byte[] encodeBase64 = Base64.encode(dokumentBytes);
                String base64Encoded = new String(encodeBase64, "UTF-8");
                model.addAttribute("naziv", dokument.getNaziv());
                model.addAttribute("image", base64Encoded);
                return "dokumenti/prikazisliku";
			}
			

			byte[] dokumentBytes = IOUtils.toByteArray(dokument.getFajl());
			loggedRole = String.valueOf(httpSession.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
			response.setHeader("Content-Disposition",
					"inline; filename=\"" + dokument.getNaziv() + "" + "." + dokument.getExtenzija() + "\"");
			// "Content-Disposition" attachment za skidanje umjesto inline
			response.setContentType(dokument.getContentType());
			response.setDateHeader("Expires", -1);
			response.setContentLength(dokumentBytes.length);
			response.getOutputStream().write(dokumentBytes);

		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
		}
		return null;
	}
	
	
	@RequestMapping(value = "/dokumenti/{id}/skini", method = RequestMethod.GET)
	protected String skiniDokument(@PathVariable("id") int id, HttpServletRequest request, Model model, HttpSession httpSession, HttpServletResponse response) {
		try {
			Dokument dokument = dokumentService.findById(id);
			
			byte[] dokumentBytes = IOUtils.toByteArray(dokument.getFajl());
			loggedRole = String.valueOf(httpSession.getAttribute("roleid"));
			model.addAttribute("loggedRole", loggedRole);
			response.setHeader("Content-Disposition","attachment; filename=\"" + dokument.getNaziv() + "" + "." + dokument.getExtenzija() + "\"");
			response.setContentType(dokument.getContentType());
			response.setDateHeader("Expires", -1);
			response.setContentLength(dokumentBytes.length);
			response.getOutputStream().write(dokumentBytes);

		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
		}
		return null;
	}
	

	@RequestMapping(value = "/dokumenti/{id}", method = RequestMethod.GET)
	public String prikaziDokument(@PathVariable("id") int id, Model model, HttpSession session) {

		logger.debug("prikaziDokument() id: {}", id);

		Dokument dokument = dokumentService.findById(id);
		if (dokument == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Dokument nije pronadjen");
		}
		model.addAttribute("naziv", dokument.getNaziv());
		model.addAttribute("showcontent", "false");
		loggedRole = String.valueOf(session.getAttribute("roleid"));
		model.addAttribute("loggedRole", loggedRole);

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
