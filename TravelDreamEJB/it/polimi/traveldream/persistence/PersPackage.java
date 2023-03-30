/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * The persistent class for the PERS_PACKAGE database table.
 * 
 */
@Entity(name = "PERS_PACKAGE")
@NamedQueries({
		@NamedQuery(name = PersPackage.FIND_ALL, query = "SELECT p FROM PERS_PACKAGE p"),
		@NamedQuery(name = PersPackage.FIND_BY_NAME, query = "SELECT p FROM PERS_PACKAGE p WHERE p.name = :name"),
		@NamedQuery(name = PersPackage.FIND_BY_USER, query = "SELECT p FROM PERS_PACKAGE p WHERE p.username = :username"),
		@NamedQuery(name = PersPackage.FIND_BY_ID, query = "SELECT p FROM PERS_PACKAGE p WHERE p.id = :id") })
public class PersPackage implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * QUERY CONSTANTS
	 */
	public static final String FIND_ALL = "PersPackage.findAll";
	public static final String FIND_BY_NAME = "PersPackage.findByName";
	public static final String FIND_BY_USER = "PersPackage.findByUser";
	public static final String FIND_BY_ID = "PersPackage.findById";
	/*
	 * FIELDS
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;
	private String name;
	private int defaultPackageId;
	private String username;
	
	@Lob
	private String description;

	@ManyToMany
	@JoinTable(name = "INCLUDES_PERS", joinColumns = { @JoinColumn(name = "pers_package_id", referencedColumnName = "id") }, inverseJoinColumns = 
		{ @JoinColumn(name = "product_id", referencedColumnName = "productId") })
	private List<Product> products;
	
	@ManyToMany
	@JoinTable(name = "PARTECIPATE", joinColumns = { @JoinColumn(name = "pers_package_id", referencedColumnName = "id") }, inverseJoinColumns = 
		{ @JoinColumn(name = "username", referencedColumnName = "username") })
	private List<User> partUsers;

	private int imageId;
	/*
	 * CONSTRUCTORS
	 */
	public PersPackage() {}

	public PersPackage(PersPackageDTO persPackageDTO) {

		if (persPackageDTO.getId() != 0) {
			this.id = persPackageDTO.getId();
		}
		this.startDate = determineStartDate(persPackageDTO.getProductDTOs());
		this.endDate = determineEndDate(persPackageDTO.getProductDTOs());
		this.defaultPackageId = persPackageDTO.getDefaultPackageId();
		this.name = persPackageDTO.getName();
		this.products = convertToProductList(persPackageDTO.getProductDTOs());
		this.username = persPackageDTO.getUsername();
		this.description = persPackageDTO.getDescription();
		this.imageId = persPackageDTO.getImageId();

	}
	/*
	 * GETTER SETTER
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefaultPackageId() {
		return defaultPackageId;
	}

	public void setDefaultPackageId(int defaultPackageId) {
		this.defaultPackageId = defaultPackageId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getPartUsers() {
		return partUsers;
	}

	public void setPartUsers(List<User> partUsers) {
		this.partUsers = partUsers;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	/*
	 * HELPER METHODSS
	 */
	private Date determineStartDate(List<ProductDTO> productDTOs) {

		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		for (ProductDTO productDTO : productDTOs) {

			if (productDTO instanceof FlightDTO) {
				flightDTOs.add((FlightDTO) productDTO);
			}
		}

		Date date1 = flightDTOs.get(0).getDate();
		Date date2 = flightDTOs.get(1).getDate();

		if (date1.before(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	private Date determineEndDate(List<ProductDTO> productDTOs) {

		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		for (ProductDTO productDTO : productDTOs) {

			if (productDTO instanceof FlightDTO) {
				flightDTOs.add((FlightDTO) productDTO);
			}
		}

		Date date1 = flightDTOs.get(0).getDate();
		Date date2 = flightDTOs.get(1).getDate();

		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
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
}