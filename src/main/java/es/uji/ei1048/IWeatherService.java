package es.uji.ei1048;

import es.uji.ei1048.utils.Unit;

import java.io.IOException;

/**
 *
 */
public interface IWeatherService {
    String getWeather(int typePetition, OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws IOException;
}
