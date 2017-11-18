package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.Uloga;

public interface UlogaDao {
	
	Uloga findById(Integer id);
	
	Uloga findByNaziv(String naziv);

	List<Uloga> findAll();

	void save(Uloga uloga);

	void update(Uloga uloga);

	void delete(Integer id);

}