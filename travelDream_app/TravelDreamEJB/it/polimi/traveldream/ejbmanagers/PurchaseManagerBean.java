/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import it.polimi.traveldream.dtos.PurchaseDTO;
import it.polimi.traveldream.managers.PurchaseManager;
import it.polimi.traveldream.persistence.Group;
import it.polimi.traveldream.persistence.PersPackage;
import it.polimi.traveldream.persistence.Purchase;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class PurchaseManagerBean implements PurchaseManager {

	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	private static Logger logger = Logger
			.getLogger(PersPackage.class.getName());

	
	/*
	 * ADD, UPDATE, REMOVE METHODS
	 */
	
	@RolesAllowed({ Group._USER })
	@Override
	public void addPurchase(PurchaseDTO purchaseDTO){

		logger.info("addPurchase: " + purchaseDTO.toString());
		
		Purchase newPurchase = new Purchase(purchaseDTO);

		em.persist(newPurchase);

	}
	
	@RolesAllowed({ Group._USER })
	@Override
	public void removePurchase(int id) {

		logger.info("removePurchase: " + ((Integer) id).toString());

		em.remove(em.find(Purchase.class, id));

	}
	
	/*
	 * QUERY METHODS
	 */
	
	
	@Override
	public List<PurchaseDTO> getAll(){
		
		return em.createNamedQuery(Purchase.FIND_ALL).getResultList();
		
	}
	
	@Override
	public PurchaseDTO findById(int purchaseId){
		
		Purchase purchase = em.find(Purchase.class, purchaseId);
		
		PurchaseDTO purchaseDTO = new PurchaseDTO();
		purchaseDTO.setId(purchase.getId());
		purchaseDTO.setPersPackageId(purchase.getPersPackageId());
		purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
		purchaseDTO.setUsername(purchase.getUsername());
		
		return purchaseDTO;
		
	}
	
	@Override
	public List<PurchaseDTO> getByUser(String username){
		
		return em.createNamedQuery(Purchase.FIND_BY_USERNAME).setParameter("username", username).getResultList();
		
	}
	
	@Override
	public List<PurchaseDTO> getByPersPackageId(int persPackageId){
		
		return em.createNamedQuery(Purchase.FIND_BY_PERS_PACKAGE_ID).setParameter("persPackageId", persPackageId).getResultList();
		
	}
	
}
