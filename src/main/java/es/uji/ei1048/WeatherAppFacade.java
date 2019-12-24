package es.uji.ei1048;

import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.exceptions.InvalidDateException;
import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Calendar;
import java.util.List;

public class WeatherAppFacade implements IWeatherAppFacade {

    private WeatherApp weatherApp;

    public WeatherAppFacade() {
        weatherApp = new WeatherApp();
    }

    // Ni siquiera se si sera necesario
    public void connect() {

    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(String ciudad) throws InvalidCityException {
        checkCity(ciudad);
        return weatherApp.getCurrentWeather(OpenWeatherMapTypeId.NAME, new String[]{ciudad}, Unit.CELSIUS);  // Por defecto he puesto celsius
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) throws InvalidCoordenatesException {
        // Comprobar ciudad valida
        checkCoordenates(coord);
        return weatherApp.getCurrentWeather(OpenWeatherMapTypeId.COORDENATES, new String[]{String.valueOf(coord.getLatitud()), String.valueOf(coord.getLongitud())}, Unit.CELSIUS);
    }

    @Override
    public List<CondicionesMeteorologicas> getPrediccion(String ciudad) throws InvalidCityException {
        // Comprobar ciudad valida
        checkCity(ciudad);

        //TODO
        throw new NotImplementedException();
    }

    @Override
    public List<CondicionesMeteorologicas> getPrediccion(Coordenadas coord) throws InvalidCoordenatesException, InvalidDateException {
        // Comprobar coordenadas validas
        checkCoordenates(coord);

        // TODO
        throw new NotImplementedException();
    }


    private void checkCity(String city) throws InvalidCityException {
        //TODO
    }

    private void checkCoordenates(Coordenadas coord) throws InvalidCoordenatesException {
        if (!(coord.isValid())) {
            throw new InvalidCoordenatesException();
        }
    }
}
