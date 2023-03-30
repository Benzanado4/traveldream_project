/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.io.IOException;

import it.polimi.traveldream.dtos.UserDTO;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.managers.UserManager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean
@RequestScoped
public class UserBean {
	
	@EJB
	private UserManager userMgr;
	
	private UserDTO loggedUser;
	
	private String redirectPage;
	
	public final String usertype_user = Constants.USERTYPE_USER;
	
	public final String usertype_admin = Constants.USERTYPE_ADMIN;
	
	@PostConstruct
	public void init(){
		if(isUserLogged()){
			loggedUser = userMgr.getLoggedUser();
		}
		
	}

	public UserDTO getLoggedUser(){
		return loggedUser;
	}
	
	public void setLoggedUser(UserDTO loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean isUserLogged(){		
		return userMgr.isUserLogged();
	}
	
	public String getUserLoggedType(){
		String UserLoggedType = userMgr.userLoggedType();
		return UserLoggedType;	
	}
	
	public String updateUserData(){
		userMgr.updateUser(loggedUser);
		return "/user/index?faces-redirect=true";
	}
 
	public String getUsertype_user() {
		return usertype_user;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

	public String getUsertype_admin() {
		return usertype_admin;
	}
}
