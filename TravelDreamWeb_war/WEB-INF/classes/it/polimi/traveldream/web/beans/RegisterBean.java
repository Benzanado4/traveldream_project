/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import it.polimi.traveldream.dtos.UserDTO;
import it.polimi.traveldream.managers.UserManager;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name="registerBean")
@RequestScoped
public class RegisterBean {
	
	@EJB
	private UserManager userMgr;

	private UserDTO user;
	
	
	public RegisterBean() {
		user = new UserDTO();
	}


	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public String register() {
		try {
			userMgr.addUser(user);
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage("registerForm:username", 
					new FacesMessage("Username utilizzato"));
			return null;
		}

		return "home?faces-redirect=true";
	}


	public String getName() {
			return userMgr.getLoggedUser().getName();
	}
	
}
