/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.FlightDTO;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/********************************************************
 * Class
 *********************************************************/
public class FlightDTODataModel extends ListDataModel<FlightDTO> implements SelectableDataModel<FlightDTO>{

	private static Logger logger = Logger
			.getLogger(FlightDTO.class.getName());
	
	public FlightDTODataModel(){}

	public FlightDTODataModel(List<FlightDTO> flightDTOs){
		super(flightDTOs);
	}
	
	@Override
	public FlightDTO getRowData(String rowKey) {
		
        List<FlightDTO> flightDTOs = (List<FlightDTO>) getWrappedData();  
        
        for(FlightDTO flightDTO: flightDTOs){
        	if(String.valueOf(flightDTO.getProductId()).equals(rowKey)){
        		return flightDTO;
        	}
        }
		
		return null;
	}

	@Override
	public Object getRowKey(FlightDTO flightDTO) {
		return flightDTO.getProductId();
	}
}
