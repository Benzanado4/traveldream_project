/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.dtos;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/********************************************************
 * Classes
 *********************************************************/
public abstract class ProductDTO {
		
	private int productId;
	
	@NotEmpty
	private String name;

	@NotEmpty
	private String description;

	@NotNull
	private float price;

	public ProductDTO() {
		
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
