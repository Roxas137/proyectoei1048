package es.uji.ei1048;

import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

/**
 *
 */
public interface IWeatherService {
    String getCurrentWeather() throws IOException;
    String getPredictionWeather() throws IOException;
    boolean checkCity(String city) throws NotImplementedException;
}
