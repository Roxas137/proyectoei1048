package es.uji.ei1048;

import java.util.Calendar;

public interface IWeatherAppFacade {
    CondicionesMeteorologicas getCondicionesActuales(String ciudad);
    CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord);
    CondicionesMeteorologicas getPrediccion(String ciudad, Calendar fecha);
    CondicionesMeteorologicas getPrediccion(Coordenadas coord, Calendar fecha);

}
