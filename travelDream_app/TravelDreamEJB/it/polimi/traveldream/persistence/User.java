/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.persistence;

import it.polimi.traveldream.dtos.UserDTO;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.List;
/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "USERS")
@NamedQueries({ 
	@NamedQuery(name = User.FIND_ALL, 
			query = "SELECT u FROM User u") })
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Users.findAll";
	/*
	 * FIELDS
	 */
	@ElementCollection(targetClass = Group.class)
    @CollectionTable(name = "USERS_GROUPS",
                    joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    @Column(name="groupname")
    private List<Group> groups;
	
	@Id
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String address;
	private String phoneNumber;

	@ManyToMany
	@JoinTable(name = "PARTECIPATE", joinColumns = { @JoinColumn(name = "username", referencedColumnName = "username") }, inverseJoinColumns = { @JoinColumn(name = "pers_package_id", referencedColumnName = "id") })
	private List<PersPackage> partPackages;
	/*
	 * CONSTRUCTOR
	 */
	public User() {
		super();
	}

	public User(UserDTO userDTO) {

		this.username = userDTO.getUsername();
		this.password = DigestUtils.sha256Hex(userDTO.getPassword());
		this.email = userDTO.getEmail();
		this.address = userDTO.getAddress();
		this.phoneNumber = userDTO.getPhoneNumber();
		this.name = userDTO.getName();
		this.surname = userDTO.getSurname();
	}
	/*
	 * GETTER SETTER 
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	
	public List<PersPackage> getPartPackages() {
		return partPackages;
	}
	
	public void setPartPackages(List<PersPackage> partPackages) {
		this.partPackages = partPackages;
	}
}