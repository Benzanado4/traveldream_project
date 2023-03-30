/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.PurchaseDTO;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
/**
 * The persistent class for the PURCHASE database table.
 * 
 */
@Entity(name = "PURCHASE")
@NamedQueries({ @NamedQuery(name = Purchase.FIND_ALL, query = "SELECT p FROM PURCHASE p"),
	@NamedQuery(name = Purchase.FIND_BY_USERNAME, query = "SELECT p FROM PURCHASE p WHERE p.username = :username"),
	@NamedQuery(name = Purchase.FIND_BY_PERS_PACKAGE_ID, query = "SELECT p FROM PURCHASE p WHERE p.persPackageId = :persPackageId") })
public class Purchase implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Purchase.findAll";
	public static final String FIND_BY_USERNAME = "Purchase.findByUsername";
	public static final String FIND_BY_PERS_PACKAGE_ID = "Purchase.findByPersPackage";
	/*
	 * FIELDS
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date purchaseDate;
	private int persPackageId;
	private String username;
	/*
	 * CONSTRUCTORS
	 */
	public Purchase() {}

	public Purchase(PurchaseDTO purchaseDTO) {

		if (purchaseDTO.getId() != 0) {
			this.id = purchaseDTO.getId();
		}
		this.persPackageId = purchaseDTO.getPersPackageId();
		this.purchaseDate = purchaseDTO.getPurchaseDate();
		this.username = purchaseDTO.getUsername();
	}
	/*
	 * GETTER SETTER
	 */
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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