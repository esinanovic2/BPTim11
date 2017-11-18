package ba.unsa.etf.model;

public class Korisnik {
	private Integer id;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String sifra;
	private String potvrdisifru;
	private Integer uloga;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Integer getUloga() {
		
		return uloga;
	}
	public void setUloga(Integer uloga) {
		this.uloga = uloga;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	
	public boolean isNew() {
		return (this.id == null);
	}
	public String getPotvrdisifru() {
		return potvrdisifru;
	}
	public void setPotvrdisifru(String potvrdisifru) {
		this.potvrdisifru = potvrdisifru;
	}
}
