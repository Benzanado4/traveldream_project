/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import java.io.Serializable;
import javax.persistence.*;
/**
 * The primary key class for the GiftList database table.
 * 
 */
@Embeddable
public class GiftListPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int persPackageId;
	private int productId;

	public GiftListPK() {
	}

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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GiftListPK)) {
			return false;
		}
		GiftListPK castOther = (GiftListPK)other;
		return 
			(this.persPackageId == castOther.persPackageId)
			&& (this.productId == castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.persPackageId;
		hash = hash * prime + this.productId;
		
		return hash;
	}
}