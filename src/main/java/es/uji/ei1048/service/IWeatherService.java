package es.uji.ei1048.service;

import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMapTypeId;
import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

/**
 *
 */
public interface IWeatherService {
    Map<Long, String> getCities();
    String getCurrentWeather() throws IOException;
    String getPredictionWeather() throws IOException;
    boolean checkCity(String city) throws NotImplementedException;
    String getCurrentWeather(OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws IOException;

    long getIdCiudadEscogido();
    Coordenadas getCoordenadasEscogidas();
    Calendar getFechaCondicion();
    Calendar getFechaPeticion();

    long getIdByCity(String ciudad);
}
