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
public class PersPackageDTO {
	/*
	 * FIELDS
	 */
	@NotNull
	private int id;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	@NotNull
	private String name;

	@NotNull
	private int defaultPackageId;

	@NotNull
	private String username;

	private String description;

	@NotNull
	private List<ProductDTO> productDTOs;

	public PersPackageDTO() {
	}

	public PersPackageDTO(DefaultPackageDTO defaultPackageDTO, String username) {
		this.startDate = defaultPackageDTO.getStartDate();
		this.endDate = defaultPackageDTO.getEndDate();
		this.name = defaultPackageDTO.getName();
		this.defaultPackageId = defaultPackageDTO.getId();
		this.imageId = defaultPackageDTO.getImageId();
		this.username = username;
		this.description = defaultPackageDTO.getDescription();
		this.productDTOs = defaultPackageDTO.getProductDTOs();
	}

	private int imageId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDefaultPackageId() {
		return defaultPackageId;
	}

	public void setDefaultPackageId(int defaultPackageId) {
		this.defaultPackageId = defaultPackageId;
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
