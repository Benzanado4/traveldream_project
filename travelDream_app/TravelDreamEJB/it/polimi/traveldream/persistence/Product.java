/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.ProductDTO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Product.FIND_ALL, query = "SELECT p FROM Product p"),
		@NamedQuery(name = Product.FIND_BY_NAME, query = "SELECT p FROM Product p WHERE p.name = :name"),
		@NamedQuery(name = Product.FIND_BY_ID, query = "SELECT p FROM Product p WHERE p.productId = :productId")})
public class Product implements Serializable {

	public static final String FIND_ALL = "Product.findAll";

	public static final String FIND_BY_NAME = "Product.findByName";
	
	public static final String FIND_BY_ID = "Product.findById";

	private static final long serialVersionUID = 1L;

	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	private String name;

	private String description;

	private float price;

	// bi-directional one-to-one association to Excursion
	@OneToOne(mappedBy = "product")
	private Excursion excursion;

	// bi-directional one-to-one association to Flight
	@OneToOne(mappedBy = "product")
	private Flight flight;

	// bi-directional one-to-one association to Hotel
	@OneToOne(mappedBy = "product")
	private Hotel hotel;
	
	@ManyToMany(mappedBy="products")
	private List<DefaultPackage> defaultPackages;
	
	
	public Product() {
	}

	public Product(ProductDTO productDTO) {
		
		if(productDTO.getProductId() != 0){
			this.productId = productDTO.getProductId();
		}
		
		this.name = productDTO.getName();
		this.price = productDTO.getPrice();
		this.description = productDTO.getDescription();

	}

	
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Excursion getExcursion() {
		return this.excursion;
	}

	public void setExcursion(Excursion excursion) {
		this.excursion = excursion;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}