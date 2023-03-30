/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.logging.Logger;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.managers.UserManager;
import it.polimi.traveldream.web.helper.MessageStrings;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "navigationBean")
@RequestScoped
public class navigationBean {
	
	@EJB
	private UserManager userMgr;
	/*
	 * Costanti
	 */
	String mandatory_field = MessageStrings.MANDATORY_FIELD;
	String correct_insert = MessageStrings.CORRECT_INSERT;
	String new_product = MessageStrings.NEW_PRODUCT;

	private static Logger logger = Logger
			.getLogger(navigationBean.class.getName());
	
	private String newProductChoose;
	private MessageStrings messageStrings;
	/**
	 * passaggio parametro choose per la scelta del prodotto da inserire
	 * 
	 * @return
	 */
	public String toNewProductFilter() {
		return "/admin/product/insertProduct?choose=" + newProductChoose
				+ "&faces-redirect=true";
	}

	public String toProductDetail(String productId) {
		logger.info("toNewPackageDetail(" + productId + ")");
		return "/admin/product/ProductDetail?productId=" + productId
				+ "&faces-redirect=true";
	}
	
	public String toPackageModify(String packageId) {
		logger.info("toPackageModify(" + packageId + ")");
		return "/admin/package/packageDetail?packageId=" + packageId
				+ "&faces-redirect=true";
	}
	
	public String toPackageDetail(String packageId) {
		logger.info("toPackageDetail(" + packageId + ")");
		return "/pages/defaultPackageDetail?packageId=" + packageId
				+ "&faces-redirect=true";
	}
	
	public String toPersPackageDetail(String persPackageId) {
		logger.info("toPersPackageDetail(" + persPackageId + ")");
		return "/user/persPackage/persPackageDetail?persPackageId=" + persPackageId
				+ "&faces-redirect=true";
	}
	
	public String toPartPersPackageDetail(String persPackageId) {
		logger.info("toPartPersPackageDetail(" + persPackageId + ")");
		return "/user/partPackageDetail?persPackageId=" + persPackageId
				+ "&faces-redirect=true";
	}
	public String toPersPackageModify(String persPackageId) {
		logger.info("toPersPackageModify(" + persPackageId + ")");
		return "/user/persPackage/persPackageModify?persPackageId=" + persPackageId
				+ "&faces-redirect=true";
	}
	
	public String toPackageGiftList(String persPackageId) {
		logger.info("toPackageGiftList(" + persPackageId + ")");
		return "/user/persPackage/packageGiftList?persPackageId=" + persPackageId
				+ "&faces-redirect=true";
	}
	
	public String toPartGiftList(String persPackageId) {
		logger.info("toPartGiftList(" + persPackageId + ")");
		return "/user/partGiftList?persPackageId=" + persPackageId
				+ "&faces-redirect=true";
	}
	
	public String toPersPackageList() {
		logger.info("toPersPackageList()");
		return "/user/persPackage/persPackageList?faces-redirect=true";
	} 
	
	public String toPartPackageList() {
		logger.info("toPersPackageList()");
		return "/user/partPackageList?faces-redirect=true";
	}
		
	public String toErrorPage(){
		return "/error";
	}
	
	public String getNew_product() {
		return new_product;
	}

	public void setNew_product(String new_product) {
		this.new_product = new_product;
	}

	public String getCorrect_insert() {
		return correct_insert;
	}

	public void setCorrect_insert(String correct_insert) {
		this.correct_insert = correct_insert;
	}

	public String getMandatory_field() {
		return mandatory_field;
	}

	public void setMandatory_field(String mandatory_field) {
		this.mandatory_field = mandatory_field;
	}

	public MessageStrings getMessageStrings() {
		return messageStrings;
	}

	public void setMessageStrings(MessageStrings messageStrings) {
		this.messageStrings = messageStrings;
	}

	public String getNewProductChoose() {
		return newProductChoose;
	}

	public void setNewProductChoose(String newProductChoose) {
		this.newProductChoose = newProductChoose;
	}
}
