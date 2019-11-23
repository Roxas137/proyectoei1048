package es.uji.ei1048;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Calendar;

public class WeatherAppFacade implements IWeatherAppFacade {

    public WeatherAppFacade() {
        // Circulen, nada que ver aqui
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(String ciudad) {
        throw new NotImplementedException();
    }

    @Override
    public CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) {
        throw new NotImplementedException();
    }

    @Override
    public CondicionesMeteorologicas getPrediccion(String ciudad, Calendar fecha) {
        throw new NotImplementedException();
    }

    @Override
    public CondicionesMeteorologicas getPrediccion(Coordenadas coord, Calendar fecha) {
        throw new NotImplementedException();
    }
}
