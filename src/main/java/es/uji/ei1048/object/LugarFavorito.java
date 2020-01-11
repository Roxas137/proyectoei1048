package es.uji.ei1048.object;

public class LugarFavorito {
    //Para diferenciar de si el lugar favorito es coordenada o ciudad --> -500
    private long idCiudad; //-500 si NO es una ciudad
    private double longitud; //-500 si NO es una coordenada
    private double latitud; //-500 si NO es una coordenada
    private String etiqueta;

    public void setIdCiudad(long idCiudad){
        this.idCiudad = idCiudad;
    }

    public void setLongitud(double longitud){
        this.longitud = longitud;
    }

    public void setLatitud(double latitud){
        this.latitud = latitud;
    }

    public void setEtiqueta(String etiqueta){
        this.etiqueta = etiqueta;
    }
}
