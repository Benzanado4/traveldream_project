/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.consistency.PackageConsistencyManager;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.dtos.PurchaseDTO;
import it.polimi.traveldream.dtos.UserDTO;
import it.polimi.traveldream.exception.AlreadyPurchasedException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.helper.MailConstants;
import it.polimi.traveldream.managers.PersPackageManager;
import it.polimi.traveldream.persistence.Group;
import it.polimi.traveldream.persistence.PersPackage;
import it.polimi.traveldream.persistence.Product;
import it.polimi.traveldream.persistence.Purchase;
import it.polimi.traveldream.persistence.User;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sun.awt.datatransfer.DataTransferer.ReencodingInputStream;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class PersPackageManagerBean implements PersPackageManager {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	private PackageConsistencyManager pcm;

	private static Logger logger = Logger
			.getLogger(PersPackage.class.getName());

	@PostConstruct
	private void init() {
		pcm = new PackageConsistencyManager(this);
	}

	/*
	 * ADD, UPDATE, REMOVE METHODS
	 */

	@RolesAllowed({ Group._USER })
	@Override
	public int addPersPackage(PersPackageDTO persPackageDTO)
			throws ConsistencyException {

		logger.info("addPersPackage: " + persPackageDTO.toString());

		persPackageDTO.determineDates();

		pcm.persPackageConsistency(persPackageDTO);

		PersPackage newPersPackage = new PersPackage(persPackageDTO);

		em.persist(newPersPackage);
		em.flush();

		logger.info(((Integer) newPersPackage.getId()).toString());

		return newPersPackage.getId();

	}

	@RolesAllowed({ Group._USER })
	@Override
	public void updatePersPackage(PersPackageDTO persPackageDTO)
			throws ConsistencyException, NoIdException {

		logger.info("updatePersPackage: " + persPackageDTO.toString());

		if (persPackageDTO.getId() == 0) {
			throw new NoIdException();
		}

		persPackageDTO.determineDates();

		pcm.persPackageConsistency(persPackageDTO);

		PersPackage persPackage = new PersPackage(persPackageDTO);
		persPackage.setId(persPackageDTO.getId());

		em.merge(persPackage);

	}

	@RolesAllowed({ Group._USER })
	@Override
	public void removePersPackage(PersPackageDTO persPackageDTO)
			throws NoIdException {

		logger.info("removePersPackage: " + persPackageDTO.toString());

		if (persPackageDTO.getId() == 0) {
			throw new NoIdException();
		}

		em.remove(em.find(PersPackage.class, persPackageDTO.getId()));

	}

	@RolesAllowed({ Group._USER })
	@Override
	public void removePersPackagebyId(int id) {

		logger.info("removePersPackagebyId: " + ((Integer) id).toString());

		em.remove(em.find(PersPackage.class, id));

	}

	/*
	 * QUERY METHODS
	 */

	@Override
	public List<PersPackageDTO> findByName(String name) {

		logger.info("findByName: " + name);

		return convertToDTOList(em.createNamedQuery(PersPackage.FIND_BY_NAME)
				.setParameter("name", name).getResultList());
	}

	@Override
	public PersPackageDTO findById(int persPackageId) {

		logger.info("persfindById: " + ((Integer) persPackageId).toString());

		PersPackage persPackage = em.find(PersPackage.class, persPackageId);
		if (persPackage == null) {
			return null;
		}
		return convertToDTO(em.find(PersPackage.class, persPackageId));
	}

	@Override
	public boolean isPurchased(int PersPackageId) {

		if (em.createNamedQuery(Purchase.FIND_BY_PERS_PACKAGE_ID)
				.setParameter("persPackageId", PersPackageId).getResultList()
				.size() > 0) {
			return true;
		}

		return false;

	}

	@RolesAllowed({ Group._USER })
	@Override
	public List<PersPackageDTO> findByUser(String username) {

		logger.info("findByUser: " + username);

		return convertToDTOList(em.createNamedQuery(PersPackage.FIND_BY_USER)
				.setParameter("username", username).getResultList());
	}

	@Override
	public PurchaseDTO purchasePersPackage(int persPackageId, String username)
			throws AlreadyPurchasedException {

		Purchase purchase = new Purchase();

		purchase.setPersPackageId(persPackageId);
		purchase.setPurchaseDate(new Date());
		purchase.setUsername(username);

		// controllo se nnon gia acquistato
		if (em.createNamedQuery(Purchase.FIND_BY_PERS_PACKAGE_ID)
				.setParameter("persPackageId", persPackageId).getResultList()
				.size() > 0) {
			throw new AlreadyPurchasedException();
		}

		em.persist(purchase);
		em.flush();

		// TODO check
		logger.info("PURCHASEID:" + ((Integer) purchase.getId()).toString());

		String email = em.find(User.class, username).getEmail();

		(new MailManager()).sendMail(email, MailConstants.TYPE_PURCHASE, null);

		PurchaseDTO purchaseDTO = new PurchaseDTO();

		purchaseDTO.setPersPackageId(purchase.getPersPackageId());
		purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
		purchaseDTO.setUsername(purchase.getUsername());

		return purchaseDTO;

	}

	@Override
	public List<HotelDTO> getPackageHotelDTOs(PersPackageDTO persPackageDTO) {

		ArrayList<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();

		if (persPackageDTO != null && persPackageDTO.getId() != 0) {
			PersPackageDTO PersPackageDTO = findById(persPackageDTO.getId());

			for (ProductDTO productDTO : PersPackageDTO.getProductDTOs()) {
				if (productDTO instanceof HotelDTO) {
					hotelDTOs.add((HotelDTO) productDTO);
				}
			}
		}
		return hotelDTOs;

	}

	@Override
	public List<FlightDTO> getPackageFlightDTOs(PersPackageDTO persPackageDTO) {

		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		if (persPackageDTO != null && persPackageDTO.getId() != 0) {
			PersPackageDTO PersPackageDTO = findById(persPackageDTO.getId());

			for (ProductDTO productDTO : PersPackageDTO.getProductDTOs()) {
				if (productDTO instanceof FlightDTO) {
					flightDTOs.add((FlightDTO) productDTO);
				}
			}
		}

		return flightDTOs;

	}

	@Override
	public List<ExcursionDTO> getPackageExcursionDTOs(
			PersPackageDTO persPackageDTO) {

		ArrayList<ExcursionDTO> excursionDTOs = new ArrayList<ExcursionDTO>();

		if (persPackageDTO != null && persPackageDTO.getId() != 0) {

			PersPackageDTO PersPackageDTO = findById(persPackageDTO.getId());

			for (ProductDTO productDTO : PersPackageDTO.getProductDTOs()) {
				if (productDTO instanceof ExcursionDTO) {
					excursionDTOs.add((ExcursionDTO) productDTO);
				}
			}
		}
		return excursionDTOs;

	}

	
	/*
	 * OTHER METHODS
	 */
	
	@RolesAllowed({ Group._USER })
	@Override
	public void sendMailInvite(String mailAddress, int persPackageId) {

		String partPackageLink = "http://localhost:8080/TravelDreamWeb/pages/partPackageDetail.xhtml?"
				+ Constants.FROM_EMAIL + "=" + Integer.valueOf(persPackageId);

		(new MailManager()).sendMail(mailAddress,
				MailConstants.TYPE_RECIEVE_INVITATION, partPackageLink);
	}

	@RolesAllowed({ Group._USER })
	@Override
	public void addPartUser(String username , int persPackageId){
		
		User user = em.find(User.class, username);
		
		PersPackage persPackage = em.find(PersPackage.class, persPackageId);
		
		List<PersPackage> persPackages = user.getPartPackages();
		
		persPackages.add(persPackage);
		
		user.setPartPackages(persPackages);
		
		em.merge(user);
		
	}
	
	@Override
	public boolean isUserPartPackage(String username , int persPackageId){
		
		PersPackage persPackage = em.find(PersPackage.class, persPackageId);
		
		List<User> partUsers = persPackage.getPartUsers();
		
		logger.info("partUser " + partUsers.toString());

		for(User user : partUsers){
			if(user.getUsername().equals(username)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<UserDTO> getPartUser(int persPackageId){
		
		PersPackage persPackage = em.find(PersPackage.class, persPackageId);
		List<User> partUsers = persPackage.getPartUsers();
		List<UserDTO> partUserDTOs = new ArrayList<UserDTO>();
		
		for(User user : partUsers){
			partUserDTOs.add(convertToDTO(user));
		}
		
		return partUserDTOs;
	}
	
	@Override
	public List<PersPackageDTO> getPartPersPackage(String username){
		
		User user = em.find(User.class, username);
		
		List<PersPackage> partPersPackage = user.getPartPackages();
		
		return convertToDTOList(partPersPackage);
	}
	
	@Override
	public boolean userOwnPersPackage(String username, int persPackageId){
		PersPackageDTO persPackageDTO = findById(persPackageId);
		
		if(persPackageDTO.getUsername().equals(username)){
			return true;
		}
		
		return false;
		
	}
	
	
	/*
	 * HELPER METHODS
	 */

	private List<PersPackageDTO> convertToDTOList(List<PersPackage> persPackages) {

		List<PersPackageDTO> persPackageDTOs = new ArrayList<PersPackageDTO>();

		for (PersPackage persPackage : persPackages) {
			persPackageDTOs.add(convertToDTO(persPackage));
		}

		return persPackageDTOs;
	}

	private PersPackageDTO convertToDTO(PersPackage persPackage) {

		if (persPackage != null) {
			em.refresh(persPackage);
		}

		PersPackageDTO persPackageDTO = new PersPackageDTO();

		persPackageDTO.setId(persPackage.getId());
		persPackageDTO.setUsername(persPackage.getUsername());
		persPackageDTO.setDefaultPackageId(persPackage.getDefaultPackageId());
		persPackageDTO.setName(persPackage.getName());
		persPackageDTO.setImageId(persPackage.getImageId());
		persPackageDTO.setDescription(persPackage.getDescription());
		persPackageDTO.setStartDate(persPackage.getStartDate());
		persPackageDTO.setEndDate(persPackage.getEndDate());
		persPackageDTO.setProductDTOs(convertoProductsToListDTO(persPackage
				.getProducts()));

		return persPackageDTO;

	}

	private List<ProductDTO> convertoProductsToListDTO(List<Product> products) {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

		for (int i = 0; i < products.size(); i++) {
			productDTOs.add(convertToDTO(products.get(i)));
		}

		return productDTOs;
	}

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

	private UserDTO convertToDTO(User principalUser) {

		UserDTO userDTO = new UserDTO();

		userDTO.setUsername(principalUser.getUsername());
		userDTO.setPassword(principalUser.getPassword());
		userDTO.setEmail(principalUser.getEmail());
		userDTO.setName(principalUser.getName());
		userDTO.setSurname(principalUser.getSurname());
		userDTO.setAddress(principalUser.getAddress());
		userDTO.setPhoneNumber(principalUser.getPhoneNumber());

		return userDTO;
	}
}
