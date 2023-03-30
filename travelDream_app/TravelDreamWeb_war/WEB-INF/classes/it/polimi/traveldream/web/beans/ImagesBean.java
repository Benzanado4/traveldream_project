/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/********************************************************
 * Class
 *********************************************************/
@ManagedBean(name = "imagesBean")
@RequestScoped
public class ImagesBean {
	
	int[] imageIds = { 1,2,3,4,5,6,7,8,9,10,11,12 };

	public int[] getImageIds() {
		return imageIds;
	}

	public void setImageIds(int[] imageIds) {
		this.imageIds = imageIds;
	}
}
