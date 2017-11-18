package ba.unsa.etf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.dao.VidljivostDao;
import ba.unsa.etf.model.Vidljivost;

@Service("vidljivostService")
public class VidljivostServiceImplementation implements VidljivostService{
	
	VidljivostDao vidljivostDao;
	
	@Autowired
	public void vidljivostDao(VidljivostDao vidljivostDao)
	{
		this.vidljivostDao=vidljivostDao;
	}

	@Override
	public Vidljivost findById(Integer id) {
		return vidljivostDao.findById(id);
	}

	@Override
	public List<Vidljivost> findAll() {
		return vidljivostDao.findAll();
	}

	@Override
	public void saveOrUpdate(Vidljivost vidljivost) {
		if (findById(vidljivost.getId())==null)
		{
			vidljivostDao.save(vidljivost);
		}
		else
		{
			vidljivostDao.update(vidljivost);
		}
	}

	@Override
	public void delete(Integer id) {
		vidljivostDao.delete(id);		
	}

}
