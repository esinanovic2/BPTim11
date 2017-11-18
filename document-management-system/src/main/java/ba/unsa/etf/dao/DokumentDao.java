package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.Dokument;

public interface DokumentDao {
	
	Dokument findById(Integer id);
	
	Dokument findByNaziv(String naziv);

	List<Dokument> findAll();

	void save(Dokument dokument);

	void update(Dokument dokument);

	void delete(Integer id);
	
}