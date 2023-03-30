/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.managers;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.CityNotFoundException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface ProductManager {

	/*
	 * Generic Products Methods
	 */
	
	public void addProduct(ProductDTO productDTO) throws CityNotFoundException, ConsistencyException;
	
	public void updateProduct(ProductDTO productDTO) throws NoIdException, CityNotFoundException, ConsistencyException;
	
	public void removeProduct(ProductDTO productDTO) throws NoIdException;
		
	public void removeProductById(int id);

	
	/*
	 * Generic Product Query Methods
	 */
	
	
	public List<ProductDTO> getAllProducts();
	
	public List<ProductDTO> findByName(String name);
	
	public ProductDTO findById(int productId);
	


	/*
	 * Hotel Query Methods
	 */
	
	public List<HotelDTO> getAllHotel();

	public List<ProductDTO> findHotelByCityName(String cityName);
	
	
	/*
	 * Flight Query Methods
	 */
	
	public List<FlightDTO> getAllFlight();

	public List<ProductDTO> findFlightByCity1Name(String city1Name);
	
	public List<ProductDTO> findFlightByCity2Name(String city1Name);

	public List<ProductDTO> findFlightByDate(Date date);
	
	
	/*
	 * Excursion Query Methods
	 */
	
	public List<ExcursionDTO> getAllExcursion();

	public List<ProductDTO> findExcursionByDate(Date date);
	
	public List<ProductDTO> findExcursionByCityName(String cityName);



		
}
