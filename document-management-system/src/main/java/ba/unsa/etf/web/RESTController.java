package ba.unsa.etf.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping("/dokumentiandroid")
	public ResponseEntity<List<Dokument>> sviDokumenti(){
		List<Dokument> dokumenti = new ArrayList<>();
		dokumenti = dokumentService.findAll();
		return new ResponseEntity<List<Dokument>>(dokumenti,HttpStatus.OK);
	}
	
	@RequestMapping("/ulogeandroid")
	public ResponseEntity<List<Uloga>> sveUloge(){
		List<Uloga> uloge = new ArrayList<>();
		uloge = ulogaService.findAll();
		return new ResponseEntity<List<Uloga>>(uloge,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/loginandroid", method = RequestMethod.POST)
	public ResponseEntity<Korisnik> login(@RequestBody Login model){
		logger.debug("Got string() : " + model.getKorisnickoIme());
		
		Korisnik korisnik = korisnikService.findByUsernameAndPassword(model.getKorisnickoIme(), model.getSifra());
		if(korisnik == null){
			korisnik = new Korisnik();
		}
		logger.debug("Response string() : " + korisnik, korisnik);
		
		ResponseEntity<Korisnik> responseEnt = new ResponseEntity<Korisnik>(korisnik,HttpStatus.OK);
		return responseEnt;
	}
	
}
