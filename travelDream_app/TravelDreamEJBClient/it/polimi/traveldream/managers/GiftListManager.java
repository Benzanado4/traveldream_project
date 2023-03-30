/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.managers;

import java.util.List;
import it.polimi.traveldream.dtos.GiftListDTO;
import javax.ejb.Local;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface GiftListManager {

	public List<GiftListDTO> findByPersPackageId(int persPackageId);
	
	public List<GiftListDTO> findByUsername(String Username);

	boolean isInPackageGiftList(int productId, int persPackageId);

	void updatePersPackageGiftList(List<GiftListDTO> giftListDTOs);

	
	/**
	 * @param persPackageId
	 * @return
	 */
	List<GiftListDTO> getGiftPurchasedByPersPackageId(int persPackageId);

	/**
	 * @param persPackageId
	 * @return
	 */
	List<GiftListDTO> getGiftNotPurchasedByPersPackageId(int persPackageId);

	/**
	 * @param persPackageId
	 * @param productId
	 * @return
	 */
	boolean isProductPurchased(int persPackageId, int productId);

	/**
	 * @param persPackageId
	 * @return
	 */
	boolean packageHasGiftList(int persPackageId);

	/**
	 * @param persPackageId
	 * @param productId
	 * @param purchasingUser
	 */
	void purchaseProductGiftList(int persPackageId, int productId,
			String purchasingUser);
	
}
