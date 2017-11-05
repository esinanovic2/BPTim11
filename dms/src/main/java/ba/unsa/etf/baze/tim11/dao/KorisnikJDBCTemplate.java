package ba.unsa.etf.baze.tim11.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class KorisnikJDBCTemplate implements KorisnikDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(String ime, String prezime, Integer uloga) {
	      String SQL = "insert into korisnici (ime, prezime, uloga) values (?, ?, ?)";
	      jdbcTemplateObject.update( SQL, ime, prezime, uloga);
	      System.out.println("Created Record Ime = " + ime + " Prezime = " + prezime + " Uloga = " + uloga);
	      return;
	}
	
	public void create(Korisnik k) {
	      String SQL = "insert into korisnici (ime, prezime, uloga) values (?, ?, ?)";
	      jdbcTemplateObject.update( SQL, k.getIme(), k.getPrezime(), k.getUloga());
	      System.out.println("Created Record Ime = " + k.getIme() + " Prezime = " + k.getPrezime() + " Uloga = " + k.getUloga());
	      return;
	}

	public Korisnik getKorisnik(Integer id) {
	      String SQL = "select * from korisnici where id = ?";
	      Korisnik korisnik = jdbcTemplateObject.queryForObject(SQL, 
	         new Object[]{id}, new KorisnikMapper());
	      
	      return korisnik;
	}

	public List<Korisnik> listKorisnici() {
	      String SQL = "select * from korisnici";
	      List <Korisnik> korisnici = jdbcTemplateObject.query(SQL, new KorisnikMapper());
	      return korisnici;
	}

	public void delete(Integer id) {
	      String SQL = "delete from korisnici where id = ?";
	      jdbcTemplateObject.update(SQL, id);
	      System.out.println("Deleted Record with ID = " + id );
	      return;
	}

	public void update(Integer id, String ime, String prezime, Integer uloga) {
	      String SQL = "update korisnici set ime = ? prezime = ? uloga = ? where id = ?";
	      jdbcTemplateObject.update(SQL, ime, prezime, uloga, id);
	      System.out.println("Updated Record with ID = " + id );
	      return;
	}

}
