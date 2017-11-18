package ba.unsa.etf.service;

import java.util.List;

import ba.unsa.etf.model.Uloga;

public interface UlogaService {
	
	Uloga findById(Integer id);
	
	Uloga findByNaziv(String naziv);

	List<Uloga> findAll();

	void saveOrUpdate(Uloga uloga);

	void delete(Integer id);
}
