package ba.unsa.etf.baze.tim11.dao;

public class Korisnik {
	private Integer id;
	private String ime;
	private String prezime;
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

}
