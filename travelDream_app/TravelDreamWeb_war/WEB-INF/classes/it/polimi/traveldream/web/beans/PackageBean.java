/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.CityNotFoundException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.managers.CityManager;
import it.polimi.traveldream.managers.PackageManager;
import it.polimi.traveldream.managers.PersPackageManager;
import it.polimi.traveldream.managers.ProductManager;
import it.polimi.traveldream.managers.UserManager;
import it.polimi.traveldream.web.helper.MessageStrings;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "packageBean")
@ViewScoped
public class PackageBean {
	/*
	 * Costanti presenti in it.polimi.traveldream.helper nel progetto Client da
	 * utilizzare nella pagine JSF
	 */
	public final String FLIGHT = Constants.FLIGHT;
	public final String HOTEL = Constants.HOTEL;
	public final String EXCURSION = Constants.EXCURSION;

	@EJB
	private PackageManager packageMgr;

	@EJB
	private PersPackageManager persPackageMgr;

	@EJB
	private UserManager userMgr;

	@EJB
	private ProductManager productMgr;

	@EJB
	private CityManager cityMgr;

	private List<DefaultPackageDTO> allDefaultPackageDTOs;

	private String currentPackageId;

	private DefaultPackageDTO defaultPackageDTO;

	private List<ProductDTO> packageProducts;

	private List<FlightDTO> packageFlights;
	private List<HotelDTO> packageHotels;
	private List<ExcursionDTO> packageExcursion;

	private ProductDTODataModel productDTODataModel;

	private HotelDTODataModel hotelDTODataModel;

	private FlightDTODataModel flightDTODataModel;

	private ExcursionDTODataModel excursionDTODataModel;

	private ProductDTO clickedProductDTO;

	private static Logger logger = Logger
			.getLogger(PackageBean.class.getName());

	@PostConstruct
	private void init() {

		currentPackageId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("packageId");

		if (currentPackageId != null) {
			defaultPackageDTO = packageMgr.findById(Integer
					.valueOf(currentPackageId));
			logger.info("DEFAULT_PACKAGE: "
					+ defaultPackageDTO.getProductDTOs().toString());
		} else {
			defaultPackageDTO = new DefaultPackageDTO();
		}

		packageProducts = new ArrayList<ProductDTO>();

		packageFlights = packageMgr.getPackageFlightDTOs(defaultPackageDTO);
		packageHotels = packageMgr.getPackageHotelDTOs(defaultPackageDTO);
		packageExcursion = packageMgr
				.getPackageExcursionDTOs(defaultPackageDTO);

		productDTODataModel = new ProductDTODataModel(
				productMgr.getAllProducts());
		flightDTODataModel = new FlightDTODataModel(productMgr.getAllFlight());
		hotelDTODataModel = new HotelDTODataModel(productMgr.getAllHotel());
		excursionDTODataModel = new ExcursionDTODataModel(
				productMgr.getAllExcursion());

		allDefaultPackageDTOs = getAllPackages();
		logger.info(allDefaultPackageDTOs.toString());

	}

