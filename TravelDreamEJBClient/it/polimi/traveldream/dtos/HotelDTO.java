/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class HotelDTO extends ProductDTO {
	
	@NotNull
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
