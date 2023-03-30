/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import it.polimi.traveldream.consistency.PackageConsistencyManager;
import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
import it.polimi.traveldream.managers.PackageManager;
import it.polimi.traveldream.persistence.DefaultPackage;
import it.polimi.traveldream.persistence.Group;
import it.polimi.traveldream.persistence.Product;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class PackageManagerBean implements PackageManager {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	private PackageConsistencyManager pcm;

	@PostConstruct
	private void init() {
		pcm = new PackageConsistencyManager(this);
	}

	
	/*
	 * ADD, UPDATE, REMOVE METHODS
	 */
	
	@RolesAllowed({ Group._ADMIN })
	@Override
	public void addDefaultPackage(DefaultPackageDTO defaultPackageDTO)
			throws ConsistencyException {

		print("addDefaultPackage: " + defaultPackageDTO.toString());
		
		defaultPackageDTO.determineDates();

		pcm.newPackageConsistency(defaultPackageDTO);

		DefaultPackage newDefaultPackage = new DefaultPackage(
				defaultPackageDTO, true);

		em.persist(newDefaultPackage);

	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void updateDefaultPackage(DefaultPackageDTO defaultPackageDTO)
			throws ConsistencyException, NoIdException {

		print("updateDefaultPackage: " + defaultPackageDTO.toString());

		if (((Integer) defaultPackageDTO.getId()) == null) {
			throw new NoIdException();
		}

		defaultPackageDTO.determineDates();

		pcm.newPackageConsistency(defaultPackageDTO);

		DefaultPackage defaultPackage = new DefaultPackage(defaultPackageDTO);
		defaultPackage.setId(defaultPackageDTO.getId());

		em.merge(defaultPackage);

	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void removeDefaultPackage(DefaultPackageDTO defaultPackageDTO)
			throws NoIdException {

		print("removeDefaultPackage: " + defaultPackageDTO.toString());

		if (((Integer) defaultPackageDTO.getId()) == null) {
			throw new NoIdException();
		}

		em.remove(em.find(DefaultPackage.class, defaultPackageDTO.getId()));

	}

	@RolesAllowed({ Group._ADMIN })
	@Override
	public void removeDefaultPackagebyId(int id) {

		print("removeDefaultPackagebyId: " + ((Integer) id).toString());

		em.remove(em.find(DefaultPackage.class, id));

	}

	

	/*
	 * QUERY METHODS
	 */
	
	@Override
	public List<DefaultPackageDTO> getAll() {

		print("getAll");

		return convertToDTOList(em.createNamedQuery(DefaultPackage.FIND_ALL,
				DefaultPackage.class).getResultList());
	}

	@Override
	public List<DefaultPackageDTO> findByName(String name) {

		print("getAll: " + name);

		return convertToDTOList(em
				.createNamedQuery(DefaultPackage.FIND_BY_NAME)
				.setParameter("name", name).getResultList());
	}

	@Override
	public DefaultPackageDTO findById(int defaultPackageId) {

		print("findById: " + ((Integer) defaultPackageId).toString());

		return convertToDTO(em.find(DefaultPackage.class, defaultPackageId));
	}

	@Override
	public List<HotelDTO> getPackageHotelDTOs(DefaultPackageDTO packageDTO) {

		ArrayList<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();

		if (packageDTO != null && packageDTO.getId() != 0) {
			DefaultPackageDTO defaultPackageDTO = findById(packageDTO.getId());

			for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
				if (productDTO instanceof HotelDTO) {
					hotelDTOs.add((HotelDTO) productDTO);
				}
			}
		}
		return hotelDTOs;

	}

	@Override
	public List<FlightDTO> getPackageFlightDTOs(DefaultPackageDTO packageDTO) {

		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		if (packageDTO != null && packageDTO.getId() != 0) {
			DefaultPackageDTO defaultPackageDTO = findById(packageDTO.getId());

			for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
				if (productDTO instanceof FlightDTO) {
					flightDTOs.add((FlightDTO) productDTO);
				}
			}
		}

		return flightDTOs;

	}

	@Override
	public List<ExcursionDTO> getPackageExcursionDTOs(
			DefaultPackageDTO packageDTO) {

		ArrayList<ExcursionDTO> excursionDTOs = new ArrayList<ExcursionDTO>();

		if (packageDTO != null && packageDTO.getId() != 0) {

			DefaultPackageDTO defaultPackageDTO = findById(packageDTO.getId());

			for (ProductDTO productDTO : defaultPackageDTO.getProductDTOs()) {
				if (productDTO instanceof ExcursionDTO) {
					excursionDTOs.add((ExcursionDTO) productDTO);
				}
			}
		}
		return excursionDTOs;

	}

	/*
	 * HELPER METHODSS
	 */

	private List<DefaultPackageDTO> convertToDTOList(
			List<DefaultPackage> defaultPackages) {

		List<DefaultPackageDTO> defaultPackageDTOs = new ArrayList<DefaultPackageDTO>();

		for (int i = 0; i < defaultPackages.size(); i++) {
			defaultPackageDTOs.add(convertToDTO(defaultPackages.get(i)));
		}

		return defaultPackageDTOs;
	}
	
	private DefaultPackageDTO convertToDTO(DefaultPackage defaultPackage) {

		if (defaultPackage != null) {
			em.refresh(defaultPackage);
		}

		DefaultPackageDTO defaultPackageDTO = new DefaultPackageDTO();

		defaultPackageDTO.setId(defaultPackage.getId());
		defaultPackageDTO.setName(defaultPackage.getName());
		defaultPackageDTO.setDescription(defaultPackage.getDescription());
		defaultPackageDTO.setStartDate(defaultPackage.getStartDate());
		defaultPackageDTO.setEndDate(defaultPackage.getEndDate());
		defaultPackageDTO.setProductDTOs(convertoProductsToListDTO(defaultPackage.getProducts()));
		defaultPackageDTO.setImageId(defaultPackage.getImageId());
		
		return defaultPackageDTO;

	}
	
//	Products Helper Methods
	
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


	private void print(String message) {
		System.out.print("PackageManagerBean: " + message);
	}
}
