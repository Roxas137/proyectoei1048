package es.uji.ei1048;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.facade.WeatherAppFacade;
import es.uji.ei1048.service.IWeatherService;
import es.uji.ei1048.service.OpenWeatherMap;

public class Main {
    public static void main(String[] args) {
        IWeatherService service = new OpenWeatherMap();
        IWeatherAppFacade weatherAppFacade = new WeatherAppFacade(service);

        weatherAppFacade.getCondicionesActuales();
    }
}
