/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.FlightDTO;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
/**
 * The persistent class for the Flight database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Flight.findAll", query = "SELECT f FROM Flight f"),
		@NamedQuery(name = Flight.FIND_BY_CITY_1, query = "SELECT f FROM Flight f WHERE f.city1 = :city1"),
		@NamedQuery(name = Flight.FIND_BY_CITY_2, query = "SELECT f FROM Flight f WHERE f.city2 = :city2"),
		@NamedQuery(name = Flight.FIND_BY_DATE, query = "SELECT f FROM Flight f WHERE f.date = :date") })
public class Flight implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Flight.findAll";

	public static final String FIND_BY_CITY_1 = "Flight.findByCity1";

	public static final String FIND_BY_CITY_2 = "Flight.findByCity2";

	public static final String FIND_BY_DATE = "Flight.findByDate";

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	// bi-directional one-to-one association to Product
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name = "city1")
	private City city1;

	// bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name = "city2")
	private City city2;

	public Flight() {
	}

	public Flight(FlightDTO flightDTO) {
		this.date = flightDTO.getDate();
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public City getCity1() {
		return this.city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return this.city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}
}