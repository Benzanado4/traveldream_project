/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.ExcursionDTO;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
/**
 * The persistent class for the Excursion database table.
 * 
 */
@Entity
@NamedQueries({

@NamedQuery(name=Excursion.FIND_ALL, query="SELECT e FROM Excursion e"),
@NamedQuery(name= Excursion.FIND_BY_CITY, query="SELECT e FROM Excursion e WHERE e.city = :city"),
@NamedQuery(name= Excursion.FIND_BY_DATE, query="SELECT e FROM Excursion e WHERE e.date = :date")})
public class Excursion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Excursion.findAll";
	
	public static final String FIND_BY_CITY = "Excursion.findByCity";

	public static final String FIND_BY_DATE = "Excursion.findByDate";

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	//bi-directional one-to-one association to Product
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="productId")
	private Product product;

	//bi-directional many-to-one association to City
	@ManyToOne
	@JoinColumn(name="city")
	private City city;

	public Excursion() {
	}
	
	public Excursion(ExcursionDTO excursionDTO) {
		this.date = excursionDTO.getDate();
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

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}