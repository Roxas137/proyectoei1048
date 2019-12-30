package es.uji.ei1048;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.exceptions.InvalidDateException;
import es.uji.ei1048.utils.Unit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class WeatherAppFacade implements IWeatherAppFacade {

    private WeatherApp weatherApp;
    private IWeatherService service;

    public WeatherAppFacade(IWeatherService service) {
        weatherApp = new WeatherApp();
        this.service = service;
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales() {

        // TODO: 27/12/2019 Usado para debug, borrar
        CondicionesMeteorologicas condicionesMeteorologicas = weatherApp.getCurrentWeather(service);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(condicionesMeteorologicas);
        System.out.println(jsonResult);

        return condicionesMeteorologicas;
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(String ciudad) throws InvalidCityException {
        checkCity(ciudad);
        return weatherApp.getCurrentWeather(service);
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) throws InvalidCoordenatesException {
        // Comprobar ciudad valida
        checkCoordenates(coord);
        return weatherApp.getCurrentWeather(service);
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
        // TODO
    }

    private void checkCoordenates(Coordenadas coord) throws InvalidCoordenatesException {
        if (!(coord.isValid())) {
            throw new InvalidCoordenatesException();
        }
    }
}
