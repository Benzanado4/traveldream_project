/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import it.polimi.traveldream.consistency.ProductConsistencyManager;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.CityNotFoundException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.managers.ProductManager;
import it.polimi.traveldream.persistence.City;
import it.polimi.traveldream.persistence.Excursion;
import it.polimi.traveldream.persistence.Flight;
import it.polimi.traveldream.persistence.GiftList;
import it.polimi.traveldream.persistence.Group;
import it.polimi.traveldream.persistence.Hotel;
import it.polimi.traveldream.persistence.PersPackage;
import it.polimi.traveldream.persistence.Product;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostRemove;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class ProductManagerBean implements ProductManager {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	private ProductConsistencyManager pcm;

	@PostConstruct
	private void init() {
		pcm = new ProductConsistencyManager(this);
	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void addProduct(ProductDTO productDTO) throws CityNotFoundException,
			ConsistencyException {

		System.out.print("ProductManagerBean: addProduct("
				+ productDTO.toString() + ")");

		pcm.newProductConsistency(productDTO);

		// Creo un nuovo Product generico
		Product newProduct = new Product(productDTO);

		// A seconda del DTO specializzo il Product creando e inserendo nel
		// database un nuovo prodotto
		// specifico collegato tramite la relazione sull id al prodotto generico
		// precedentemente creato

		// HOTEL
		if (productDTO instanceof HotelDTO) {

			em.persist(newProduct);

			Hotel newHotel = new Hotel((HotelDTO) productDTO);
			City city = findCity(((HotelDTO) productDTO).getCityName());

			newHotel.setProduct(newProduct);
			newHotel.setCity(city);

			em.persist(newHotel);

		}

		// FLIGHT
		if (productDTO instanceof FlightDTO) {

			em.persist(newProduct);

			Flight newFlight = new Flight((FlightDTO) productDTO);
			City city1 = findCity(((FlightDTO) productDTO).getCity1Name());
			City city2 = findCity(((FlightDTO) productDTO).getCity2Name());

			newFlight.setProduct(newProduct);
			newFlight.setCity1(city1);
			newFlight.setCity2(city2);

			em.persist(newFlight);

		}

		// EXCURSION
		if (productDTO instanceof ExcursionDTO) {

			em.persist(newProduct);

			Excursion newExcursion = new Excursion((ExcursionDTO) productDTO);

			// Trovo la citta corrispondente all'id prensente nel DTO
			City city = findCity(((ExcursionDTO) productDTO).getCityName());

			newExcursion.setProduct(newProduct);
			newExcursion.setCity(city);

			em.persist(newExcursion);

		}
	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void updateProduct(ProductDTO productDTO) throws NoIdException,
			CityNotFoundException, ConsistencyException {

		// Controllo che il DTO contenga l'id del prodotto.

		if (((Integer) productDTO.getProductId()) == null) {
			throw new NoIdException();
		}

		pcm.newProductConsistency(productDTO);

		Product product = new Product(productDTO);
		product.setProductId(productDTO.getProductId());

		em.merge(product);

		if (productDTO instanceof FlightDTO) {
			System.out.print("ProductManagerBean: FLIGHT");
			Flight flight = new Flight((FlightDTO) productDTO);

			City city1 = findCity(((FlightDTO) productDTO).getCity1Name());
			City city2 = findCity(((FlightDTO) productDTO).getCity2Name());

			flight.setProduct(product);
			flight.setCity1(city1);
			flight.setCity2(city2);

			em.merge(flight);
		}

		if (productDTO instanceof HotelDTO) {
			System.out.print("ProductManagerBean: HOTEL");

			Hotel hotel = new Hotel((HotelDTO) productDTO);
			City city = findCity(((HotelDTO) productDTO).getCityName());

			hotel.setCity(city);
			hotel.setProduct(product);

			em.merge(hotel);
		}

		if (productDTO instanceof ExcursionDTO) {
			System.out.print("ProductManagerBean: EXCURSION");

			Excursion excursion = new Excursion((ExcursionDTO) productDTO);
			City city = findCity(((ExcursionDTO) productDTO).getCityName());

			excursion.setCity(city);
			excursion.setProduct(product);

			em.merge(excursion);
		}

	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void removeProduct(ProductDTO productDTO) throws NoIdException {

		// Controllo che il DTO contenga l'id del prodotto.

		if (((Integer) productDTO.getProductId()) == null) {
			throw new NoIdException();
		}

		Product product = em.find(Product.class, productDTO.getProductId());

		// A seconda del tipo di prodotto cancello dalla tabella corrispondente
		// mettere null
		if (product.getExcursion() != null) {
			em.remove(em.find(Excursion.class, product.getProductId()));
		}

		if (product.getFlight() != null) {
			em.remove(em.find(Flight.class, product.getProductId()));
		}

		if (product.getHotel() != null) {
			em.remove(em.find(Hotel.class, product.getProductId()));
		}

	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void removeProductById(int id) {

		Product product = em.find(Product.class, id);

		// A seconda del tipo di prodotto cancello dalla tabella corrispondente
		// mettere null
		if (product.getExcursion() != null) {
			em.remove(em.find(Excursion.class, product.getProductId()));
		}

		if (product.getFlight() != null) {
			em.remove(em.find(Flight.class, product.getProductId()));
		}

		if (product.getHotel() != null) {
			em.remove(em.find(Hotel.class, product.getProductId()));
		}

	}

	/*
	 * Generic Product Query Methods
	 */

	@Override
	public List<ProductDTO> getAllProducts() {
		return convertoToListDTO(em.createNamedQuery(Product.FIND_ALL,
				Product.class).getResultList());
	}

	@Override
	public List<ProductDTO> findByName(String name) {
		return convertoToListDTO(em.createNamedQuery(Product.FIND_BY_NAME)
				.setParameter("name", name).getResultList());
	}

	@Override
	public ProductDTO findById(int id) {

		return convertToDTO(em.find(Product.class, id));

	}



	/*
	 * Hotel Query Methods
	 */

	@Override
	public List<HotelDTO> getAllHotel() {
		List<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();

		List<ProductDTO> productDTOs = getAllProducts();

		for (ProductDTO productDTO : productDTOs) {
			if (productDTO instanceof HotelDTO) {
				hotelDTOs.add((HotelDTO) productDTO);
			}
		}

		return hotelDTOs;
	}

	@Override
	public List<ProductDTO> findHotelByCityName(String cityName) {
		return convertoToListDTO(em.createNamedQuery(Hotel.FIND_BY_CITY)
				.setParameter("city", cityName).getResultList());
	}

	/*
	 * Flight Query Methods
	 */

	@Override
	public List<FlightDTO> getAllFlight() {

		List<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		List<ProductDTO> productDTOs = getAllProducts();

		for (ProductDTO productDTO : productDTOs) {
			if (productDTO instanceof FlightDTO) {
				flightDTOs.add((FlightDTO) productDTO);
			}
		}

		return flightDTOs;
	}

	@Override
	public List<ProductDTO> findFlightByCity1Name(String city1Name) {
		return convertoToListDTO(em.createNamedQuery(Flight.FIND_BY_CITY_1)
				.setParameter("city1", city1Name).getResultList());
	}

	@Override
	public List<ProductDTO> findFlightByCity2Name(String city2Name) {
		return convertoToListDTO(em.createNamedQuery(Flight.FIND_BY_CITY_2)
				.setParameter("city2", city2Name).getResultList());
	}

	@Override
	public List<ProductDTO> findFlightByDate(Date date) {
		return convertoToListDTO(em.createNamedQuery(Flight.FIND_BY_DATE)
				.setParameter("date", date).getResultList());
	}

	/*
	 * Excursion Query Methods
	 */

	@Override
	public List<ExcursionDTO> getAllExcursion() {
		List<ExcursionDTO> excursionDTOs = new ArrayList<ExcursionDTO>();

		List<ProductDTO> productDTOs = getAllProducts();

		for (ProductDTO productDTO : productDTOs) {
			if (productDTO instanceof ExcursionDTO) {
				excursionDTOs.add((ExcursionDTO) productDTO);
			}
		}

		return excursionDTOs;
	}

	@Override
	public List<ProductDTO> findExcursionByDate(Date date) {
		return convertoToListDTO(em.createNamedQuery(Excursion.FIND_BY_DATE)
				.setParameter("date", date).getResultList());
	}

	@Override
	public List<ProductDTO> findExcursionByCityName(String cityName) {
		return convertoToListDTO(em.createNamedQuery(Excursion.FIND_BY_CITY)
				.setParameter("city", cityName).getResultList());
	}

	/*
	 * Helper Methods
	 */

	// Trovo la citta data una String e lancia CityNotFoundException se la citta
	// non esiste
	public City findCity(String cityName) throws CityNotFoundException {
		City city = em.find(City.class, (cityName));
		if (city == null) {
			throw new CityNotFoundException();
		}
		return city;
	}

	/**
	 * Converte una lista di Product in una lista di ProductDTO.
	 * 
	 * @param products
	 * @return
	 */
	private List<ProductDTO> convertoToListDTO(List<Product> products) {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

		for (int i = 0; i < products.size(); i++) {
			productDTOs.add(convertToDTO(products.get(i)));
		}

		return productDTOs;
	}

	/**
	 * Converte il prodotto passato come parametro in un DTO specifico per il
	 * tipo di prodotto.
	 * 
	 * @param product
	 * @return productDTO
	 */
	private ProductDTO convertToDTO(Product product) {

		if (product != null) {
			em.refresh(product);
		}

		// FLIGHT
		if (product.getFlight() != null) {

			FlightDTO flightDTO = new FlightDTO();

			flightDTO.setName(product.getName());
			flightDTO.setDescription(product.getDescription());
			flightDTO.setProductId(product.getProductId());
			flightDTO.setPrice(product.getPrice());

			flightDTO
					.setCity1Name(product.getFlight().getCity1().getCityName());
			flightDTO
					.setCity2Name(product.getFlight().getCity2().getCityName());
			flightDTO.setDate(product.getFlight().getDate());

			return flightDTO;
		}

		// HOTEL
		if (product.getHotel() != null) {

			HotelDTO hotelDTO = new HotelDTO();

			hotelDTO.setName(product.getName());
			hotelDTO.setDescription(product.getDescription());
			hotelDTO.setProductId(product.getProductId());
			hotelDTO.setPrice(product.getPrice());

			hotelDTO.setCityName(product.getHotel().getCity().getCityName());

			return hotelDTO;
		}

		// EXCURSION
		if (product.getExcursion() != null) {

			ExcursionDTO excursionDTO = new ExcursionDTO();

			excursionDTO.setName(product.getName());
			excursionDTO.setDescription(product.getDescription());
			excursionDTO.setProductId(product.getProductId());
			excursionDTO.setPrice(product.getPrice());

			excursionDTO.setCityName(product.getExcursion().getCity()
					.getCityName());
			excursionDTO.setDate(product.getExcursion().getDate());

			return excursionDTO;
		}

		return null;

	}

}
