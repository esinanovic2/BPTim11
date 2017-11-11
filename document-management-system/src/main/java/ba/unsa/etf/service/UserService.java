package ba.unsa.etf.service;

import java.util.List;

import ba.unsa.etf.model.User;

public interface UserService {

	User findById(Integer id);
	
	List<User> findAll();

	void saveOrUpdate(User user);
	
	void delete(int id);
	
}