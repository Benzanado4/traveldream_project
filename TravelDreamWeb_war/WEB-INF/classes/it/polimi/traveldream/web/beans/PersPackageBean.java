/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.AlreadyPurchasedException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.managers.CityManager;
import it.polimi.traveldream.managers.PackageManager;
import it.polimi.traveldream.managers.PersPackageManager;
import it.polimi.traveldream.managers.ProductManager;
import it.polimi.traveldream.managers.PurchaseManager;
import it.polimi.traveldream.managers.UserManager;
import it.polimi.traveldream.web.helper.MessageStrings;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.eclipse.persistence.jpa.jpql.parser.DatetimeExpressionBNF;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;
import org.primefaces.event.FlowEvent;
import com.google.common.hash.HashCode;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "persPackageBean")
@ViewScoped
public class PersPackageBean {

	public final String FLIGHT = Constants.FLIGHT;
	public final String HOTEL = Constants.HOTEL;
	public final String EXCURSION = Constants.EXCURSION;

	@EJB
	private PersPackageManager persPackageMgr;

	@EJB
	private PackageManager packageMgr;

	@EJB
	private UserManager userMgr;

	@EJB
	private ProductManager productMgr;

	@EJB
	private CityManager cityMgr;

	private List<PersPackageDTO> allPersPackageDTOs;

	private PersPackageDTO persPackageDTO;

	private PersPackageDTO persPackagePurchasedDTO;

	private PersPackageDTO persPackageNotPurchasedDTO;

	private List<ProductDTO> packageProducts;

	private List<FlightDTO> packageFlights;
	private List<HotelDTO> packageHotels;
	private List<ExcursionDTO> packageExcursion;

	private ProductDTODataModel productDTODataModel;

	private HotelDTODataModel hotelDTODataModel;

	private FlightDTODataModel flightDTODataModel;

	private ExcursionDTODataModel excursionDTODataModel;

	private ProductDTO clickedProductDTO;

	private String curretnPersPackageId;

	private boolean currentPersPackagePurchasedState;

	private String confirmMessageType;

	private String inviteMail;

	private static Logger logger = Logger.getLogger(PersPackageBean.class
			.getName());

	@PostConstruct
	private void init() {

		logger.info("PersPackageBean init()");

		// Se ci troviamo in una pagina di un singolo pacchetto ho il parametro
		// persPackageId
		// e inizializzo le varaibili corrispondenti
		curretnPersPackageId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("persPackageId");

		if (curretnPersPackageId != null) {

			// Trovo il PersPackage nel DB
			persPackageDTO = persPackageMgr.findById(Integer
					.valueOf(curretnPersPackageId));

			currentPersPackagePurchasedState = persPackageMgr
					.isPurchased(Integer.valueOf(curretnPersPackageId));

			logger.info("PersPackageBean init() persPackage.name: "
					+ persPackageDTO.getName());
			logger.info("PersPackageBean init() persPackage.usernname: "
					+ persPackageDTO.getUsername());
			logger.info("PersPackageBean init() persPackage.defaultPackage: "
					+ persPackageDTO.getDefaultPackageId());

		} else {
			persPackageDTO = new PersPackageDTO();
			currentPersPackagePurchasedState = false;
		}

		// Controllo se arrivo da un link di un'email
		String fromEmailParameter = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get(Constants.FROM_EMAIL);

		if (fromEmailParameter != null) {

			curretnPersPackageId = fromEmailParameter;

			persPackageDTO = persPackageMgr.findById(Integer
					.valueOf(curretnPersPackageId));

			currentPersPackagePurchasedState = persPackageMgr
					.isPurchased(Integer.valueOf(curretnPersPackageId));

		}
		
		logger.info("curretnPersPackageId: " + curretnPersPackageId);


		// List contenti i prodotti
		packageProducts = new ArrayList<ProductDTO>();

		packageFlights = persPackageMgr.getPackageFlightDTOs(persPackageDTO);
		packageHotels = persPackageMgr.getPackageHotelDTOs(persPackageDTO);
		packageExcursion = persPackageMgr
				.getPackageExcursionDTOs(persPackageDTO);

		// Modelli per Tabelle selezionabili
		productDTODataModel = new ProductDTODataModel(
				productMgr.getAllProducts());
		flightDTODataModel = new FlightDTODataModel(productMgr.getAllFlight());
		hotelDTODataModel = new HotelDTODataModel(productMgr.getAllHotel());
		excursionDTODataModel = new ExcursionDTODataModel(
				productMgr.getAllExcursion());

		if (userMgr.isUserLogged()) {
			allPersPackageDTOs = getAllPersPackages();
			logger.info("AllPersPackage: " + allPersPackageDTOs.toString());
		}
	}
	/*
	 * Metodi gestione pacchetti pers
	 */
	public List<PersPackageDTO> getAllPersPackages() {
		List<PersPackageDTO> allPackageDTOs = persPackageMgr.findByUser(userMgr
				.getLoggedUser().getUsername());

		return allPackageDTOs;
	}

