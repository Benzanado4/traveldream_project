/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.managers;

import it.polimi.traveldream.dtos.UserDTO;
import javax.ejb.Local;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface UserManager {

	public void addUser(UserDTO utenteDTO);

	public void updateUser(UserDTO utenteDTO);

	public void unregisterLoggedUser();
	
	public UserDTO getLoggedUser();

	public boolean isUserLogged();
	
	public String userLoggedType();


}
