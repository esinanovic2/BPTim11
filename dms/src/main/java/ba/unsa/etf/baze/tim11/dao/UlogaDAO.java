package ba.unsa.etf.baze.tim11.dao;
import java.util.List;
import javax.sql.DataSource;

public interface UlogaDAO {
	   /** 
	    * This is the method to be used to initialize
	    * database resources ie. connection.
	    */
	public void setDataSource(DataSource ds);
	
	   /** 
	     * This is the method to be used to create
	     * a record in the Uloge table.
	     */
	public void create(String naziv);
	
	   /** 
	     * This is the method to be used to list down
	     * a record from the Uloga table corresponding
	     * to a passed uloga id.
	     */
	public Uloga getUloga(Integer id);
	
	   /** 
	     * This is the method to be used to list down
	     * all the records from the Uloga table.
	     */
	public List<Uloga> listUloge();
	
	   /** 
	     * This is the method to be used to delete
	     * a record from the Student table corresponding
	     * to a passed student id.
	     */
	public void delete(Integer id);
	
	   /** 
	     * This is the method to be used to update
	     * a record into the Student table.
	     */
	public void update(Integer id, Integer age);
	
}
