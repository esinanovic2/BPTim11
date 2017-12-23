package ba.unsa.etf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.dao.KorisnikDao;
import ba.unsa.etf.model.Korisnik;

@Service("korisnikService")
public class KorisnikServiceImplementation implements KorisnikService {

	KorisnikDao korisnikDao;
	
	@Autowired
	public void setKorisnikDao(KorisnikDao korisnikDao) {
		this.korisnikDao = korisnikDao;
	}
	
	@Override
	public Korisnik findById(Integer id) {
		return korisnikDao.findById(id);	
	}

	@Override
	public List<Korisnik> findAll() {
		return korisnikDao.findAll();
	}
	
	@Override
	public List<Korisnik> findUsersWithRole(Integer roleId) {
		return korisnikDao.findUsersWithRole(roleId);
	}

	@Override
	public void saveOrUpdate(Korisnik korisnik) {
		if (findById(korisnik.getId())==null) {
			korisnikDao.save(korisnik);
		} else {
			korisnikDao.update(korisnik);
		}
	}

	@Override
	public void delete(Integer id) {
		korisnikDao.delete(id);
	}
	
	@Override
	public Korisnik findByUsername(String username) {
		return korisnikDao.findByUsername(username);
	}

	@Override
	public Korisnik findByUsernameAndPassword(String username, String password) {
		return korisnikDao.findByUsernameAndPassword(username, password);
	}
}
