/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class PurchaseDTO {	
	/*
	 * FIELDS
	 */
	@NotNull
	private int id;

	@NotNull
	private Date purchaseDate;

	@NotNull
	private int persPackageId;

	@NotNull
	private String username;

	
	public PurchaseDTO(){}
	/*
	 * GETTER SETTER
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getPersPackageId() {
		return persPackageId;
	}

	public void setPersPackageId(int persPackageId) {
		this.persPackageId = persPackageId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
