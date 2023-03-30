/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class DefaultPackageDTO {

	private int id;

	@NotNull
	private Date endDate;

	@NotNull
	private Date startDate;

	@NotNull
	private String description;

	@NotNull
	private String name;

	@NotNull
	private List<ProductDTO> productDTOs;
	private int imageId;
	
	public DefaultPackageDTO() {
		productDTOs = new ArrayList<ProductDTO>();
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

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	/*
	 * HELPER METHODS
	 */
	public void determineDates() {

		ArrayList<FlightDTO> flightDTOs = new ArrayList<FlightDTO>();

		for (ProductDTO productDTO : productDTOs) {

			if (productDTO instanceof FlightDTO) {
				flightDTOs.add((FlightDTO) productDTO);
			}
		}
		if (flightDTOs.size() > 1) {
			Date date1 = flightDTOs.get(0).getDate();
			Date date2 = flightDTOs.get(1).getDate();

			if (date1.before(date2)) {
				startDate = date1;
				endDate = date2;
			} else {
				startDate = date2;
				endDate = date1;
			}
		}
	}
}
