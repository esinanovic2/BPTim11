package ba.unsa.etf.service;

import java.util.List;

import ba.unsa.etf.model.Korisnik;

public interface KorisnikService {

	Korisnik findById(Integer id);

	List<Korisnik> findAll();

	void saveOrUpdate(Korisnik korisnik);

	void delete(Integer id);
}
