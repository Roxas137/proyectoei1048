package e2e;

import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.object.CondicionesMeteorologicas;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class E2ECondicionesCiudad extends E2ETestBed {

    @Test
    public void cityValid() {
        // Given:   una ciudad (String)
        String ciudad = Constants.NOMBRE_CIUDAD;

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

    @Test
    public void saveCondicionesCoordenadasTest(){
        //Given un id de una Ciudad y unas condiciones meteorologicas
        CondicionesMeteorologicas cm = weatherApp.getCondicionesActuales(Constants.NOMBRE_CIUDAD);
        assertTrue(gestionDB.registrarCondicionesMeteorologicas(cm, Constants.ID_CASTELLON, 1));
    }
}
