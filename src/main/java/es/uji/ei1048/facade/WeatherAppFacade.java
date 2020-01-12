package es.uji.ei1048.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.WeatherApp;
import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.exceptions.InvalidDateException;
import es.uji.ei1048.service.IWeatherService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class WeatherAppFacade implements IWeatherAppFacade {

    private WeatherApp weatherApp;
    private IWeatherService service;
    private Gson gson;

    public WeatherAppFacade(IWeatherService service) {
        weatherApp = new WeatherApp();
        this.service = service;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales() {

        // TODO: 27/12/2019 Usado para debug, borrar
        //CondicionesMeteorologicas condicionesMeteorologicas = weatherApp.getCurrentWeather(service);
        //String jsonResult = gson.toJson(condicionesMeteorologicas);

        return null;//condicionesMeteorologicas;
    }

    @Override
    public List<CondicionesMeteorologicas> getPrediccion() {
        List<CondicionesMeteorologicas> prediction = weatherApp.getPredictionWeather(service);
        String jsonResult = gson.toJson(prediction);
        System.out.println(jsonResult);

        return prediction;
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(String ciudad) throws InvalidCityException {
        checkCity(ciudad);
        return weatherApp.getCurrentWeather(service, ciudad);
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) throws InvalidCoordenatesException {
        // Comprobar ciudad valida
        checkCoordenates(coord);
        return null; //weatherApp.getCurrentWeather(service);
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

    /**
     * Comprueba que la ciudad exista.
     * @param city Ciudad introducida por el usuario.
     * @throws InvalidCityException En el caso de que la ciudad no exista.
     */
    private void checkCity(String city) throws InvalidCityException {
        if (!service.checkCity(city)){
            throw new InvalidCityException();
        }
    }

    private void checkCoordenates(Coordenadas coord) throws InvalidCoordenatesException {
        if (!(coord.isValid())) {
            throw new InvalidCoordenatesException();
        }
    }
}
