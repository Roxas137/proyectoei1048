package es.uji.ei1048;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.facade.WeatherAppFacade;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.service.IWeatherService;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMap;
import es.uji.ei1048.utils.Constants;

public class Main {
    public static void main(String[] args) {

        // TODO: 12/01/2020 Los atributos se tienen que coger a traves de JavaFX
        IWeatherService service = new OpenWeatherMap();
        String ciudad = "";
        double latitud = 0;
        double longitud = 0;
        Coordenadas coor = new Coordenadas(latitud, longitud);
        int placeType = Constants.PLACE_CITY;  // Si la peticion es una ciudad o unas coordenadas
        int petitionType = Constants.PETITION_CURRENT;  // Si la peticion es tiempo actual o prediccion

        IWeatherAppFacade weatherAppFacade = new WeatherAppFacade(service);

        if (placeType == Constants.PLACE_CITY && petitionType == Constants.PETITION_CURRENT) {
            weatherAppFacade.getCondicionesActuales(ciudad);
        } else if (placeType == Constants.PLACE_CITY && petitionType == Constants.PETITION_PREDICTION) {
            weatherAppFacade.getPrediccion(ciudad);
        } else if (placeType == Constants.PLACE_COORDENATES && petitionType == Constants.PETITION_CURRENT) {
            weatherAppFacade.getCondicionesActuales(coor);
        } else if (placeType == Constants.PLACE_COORDENATES && petitionType == Constants.PETITION_PREDICTION) {
            weatherAppFacade.getPrediccion(coor);
        }

    }
}
