package es.uji.ei1048.service;

import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Calendar;

/**
 *
 */
public interface IWeatherService {
    String getCurrentWeather() throws IOException;
    String getPredictionWeather() throws IOException;
    boolean checkCity(String city) throws NotImplementedException;

    long getIdCiudadEscogido();
    Coordenadas getCoordenadasEscogidas();
    Calendar getFechaCondicion();
    Calendar getFechaPeticion();

    long getIdByCity(String ciudad);
}
