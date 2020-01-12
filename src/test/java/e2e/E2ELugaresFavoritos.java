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

    @Test
    public void modifyLugarFavoritoCoordenadasTest(){
        double longitudAntigua = 42.8503;
        double latitudAntigua = -2.6985;
        double nuevaLongitud = 50;
        double nuevaLatitud = -5.50;
        String nuevaEtiqueta = "clase";

        assertTrue(gestionDB.modificarLugarFavorito(longitudAntigua, latitudAntigua, nuevaLongitud, nuevaLatitud, nuevaEtiqueta));
    }

    @Test
    public void modifyLugarFavoritoCiudadTest(){
        String nombreCiudadAntigua = "salamanca";
        String nombreCiudadNueva = "vila-real";
        String country = "es";
        long antiguoIdCiudad = 0;
        long nuevoIdCiudad = 0;
        String etiquetaNueva = "pisito";

        for (long city : cities.keySet()){
            if (cities.get(city).equals(nombreCiudadNueva+"#"+country)){
                nuevoIdCiudad = city;
                break;
            }
        }

        for (long city : cities.keySet()){
            if (cities.get(city).equals(nombreCiudadAntigua+"#"+country)){
                antiguoIdCiudad = city;
                break;
            }
        }

        assertTrue(gestionDB.modificarLugarFavorito(antiguoIdCiudad, nuevoIdCiudad, etiquetaNueva));
    }

    @Test
    public void deleteLugarFavoritoCoordenadasTest(){
        double longitud = 50;
        double latitud = -5.50;

        assertTrue(gestionDB.eliminaLugarFavorito(longitud, latitud));
    }

    @Test
    public void deleteLugarFavoritoCiudadTest(){
        String nombreCiudad = "vila-real";
        String country = "es";
        long idCiudad = 0;

        for (long city : cities.keySet()){
            if (cities.get(city).equals(nombreCiudad+"#"+country)){
                idCiudad = city;
                break;
            }
        }

        assertTrue(gestionDB.eliminaLugarFavorito(idCiudad));
    }
}
