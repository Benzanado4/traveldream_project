/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean
@RequestScoped
public class LogoutBean {
	
	public String logout() {
		
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "/home?faces-redirect=true";
	  }
}
