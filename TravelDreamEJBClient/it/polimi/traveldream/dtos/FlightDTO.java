/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import java.util.Date;
import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class FlightDTO extends ProductDTO{
	
	@NotNull
	private String city1Name;
	
	@NotNull
	private String city2Name;
	
	@NotNull
	private Date date;

	public String getCity1Name() {
		return city1Name;
	}

	public void setCity1Name(String city1Name) {
		this.city1Name = city1Name;
	}

	public String getCity2Name() {
		return city2Name;
	}

	public void setCity2Name(String city2Name) {
		this.city2Name = city2Name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
