package ba.unsa.etf.dao;

import java.util.List;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
import ba.unsa.etf.model.Dokument;

@Repository
public class DokumentDaoImplementation implements DokumentDao {
		
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Dokument findById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM dokumenti WHERE id=:id";

		Dokument result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new DokumentMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}

	@Override
	public Dokument findByNaziv(String naziv) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("naziv", naziv);
		
		String sql = "SELECT * FROM dokumenti WHERE naziv=:naziv";

		Dokument result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new DokumentMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}

	@Override
	public List<Dokument> findAll() {
		String sql = "SELECT * FROM dokumenti";
		List<Dokument> result = namedParameterJdbcTemplate.query(sql, new DokumentMapper());
		
		return result;
	}
	
	@Override
	public List<Dokument> findDocumentsByUserId(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vlasnik", id);
		
		String sql = "SELECT * FROM dokumenti WHERE vlasnik=:vlasnik";
		
		List<Dokument> result = namedParameterJdbcTemplate.query(sql, params, new DokumentMapper());
		
		return result;
	}
	
	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM dokumenti WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
	}

	@Override
	public void save(Dokument dokument) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO dokumenti(naziv, contenttype, extenzija, fajl, vlasnik, vidljivost) values (:naziv, :contenttype, :extenzija, :fajl, :vlasnik, :vidljivost)";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(dokument), keyHolder);
		
		dokument.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Dokument dokument) {
		String sql = "UPDATE dokumenti SET naziv = :naziv, fajl = :fajl, vlasnik = :vlasnik, vidljivost = :vidljivost WHERE id = :id";
		
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(dokument));
	}
	
	public class DokumentMapper implements RowMapper<Dokument> {
		public Dokument mapRow(ResultSet rs, int rowNum) throws SQLException {
			Dokument dokument = new Dokument();
		    dokument.setId(rs.getInt("id"));
		    String naziv=rs.getString("naziv");
		    dokument.setNaziv(naziv);
		    String contentType=rs.getString("contenttype");
		    dokument.setContentType(contentType);
		    String extenzija=rs.getString("extenzija");
		    dokument.setExtenzija(extenzija);
		    InputStream is=rs.getBinaryStream("fajl");
		    dokument.setFajlDrugi(is);
		    dokument.setVlasnik(rs.getInt("vlasnik"));
		    dokument.setVidljivost(rs.getInt("vidljivost"));
		    return dokument;
		}
	}
	
	private SqlParameterSource getSqlParameterByModel(Dokument dokument) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", dokument.getId());
		paramSource.addValue("naziv", dokument.getNaziv());
		paramSource.addValue("fajl", dokument.getFajl());
		paramSource.addValue("vlasnik", dokument.getVlasnik());
		paramSource.addValue("vidljivost", dokument.getVidljivost());
		paramSource.addValue("contenttype", dokument.getContentType());
		paramSource.addValue("extenzija", dokument.getExtenzija());
		
		
		return paramSource;
	}

}
