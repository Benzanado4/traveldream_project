/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.exception.NoIdException;
import java.io.Serializable;
import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * The persistent class for the DEFAULT_PACKAGE database table.
 * 
 */
@Entity(name = "DEFAULT_PACKAGE")
@NamedQueries({
		@NamedQuery(name = DefaultPackage.FIND_ALL, query = "SELECT p FROM DEFAULT_PACKAGE p"),
		@NamedQuery(name = DefaultPackage.FIND_BY_NAME, query = "SELECT p FROM DEFAULT_PACKAGE p WHERE p.name = :name"),
		@NamedQuery(name = DefaultPackage.FIND_BY_ID, query = "SELECT p FROM DEFAULT_PACKAGE p WHERE p.id = :id") })
public class DefaultPackage implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "DefaultPackage.findAll";

	public static final String FIND_BY_NAME = "DefaultPackage.findByName";

	public static final String FIND_BY_ID = "DefaultPackage.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Lob
	private String description;
	private String name;

	@ManyToMany
	@JoinTable(name = "INCLUDES", joinColumns = { @JoinColumn(name = "package_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "productId") })
	private List<Product> products;

	private int imageId = 0;
	
	public DefaultPackage() {
	}
	/**
	 * @param defaultPackageDTO
	 * @throws NoIdException
	 */
	public DefaultPackage(DefaultPackageDTO defaultPackageDTO)
			throws NoIdException {

		if (defaultPackageDTO.getId() == 0) {
			throw new NoIdException();
		}

		this.id = defaultPackageDTO.getId();
		this.name = defaultPackageDTO.getName();
		this.description = defaultPackageDTO.getDescription();
		this.startDate = determineStartDate(defaultPackageDTO.getProductDTOs());
		this.endDate = determineEndDate(defaultPackageDTO.getProductDTOs());
		this.imageId = defaultPackageDTO.getImageId();
		
		this.products = convertToProductList(defaultPackageDTO.getProductDTOs());

	}

	// Metodo per la creazione di un nuovo pacchetto che richiede un DTO privo di database
	public DefaultPackage(DefaultPackageDTO defaultPackageDTO, boolean noId) {

		this.name = defaultPackageDTO.getName();
		this.description = defaultPackageDTO.getDescription();
		this.startDate = defaultPackageDTO.getStartDate();
		this.endDate = defaultPackageDTO.getEndDate();
		this.startDate = determineStartDate(defaultPackageDTO.getProductDTOs());
		this.endDate = determineEndDate(defaultPackageDTO.getProductDTOs());
		this.imageId = defaultPackageDTO.getImageId();

		this.products = convertToProductList(defaultPackageDTO.getProductDTOs());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	private List<Product> convertToProductList(List<ProductDTO> productDTOs) {

		ArrayList<Product> products = new ArrayList<Product>();

		if (productDTOs != null) {

			for (ProductDTO productDTO : productDTOs) {				
				products.add(new Product(productDTO));
			}
		}

		return products;
	}
	
	private Date determineStartDate(List<ProductDTO> productDTOs){
		
		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();
		
		for(ProductDTO productDTO : productDTOs){
			
			if(productDTO instanceof FlightDTO){
				flightDTOs.add((FlightDTO)productDTO);
			}	
		}
		
		Date date1 = flightDTOs.get(0).getDate();
		Date date2 = flightDTOs.get(1).getDate();
		
		if(date1.before(date2)){
			return date1;
		}else {
			return date2;
		}	
	}
	
	private Date determineEndDate(List<ProductDTO> productDTOs){
		
		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();
		
		for(ProductDTO productDTO : productDTOs){
			
			if(productDTO instanceof FlightDTO){
				flightDTOs.add((FlightDTO)productDTO);
			}	
		}
		
		Date date1 = flightDTOs.get(0).getDate();
		Date date2 = flightDTOs.get(1).getDate();
		
		if(date1.after(date2)){
			return date1;
		}else {
			return date2;
		}	
	}
}