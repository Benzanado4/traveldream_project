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
import it.polimi.traveldream.exception.ConsistencyException;
import it.polimi.traveldream.exception.NoIdException;
/********************************************************
 * Interface
 *********************************************************/
@Local
public interface PackageManager  {
	
	/*
	 * Generic Products Methods
	 */
	
	public void addDefaultPackage(DefaultPackageDTO defaultPackageDTO) throws ConsistencyException;
	
	public void updateDefaultPackage(DefaultPackageDTO defaultPackageDTO) throws ConsistencyException, NoIdException;
	
	public void removeDefaultPackage(DefaultPackageDTO defaultPackageDTO) throws NoIdException;
	
	public void removeDefaultPackagebyId(int id);
	
	
	/*
	 * Generic Product Query Methods
	 */
	
	public List<DefaultPackageDTO> getAll();
	
	public List<DefaultPackageDTO> findByName(String name);
	
	public DefaultPackageDTO findById(int defaultPackageId);

	
	public List<HotelDTO> getPackageHotelDTOs(DefaultPackageDTO packageDTO);

	public List<FlightDTO> getPackageFlightDTOs(DefaultPackageDTO packageDTO);

	public List<ExcursionDTO> getPackageExcursionDTOs(DefaultPackageDTO packageDTO);
	

}
