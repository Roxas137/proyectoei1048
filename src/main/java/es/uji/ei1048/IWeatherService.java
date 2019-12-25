package es.uji.ei1048;

import es.uji.ei1048.utils.Unit;

import java.io.IOException;

/**
 *
 */
public interface IWeatherService {
    String getCurrentWeather() throws IOException;
    String getPredictionWeather() throws IOException;
}
