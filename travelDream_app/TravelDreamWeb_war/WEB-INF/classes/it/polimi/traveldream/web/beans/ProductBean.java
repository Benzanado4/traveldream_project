/********************************************************
 * Declarations
 *********************************************************/

package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.CityNotFoundException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.managers.CityManager;
import it.polimi.traveldream.managers.ProductManager;
import it.polimi.traveldream.web.helper.MessageStrings;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean {

	/*
	 * Costanti presenti in it.polimi.traveldream.helper nel progetto Client da
	 * utilizzare nella pagine JSF
	 */
	public final String FLIGHT = Constants.FLIGHT;
	public final String HOTEL = Constants.HOTEL;
	public final String EXCURSION = Constants.EXCURSION;

	public final String FLIGHT_NAME = Constants.FLIGHT_NAME;
	public final String HOTEL_NAME = Constants.HOTEL_NAME;
	public final String EXCURSION_NAME = Constants.EXCURSION_NAME;

	private static Logger logger = Logger
			.getLogger(ProductBean.class.getName());

	@EJB
	private ProductManager productMgr;

	@EJB
	private CityManager cityMgr;

	private ProductDTO currentDetailProduct;
	private FlightDTO flightCurrent;
	private HotelDTO hotelCurrent;
	private ExcursionDTO excursionCurrent;
	private FlightDTO flight;
	private HotelDTO hotel;
	private ExcursionDTO excursion;
	private String newProductChoose;
	private String newMessage;

	public ProductBean() {
	}
	/**
	 * costruttore che istanzia, in base all'if, il DTO corrispondente al tipo
	 * di prodotto che l'utente vuole aggiungere
	 */
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		newProductChoose = params.get("choose");

		System.out.print("Init");

		if (newProductChoose != null) {

			System.out.print("Init: " + newProductChoose);

			if (newProductChoose.equals(FLIGHT)) {
				flight = new FlightDTO();
			}
			if (newProductChoose.equals(HOTEL)) {
				hotel = new HotelDTO();
			}
			if (newProductChoose.equals(EXCURSION)) {
				excursion = new ExcursionDTO();
			}
		}
	}
	/**
	 * Metodo che restituisce un lista di String con i nomi delle citta
	 * 
	 */
	public List<String> getCities() {
		List<String> cityNames = cityMgr.getAllCitiesNames();
		return cityNames;
	}

	/**
	 * restituisce la lista di tutti i prodotti
	 * 
	 * @return productDTOs
	 */
	public List<ProductDTO> getProducts() {
		return productMgr.getAllProducts();
	}

	public List<FlightDTO> getFlightDTOs() {
		return productMgr.getAllFlight();
	}
	
	public List<HotelDTO> getHotelDTOs() {
		return productMgr.getAllHotel();
	}
	
	public List<ExcursionDTO> getExcursionDTOs() {
		return productMgr.getAllExcursion();
	}
	
	public ProductDTO getProductById(int productId) {
		ProductDTO productDTO = productMgr.findById(productId);
		return productDTO;
	}
	/**
	 * restituisce un prodotto da ID
	 * 
	 * @param productDTO
	 * @return costante del nome prodotto
	 */
	public String getProductTypeById(int productId) {
		System.out.print(productId);

		ProductDTO productDTO = productMgr.findById(productId);
		if (productDTO instanceof HotelDTO) {
			System.out.print("get product by id HOTEL ");
			hotelCurrent = (HotelDTO) productDTO;
			return HOTEL;
		}
		if (productDTO instanceof FlightDTO) {
			System.out.print("get product by id FLIGHT");
			flightCurrent = (FlightDTO) productDTO;
			return FLIGHT;
		}
		if (productDTO instanceof ExcursionDTO) {
			System.out.print("get product by id EXCURSION");
			excursionCurrent = (ExcursionDTO) productDTO;
			return EXCURSION;
		}
		System.out.print("get product by id ");
		return "Prodotto Generico";

	}
	/**
	 * restituisce un prodotto dal nome
	 * 
	 * @param productDTO
	 * @return nome prodotto
	 */
	public String getProductTypeName(ProductDTO productDTO) {
		if (productDTO instanceof HotelDTO) {
			logger.info("getProductTypeName: " + HOTEL_NAME);
			return HOTEL_NAME;
		}
		if (productDTO instanceof FlightDTO) {
			logger.info("getProductTypeName: " + FLIGHT_NAME);
			return FLIGHT_NAME;
		}
		if (productDTO instanceof ExcursionDTO) {
			logger.info("getProductTypeName: " + EXCURSION_NAME);
			return EXCURSION_NAME;
		}
		return "Prodotto Generico";
	}
	/**
	 * Metodo per l'inserimento di un nuovo volo
	 * 
	 * @return pagina elenco prodotti
	 */
	public String addNewFlight() {
		System.out.print("ProductBean: addNewflight");
		try {
			productMgr.addProduct(flight);
		} catch (CityNotFoundException e) {

			print("addNewFlight: CityNotFoundException");

			return null;

		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.SAME_CITY1_CITY2)) {

				print("addNewFlight: ConsistencyException: SAME_CITY1_CITY2");
				addMessage("insertProductFormFlight",
						MessageStrings.SAME_CITY1_CITY2);

				return null;
			}

			if (e.getType().equals(ConsistencyException.BEFORE_TODAY)) {

				print("addNewFlight: ConsistencyException: BEFORE_TODAY");
				addMessage("insertProductFormFlight",
						MessageStrings.BEFORE_TODAY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("addNewFlight: ConsistencyException: EXISTING_PRODUCT");
				addMessage("insertProductFormFlight",
						MessageStrings.EXISTING_PRODUCT);

				return null;
			}
		}

		return "/admin/product/productList?confirm=true&faces-redirect=true";
	}

	/**
	 * Metodo per l'inserimento di un nuovo hotel
	 * 
	 * @return pagina elenco prodotti
	 */
	public String addNewHotel() {
		System.out.print("ProductBean: addNewhotel");

		try {
			productMgr.addProduct(hotel);

		} catch (CityNotFoundException e) {
			print("addNewHotel: CityNotFoundException");

			return null;
		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("addNewHotel: ConsistencyException: EXISTING_PRODUCT");
				addMessage("insertProductFormHotel",
						MessageStrings.EXISTING_PRODUCT);

			}
			return null;
		}
		return "/admin/product/productList?faces-redirect=true";
	}

	/**
	 * Metodo per l'inserimento di una nuova escursione
	 * 
	 * @return pagina elenco prodotti
	 */
	public String addNewExcursion() {

		System.out.print("ProductBean: addNewProduct");

		try {
			productMgr.addProduct(excursion);

		} catch (CityNotFoundException e) {

			print("addNewExcursion: CityNotFoundException");

			return null;

		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.BEFORE_TODAY)) {

				print("addNewExcursion: ConsistencyException: BEFORE_TODAY");
				addMessage("insertProductFormExcursion",
						MessageStrings.BEFORE_TODAY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("addNewExcursion: ConsistencyException: EXISTING_PRODUCT");
				addMessage("insertProductFormExcursion",
						MessageStrings.EXISTING_PRODUCT);

				return null;
			}
		}
		return "/admin/product/productList?faces-redirect=true";
	}
	/**
	 * Aggiornamento Volo da parte de dipendente
	 * 
	 * @return pagina productlist
	 */
	public String updateFlight() {
		try {
			System.out.print("ProductBean: updateFlight: try");

			productMgr.updateProduct(flightCurrent);

		} catch (NoIdException e) {
			print("updateFlight: NoIdException");

			return null;

		} catch (CityNotFoundException e) {

			print("updateFlight: CityNotFoundException");

			return null;

		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.SAME_CITY1_CITY2)) {

				print("updateFlight: ConsistencyException: SAME_CITY1_CITY2");
				addMessage("seeProductFormFlight",
						MessageStrings.SAME_CITY1_CITY2);

				return null;
			}

			if (e.getType().equals(ConsistencyException.BEFORE_TODAY)) {

				print("updateFlight: ConsistencyException: BEFORE_TODAY");
				addMessage("seeProductFormFlight", MessageStrings.BEFORE_TODAY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("updateFlight: ConsistencyException: EXISTING_PRODUCT");
				addMessage("seeProductFormFlight",
						MessageStrings.EXISTING_PRODUCT);

				return null;
			}
		}
		System.out.print("ProductBean: updateFlight");
		return "/admin/product/productList?faces-redirect=true";
	}

	public String updateHotel() {
		System.out.print("ProductBean: updateNewhotel");

		try {
			productMgr.updateProduct(hotelCurrent);

		} catch (CityNotFoundException e) {
			print("updateNewHotel: CityNotFoundException");

			return null;
		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("updateNewHotel: ConsistencyException: EXISTING_PRODUCT");
				addMessage("seeProductFormHotel",
						MessageStrings.EXISTING_PRODUCT);

			}
			return null;

		} catch (NoIdException e) {
			print("updateNewHotel: NoIdException");
		}

		return "/admin/product/productList?faces-redirect=true";
	}

	public String updateExcursion() {
		try {
			productMgr.updateProduct(excursionCurrent);
		} catch (NoIdException e) {
			print("updateExcursion: NoIdException");

		} catch (CityNotFoundException e) {

			print("updateExcursion: CityNotFoundException");

			return null;

		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.BEFORE_TODAY)) {

				print("updateExcursion: ConsistencyException: BEFORE_TODAY");
				addMessage("seeProductFormExcursion",
						MessageStrings.BEFORE_TODAY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.EXISTING_PRODUCT)) {

				print("updateExcursion: ConsistencyException: EXISTING_PRODUCT");
				addMessage("seeProductFormExcursion",
						MessageStrings.EXISTING_PRODUCT);

				return null;
			}

		}

		System.out.print("updateExcursion");
		return "/admin/product/productList?faces-redirect=true";
	}

	public String removeProduct(int id) {
		productMgr.removeProductById(id);
		return "/admin/product/productList?faces-redirect=true";
	}

	private void print(String message) {
		System.out.print("ProductBean: " + message);
	}

	private void addMessage(String formId, String message) {
		FacesContext.getCurrentInstance().addMessage(formId,
				new FacesMessage(message));
	}

	public FlightDTO getFlightDTO(ProductDTO productDTO) {
		if (productDTO != null) {
			logger.info(productDTO.toString());
			logger.info(productDTO.getName());
		}
		return (FlightDTO) productDTO;
	}

	public HotelDTO getHotelDTO(ProductDTO productDTO) {
		logger.info(productDTO.toString());
		logger.info(productDTO.getName());
		return (HotelDTO) productDTO;
	}

	public ExcursionDTO getExcursionDTO(ProductDTO productDTO) {
		logger.info(productDTO.toString());
		logger.info(productDTO.getName());
		return (ExcursionDTO) productDTO;
	}
	/*
	 * getter and setter
	 */
	public FlightDTO getFlightCurrent() {
		return flightCurrent;
	}

	public void setFlightCurrent(FlightDTO flightCurrent) {
		this.flightCurrent = flightCurrent;
	}

	public HotelDTO getHotelCurrent() {
		return hotelCurrent;
	}

	public void setHotelCurrent(HotelDTO hotelCurrent) {
		this.hotelCurrent = hotelCurrent;
	}

	public ExcursionDTO getExcursionCurrent() {
		return excursionCurrent;
	}

	public void setExcursionCurrent(ExcursionDTO excursionCurrent) {
		this.excursionCurrent = excursionCurrent;
	}

	public ProductDTO getCurrentDetailProduct() {
		return currentDetailProduct;
	}

	public void setCurrentDetailProduct(ProductDTO currentDetailProduct) {
		this.currentDetailProduct = currentDetailProduct;
	}

	public String getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public ExcursionDTO getExcursion() {
		return excursion;
	}

	public void setExcursion(ExcursionDTO excursion) {
		this.excursion = excursion;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	public String getFLIGHT() {
		return FLIGHT;
	}

	public String getHOTEL() {
		return HOTEL;
	}

	public String getEXCURSION() {
		return EXCURSION;
	}

	public String getFLIGHT_NAME() {
		return FLIGHT_NAME;
	}

	public String getHOTEL_NAME() {
		return HOTEL_NAME;
	}

	public String getEXCURSION_NAME() {
		return EXCURSION_NAME;
	}
}
