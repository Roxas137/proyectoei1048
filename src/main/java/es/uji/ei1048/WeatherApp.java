package es.uji.ei1048;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class WeatherApp {

    public WeatherApp() {
    }

    public CondicionesMeteorologicas getCurrentWeather(IWeatherService service) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getCurrentWeather();
            // TODO convertir a CondicionesMetereologicas
            // TODO actualizar la BBDD
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException();
    }

    public CondicionesMeteorologicas getPredictionWeather(IWeatherService service) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getPredictionWeather();
            // TODO convertir a CondicionesMetereologicas
            // TODO actualizar la BBDD
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException();
    }
}
