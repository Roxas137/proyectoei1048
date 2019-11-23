package es.uji.ei1048;

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
}
