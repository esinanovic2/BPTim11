package ba.unsa.etf.model;

public class Vidljivost {
	
	private Integer id;
	private String naziv;
	
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
	public boolean isNew() {
		return (this.id == null);
	}

}
