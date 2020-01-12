package e2e;

import org.junit.Before;
import org.junit.Test;
import es.uji.ei1048.gestionDB.GestionDB;
import es.uji.ei1048.jsonTreatment.CityListReader;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class E2ELugaresFavoritos extends E2ETestBed {

    @Test
    public void saveLugarFavoritoCoordenadasTest(){
        //Given unas coordenadas validas
        double longitud = 42.8503;
        double latitud = -2.6985;
        String etiqueta = "casa";

        //When el usuario quiere guardarlas como lugar favorito
        assertTrue(gestionDB.registrarLugarFavorito(longitud, latitud, etiqueta));
    }

    @Test
    public void saveLugarFavoritoCiudadTest(){
        //Given una ciudad valida
        String nombreCiudad = "salamanca";
        String country = "es";
        String etiqueta = "casa";

        for (long city : cities.keySet()){
            if (cities.get(city).equals(nombreCiudad+"#"+country)){
                assertTrue(gestionDB.registrarLugarFavorito(city, etiqueta));
                break;
            }
        }
    }
}
