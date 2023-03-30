/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.HotelDTO;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
/********************************************************
 * Class
 *********************************************************/
public class HotelDTODataModel extends ListDataModel<HotelDTO> implements SelectableDataModel<HotelDTO>{

	private static Logger logger = Logger
			.getLogger(HotelDTO.class.getName());
	
	public HotelDTODataModel(){}

	public HotelDTODataModel(List<HotelDTO> hotelDTOs){
		super(hotelDTOs);
	}
	
	@Override
	public HotelDTO getRowData(String rowKey) {
		
        List<HotelDTO> hotelDTOs = (List<HotelDTO>) getWrappedData();  
        
        for(HotelDTO hotelDTO: hotelDTOs){
        	if(String.valueOf(hotelDTO.getProductId()).equals(rowKey)){
        		return hotelDTO;
        	}
        }
		
		return null;
	}

	@Override
	public Object getRowKey(HotelDTO hotelDTO) {
		return hotelDTO.getProductId();
	}
}
