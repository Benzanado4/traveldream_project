/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.managers;

import java.util.List;
import javax.ejb.Local;
import it.polimi.traveldream.dtos.PurchaseDTO;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface PurchaseManager {

	/**
	 * @param purchaseDTO
	 */
	void addPurchase(PurchaseDTO purchaseDTO);

	/**
	 * @param id
	 */
	void removePurchase(int id);

	/**
	 * @param usernmame
	 * @return
	 */
	public List<PurchaseDTO> getByUser(String usernmame);

	/**
	 * @param purchaseId
	 * @return
	 */
	public PurchaseDTO findById(int purchaseId);

	/**
	 * @param perPackageId
	 * @return
	 */
	public List<PurchaseDTO> getByPersPackageId(int perPackageId);

	/**
	 * @return
	 */
	public List<PurchaseDTO> getAll();

}
