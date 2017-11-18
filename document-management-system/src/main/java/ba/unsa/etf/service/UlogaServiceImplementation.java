package ba.unsa.etf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.dao.UlogaDao;
import ba.unsa.etf.model.Uloga;

@Service("ulogaService")
public class UlogaServiceImplementation implements UlogaService{

	UlogaDao ulogaDao;
	
	@Autowired
	public void setUlogaDao(UlogaDao ulogaDao) {
		this.ulogaDao = ulogaDao;
	}
	
	@Override
	public Uloga findById(Integer id) {
		return ulogaDao.findById(id);
	}

	@Override
	public Uloga findByNaziv(String naziv) {
		return ulogaDao.findByNaziv(naziv);
	}

	@Override
	public List<Uloga> findAll() {
		return ulogaDao.findAll();
	}

	@Override
	public void saveOrUpdate(Uloga uloga) {
		if (findById(uloga.getId())==null) {
			ulogaDao.save(uloga);
		} else {
			ulogaDao.update(uloga);
		}	
	}

	@Override
	public void delete(Integer id) {
		ulogaDao.delete(id);
	}

}
