/********************************************************
 * Declarations
 ********************************************************/
package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.dtos.GiftListDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.ProductDTO;
import it.polimi.traveldream.managers.GiftListManager;
import it.polimi.traveldream.managers.PersPackageManager;
import it.polimi.traveldream.managers.ProductManager;
import it.polimi.traveldream.managers.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "giftListBean")
@ViewScoped
public class GiflListBean {

	@EJB
	private GiftListManager giftListMgr;
	
	@EJB
	private UserManager userMgr;
	
	@EJB
	private ProductManager productMgr;

	@EJB
	private PersPackageManager persPackageMgr;

	private List<ProductDTO> productsInGiftList;

	private ProductDTODataModel productDTOsDataModel;
	
	private PersPackageDTO currentPersPackageDTO;

	private List<GiftListDTO> giftListDTOs;

	private String curretnPersPackageId;

	private static Logger logger = Logger.getLogger(PersPackageBean.class
			.getName());

	@PostConstruct
	private void init() {

		logger.info("GiftListBean init()");

		curretnPersPackageId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("persPackageId");

		productsInGiftList = new ArrayList<ProductDTO>();
		
		if (curretnPersPackageId != null) {
			
			logger.info("curretnPersPackageId: " + curretnPersPackageId);
			
			List<GiftListDTO> giftListDTOs = giftListMgr
					.findByPersPackageId(Integer.valueOf(curretnPersPackageId));
			
			logger.info("GiftListBean: giftListDTOs: " + giftListDTOs.toString());
			
			currentPersPackageDTO = persPackageMgr.findById(Integer.valueOf(curretnPersPackageId));

			logger.info("GiftListBean: currentPersPackageDTO" +currentPersPackageDTO.toString());
			
			logger.info("GiftListBean: currentPersPackageDTO.getProductDTOs()" + currentPersPackageDTO.getProductDTOs().toString());
			
			productDTOsDataModel = new ProductDTODataModel(currentPersPackageDTO.getProductDTOs());
			
			logger.info("GiftListBean: productDTOsDataModel" + productDTOsDataModel.toString());

			
			if(giftListDTOs.size() > 0){
				productsInGiftList = fromGiftListDTOtoProductDTO(giftListDTOs);
			}
			
			logger.info("GiftListBean: productsInGiftList" + productsInGiftList.toString());


		} 
		
	}

	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		return event.getNewStep();
	}

	public String confirmGiftList(){
		
		giftListMgr.updatePersPackageGiftList(fromProductDTOstoGiftListDTOs(productsInGiftList));
		
		return "/user/persPackage/persPackageDetail?persPackageId=" + curretnPersPackageId
				+ "&faces-redirect=true";	
	}
	
	public boolean isProductPurchased(int productId){
			
		Boolean isProductPuchased = giftListMgr.isProductPurchased( Integer.valueOf(curretnPersPackageId),productId);
		
		logger.info("isProductPuchased: " + isProductPuchased);
		
		return isProductPuchased;
		
	}
	
	public String purchaseProductGiftList(int productId){
		giftListMgr.purchaseProductGiftList(Integer.valueOf(curretnPersPackageId), productId, userMgr.getLoggedUser().getUsername());
		return "/user/partPackageDetail?persPackageId=" + curretnPersPackageId + "&faces-redirect=true"; 
	}
	/*
	 * Helper Methods
	 */
	public List<ProductDTO> fromGiftListDTOtoProductDTO(
			List<GiftListDTO> giftListDTOs) {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

		for (GiftListDTO giftListDTO : giftListDTOs) {
			productDTOs.add(productMgr.findById(giftListDTO.getProductId()));
		}

		return productDTOs;
	}

	public boolean packageHasGiftList(){
		return giftListMgr.packageHasGiftList(Integer.valueOf(curretnPersPackageId));
	}

	public List<GiftListDTO> fromProductDTOstoGiftListDTOs(List<ProductDTO> productDTOs){
		
		List<GiftListDTO> giftListDTOs = new ArrayList<GiftListDTO>();
		
		for(ProductDTO productDTO : productDTOs){
			
			GiftListDTO giftListDTO = new GiftListDTO();
			
			giftListDTO.setPersPackageId(Integer.valueOf(curretnPersPackageId));
			giftListDTO.setProductId(productDTO.getProductId());
			
			giftListDTOs.add(giftListDTO);
		}
		
		return giftListDTOs;
	}
	/*
	 * Getter Setter
	 */

	public List<ProductDTO> getProductsInGiftList() {
		return productsInGiftList;
	}

	public void setProductsInGiftList(List<ProductDTO> productsInGiftList) {
		this.productsInGiftList = productsInGiftList;
	}

	public List<GiftListDTO> getGiftListDTOs() {
		return giftListDTOs;
	}

	public void setGiftListDTOs(List<GiftListDTO> giftListDTOs) {
		this.giftListDTOs = giftListDTOs;
	}

	public ProductDTODataModel getProductDTOsDataModel() {
		return productDTOsDataModel;
	}

	public void setProductDTOsDataModel(ProductDTODataModel productDTOsDataModel) {
		this.productDTOsDataModel = productDTOsDataModel;
	}

	public PersPackageDTO getCurrentPersPackageDTO() {
		return currentPersPackageDTO;
	}

	public void setCurrentPersPackageDTO(PersPackageDTO currentPersPackageDTO) {
		this.currentPersPackageDTO = currentPersPackageDTO;
	}

	public String getCurretnPersPackageId() {
		return curretnPersPackageId;
	}

	public void setCurretnPersPackageId(String curretnPersPackageId) {
		this.curretnPersPackageId = curretnPersPackageId;
	}
}
