/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import it.polimi.traveldream.dtos.GiftListDTO;
import it.polimi.traveldream.managers.GiftListManager;
import it.polimi.traveldream.persistence.GiftList;
import it.polimi.traveldream.persistence.GiftListPK;
import it.polimi.traveldream.persistence.PersPackage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.securityRoleRefType;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class GiftListManagerBean implements GiftListManager {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	private static Logger logger = Logger
			.getLogger(PersPackage.class.getName());

	@Override
	public List<GiftListDTO> findByPersPackageId(int persPackageId) {

		logger.info("findByPersPackageId: " + persPackageId);

		List<GiftList> giftLists = em
				.createNamedQuery(GiftList.FIND_BY_PERS_PACKAGE_ID)
				.setParameter("persPackageId", persPackageId).getResultList();

		return convertListToDTO(giftLists);
	}

	@Override
	public List<GiftListDTO> findByUsername(String username) {

		List<GiftList> giftLists = em.createNamedQuery(GiftList.FIND_BY_USER)
				.setParameter("username", username).getResultList();

		return convertListToDTO(giftLists);
	}

	@Override
	public List<GiftListDTO> getGiftPurchasedByPersPackageId(int persPackageId) {

		List<GiftList> giftLists = em
				.createNamedQuery(GiftList.FIND_BY_PERS_PACKAGE_ID)
				.setParameter("persPackageId", persPackageId).getResultList();

		List<GiftList> purchasedGiftLists = new ArrayList<GiftList>();

		for (GiftList giftList : giftLists) {
			if (giftList.getUsername() != null) {
				purchasedGiftLists.add(giftList);
			}
		}
		return convertListToDTO(purchasedGiftLists);
	}

	@Override
	public List<GiftListDTO> getGiftNotPurchasedByPersPackageId(
			int persPackageId) {

		List<GiftList> giftLists = em
				.createNamedQuery(GiftList.FIND_BY_PERS_PACKAGE_ID)
				.setParameter("persPackageId", persPackageId).getResultList();

		List<GiftList> notPurchasedGiftLists = new ArrayList<GiftList>();

		for (GiftList giftList : giftLists) {
			if (giftList.getUsername() == null) {
				notPurchasedGiftLists.add(giftList);
			}
		}

		return convertListToDTO(notPurchasedGiftLists);
	}

	@Override
	public void updatePersPackageGiftList(List<GiftListDTO> giftListDTOs) {

		int persPackageId = giftListDTOs.get(0).getPersPackageId();

		em.createNamedQuery(GiftList.DELETE_BY_PERS_PACKAGE)
				.setParameter("persPackageId", persPackageId).executeUpdate();

		for (GiftListDTO giftListDTO : giftListDTOs) {
			em.persist(new GiftList(giftListDTO));
		}

	}

	@Override
	public boolean isInPackageGiftList(int productId, int persPackageId) {

		List<GiftList> giftLists = em
				.createNamedQuery(GiftList.FIND_BY_PRODUCT_AND_PERS_PACKAGE)
				.setParameter("persPackageId", persPackageId)
				.setParameter("productId", productId).getResultList();

		if (giftLists.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean isProductPurchased(int persPackageId, int productId) {

		List<GiftListDTO> giftLists = findByPersPackageId(persPackageId);

		logger.info("persPackageId:" + persPackageId + "productId" + productId);
		logger.info("giftlists:" + giftLists.toString());

		if (giftLists.size() > 0) {
			for (GiftListDTO giftList : giftLists) {

				if (giftList.getProductId() == productId
						&& giftList.getUsername() != null) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean packageHasGiftList(int persPackageId) {
		if (findByPersPackageId(persPackageId).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public GiftList getGiftListByProductAndPackage(int persPackageId, int productId){
		List<GiftList> giftLists = em
				.createNamedQuery(GiftList.FIND_BY_PRODUCT_AND_PERS_PACKAGE)
				.setParameter("persPackageId", persPackageId)
				.setParameter("productId", productId).getResultList();
		
		logger.info("GiftList(" + persPackageId + ", " + productId + ")");
		logger.info("GiftLists: " + giftLists.toString());
		
		if(giftLists != null && giftLists.size() > 0){
			GiftList giftList = giftLists.get(0);
			return giftList;
		}
		
		return null;
	}
	
	@Override
	public void purchaseProductGiftList(int persPackageId, int productId,
			String purchasingUser) {

		GiftList giftList = getGiftListByProductAndPackage(persPackageId, productId);

		logger.info("GiftList(" + persPackageId + ", " + productId + ")");
		logger.info("GiftList: " + giftList.toString());
		
		if(giftList != null){
			giftList.setUsername(purchasingUser);
			em.merge(giftList);
		}
	}
	/*
	 * Helper Methods
	 */
	private GiftListDTO convertToDTO(GiftList giList) {
		GiftListDTO giftListDTO = new GiftListDTO();

		giftListDTO.setPersPackageId(giList.getId().getPersPackageId());
		giftListDTO.setProductId(giList.getId().getProductId());
		giftListDTO.setUsername(giList.getUsername());

		return giftListDTO;
	}

	private List<GiftListDTO> convertListToDTO(List<GiftList> giftLists) {
		List<GiftListDTO> giftListDTOs = new ArrayList<GiftListDTO>();

		for (GiftList giftList : giftLists) {
			giftListDTOs.add(convertToDTO(giftList));
		}
		return giftListDTOs;
	}
}
