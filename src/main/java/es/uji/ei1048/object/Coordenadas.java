package es.uji.ei1048.object;

import static es.uji.ei1048.utils.Constants.*;

public class Coordenadas {
    private double latitud;
    private double longitud;

    public Coordenadas() {
        latitud = 0.0;
        longitud = 0.0;
    }

    public Coordenadas(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isValid() {
        return !(latitud < LATITUD_MINIMA || latitud > LATITUD_MAXIMA ||
                longitud < LONGITUD_MINIMA || longitud > LONGITUD_MAXIMA);
    }
}
