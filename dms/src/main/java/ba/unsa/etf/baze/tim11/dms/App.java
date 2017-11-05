package ba.unsa.etf.baze.tim11.dms;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ba.unsa.etf.baze.tim11.dao.Korisnik;
import ba.unsa.etf.baze.tim11.dao.KorisnikJDBCTemplate;
import ba.unsa.etf.baze.tim11.dao.Uloga;
import ba.unsa.etf.baze.tim11.dao.UlogaJDBCTemplate;


//((ConfigurableApplicationContext)context).close(); Za zatvaranje contexta

public class App {
	   public static void main(String[] args) {
		   ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		   KorisnikJDBCTemplate korisnikJDBCTemplate = 
			         (KorisnikJDBCTemplate) context.getBean("korisnikJDBCTemplate");
			      
			      System.out.println("------Records Creation--------" );
			      korisnikJDBCTemplate.create("AAA","BBB", 2);


			      System.out.println("------Listing Multiple Records--------" );
			      List<Korisnik> korisnici = korisnikJDBCTemplate.listKorisnici();
			      
			      for (Korisnik record : korisnici) {
			         System.out.print("ID : " + record.getId() );
			         System.out.print(", Ime : " + record.getIme() );
			         System.out.println(", Prezime : " + record.getPrezime());
			      }
    	 }
}