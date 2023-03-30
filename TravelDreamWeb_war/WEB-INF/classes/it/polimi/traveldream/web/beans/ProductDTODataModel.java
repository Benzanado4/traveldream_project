/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import java.util.List;
import java.util.logging.Logger;
import it.polimi.traveldream.dtos.ProductDTO;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/********************************************************
 * Class
 *********************************************************/
public class ProductDTODataModel extends ListDataModel<ProductDTO> implements SelectableDataModel<ProductDTO>{

	private static Logger logger = Logger
			.getLogger(PackageBean.class.getName());
	
	public ProductDTODataModel(){}

	public ProductDTODataModel(List<ProductDTO> productDTOs){
		super(productDTOs);
	}
	
	@Override
	public ProductDTO getRowData(String rowKey) {
		
        List<ProductDTO> productDTOs = (List<ProductDTO>) getWrappedData();  

        for(ProductDTO productDTO: productDTOs){
        	if(String.valueOf(productDTO.getProductId()).equals(rowKey)){
        		return productDTO;
        	}
        }
		
		return null;
	}

	@Override
	public Object getRowKey(ProductDTO productDTO) {
		return productDTO.getProductId();
	}
}
