package ba.unsa.etf.service;

import ba.unsa.etf.model.Vidljivost;
import java.util.List;

public interface VidljivostService {
	
	Vidljivost findById(Integer id);
	
	List<Vidljivost> findAll();
	
	void saveOrUpdate(Vidljivost vidljivost);
	
	void delete (Integer id);

}
