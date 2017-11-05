package ba.unsa.etf.baze.tim11.dms;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//((ConfigurableApplicationContext)context).close(); Za zatvaranje contexta

public class App {
	   public static void main(String[] args) {
		      ConfigurableApplicationContext context = 
		    	         new ClassPathXmlApplicationContext("Beans.xml");
    	 }
}