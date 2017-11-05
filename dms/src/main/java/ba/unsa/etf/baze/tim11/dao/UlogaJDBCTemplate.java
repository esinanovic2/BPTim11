package ba.unsa.etf.baze.tim11.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UlogaJDBCTemplate implements UlogaDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
	      this.dataSource = dataSource;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(String naziv) {
	      String SQL = "insert into uloge (naziv) values (?)";
	      jdbcTemplateObject.update( SQL, naziv);
	      System.out.println("Created Record Name = " + naziv);
	      return;
	}

	public Uloga getUloga(Integer id) {

		return null;
	}

	public List<Uloga> listUloge() {

		return null;
	}

	public void delete(Integer id) {

	}

	public void update(Integer id, Integer age) {

	}

}
