/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import java.util.ArrayList;
import java.util.List;
import it.polimi.traveldream.managers.CityManager;
import it.polimi.traveldream.persistence.City;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class CityManagerBean implements CityManager{

	@PersistenceContext
	private EntityManager em;	
	/*
	 * Esegue la namedQuery City.FIND_ALL e restituisce la lista convertita di String
	 */
	@Override
	public List<String> getAllCitiesNames(){
		
		List<String> cityNames = new ArrayList<String>();
		List<City> cityList = new ArrayList<City>();
		
		cityList = em.createNamedQuery(City.FIND_ALL, City.class).getResultList();
		
		for(int i = 0; i < cityList.size(); i++){
			cityNames.add(cityList.get(i).getCityName());
		}
		return cityNames;
	}
	
}
