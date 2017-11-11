package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.Korisnik;

public interface KorisnikDao {
	
	Korisnik findById(Integer id);

	List<Korisnik> findAll();

	void save(Korisnik korisnik);

	void update(Korisnik korisnik);

	void delete(Integer id);
}
