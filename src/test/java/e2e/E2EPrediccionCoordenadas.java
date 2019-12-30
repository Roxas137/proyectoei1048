package e2e;

import es.uji.ei1048.CondicionesMeteorologicas;
import es.uji.ei1048.Coordenadas;
import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.exceptions.InvalidDateException;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class E2EPrediccionCoordenadas extends E2ETestBed{

    /**
     * Comprobar que funciona cuando las coordenadas no son validas
     */
    @Test
    public void coorValid() {
        // Given:   una coordenadas
        double latitud = 5.0;
        double longitud = 0.0;
        Coordenadas coor = new Coordenadas(latitud, longitud);

        // Given:   una fecha y hora para la prediccion
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.add(Calendar.DAY_OF_MONTH, 3);
        fecha.set(Calendar.HOUR_OF_DAY, 16);

        // When:    el usuario busca esa ciudad
        //          la aplicacion hace una peticion al servicio meteorologico
        List<CondicionesMeteorologicas> condicionesCiudad = weatherApp.getPrediccion(coor);

        // Then:    el servicio meteorologico devuelve las condiciones climaticas de esa ciudad
        assertNotNull(condicionesCiudad);
    }

    /**
     * Comprobar que salta la excepcion correspondiente cuando las coordenadas no son validas
     */
    @Test(expected = InvalidCoordenatesException.class)
    public void coorInvalid() {
        // Given:   una coordenadas
        double latitud = 91.0;
        double longitud = 181.0;
        Coordenadas coor = new Coordenadas(latitud, longitud);

        // Given:   una fecha y hora para la prediccion
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.add(Calendar.DAY_OF_MONTH, 3);
        fecha.set(Calendar.HOUR_OF_DAY, 16);

        // When:    el usuario busca esa ciudad
        //          la aplicacion hace una peticion al servicio meteorologico
        List<CondicionesMeteorologicas> condicionesCiudad = weatherApp.getPrediccion(coor);

        // Then:    espero que se lance una excepcion

    }
//
//    /**
//     * Comprobar que salta la excepcion correspondiente cuando la fecha no es valida
//     */
//    @Test(expected = InvalidDateException.class)
//    public void dateInvalid() {
//        // Given:   una ciudad valida (String)
//        String ciudad = "";
//
//        // Given:   una fecha y hora invalida para la prediccion (Fecha pasada)
//        Calendar fecha = GregorianCalendar.getInstance();
//        fecha.add(Calendar.DAY_OF_MONTH, -3);
//        fecha.set(Calendar.HOUR_OF_DAY, 16);
//
//        // When:    la ciudad no existe
//        List<CondicionesMeteorologicas> condicionesCiudad = weatherApp.getPrediccion(ciudad);
//
//        // Then:    espero que se lance una excepcion
//    }
}
