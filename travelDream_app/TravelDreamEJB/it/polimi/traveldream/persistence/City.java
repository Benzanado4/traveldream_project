/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
/**
 * The persistent class for the City database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = City.FIND_ALL, query = "SELECT c FROM City c") })
public class City implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "City.findAll";

	@Id
	private String cityName;

	// bi-directional many-to-one association to Excursion
	@OneToMany(mappedBy = "city")
	private List<Excursion> excursions;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "city1")
	private List<Flight> flights1;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "city2")
	private List<Flight> flights2;

	// bi-directional many-to-one association to Hotel
	@OneToMany(mappedBy = "city")
	private List<Hotel> hotels;

	public City() {
	}
	
	public City(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Excursion> getExcursions() {
		return this.excursions;
	}

	public void setExcursions(List<Excursion> excursions) {
		this.excursions = excursions;
	}

	public Excursion addExcursion(Excursion excursion) {
		getExcursions().add(excursion);
		excursion.setCity(this);

		return excursion;
	}

	public Excursion removeExcursion(Excursion excursion) {
		getExcursions().remove(excursion);
		excursion.setCity(null);

		return excursion;
	}

	public List<Flight> getFlights1() {
		return this.flights1;
	}

	public void setFlights1(List<Flight> flights1) {
		this.flights1 = flights1;
	}

	public Flight addFlights1(Flight flights1) {
		getFlights1().add(flights1);
		flights1.setCity1(this);

		return flights1;
	}

	public Flight removeFlights1(Flight flights1) {
		getFlights1().remove(flights1);
		flights1.setCity1(null);

		return flights1;
	}

	public List<Flight> getFlights2() {
		return this.flights2;
	}

	public void setFlights2(List<Flight> flights2) {
		this.flights2 = flights2;
	}

	public Flight addFlights2(Flight flights2) {
		getFlights2().add(flights2);
		flights2.setCity2(this);

		return flights2;
	}

	public Flight removeFlights2(Flight flights2) {
		getFlights2().remove(flights2);
		flights2.setCity2(null);

		return flights2;
	}

	public List<Hotel> getHotels() {
		return this.hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Hotel addHotel(Hotel hotel) {
		getHotels().add(hotel);
		hotel.setCity(this);

		return hotel;
	}

	public Hotel removeHotel(Hotel hotel) {
		getHotels().remove(hotel);
		hotel.setCity(null);

		return hotel;
	}
}