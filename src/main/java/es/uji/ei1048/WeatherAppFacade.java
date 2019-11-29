package es.uji.ei1048;

import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.exceptions.InvalidDateException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Calendar;
import java.util.List;

public class WeatherAppFacade implements IWeatherAppFacade {

    public WeatherAppFacade() {
        // Circulen, nada que ver aqui
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(String ciudad) throws InvalidCityException {
        // Comprobar ciudad valida
        checkCity(ciudad);

        //TODO
        throw new NotImplementedException();
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) throws InvalidCoordenatesException {
        // Comprobar ciudad valida
        checkCoordenates(coord);

        //TODO
        throw new NotImplementedException();
    }

    @Override
    public List<CondicionesMeteorologicas> getPrediccion(String ciudad) throws InvalidCityException {
        // Comprobar ciudad valida
        checkCity(ciudad);

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
        if (coord.getLatitud() < -90 || coord.getLatitud() > 90 ||
            coord.getLongitud() < -180 || coord.getLongitud() > 180) {
            throw new InvalidCoordenatesException();
        }
    }
}
