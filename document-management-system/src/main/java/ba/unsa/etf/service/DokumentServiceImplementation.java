package ba.unsa.etf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.dao.DokumentDao;
import ba.unsa.etf.model.Dokument;

@Service("dokumentService")
public class DokumentServiceImplementation implements DokumentService {
	
	DokumentDao dokumentDao;
	
	@Autowired
	public void setDokumentkDao(DokumentDao dokumentDao){
		this.dokumentDao = dokumentDao;
	}

	@Override
	public Dokument findById(Integer id) {
		return dokumentDao.findById(id);
	}

	@Override
	public List<Dokument> findAll() {
		return dokumentDao.findAll();
	}
	
	@Override
	public List<Dokument> findDocumentsByUserId(Integer id) {
		return dokumentDao.	findDocumentsByUserId(id);
	}

	@Override
	public void saveOrUpdate(Dokument dokument) {
		if (findById(dokument.getId())==null) {
			dokumentDao.save(dokument);
		} else {
			dokumentDao.update(dokument);
		}
	}

	@Override
	public void delete(Integer id) {
		dokumentDao.delete(id);
	}

}
