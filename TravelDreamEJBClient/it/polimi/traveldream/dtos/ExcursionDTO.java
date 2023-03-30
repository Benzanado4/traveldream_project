/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import java.util.Date;
import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class ExcursionDTO extends ProductDTO{
	
	@NotNull
	private Date date;

	@NotNull
	private String cityName;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
