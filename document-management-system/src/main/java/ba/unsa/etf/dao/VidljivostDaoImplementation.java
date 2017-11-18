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

import ba.unsa.etf.model.Vidljivost;

@Repository
public class VidljivostDaoImplementation implements VidljivostDao {
	
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public Vidljivost findById(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM vidljivosti WHERE id=:id";

		Vidljivost result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new VidljivostkMapper());
		} catch (EmptyResultDataAccessException e) {}

		return result;
	}

	@Override
	public List<Vidljivost> findAll() {
		String sql = "SELECT * FROM vidljivosti";
		List<Vidljivost> result = namedParameterJdbcTemplate.query(sql, new VidljivostkMapper());

		return result;
	}

	@Override
	public void save(Vidljivost vidljivost) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String sql = "INSERT INTO vidljivosti(naziv) values (:naziv)";
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(vidljivost), keyHolder);
		
		vidljivost.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Vidljivost vidljivost) {
		String sql = "UPDATE vidljivosti SET naziv = :naziv WHERE id = :id";
		
		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(vidljivost));	
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM vidljivosti WHERE id= :id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));		
	}
	
	public class VidljivostkMapper implements RowMapper<Vidljivost> 
	{
		public Vidljivost mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			Vidljivost vidljivost = new Vidljivost();
		    vidljivost.setId(rs.getInt("id"));
		    vidljivost.setNaziv(rs.getString("naziv"));
		   
		    return vidljivost;
		}
	}
	
	private SqlParameterSource getSqlParameterByModel(Vidljivost vidljivost) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", vidljivost.getId());
		paramSource.addValue("naziv", vidljivost.getNaziv());
		
		return paramSource;
	}

}
