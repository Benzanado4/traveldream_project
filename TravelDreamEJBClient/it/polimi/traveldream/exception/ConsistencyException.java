/********************************************************
 * Declarations
 *********************************************************/
package it.polimi.traveldream.exception;
/********************************************************
 * Classes
 *********************************************************/
public class ConsistencyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String SAME_CITY1_CITY2 = "same_city1_city2"; 
	
	public static final String BEFORE_TODAY = "before_today"; 
	
	public static final String EXISTING_PRODUCT = "existing_product";

	public static final String WRONG_FLIGHTS_NUM = "wrong_flights_num";
	
	public static final String WRONG_HOTELS_NUM = "wrong_hotels_num";
	
	public static final String WRONG_FLIGHT_CITIES = "wrong_flight_cities";
	
	public static final String WRONG_HOTEL_CITY = "wrong_hotel_city";
	
	public static final String WRONG_EXCURSION_CITY = "wrong_excursione_city";
	
	public static final String WRONG_EXCURSION_DATE = "wrong_excursion_date"; 
		
	private String type;

	public ConsistencyException(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
