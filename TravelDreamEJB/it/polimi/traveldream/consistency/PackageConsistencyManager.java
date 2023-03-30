/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.consistency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.ejbmanagers.PackageManagerBean;
import it.polimi.traveldream.ejbmanagers.PersPackageManagerBean;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.persistence.PersPackage;
/********************************************************
 * Classes
 *********************************************************/
public class PackageConsistencyManager {
	// Attributes
	private static Logger logger = Logger
			.getLogger(PackageConsistencyManager.class.getName());
	private PackageManagerBean pmb;
	private PersPackageManagerBean ppmb;
	// Constructors
	public PackageConsistencyManager(PackageManagerBean pmb) {
		this.pmb = pmb;
	}
	public PackageConsistencyManager(PersPackageManagerBean ppmb) {
		this.ppmb = ppmb;
	}
	// Methods
	public void newPackageConsistency(DefaultPackageDTO defaultPackageDTO)
			throws ConsistencyException {

		ArrayList<HotelDTO> hotels = new ArrayList<HotelDTO>();
		ArrayList<FlightDTO> flights = new ArrayList<FlightDTO>();
		ArrayList<ExcursionDTO> excursions = new ArrayList<ExcursionDTO>();

		for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
			if (productDTO instanceof HotelDTO) {
				hotels.add((HotelDTO) productDTO);
			}
			if (productDTO instanceof FlightDTO) {
				flights.add((FlightDTO) productDTO);
			}
			if (productDTO instanceof ExcursionDTO) {
				excursions.add((ExcursionDTO) productDTO);
			}
		}
		Date startDate = defaultPackageDTO.getStartDate();
		Date endDate = defaultPackageDTO.getEndDate();
		
		logger.info("STARTDATE: " +startDate);
		logger.info("ENDDATE: " + endDate);

		FlightDTO startFlightDTO;
		FlightDTO endFlightDTO;
		/*
		 * FLIGHT CONSTRAINTS
		 */
		if (flights.size() != 2) {
			logger.info("FLIGHT_SIZE: " + ((Integer) flights.size()).toString());
			logger.info("WRONG_FLIGHTS_NUM");
			throw new ConsistencyException(
					ConsistencyException.WRONG_FLIGHTS_NUM);
		}
		if (flights.get(0).getDate().equals(startDate)) {
			startFlightDTO = (flights).get(0);
			endFlightDTO = (flights).get(1);
		} else {
			startFlightDTO = (flights).get(1);
			endFlightDTO = (flights).get(0);
		}
		logger.info("STARTFLIGHT city1: " + startFlightDTO.getCity1Name());
		logger.info("STARTFLIGHT city2: " + startFlightDTO.getCity2Name());
		logger.info("STARTFLIGHT date: " + startFlightDTO.getDate().toString());

		logger.info("ENDFLIGHT city1: " + endFlightDTO.getCity1Name());
		logger.info("ENDFLIGHT city2: " + endFlightDTO.getCity2Name());
		logger.info("ENDFLIGHT date: " + endFlightDTO.getDate().toString());
		
		String homeCity = startFlightDTO.getCity1Name();
		String vacationCity = startFlightDTO.getCity2Name();
		
		logger.info("vacationCity: " + vacationCity);
		logger.info("homeCity: " + homeCity);

		if (!endFlightDTO.getCity1Name().equals(vacationCity)
				|| !endFlightDTO.getCity2Name().equals(homeCity)) {
			logger.info("WRONG_FLIGHT_CITIES");
			throw new ConsistencyException(
					ConsistencyException.WRONG_FLIGHT_CITIES);
		}
		/*
		 * HOTEL CONSTRAINTS
		 */
		if (hotels.size() != 1) {
			logger.info("HOTELS_SIZE: " + ((Integer) hotels.size()).toString());
			logger.info("WRONG_HOTELS_NUM");
			throw new ConsistencyException(
					ConsistencyException.WRONG_HOTELS_NUM);
		}
		HotelDTO hotelDTO = hotels.get(0);

