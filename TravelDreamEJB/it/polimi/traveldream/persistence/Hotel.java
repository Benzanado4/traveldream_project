/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.HotelDTO;
import java.io.Serializable;
import javax.persistence.*;
/**
 * The persistent class for the Hotel database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Hotel.FIND_ALL, query = "SELECT h FROM Hotel h"),
		@NamedQuery(name = Hotel.FIND_BY_CITY, query = "SELECT h FROM Hotel h WHERE h.city = :city") })
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Hotel.findAll";

	public static final String FIND_BY_CITY = "Hotel.findByCity";

	// bi-directional one-to-one association to Product
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name = "City")
	private City city;

	public Hotel() {}

	public Hotel(HotelDTO hotelDTO) {

	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}