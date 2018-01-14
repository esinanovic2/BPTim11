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

import ba.unsa.etf.model.Uloga;
import ba.unsa.etf.service.KorisnikService;

@Repository
public class UlogaDaoImplementation implements UlogaDao{
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	private KorisnikService korisnikService;

	@Autowired
	public void setKorisnikService(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}

	@Override
	public Uloga findById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT id, naziv FROM uloge WHERE id=:id";

		Uloga result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new UlogaMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}
	
	@Override
	public Uloga findByNaziv(String naziv) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("naziv", naziv);
	
		String sql = "SELECT id, naziv FROM uloge WHERE naziv=:naziv";

		Uloga result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new UlogaMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}
	
	@Override
	public List<Uloga> findAll() {
		String sql = "SELECT id, naziv FROM uloge";
		List<Uloga> result = namedParameterJdbcTemplate.query(sql, new UlogaMapper());

		return result;
	}
	
	@Override
	public void delete(Integer id) {

		//korisnikService.delete(id);
		
		String sql = "DELETE FROM uloge WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
		
	}

	@Override
	public void save(Uloga uloga) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO uloge(naziv) values (:naziv)";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(uloga), keyHolder);
		
		uloga.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Uloga uloga) {
		String sql = "UPDATE uloge SET naziv = :naziv WHERE id = :id";
		
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(uloga));
	}

	public class UlogaMapper implements RowMapper<Uloga> {
		public Uloga mapRow(ResultSet rs, int rowNum) throws SQLException {
			Uloga uloga = new Uloga();
		    uloga.setId(rs.getInt("id"));
		    uloga.setNaziv(rs.getString("naziv"));      
		    return uloga;
		}
	}
	
	private SqlParameterSource getSqlParameterByModel(Uloga uloga) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", uloga.getId());
		paramSource.addValue("naziv", uloga.getNaziv());

		return paramSource;
	}

}
