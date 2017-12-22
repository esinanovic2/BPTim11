package ba.unsa.etf.service;

import java.util.List;

import ba.unsa.etf.model.Dokument;

public interface DokumentService {
	
	Dokument findById(Integer id);
	
	List<Dokument> findAll();
	
	void saveOrUpdate(Dokument dokument);
	
	void delete(Integer id);

	List<Dokument> findDocumentsByUserId(Integer id); 

}
