package ba.unsa.etf.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Dokument {
	private Integer id;
	private String naziv;
	@JsonSerialize(using=StreamSerializer.class, as=ByteArrayInputStream.class)
	private InputStream fajl;
	private Integer vlasnik;
	private Integer vidljivost;
	
	private String contentType;
	private String extenzija;
	
	
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
	public ByteArrayInputStream getFajl() {
		return (ByteArrayInputStream) fajl;
	}
	public void setFajl(MultipartFile fajl) {
		try {
			this.fajl = new ByteArrayInputStream(fajl.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public void setFajlDrugi(InputStream fajl) {
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
	
	public String getContent() {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(fajl, writer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sadrzaj = writer.toString();
		
		return sadrzaj;
	}
	
	public void contentToInputStream(String s) {
		try {
			InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8.name()));
			fajl=stream;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getExtenzija() {
		return extenzija;
	}
	public void setExtenzija(String extenzija) {
		this.extenzija = extenzija;
	}
	
	

}
