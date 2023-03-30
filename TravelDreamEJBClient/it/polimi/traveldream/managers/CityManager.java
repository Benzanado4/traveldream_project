/********************************************************
 * Declarations 
 *********************************************************/
package it.polimi.traveldream.managers;

import java.util.List;
import javax.ejb.Local;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface CityManager {
	
	public List<String> getAllCitiesNames();
	
	
}
