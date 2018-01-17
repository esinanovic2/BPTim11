package ba.unsa.etf.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ba.unsa.etf.model.Dokument;
import ba.unsa.etf.model.Korisnik;
import ba.unsa.etf.model.Login;
import ba.unsa.etf.model.Uloga;
import ba.unsa.etf.service.DokumentService;
import ba.unsa.etf.service.KorisnikService;
import ba.unsa.etf.service.UlogaService;

@RestController
public class RESTController {

	private final Logger logger = LoggerFactory.getLogger(RESTController.class);
	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}
	
	private DokumentService dokumentService;

	@Autowired
	public void setDokumentService(DokumentService dokumentService) {
		this.dokumentService = dokumentService;
	}
	
	private UlogaService ulogaService;

	@Autowired
	public void setUlogaService(UlogaService ulogaService) {
		this.ulogaService = ulogaService;
	}
	
	@RequestMapping("/korisniciandroid")
	public ResponseEntity<List<Korisnik>> sviKorisnici(){
		List<Korisnik> korisnici = new ArrayList<>();
		korisnici = korisnikService.findAll();
		return new ResponseEntity<List<Korisnik>>(korisnici,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/nadjivlasnikasaidemandroid",method = RequestMethod.POST)
	public ResponseEntity<Korisnik> getKorisnikWithId(@RequestBody Integer id){
		Korisnik vlasnik = new Korisnik();
		vlasnik = korisnikService.findById(id);
		logger.debug("Response get korisnik with id : " + vlasnik.toString());

		ResponseEntity<Korisnik> responseEnt = new ResponseEntity<Korisnik>(vlasnik,HttpStatus.OK);
		return responseEnt;
	}
	
	@RequestMapping("/ulogeandroid")
	public ResponseEntity<List<Uloga>> sveUloge(){
		List<Uloga> uloge = new ArrayList<>();
		uloge = ulogaService.findAll();
		return new ResponseEntity<List<Uloga>>(uloge,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dokumentiandroid",method = RequestMethod.POST)
	public ResponseEntity<List<Dokument>> sviDokumentiUsera(@RequestBody Integer id){
		List<Dokument> dokumenti = new ArrayList<>();
		dokumenti = dokumentService.findDocumentsByUserId(id);
		logger.debug("Response svi dokumenti usera : " + dokumenti.toString());

		ResponseEntity<List<Dokument>> responseEnt = new ResponseEntity<List<Dokument>>(dokumenti,HttpStatus.OK);
		return responseEnt;
	}
	
	@RequestMapping(value = "/loginandroid", method = RequestMethod.POST)
	public ResponseEntity<Korisnik> login(@RequestBody Login model){
		logger.debug("Got string() : " + model.getKorisnickoIme());
		
		Korisnik korisnik = korisnikService.findByUsernameAndPassword(model.getKorisnickoIme(), model.getSifra());
		if(korisnik == null){
			korisnik = null;
		}
		logger.debug("Response login android : " + korisnik, korisnik);
		
		ResponseEntity<Korisnik> responseEnt = new ResponseEntity<Korisnik>(korisnik,HttpStatus.OK);
		return responseEnt;
	}
	
	
	@RequestMapping(value = "/dodajdokumentandroid", method = RequestMethod.POST)
	public ResponseEntity<ResponseBody> dodajDokument(@RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "naziv", required = false) String naziv, @RequestParam(value = "vlasnik", required = false) Integer vlasnik, @RequestParam(value = "vidljivost", required = false) Integer vidljivost, @RequestParam(value = "contentType", required = false) String contentType, @RequestParam(value = "extenzija", required = false) String extenzija, @RequestParam(value = "fajl", required = false) MultipartFile fileUpload    ){
		
		logger.debug("Response dodajdokumentandroid : " + id + " " + naziv + " " +vlasnik + " " + vidljivost + " " + contentType + " " + extenzija + " " + fileUpload.getOriginalFilename(), id);
		Dokument d=new Dokument();
		d.setNaziv(naziv);
		d.setVlasnik(vlasnik);
		d.setVidljivost(vidljivost+1);
		d.setContentType(contentType);
		d.setExtenzija(extenzija);
		d.setFajl(fileUpload);
		
		dokumentService.saveOrUpdate(d);
		
		
		
		return new ResponseEntity<ResponseBody>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/dodajkorisnikaandroid", method = RequestMethod.POST)
	public ResponseEntity<Void> dodajKorisnika(@RequestBody Korisnik korisnik){
		logger.debug("Pozvan dodaj korisnika id : " + korisnik.getId(), korisnik);
		korisnikService.saveOrUpdate(korisnik);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajuloguandroid", method = RequestMethod.POST)
	public ResponseEntity<Void> dodajUlogu(@RequestBody Uloga uloga){
		ulogaService.saveOrUpdate(uloga);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value ="/brisiuloguandroid", method = RequestMethod.POST)
	public ResponseEntity<Void> brisiUlogu(@RequestBody int id){
		logger.debug("Pozvan brisi ulogu id : " + id);
		ulogaService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value ="/brisikorisnikaandroid", method = RequestMethod.POST)
	public ResponseEntity<Void> brisiKorisnika(@RequestBody int id){
		korisnikService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping("/brisidokumentandroid")
	public ResponseEntity<Void> brisiDokument(@RequestBody int id){
		dokumentService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
