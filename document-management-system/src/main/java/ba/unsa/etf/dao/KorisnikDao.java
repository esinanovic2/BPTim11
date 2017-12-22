package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.Korisnik;

public interface KorisnikDao {
	
	Korisnik findById(Integer id);
	
	Korisnik findByUsernameAndPassword(String username, String password);
	
	Korisnik findByUsername(String username);

	List<Korisnik> findAll();

	void save(Korisnik korisnik);

	void update(Korisnik korisnik);

	void delete(Integer id);

	List<Korisnik> findUsersWithRole(Integer roleId);
}
