package e2e;

import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import org.junit.Test;

import static org.junit.Assert.*;

public class E2ECondicionesCoordenadas extends E2ETestBed {

    /**
     * Comprobar que funciona cuando las coordenadas no son validas
     */
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


    /**
     * Comprobar que salta la excepcion correspondiente cuando las coordenadas no son validas
     */
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


    @Test
    public void saveCondicionesCoordenadasTest(){
        //Given unas condiciones meteorologicas y unas coordenadas validas
        Coordenadas coordenadas = new Coordenadas();
        coordenadas.setLatitud(90);
        coordenadas.setLongitud(150.5);
        CondicionesMeteorologicas cm = weatherApp.getCondicionesActuales(coordenadas);


        assertTrue(gestionDB.registrarCondicionesMeteorologicas(cm, coordenadas, 1));
    }
}
