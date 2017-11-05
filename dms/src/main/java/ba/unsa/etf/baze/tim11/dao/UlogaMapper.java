package ba.unsa.etf.baze.tim11.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UlogaMapper implements RowMapper<Uloga> {

	public Uloga mapRow(ResultSet rs, int rowNum) throws SQLException {

		Uloga uloga=new Uloga();
		uloga.setId(rs.getInt("id"));
		uloga.setNaziv(rs.getString("naziv"));
		
		return uloga;
	}
}
