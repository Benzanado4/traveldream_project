/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import javax.validation.constraints.NotNull;
/********************************************************
 * Classes
 *********************************************************/
public class GiftListDTO {
	
	@NotNull
	private int persPackageId;
	
	@NotNull
	private int productId;

	@NotNull
	private String username;

	public int getPersPackageId() {
		return persPackageId;
	}

	public void setPersPackageId(int persPackageId) {
		this.persPackageId = persPackageId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
