package ba.unsa.etf.baze.tim11.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UlogaJDBCTemplate implements UlogaDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
	      this.dataSource = ds;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(String naziv) {
	      String SQL = "insert into uloge (naziv) values (?)";
	      jdbcTemplateObject.update(SQL, naziv);
	      System.out.println("Created Record Name = " + naziv);
	      return;
	}

	public Uloga getUloga(Integer id) {
	      String SQL = "select * from uloge where id = ?";
	      Uloga uloga = jdbcTemplateObject.queryForObject(SQL, 
	         new Object[]{id}, new UlogaMapper());
	      
	      return uloga;
	}

	public List<Uloga> listUloge() {
	      String SQL = "select * from uloge";
	      List <Uloga> uloge = jdbcTemplateObject.query(SQL, new UlogaMapper());
	      return uloge;
	}

	public void delete(Integer id) {
	      String SQL = "delete from uloge where id = ?";
	      jdbcTemplateObject.update(SQL, id);
	      System.out.println("Deleted Record with ID = " + id );
	      return;
	}

	public void update(Integer id, String naziv) {
	      String SQL = "update uloge set naziv = ? where id = ?";
	      jdbcTemplateObject.update(SQL, naziv, id);
	      System.out.println("Updated Record with ID = " + id );
	      return;
	}
}
