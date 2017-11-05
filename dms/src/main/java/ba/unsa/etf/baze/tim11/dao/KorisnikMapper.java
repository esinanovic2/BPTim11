package ba.unsa.etf.baze.tim11.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KorisnikMapper implements RowMapper<Korisnik> {
	public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
		Korisnik korisnik = new Korisnik();
	    korisnik.setId(rs.getInt("id"));
	    korisnik.setIme(rs.getString("ime"));
	    korisnik.setPrezime(rs.getString("prezime"));
	    korisnik.setUloga(rs.getInt("uloga"));
	      
	    return korisnik;
	}

}
