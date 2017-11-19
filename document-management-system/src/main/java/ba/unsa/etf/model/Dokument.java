package ba.unsa.etf.model;

import java.io.InputStream;
import java.sql.Blob;

public class Dokument {
	private Integer id;
	private String naziv;
	private InputStream fajl;
	private Integer vlasnik;
	private Integer vidljivost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public InputStream getFajl() {
		return fajl;
	}
	public void setFajl(InputStream fajl) {
		this.fajl = fajl;
	}
	public Integer getVlasnik() {
		return vlasnik;
	}
	public void setVlasnik(Integer vlasnik) {
		this.vlasnik = vlasnik;
	}
	public Integer getVidljivost() {
		return vidljivost;
	}
	public void setVidljivost(Integer vidljivost) {
		this.vidljivost = vidljivost;
	}
	public boolean isNew() {
		return (this.id == null);
	}

}
