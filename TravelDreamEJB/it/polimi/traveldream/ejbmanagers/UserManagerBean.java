/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.ejbmanagers;

import java.util.ArrayList;
import java.util.List;
import it.polimi.traveldream.consistency.ProductConsistencyManager;
import it.polimi.traveldream.dtos.UserDTO;
import it.polimi.traveldream.helper.Constants;
import it.polimi.traveldream.helper.MailConstants;
import it.polimi.traveldream.managers.UserManager;
import it.polimi.traveldream.persistence.City;
import it.polimi.traveldream.persistence.Group;
import it.polimi.traveldream.persistence.Product;
import it.polimi.traveldream.persistence.User;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.platform.database.MySQLPlatform;
/********************************************************
 * Classes
 *********************************************************/
@Stateless
public class UserManagerBean implements UserManager {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	@Override
	public void addUser(UserDTO userDTO) {
		User newUser = new User(userDTO);
		List<Group> groups = new ArrayList<Group>();
		groups.add(Group.USER);
		newUser.setGroups(groups);
		em.persist(newUser);
		
		(new MailManager()).sendMail(newUser.getEmail(), MailConstants.TYPE_REGISTER_CONFIRM, null);
		
	}

	@Override
	@RolesAllowed({ Group._USER, Group._ADMIN })
	public void updateUser(UserDTO utenteDTO) {
		User newUser = new User(utenteDTO);
		List<Group> groups = new ArrayList<Group>();
		groups.add(Group.USER);
		newUser.setGroups(groups);
		em.merge(newUser);
	}
	
	public City findById(int id){

		return em.find(City.class, id);
	}

	@Override
	@RolesAllowed({ Group._USER })
	public void unregisterLoggedUser() {
		remove(getPrincipalId());
	}

	@Override
	@RolesAllowed({ Group._USER, Group._ADMIN })
	public UserDTO getLoggedUser(){
		UserDTO userDTO = convertToDTO(getPrincipalUser());
		return userDTO;
	}


	@Override
	public boolean isUserLogged() {
		if(context.isCallerInRole(Group._USER) || context.isCallerInRole(Group._ADMIN)){
			return true;
		}
		
		return false;
	}

	private UserDTO convertToDTO(User principalUser) {

		UserDTO userDTO = new UserDTO();

		userDTO.setUsername(principalUser.getUsername());
		userDTO.setPassword(principalUser.getPassword());
		userDTO.setEmail(principalUser.getEmail());
		userDTO.setName(principalUser.getName());
		userDTO.setSurname(principalUser.getSurname());
		userDTO.setAddress(principalUser.getAddress());
		userDTO.setPhoneNumber(principalUser.getPhoneNumber());

		return userDTO;
	}

	public User find(String id) {
		return em.find(User.class, id);
	}

	public List<User> getAllUsers() {
		return em.createNamedQuery(User.FIND_ALL, User.class).getResultList();
	}

	public void remove(String id) {
		User user = find(id);
		em.remove(user);
	}

	public void remove(User user) {
		em.remove(user);
	}

	public User getPrincipalUser() {
		return find(getPrincipalId());
	}

	public String getPrincipalId() {
		return context.getCallerPrincipal().getName();
	}
	
	@Override
	public String userLoggedType(){
		
		if(context.isCallerInRole(Group._USER)){
			return Constants.USERTYPE_USER;
		}
		
		if(context.isCallerInRole(Group._ADMIN)){
			return Constants.USERTYPE_ADMIN;
		}
		
		return Constants.NOT_LOGGED;
		
	}

}
