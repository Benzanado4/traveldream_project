/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.managers;

import java.util.List;
import javax.ejb.Local;
import it.polimi.traveldream.dtos.DefaultPackageDTO;
import it.polimi.traveldream.dtos.ExcursionDTO;
import it.polimi.traveldream.dtos.FlightDTO;
import it.polimi.traveldream.dtos.HotelDTO;
import it.polimi.traveldream.dtos.PersPackageDTO;
import it.polimi.traveldream.dtos.PurchaseDTO;
import it.polimi.traveldream.dtos.UserDTO;
import it.polimi.traveldream.exception.AlreadyPurchasedException;
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface PersPackageManager {

	/**
	 * @param persPackageDTO
	 * @return 
	 * @throws ConsistencyException
	 */
	public int addPersPackage(PersPackageDTO persPackageDTO)
			throws ConsistencyException;

	/**
	 * @param persPackageDTO
	 * @throws NoIdException
	 */
	public void removePersPackage(PersPackageDTO persPackageDTO) throws NoIdException;

	/**
	 * @param id
	 */
	public void removePersPackagebyId(int id);

	/**
	 * @param persPackageDTO
	 * @throws ConsistencyException
	 * @throws NoIdException
	 */
	public void updatePersPackage(PersPackageDTO persPackageDTO)
			throws ConsistencyException, NoIdException;


	/**
	 * @param name
	 * @return
	 */
	public List<PersPackageDTO> findByName(String name);

	/**
	 * @param persPackageId
	 * @return
	 */
	public PersPackageDTO findById(int persPackageId);

	/**
	 * @param PersPackageId
	 * @return
	 */
	public boolean isPurchased(int PersPackageId);

	/**
	 * @param username
	 * @return
	 */
	public List<PersPackageDTO> findByUser(String username);

	/**
	 * @param packageDTO
	 * @return
	 */
	public List<HotelDTO> getPackageHotelDTOs(PersPackageDTO persPackageDTO);

	/**
	 * @param packageDTO
	 * @return
	 */
	public List<ExcursionDTO> getPackageExcursionDTOs(PersPackageDTO persPackageDTO);

	/**
	 * @param packageDTO
	 * @return
	 */
	public List<FlightDTO> getPackageFlightDTOs(PersPackageDTO persPackageDTO);

	/**
	 * @param persPackageId
	 * @param username
	 * @return
	 * @throws AlreadyPurchasedException 
	 */
	public PurchaseDTO purchasePersPackage(int persPackageId, String username) throws AlreadyPurchasedException;

	/**
	 * @param mailAddress
	 * @param persPackageId
	 */
	void sendMailInvite(String mailAddress, int persPackageId);

	/**
	 * @param username
	 * @param persPackageId
	 */
	void addPartUser(String username, int persPackageId);

	/**
	 * @param username
	 * @param persPackageId
	 * @return
	 */
	boolean isUserPartPackage(String username, int persPackageId);

	/**
	 * @param persPackageId
	 * @return
	 */
	List<UserDTO> getPartUser(int persPackageId);

	/**
	 * @param username
	 * @return
	 */
	List<PersPackageDTO> getPartPersPackage(String username);

	/**
	 * @param username
	 * @param persPackageId
	 * @return
	 */
	boolean userOwnPersPackage(String username, int persPackageId);


}
