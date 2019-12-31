package es.uji.ei1048.object;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class CondicionesMeteorologicas {

    @SerializedName("temp")
    private double temperaturaActual;

    @SerializedName("feels_like")
    private double sensacionTermica;

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    @SerializedName("temp_min")

    private double temperaturaMin;

    @SerializedName("temp_max")
    private double temperaturaMax;

    @SerializedName("description")
    private String estadoClima;

    @SerializedName("speed")
    private double velViento;

    @SerializedName("deg")
    private double dirViento;

    @SerializedName("pressure")
    private double presion;

    @SerializedName("humidity")
    private double humedad;

    private Calendar fechaCondiciones;
    private transient Calendar fechaPeticion;


    public CondicionesMeteorologicas() {
        this.temperaturaMax = 0;
        this.estadoClima = "";
        this.velViento = 0;
        this.dirViento = 0;
        fechaCondiciones = Calendar.getInstance();
        fechaPeticion = Calendar.getInstance();
    }

    public double getTemperaturaActual() {
        return temperaturaActual;
    }

    public void setTemperaturaActual(double temperaturaActual) {
        this.temperaturaActual = temperaturaActual;
    }

    public double getSensacionTermica() {
        return sensacionTermica;
    }

    public void setSensacionTermica(double sensacionTermica) {
        this.sensacionTermica = sensacionTermica;
    }

    public Calendar getFechaCondiciones() {
        return fechaCondiciones;
    }

    public void setFechaCondiciones(Calendar fechaCondiciones) {
        this.fechaCondiciones = fechaCondiciones;
    }

    public Calendar getFechaPeticion() {
        return fechaPeticion;
    }

    public void setFechaPeticion(Calendar fechaPeticion) {
        this.fechaPeticion = fechaPeticion;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public double getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(double temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public String getEstadoClima() {
        return estadoClima;
    }

    public void setEstadoClima(String estadoClima) {
        this.estadoClima = estadoClima;
    }

    public double getVelViento() {
        return velViento;
    }

    public void setVelViento(double velViento) {
        this.velViento = velViento;
    }

    public double getDirViento() {
        return dirViento;
    }

    public void setDirViento(double dirViento) {
        this.dirViento = dirViento;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (this == other)
            return true;
        if (!(other instanceof CondicionesMeteorologicas))
            return false;
        CondicionesMeteorologicas copyOther = (CondicionesMeteorologicas) other;
        return  this.temperaturaMax == copyOther.getTemperaturaMax() &&
                this.temperaturaMin == copyOther.getTemperaturaMin() &&
                this.estadoClima.equals(copyOther.getEstadoClima()) &&
                this.dirViento == copyOther.getDirViento() &&
                this.velViento == copyOther.getVelViento() &&
                fechaCondiciones.equals(copyOther.getFechaCondiciones());
    }
}
