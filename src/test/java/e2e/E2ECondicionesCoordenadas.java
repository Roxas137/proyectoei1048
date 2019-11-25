package e2e;

import es.uji.ei1048.CondicionesMeteorologicas;
import es.uji.ei1048.Coordenadas;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class E2ECondicionesCoordenadas extends E2ETestBed {
    @Test
    public void coorValid() {
        // Given:   una coordenadas
        double latitud = 5.0;
        double longitud = 0.0;
        Coordenadas coor = new Coordenadas(latitud, longitud);

        // When:    el usuario busca esas coordenadas
        //          la aplicacion hace una peticion al servicio meteorologico
        CondicionesMeteorologicas condicionesCiudad = weatherApp.getCondicionesActuales(coor);

        // Then:    el servicio meteorologico devuelve las condiciones climaticas de esa ciudad
        assertNotNull(condicionesCiudad);
    }

    @Test(expected = InvalidCoordenatesException.class)
    public void coorInvalid() {
        // Given:   una coordenadas
        double latitud = 205.0;
        double longitud = -1800.0;
        Coordenadas coor = new Coordenadas(latitud, longitud);
        // When:    el usuario busca esas coordenadas
        //          la aplicacion hace una peticion al servicio meteorologico
        CondicionesMeteorologicas condicionesCiudad = weatherApp.getCondicionesActuales(coor);

        // Then:    espero que se lance una excepcion

    }
}
