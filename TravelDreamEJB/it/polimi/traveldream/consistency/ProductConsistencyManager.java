/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.consistency;

import java.util.Date;
import java.util.List;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.ejbmanagers.ProductManagerBean;
import it.polimi.traveldream.exception.ConsistencyException;
/********************************************************
 * Classes
 *********************************************************/
public class ProductConsistencyManager {
	//Attributes	 
	private ProductManagerBean pmb;
	// Constructors
	public ProductConsistencyManager(ProductManagerBean pmb) {
		this.pmb = pmb;
	}
	// Consistency Methods
	/*
	 * Execute specific checks for different types of products
	 */
	public void newProductConsistency(ProductDTO productDTO)
			throws ConsistencyException {
		if (productDTO instanceof HotelDTO)
			checkNewHotel((HotelDTO) productDTO);
		if (productDTO instanceof FlightDTO)
			checkNewFlight((FlightDTO) productDTO);
		if (productDTO instanceof ExcursionDTO)
			checkNewExcursion((ExcursionDTO) productDTO);
	}
	/**
	 * 
	 * @param hotelDTO
	 * @throws ConsistencyException
	 */
	private void checkNewHotel(HotelDTO hotelDTO) throws ConsistencyException {

		System.out.print("ProductConsistency: checkNewHotel");
		List<ProductDTO> sameNameProducts = pmb.findByName(hotelDTO.getName());
		System.out.print("ProductConsistency: sameNameProducts: "
				+ sameNameProducts.toString());

		// The product exists
		if (sameNameProducts.size() > 0) {
			for (int i = 0; i < sameNameProducts.size(); i++) {
				HotelDTO sameNameHotel = (HotelDTO) sameNameProducts.get(i);
				// Check if hotel has the same city
				if (sameNameHotel.getCityName().equals(hotelDTO.getCityName())) {
					System.out.print("ProductConsistency: EXISTING_PRODUCT");
					throw new ConsistencyException(
							ConsistencyException.EXISTING_PRODUCT);
				}
			}
		}
	}
	/**
	 * 
	 * @param flightDTO
	 * @throws ConsistencyException
	 */
	private void checkNewFlight(FlightDTO flightDTO)
			throws ConsistencyException {

		System.out.print("ProductConsistency: checkNewFlight");
		List<ProductDTO> sameNameProducts = pmb.findByName(flightDTO.getName());
		// The product exists
		if (sameNameProducts.size() > 0) {
			throw new ConsistencyException(
					ConsistencyException.EXISTING_PRODUCT);
		}
		// date > TODAY
		if (flightDTO.getDate().before(new Date())) {
			throw new ConsistencyException(ConsistencyException.BEFORE_TODAY);
		}
		// citta1 != citta2
		if (flightDTO.getCity1Name().equals(flightDTO.getCity2Name())) {
			throw new ConsistencyException(
					ConsistencyException.SAME_CITY1_CITY2);
		}
	}
	/**
	 * 
	 * @param excursionDTO
	 * @throws ConsistencyException
	 */
	private void checkNewExcursion(ExcursionDTO excursionDTO)
			throws ConsistencyException {

		System.out.print("ProductConsistency: checkNewHExcursion");
		List<ProductDTO> sameNameProducts = pmb.findByName(excursionDTO
				.getName());
		// The product exists
		if (sameNameProducts.size() > 0) {
			for (int i = 0; i < sameNameProducts.size(); i++) {
				ExcursionDTO sameNameExcursion = (ExcursionDTO) sameNameProducts
						.get(i);

				// Check if escursioni have the same date
				if (excursionDTO.getDate().compareTo(
						sameNameExcursion.getDate()) == 0) {
					if (excursionDTO.getCityName().equals(
							sameNameExcursion.getCityName())) {
						throw new ConsistencyException(
								ConsistencyException.EXISTING_PRODUCT);
					}
				}
			}
		}
		// date > TODAY
		if (excursionDTO.getDate().before(new Date())) {
			throw new ConsistencyException(ConsistencyException.BEFORE_TODAY);
		}
	}
}
