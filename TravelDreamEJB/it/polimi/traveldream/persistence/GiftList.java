/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.GiftListDTO;
import java.io.Serializable;
import javax.persistence.*;
/**
 * The persistent class for the GiftList database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = GiftList.FIND_BY_PERS_PACKAGE_ID, query = "SELECT g FROM GiftList g where g.id.persPackageId = :persPackageId"), 
	@NamedQuery(name = GiftList.DELETE_BY_PERS_PACKAGE, query = "DELETE FROM GiftList  where id.persPackageId = :persPackageId"), 
	@NamedQuery(name = GiftList.FIND_BY_PRODUCT_AND_PERS_PACKAGE, query = "SELECT g FROM GiftList g where (g.id.persPackageId = :persPackageId and g.id.productId = :productId)"), 
	@NamedQuery(name = GiftList.FIND_BY_USER, query = "SELECT g FROM GiftList g where g.username = :username"), })
/********************************************************
 * Classes
 *********************************************************/
public class GiftList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_PERS_PACKAGE_ID = "GiftList.find_by_pers_package_id";

	public static final String FIND_BY_USER = "GiftList.find_by_user";
	
	public static final String FIND_BY_PRODUCT_AND_PERS_PACKAGE = "GiftList.find_by_product_and_pers_package";
	
	public static final String DELETE_BY_PERS_PACKAGE  = "GiftList.delete_by_pers_package";

	@EmbeddedId
	private GiftListPK id;
	private String username;

	public GiftList() {}

	public GiftList(GiftListDTO giftListDTO) {
		
		
		GiftListPK giftListPK = new GiftListPK();
		
		giftListPK.setPersPackageId(giftListDTO.getPersPackageId());
		giftListPK.setProductId(giftListDTO.getProductId());
		
		
		this.setId(giftListPK);
		
		this.setUsername(giftListDTO.getUsername());
	}
	
	public GiftListPK getId() {
		return this.id;
	}

	public void setId(GiftListPK id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}