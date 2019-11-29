package e2e;

import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.CondicionesMeteorologicas;

import org.junit.Test;

import static org.junit.Assert.*;

public class E2ECondicionesCiudad extends E2ETestBed {

    @Test
    public void cityValid() {
        // Given:   una ciudad (String)
        String ciudad = Constants.CASTELLON;

        // When:    el usuario busca esa ciudad
        //          la aplicacion hace una peticion al servicio meteorologico
        CondicionesMeteorologicas condicionesCiudad = weatherApp.getCondicionesActuales(ciudad);

        // Then:    el servicio meteorologico devuelve las condiciones climaticas de esa ciudad
        assertNotNull(condicionesCiudad);
    }

    @Test(expected = InvalidCityException.class)
    public void cityInvalid() {
        // Given:   una ciudad (String)
        String ciudad = "";

        // When:    la ciudad no existe
        CondicionesMeteorologicas condicionesCiudad = weatherApp.getCondicionesActuales(ciudad);

        // Then:    espero que se lance una excepcion

    }
}
