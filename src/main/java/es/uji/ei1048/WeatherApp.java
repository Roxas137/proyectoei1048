package es.uji.ei1048;

import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class WeatherApp {
    private IWeatherService service;

    public WeatherApp() {
    }

    public WeatherApp(IWeatherService service) {
        this.service = service;
    }

    public CondicionesMeteorologicas getCurrentWeather(OpenWeatherMapTypeId typeId, String[] place, Unit unit) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getWeather(Constants.PETITION_CURRENT, typeId, place, unit);
            // TODO convertir a CondicionesMetereologicas
            // TODO actualizar la BBDD
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException();
    }
}