	public List<PersPackageDTO> getAllPersPackagesPurchased() {

		List<PersPackageDTO> allPackageDTOs = persPackageMgr.findByUser(userMgr
				.getLoggedUser().getUsername());

		List<PersPackageDTO> persPackagePurchasedDTOs = new ArrayList<PersPackageDTO>();

		for (PersPackageDTO persPackageDTO : allPackageDTOs) {
			if (persPackageMgr.isPurchased(persPackageDTO.getId())) {
				persPackagePurchasedDTOs.add(persPackageDTO);
			}
		}

		return persPackagePurchasedDTOs;
	}

	public List<PersPackageDTO> getAllPersPackagesNotPurchased() {

		List<PersPackageDTO> allPackageDTOs = persPackageMgr.findByUser(userMgr
				.getLoggedUser().getUsername());

		List<PersPackageDTO> persPackageNotPurchasedDTOs = new ArrayList<PersPackageDTO>();

		for (PersPackageDTO persPackageDTO : allPackageDTOs) {
			if (!persPackageMgr.isPurchased(persPackageDTO.getId())) {
				persPackageNotPurchasedDTOs.add(persPackageDTO);
			}
		}

		return persPackageNotPurchasedDTOs;
	}

	public String updatePersPackage() {

		try {

			packageProducts.clear();

			packageProducts.addAll(packageFlights);
			packageProducts.addAll(packageHotels);
			packageProducts.addAll(packageExcursion);

			persPackageDTO.setProductDTOs(packageProducts);

			persPackageMgr.updatePersPackage(persPackageDTO);

			for (ProductDTO productDTO : persPackageDTO.getProductDTOs()) {
				logger.info(productDTO.toString());
			}

		} catch (NoIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHTS_NUM)) {
				print("updatePersPackage: ConsistencyException: FLIGHTS_NUM");
				addMessage("insertPersPackageForm", MessageStrings.FLIGHTS_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHT_CITIES)) {
				print("updatePersPackage: ConsistencyException: FLIGHTS_CITY");
				addMessage("insertPersPackageForm", MessageStrings.FLIGHTS_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTELS_NUM)) {
				print("updatePersPackage: ConsistencyException: HOTEL_NUM");
				addMessage("insertPersPackageForm", MessageStrings.HOTEL_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTEL_CITY)) {
				print("updatePersPackage: ConsistencyException: HOTEL_CITY");
				addMessage("insertPersPackageForm", MessageStrings.HOTEL_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_CITY)) {
				print("updatePersPackage: ConsistencyException: EXCURSION_CITY");
				addMessage("insertPersPackageForm",
						MessageStrings.EXCURSION_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_DATE)) {
				print("updatePersPackage: ConsistencyException: EXCURSION_DATE");
				addMessage("insertPersPackageForm",
						MessageStrings.EXCURSION_DATE);

				return null;
			}
		}

		return "/user/persPackage/persPackageList?confirm=true&faces-redirect=true";

	}

	public String removePersPackage(int packageId) {

		persPackageMgr.removePersPackagebyId(packageId);

		return "/user/persPackage/persPackageList?confirm=true&faces-redirect=true";

	}

	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		return event.getNewStep();
	}

	public PersPackageDTO getPersPackageDTOById(int persPackageId) {
		return persPackageMgr.findById(persPackageId);
	}

	public String purchasePersPackage() {

		logger.info("purchasePersPackage");

		logger.info("PERSPACKAGEID:" + curretnPersPackageId);

		String username;

		if (userMgr.isUserLogged()) {
			username = userMgr.getLoggedUser().getUsername();
		} else {
			return "/user/index?faces-redirect=true";
		}

		logger.info("purchasePersPackage: username :" + username);

		try {
			persPackageMgr.purchasePersPackage(
					Integer.valueOf(curretnPersPackageId), username);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyPurchasedException e) {
			logger.info("GIA ACQUISTTO");
		}

		return "/user/persPackage/persPackageList?confirm=true&faces-redirect=true";
	}

	public void inviteToPersPackage() {
		
		logger.info("inviteMail: " + inviteMail + ", curretnPersPackageId: " + curretnPersPackageId.toString());

		persPackageMgr.sendMailInvite(inviteMail,
				Integer.valueOf(curretnPersPackageId));

	}

	public String partecipatePersPackage() {
		
		if (userMgr.isUserLogged()) {
			persPackageMgr.addPartUser(userMgr.getLoggedUser().getUsername(),
					Integer.valueOf(curretnPersPackageId));
		}
		
		return "/user/partPackageList?faces-redirect=true";

	}

	public boolean isUserPartPackage() {
		
		if (userMgr.isUserLogged()) {
			return persPackageMgr.isUserPartPackage(userMgr.getLoggedUser()
					.getUsername(), Integer.valueOf(curretnPersPackageId));
		}
		return false;
	}

	public List<PersPackageDTO> getPartPersPackage(){
		List<PersPackageDTO> partPersPackage = persPackageMgr.getPartPersPackage(userMgr.getLoggedUser().getUsername());
		return partPersPackage;
		
	}
	/*
	 * Helper Method
	 */
	private void print(String message) {
		System.out.print("PackageBean: " + message);
	}

	private void addMessage(String formId, String message) {
		FacesContext.getCurrentInstance().addMessage(formId,
				new FacesMessage(message));
	}

	public int calculatePrice() {

		int totalPrice = 0;
		ArrayList<ProductDTO> productsForPrice = new ArrayList<ProductDTO>();

		DateTime startDateTime = new DateTime(persPackageDTO.getStartDate());
		DateTime endDateTime = new DateTime(persPackageDTO.getEndDate());

		int numberDays = Days.daysBetween(startDateTime, endDateTime).getDays();

		productsForPrice.addAll(packageFlights);
		productsForPrice.addAll(packageHotels);
		productsForPrice.addAll(packageExcursion);

		for (ProductDTO productDTO : productsForPrice) {

			if (productDTO instanceof HotelDTO) {
				totalPrice = (int) (totalPrice + (productDTO.getPrice() * numberDays));
			} else {
				totalPrice = (int) (totalPrice + productDTO.getPrice());
			}
		}

		return totalPrice;
	}
	/*
	 * GETTER SETTER
	 */
	public List<PersPackageDTO> getAllPersPackageDTOs() {
		return allPersPackageDTOs;
	}

	public void setAllPersPackageDTOs(List<PersPackageDTO> allPersPackageDTOs) {
		this.allPersPackageDTOs = allPersPackageDTOs;
	}

	public PersPackageDTO getPersPackageDTO() {
		logger.info("getPersPackageDTO");
		return persPackageDTO;
	}

	public void setPersPackageDTO(PersPackageDTO persPackageDTO) {
		this.persPackageDTO = persPackageDTO;
	}

	public List<ProductDTO> getPackageProducts() {
		return packageProducts;
	}

	public void setPackageProducts(List<ProductDTO> packageProducts) {
		this.packageProducts = packageProducts;
	}

	public List<FlightDTO> getPackageFlights() {
		return packageFlights;
	}

	public void setPackageFlights(List<FlightDTO> packageFlights) {
		this.packageFlights = packageFlights;
	}

	public List<HotelDTO> getPackageHotels() {
		return packageHotels;
	}

	public void setPackageHotels(List<HotelDTO> packageHotels) {
		this.packageHotels = packageHotels;
	}

	public List<ExcursionDTO> getPackageExcursion() {
		return packageExcursion;
	}

	public void setPackageExcursion(List<ExcursionDTO> packageExcursion) {
		this.packageExcursion = packageExcursion;
	}

	public ProductDTO getClickedProductDTO() {
		return clickedProductDTO;
	}

	public void setClickedProductDTO(ProductDTO clickedProductDTO) {
		this.clickedProductDTO = clickedProductDTO;
	}

	public ProductDTODataModel getProductDTODataModel() {
		return productDTODataModel;
	}

	public void setProductDTODataModel(ProductDTODataModel productDTODataModel) {
		this.productDTODataModel = productDTODataModel;
	}

	public HotelDTODataModel getHotelDTODataModel() {
		return hotelDTODataModel;
	}

	public void setHotelDTODataModel(HotelDTODataModel hotelDTODataModel) {
		this.hotelDTODataModel = hotelDTODataModel;
	}

	public FlightDTODataModel getFlightDTODataModel() {
		return flightDTODataModel;
	}

	public void setFlightDTODataModel(FlightDTODataModel flightDTODataModel) {
		this.flightDTODataModel = flightDTODataModel;
	}

	public ExcursionDTODataModel getExcursionDTODataModel() {
		return excursionDTODataModel;
	}

	public void setExcursionDTODataModel(
			ExcursionDTODataModel excursionDTODataModel) {
		this.excursionDTODataModel = excursionDTODataModel;
	}

	public String getCurretnPersPackageId() {
		return curretnPersPackageId;
	}

	public void setCurretnPersPackageId(String curretnPersPackageId) {
		this.curretnPersPackageId = curretnPersPackageId;
	}

	public boolean isCurrentPersPackagePurchasedState() {
		return currentPersPackagePurchasedState;
	}

	public void setCurrentPersPackagePurchasedState(
			boolean currentPersPackagePurchasedState) {
		this.currentPersPackagePurchasedState = currentPersPackagePurchasedState;
	}

	public String getInviteMail() {
		return inviteMail;
	}

	public void setInviteMail(String inviteMail) {
		this.inviteMail = inviteMail;
	}
}
