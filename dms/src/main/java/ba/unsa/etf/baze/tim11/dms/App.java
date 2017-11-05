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
		    
		    System.out.println("------Delete extra uloga--------" );
		    ulogaJDBCTemplate.delete(5);

    	 }
}