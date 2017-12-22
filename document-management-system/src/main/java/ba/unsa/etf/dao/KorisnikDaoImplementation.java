package ba.unsa.etf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.model.Korisnik;

@Repository
public class KorisnikDaoImplementation implements KorisnikDao {
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Korisnik findById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM korisnici WHERE id=:id";

		Korisnik result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new KorisnikMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}
	
	@Override
	public Korisnik findByUsernameAndPassword(String username, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("korisnickoime", username);
		params.put("sifra", password);

		String sql = "SELECT * FROM korisnici WHERE korisnickoime=:korisnickoime AND sifra=:sifra";

		Korisnik result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new KorisnikMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}
	
	@Override
	public List<Korisnik> findAll() {
		String sql = "SELECT * FROM korisnici";		
		List<Korisnik> result = namedParameterJdbcTemplate.query(sql, new KorisnikMapper());

		return result;
	}
	
	@Override
	public List<Korisnik> findUsersWithRole(Integer roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uloga", roleId);
		
		String sql = "SELECT * FROM korisnici WHERE uloga= :uloga";

		List<Korisnik> result = namedParameterJdbcTemplate.query(sql, params, new KorisnikMapper());
		
		
		return result;
	}
	
	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM korisnici WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

	@Override
	public void save(Korisnik korisnik) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO korisnici(ime, prezime, korisnickoime, sifra, uloga) values (:ime, :prezime, :korisnickoime, :sifra, :uloga)";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(korisnik), keyHolder);
		
		korisnik.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET ime = :ime, prezime = :prezime, korisnickoime = :korisnickoime, sifra = :sifra, uloga = :uloga WHERE id = :id";
		
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(korisnik));
	}

	public class KorisnikMapper implements RowMapper<Korisnik> {
		public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
			Korisnik korisnik = new Korisnik();
		    korisnik.setId(rs.getInt("id"));
		    korisnik.setIme(rs.getString("ime"));
		    korisnik.setPrezime(rs.getString("prezime"));
		    korisnik.setKorisnickoIme(rs.getString("korisnickoime"));
		    korisnik.setSifra(rs.getString("sifra"));
		    korisnik.setUloga(rs.getInt("uloga"));
		      
		    return korisnik;
		}
	}
	
	private SqlParameterSource getSqlParameterByModel(Korisnik korisnik) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", korisnik.getId());
		paramSource.addValue("ime", korisnik.getIme());
		paramSource.addValue("sifra", korisnik.getSifra());
		paramSource.addValue("uloga", korisnik.getUloga());
		paramSource.addValue("prezime", korisnik.getPrezime());
		paramSource.addValue("korisnickoime", korisnik.getKorisnickoIme());

		return paramSource;
	}
}
