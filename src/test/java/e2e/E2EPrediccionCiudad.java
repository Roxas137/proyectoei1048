package e2e;

import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.utils.Constants;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class E2EPrediccionCiudad extends E2ETestBed {

    /**
     * Comprobar que funciona cuando la ciudad es valida
     */
    @Test
    public void cityValid() {
        // Given:   una ciudad (String)
        String ciudad = Constants.ID_CASTELLON;

        // Given:   una fecha y hora para la prediccion
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.add(Calendar.DAY_OF_MONTH, 3);
        fecha.set(Calendar.HOUR_OF_DAY, 16);

        // When:    el usuario busca esa ciudad
        //          la aplicacion hace una peticion al servicio meteorologico
        List<CondicionesMeteorologicas> condicionesCiudad = weatherApp.getPrediccion(ciudad);

        // Then:    el servicio meteorologico devuelve las condiciones climaticas de esa ciudad
        assertNotNull(condicionesCiudad);
    }


    /**
     * Comprobar que salta la excepcion correspondiente cuando la ciudad no es valida
     */
    @Test(expected = InvalidCityException.class)
    public void cityInvalid() {
        // Given:   una ciudad (String)
        String ciudad = "";

        // Given:   una fecha y hora para la prediccion
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.add(Calendar.DAY_OF_MONTH, 3);
        fecha.set(Calendar.HOUR_OF_DAY, 16);

        // When:    la ciudad no existe
        List<CondicionesMeteorologicas> condicionesCiudad = weatherApp.getPrediccion(ciudad);

        // Then:    espero que se lance una excepcion

    }

    /*
    Test sobre la fecha, pero como no se le pasa como parametro porque siempre sera 3, entonces no haria falta
    @Test(expected = InvalidDateException.class)
    public void dateInvalid() {
        // Given:   una ciudad valida (String)
        String ciudad = "";

        // Given:   una fecha y hora invalida para la prediccion (Fecha pasada)
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.add(Calendar.DAY_OF_MONTH, -3);
        fecha.set(Calendar.HOUR_OF_DAY, 16);

        // When:    la ciudad no existe
        CondicionesMeteorologicas condicionesCiudad = weatherApp.getCondicionesActuales(ciudad);

        // Then:    espero que se lance una excepcion
    }*/
}
