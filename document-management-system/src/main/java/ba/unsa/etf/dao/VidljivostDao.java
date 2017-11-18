package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.Vidljivost;

public interface VidljivostDao {
	
	Vidljivost findById(Integer id);
	
	List<Vidljivost> findAll();

	void save(Vidljivost vidljivost);

	void update(Vidljivost vidljivost);

	void delete(Integer id);

}
