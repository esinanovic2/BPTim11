package ba.unsa.etf.baze.tim11.dms;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ba.unsa.etf.baze.tim11.dao.Uloga;
import ba.unsa.etf.baze.tim11.dao.UlogaJDBCTemplate;


//((ConfigurableApplicationContext)context).close(); Za zatvaranje contexta

public class App {
	   public static void main(String[] args) {
		    ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		    UlogaJDBCTemplate ulogaJDBCTemplate = (UlogaJDBCTemplate)context.getBean("ulogaJDBCTemplate");
		    
		    System.out.println("------Records Creation--------" );
		    ulogaJDBCTemplate.create("Administrator2");

		    System.out.println("------Listing Multiple Records--------" );
		    List<Uloga> uloge = ulogaJDBCTemplate.listUloge();
		    
		    for (Uloga record : uloge) {
		       System.out.print("ID : " + record.getId() );
		       System.out.print(", Name : " + record.getNaziv() );
		    }

		    System.out.println("----Updating Record with ID = 2 -----" );
		    ulogaJDBCTemplate.update(5,"Admin2");

		    System.out.println("----Listing Record with ID = 2 -----" );
		    Uloga uloga = ulogaJDBCTemplate.getUloga(5);
		    System.out.print("ID : " + uloga.getId() );
		    System.out.print(", Name : " + uloga.getNaziv() );
    	 }
}