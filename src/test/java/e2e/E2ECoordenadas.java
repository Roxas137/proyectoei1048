package e2e;

import org.junit.Test;
import static org.junit.Assert.*;

public class E2ECoordenadas extends E2ETestBed {

    @Test
    public void saveCoordenadas(){
        double longitud = 42.8503;
        double latitud = -2.6985;
        String etiqueta = "casa";

        assertTrue(gestionDB.registrarCoordenadas(longitud, latitud, etiqueta));
    }

    @Test
    public void modifyCoordenadas(){
        double antiguaLongitud = 42.8503;
        double antiguaLatitud = -2.6985;
        double nuevaLongitud = 50;
        double nuevaLatitud = -5.50;
        String nuevaEtiqueta = "pisito";

        assertTrue(gestionDB.modificarCoordenadas(antiguaLongitud, antiguaLatitud, nuevaLongitud, nuevaLatitud, nuevaEtiqueta));
    }

    @Test
    public void deleteCoordenadas(){
        double longitud = 50;
        double latitud = -5.50;

        assertTrue(gestionDB.eliminarCoordenadas(longitud, latitud));
    }

}