		if (!hotelDTO.getCityName().equals(vacationCity)) {
			logger.info("WRONG_HOTEL_CITY");
			throw new ConsistencyException(
					ConsistencyException.WRONG_HOTEL_CITY);
		}
		/*
		 * EXCURSION CONSTRAINTS
		 */
		if (excursions.size() > 0) {
			for (ExcursionDTO excursionDTO : excursions) {
				String excursionCity = excursionDTO.getCityName();
				Date excursionDate = excursionDTO.getDate();

				if (!excursionCity.equals(vacationCity)) {
					logger.info("WRONG_EXCURSION_CITY");
					throw new ConsistencyException(
							ConsistencyException.WRONG_EXCURSION_CITY);
				}
				if (excursionDate.before(startDate)
						|| excursionDate.after(endDate)) {
					logger.info("WRONG_EXCURSION_DATE");
					throw new ConsistencyException(
							ConsistencyException.WRONG_EXCURSION_DATE);
				}
			}
		}
	}
	
	public void persPackageConsistency(PersPackageDTO persPackageDTO)
			throws ConsistencyException {

		ArrayList<HotelDTO> hotels = new ArrayList<HotelDTO>();
		ArrayList<FlightDTO> flights = new ArrayList<FlightDTO>();
		ArrayList<ExcursionDTO> excursions = new ArrayList<ExcursionDTO>();

		for (ProductDTO productDTO : persPackageDTO.getProductDTOs()) {
			if (productDTO instanceof HotelDTO) {
				hotels.add((HotelDTO) productDTO);
			}
			if (productDTO instanceof FlightDTO) {
				flights.add((FlightDTO) productDTO);
			}
			if (productDTO instanceof ExcursionDTO) {
				excursions.add((ExcursionDTO) productDTO);
			}
		}

		Date startDate = persPackageDTO.getStartDate();
		Date endDate = persPackageDTO.getEndDate();
		
		logger.info("STARTDATE: " +startDate);
		logger.info("ENDDATE: " + endDate);

		FlightDTO startFlightDTO;
		FlightDTO endFlightDTO;
		/*
		 * FLIGHT CONSTRAINTS
		 */
		if (flights.size() != 2) {
			logger.info("FLIGHT_SIZE: " + ((Integer) flights.size()).toString());
			logger.info("WRONG_FLIGHTS_NUM");
			throw new ConsistencyException(
					ConsistencyException.WRONG_FLIGHTS_NUM);
		}

		if (flights.get(0).getDate().equals(startDate)) {
			startFlightDTO = (flights).get(0);
			endFlightDTO = (flights).get(1);
		} else {
			startFlightDTO = (flights).get(1);
			endFlightDTO = (flights).get(0);
		}

		logger.info("STARTFLIGHT city1: " + startFlightDTO.getCity1Name());
		logger.info("STARTFLIGHT city2: " + startFlightDTO.getCity2Name());
		logger.info("STARTFLIGHT date: " + startFlightDTO.getDate().toString());

		logger.info("ENDFLIGHT city1: " + endFlightDTO.getCity1Name());
		logger.info("ENDFLIGHT city2: " + endFlightDTO.getCity2Name());
		logger.info("ENDFLIGHT date: " + endFlightDTO.getDate().toString());
		
		String homeCity = startFlightDTO.getCity1Name();
		String vacationCity = startFlightDTO.getCity2Name();
		
		logger.info("vacationCity: " + vacationCity);
		logger.info("homeCity: " + homeCity);

		if (!endFlightDTO.getCity1Name().equals(vacationCity)
				|| !endFlightDTO.getCity2Name().equals(homeCity)) {
			logger.info("WRONG_FLIGHT_CITIES");
			throw new ConsistencyException(
					ConsistencyException.WRONG_FLIGHT_CITIES);
		}
		/*
		 * HOTEL CONSTRAINTS
		 */
		if (hotels.size() != 1) {
			logger.info("HOTELS_SIZE: " + ((Integer) hotels.size()).toString());
			logger.info("WRONG_HOTELS_NUM");
			throw new ConsistencyException(
					ConsistencyException.WRONG_HOTELS_NUM);
		}
		HotelDTO hotelDTO = hotels.get(0);

		if (!hotelDTO.getCityName().equals(vacationCity)) {
			logger.info("WRONG_HOTEL_CITY");
			throw new ConsistencyException(
					ConsistencyException.WRONG_HOTEL_CITY);
		}
		/*
		 * EXCURSION CONSTRAINTS
		 */
		if (excursions.size() > 0) {
			for (ExcursionDTO excursionDTO : excursions) {
				String excursionCity = excursionDTO.getCityName();
				Date excursionDate = excursionDTO.getDate();

				if (!excursionCity.equals(vacationCity)) {
					logger.info("WRONG_EXCURSION_CITY");
					throw new ConsistencyException(
							ConsistencyException.WRONG_EXCURSION_CITY);
				}
				if (excursionDate.before(startDate)
						|| excursionDate.after(endDate)) {
					logger.info("WRONG_EXCURSION_DATE");
					throw new ConsistencyException(
							ConsistencyException.WRONG_EXCURSION_DATE);
				}
			}
		}
	}
}
