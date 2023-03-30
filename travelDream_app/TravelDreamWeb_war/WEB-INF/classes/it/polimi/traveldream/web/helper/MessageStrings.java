package it.polimi.traveldream.web.helper;

public class MessageStrings {
	
	public static final String MANDATORY_FIELD = "Campo Obbligatorio";
	public static final String CORRECT_INSERT = "Inserimento Completato";
	public static final String NEW_PRODUCT = "Nuovo Prodotto";
	public static final String SAME_CITY1_CITY2 = "Citta di partenza e arrivo non possono coincidere";
	public static final String BEFORE_TODAY = "La data selezionata deve essere successiva alla data odierna";
	public static final String EXISTING_PRODUCT = "Il prodotto esiste gia";	
	public static final String FLIGHTS_NUM = "Non e possibile inserire piu di 2 voli";
	public static final String FLIGHTS_CITY = "Il volo di andata e ritorno devono essere compatibili. La destinazione del volo di andata deve essere uguale alla partenza del volo di ritorno";
	public static final String HOTEL_CITY = " L hotel deve essere scelto nella stesso luogo di destinazione del volo";
	public static final String HOTEL_NUM = "Non e possibile inserire piu di un hotel";
	public static final String EXCURSION_CITY = "Non e possibile inserire una escursione in un luogo diverso dalla destinazione del volo";
	public static final String EXCURSION_DATE = "L'escursione scelta non rientra nelle date di inzio e fine pacchetto";
	
	public static String getMandatoryField() {
		return MANDATORY_FIELD;
	}	
}
