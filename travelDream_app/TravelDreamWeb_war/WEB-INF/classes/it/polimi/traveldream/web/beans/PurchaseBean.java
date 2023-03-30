/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.dtos.PurchaseDTO;
import it.polimi.traveldream.managers.PersPackageManager;
import it.polimi.traveldream.managers.PurchaseManager;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "purchaseBean")
@RequestScoped
public class PurchaseBean {
	
	@EJB
	private PersPackageManager persPackageMgr;
	
	@EJB
	private PurchaseManager purchaseMgr;
	
	private static Logger logger = Logger
			.getLogger(PackageBean.class.getName());

	@PostConstruct
	public void init(){
		
	}
	
	public List<PurchaseDTO> getAll(){
		return purchaseMgr.getAll();
	}
	
	public PersPackageDTO getPersPackageByPurchaseId(int purchaseId){
		return persPackageMgr.findById(purchaseId);
	}
	
	public String getPersPackageNameByPurchaseId(int purchaseId){
		return persPackageMgr.findById(purchaseId).getName();
	}
	
	public float calculatePersPackagePriceByPurchaseId(int purchaseId){
		PersPackageDTO persPackageDTO = persPackageMgr.findById(purchaseId);
		
		List<ProductDTO> productDTOs = persPackageDTO.getProductDTOs();
		
		float totalPrice = 0;
		
		for (ProductDTO productDTO : productDTOs) {
			totalPrice = totalPrice + productDTO.getPrice();
		}

		return totalPrice;
	}
}