	public List<DefaultPackageDTO> getAllPackages() {
		List<DefaultPackageDTO> packageDTOs = packageMgr.getAll();

		return packageDTOs;
	}
	/*
	 * DEFAULT_PACKAGE METHODS
	 */
	public String addDefaultPackage() {

		try {
			packageProducts.clear();
			packageProducts.addAll(packageFlights);
			packageProducts.addAll(packageHotels);
			packageProducts.addAll(packageExcursion);

			defaultPackageDTO.setProductDTOs(packageProducts);

			logger.info("imageId: " + defaultPackageDTO.getImageId());

			packageMgr.addDefaultPackage(defaultPackageDTO);

			for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
				logger.info(productDTO.toString());
			}

		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHTS_NUM)) {
				print("addDefaultPackage: ConsistencyException: FLIGHTS_NUM");
				addMessage("insertDefaultPackageForm",
						MessageStrings.FLIGHTS_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHT_CITIES)) {
				print("addDefaultPackage: ConsistencyException: FLIGHTS_CITY");
				addMessage("insertDefaultPackageForm",
						MessageStrings.FLIGHTS_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTELS_NUM)) {
				print("addDefaultPackage: ConsistencyException: HOTEL_NUM");
				addMessage("insertDefaultPackageForm", MessageStrings.HOTEL_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTEL_CITY)) {
				print("addDefaultPackage: ConsistencyException: HOTEL_CITY");
				addMessage("insertDefaultPackageForm",
						MessageStrings.HOTEL_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_CITY)) {
				print("addDefaultPackage: ConsistencyException: EXCURSION_CITY");
				addMessage("insertDefaultPackageForm",
						MessageStrings.EXCURSION_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_DATE)) {
				print("addDefaultPackage: ConsistencyException: EXCURSION_DATE");
				addMessage("insertDefaultPackageForm",
						MessageStrings.EXCURSION_DATE);

				return null;
			}
		}
		return "/admin/package/packageList?confirm=true&faces-redirect=true";
	}

	public String updateDefaultPackage() {

		try {

			packageProducts.clear();
			packageProducts.addAll(packageFlights);
			packageProducts.addAll(packageHotels);
			packageProducts.addAll(packageExcursion);

			defaultPackageDTO.setProductDTOs(packageProducts);

			packageMgr.updateDefaultPackage(defaultPackageDTO);

			for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
				logger.info(productDTO.toString());
			}

		} catch (NoIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHTS_NUM)) {
				print("updateDefaultPackage: ConsistencyException: FLIGHTS_NUM");
				addMessage("updateDefaultPackageForm",
						MessageStrings.FLIGHTS_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHT_CITIES)) {
				print("updateDefaultPackage: ConsistencyException: FLIGHTS_CITY");
				addMessage("updateDefaultPackageForm",
						MessageStrings.FLIGHTS_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTELS_NUM)) {
				print("updateDefaultPackage: ConsistencyException: HOTEL_NUM");
				addMessage("updateDefaultPackageForm", MessageStrings.HOTEL_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTEL_CITY)) {
				print("updateDefaultPackage: ConsistencyException: HOTEL_CITY");
				addMessage("updateDefaultPackageForm",
						MessageStrings.HOTEL_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_CITY)) {
				print("updateDefaultPackage: ConsistencyException: EXCURSION_CITY");
				addMessage("updateDefaultPackageForm",
						MessageStrings.EXCURSION_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_DATE)) {
				print("updateDefaultPackage: ConsistencyException: EXCURSION_DATE");
				addMessage("updateDefaultPackageForm",
						MessageStrings.EXCURSION_DATE);

				return null;
			}
		}
		return "/admin/package/packageList?confirm=true&faces-redirect=true";
	}

	public String removeDefaultPackage(int packageId) {

		packageMgr.removeDefaultPackagebyId(packageId);

		return "/admin/package/packageList?confirm=true&faces-redirect=true";
	}

	public String createPersPackage() {

		logger.info("createPersPackage");

		logger.info("PACKAGEID:" + currentPackageId);

		if (currentPackageId == null) {
			return null;
		}

		DefaultPackageDTO newDefaultPackageDTO = packageMgr.findById(Integer
				.valueOf(currentPackageId));

		String username;

		if (userMgr.isUserLogged()) {
			username = userMgr.getLoggedUser().getUsername();
		} else {
			return "/user/index?faces-redirect=true";
		}

		logger.info("createPersPackage: defaultPackageId: "
				+ currentPackageId.toString());

		logger.info(defaultPackageDTO.getProductDTOs().toString());

		logger.info("createPersPackage: username :" + username);

		PersPackageDTO persPackageDTO = new PersPackageDTO(
				newDefaultPackageDTO, username);

		try {
			persPackageMgr.addPersPackage(persPackageDTO);
		} catch (ConsistencyException e) {

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHTS_NUM)) {
				print("addPersPackage: ConsistencyException: FLIGHTS_NUM");
				addMessage("insertPersPackageForm", MessageStrings.FLIGHTS_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_FLIGHT_CITIES)) {
				print("addPersPackage: ConsistencyException: FLIGHTS_CITY");
				addMessage("insertPersPackageForm", MessageStrings.FLIGHTS_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTELS_NUM)) {
				print("addPersPackage: ConsistencyException: HOTEL_NUM");
				addMessage("insertPersPackageForm", MessageStrings.HOTEL_NUM);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_HOTEL_CITY)) {
				print("addPersPackage: ConsistencyException: HOTEL_CITY");
				addMessage("insertPersPackageForm", MessageStrings.HOTEL_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_CITY)) {
				print("addPersPackage: ConsistencyException: EXCURSION_CITY");
				addMessage("insertPersPackageForm",
						MessageStrings.EXCURSION_CITY);

				return null;
			}

			if (e.getType().equals(ConsistencyException.WRONG_EXCURSION_DATE)) {
				print("addPersPackage: ConsistencyException: EXCURSION_DATE");
				addMessage("insertPersPackageForm",
						MessageStrings.EXCURSION_DATE);

				return null;
			}
		}

		return "/user/persPackage/persPackageList?" + Constants.CONFIRM_MESSAGE
				+ "=" + Constants.CONFIRM_MESSAGE_ADDED_PACKAGE
				+ "&faces-redirect=true";
	}

	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		return event.getNewStep();
	}

	public DefaultPackageDTO getDefaultPackageDTOById(int packageId) {
		return packageMgr.findById(packageId);
	}

	public void handleFileUpload(FileUploadEvent event) {

		try {
			File targetFolder = new File("/var/TravelDream/images");
			InputStream inputStream = event.getFile().getInputstream();
			OutputStream out = new FileOutputStream(new File(targetFolder,
					event.getFile().getFileName()));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");

		FacesContext.getCurrentInstance().addMessage(null, msg);
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
		DateTime startDateTime = new DateTime(defaultPackageDTO.getStartDate());
		DateTime endDateTime = new DateTime(defaultPackageDTO.getEndDate());

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
	 * Getter Setter
	 */
	public DefaultPackageDTO getDefaultPackageDTO() {
		return defaultPackageDTO;
	}

	public void setDefaultPackageDTO(DefaultPackageDTO defaultPackageDTO) {
		this.defaultPackageDTO = defaultPackageDTO;
	}

	public ProductDTODataModel getProductDTODataModel() {
		return productDTODataModel;
	}

	public void setProductDTODataModel(ProductDTODataModel productDTODataModel) {
		this.productDTODataModel = productDTODataModel;
	}

	public ProductDTO getClickedProductDTO() {
		return clickedProductDTO;
	}

	public void setClickedProductDTO(ProductDTO clickedProductDTO) {
		this.clickedProductDTO = clickedProductDTO;
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
}
