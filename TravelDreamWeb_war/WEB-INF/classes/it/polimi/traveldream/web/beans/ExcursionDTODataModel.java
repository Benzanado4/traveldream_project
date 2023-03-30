/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.ExcursionDTO;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/********************************************************
 * Class
 *********************************************************/
public class ExcursionDTODataModel extends ListDataModel<ExcursionDTO> implements SelectableDataModel<ExcursionDTO>{

	private static Logger logger = Logger
			.getLogger(ExcursionDTO.class.getName());
	
	public ExcursionDTODataModel(){}

	public ExcursionDTODataModel(List<ExcursionDTO> excursionDTOs){
		super(excursionDTOs);
	}
	
	@Override
	public ExcursionDTO getRowData(String rowKey) {
		
        List<ExcursionDTO> excursionDTOs = (List<ExcursionDTO>) getWrappedData();  
        
        for(ExcursionDTO excursionDTO: excursionDTOs){
        	if(String.valueOf(excursionDTO.getProductId()).equals(rowKey)){
        		return excursionDTO;
        	}
        }
		
		return null;
	}


	@Override
	public Object getRowKey(ExcursionDTO excursionDTO) {
		return excursionDTO.getProductId();
	}
}