package ba.unsa.etf.dao;

import java.util.List;

import ba.unsa.etf.model.User;

public interface UserDao {

	User findById(Integer id);

	List<User> findAll();

	void save(User user);

	void update(User user);

	void delete(Integer id);

}