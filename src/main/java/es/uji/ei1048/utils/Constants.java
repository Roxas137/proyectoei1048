package es.uji.ei1048.utils;

public class Constants {
    // Api Key
    public static final String APIKEY = "6380e82204180cff12d9e9393a003a08";

    // Api Call
    public static final String APICALL = "http://api.openweathermap.org/data/2.5/";
    public static final String CURRENT = "weather";
    public static final String PREDICTION = "forecast";

    // Petition types
    public static final int PETITION_PREDICTION = 0;
    public static final int PETITION_CURRENT = 1;

    // Place types
    public static final int PLACE_CITY = 0;
    public static final int PLACE_COORDENATES = 1;

    // ID Ciudades
    public static final String ID_CASTELLON = "6356995";
    public static final String ID_VALLADOLID = "6362308";
    public static final Long NULL_CITY = -500L;

    // Condiciones minimas
    public static final int LATITUD_MINIMA = -90;
    public static final int LATITUD_MAXIMA = 90;
    public static final int LONGITUD_MINIMA = -180;
    public static final int LONGITUD_MAXIMA = 180;

}
