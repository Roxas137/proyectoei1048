package es.uji.ei1048.object;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

/**
 * Clase que representa las condiciones meteorologicas tras una peticion
 * Los valores se inicializan por defecto a null para que al convertirlo a json puedan ser ignorados
 */
public class CondicionesMeteorologicas {

    @SerializedName("temp")
    private Double temperaturaActual;

    @SerializedName("feels_like")
    private Double sensacionTermica;

    @SerializedName("temp_min")

    private Double temperaturaMin;

    @SerializedName("temp_max")
    private Double temperaturaMax;

    @SerializedName("description")
    private String estadoClima;

    @SerializedName("speed")
    private Double velViento;

    @SerializedName("deg")
    private Double dirViento;

    @SerializedName("pressure")
    private Double presion;

    @SerializedName("humidity")
    private Double humedad;

    private Calendar fechaCondiciones;
    private Calendar fechaPeticion;


    public CondicionesMeteorologicas() {
        this.temperaturaActual = null;
        this.sensacionTermica = null;
        this.temperaturaMin = null;
        this.temperaturaMax = null;
        this.estadoClima = null;
        this.velViento = null;
        this.dirViento = null;
        this.presion = null;
        this.humedad = null;
        this.fechaCondiciones = Calendar.getInstance();
        this.fechaPeticion = Calendar.getInstance();
    }

    public Double getTemperaturaActual() {
        return temperaturaActual;
    }

    public void setTemperaturaActual(Double temperaturaActual) {
        this.temperaturaActual = temperaturaActual;
    }

    public Double getSensacionTermica() {
        return sensacionTermica;
    }

    public void setSensacionTermica(double sensacionTermica) {
        this.sensacionTermica = sensacionTermica;
    }

    public Double getPresion() {
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

    public Double getVelViento() {
        return this.velViento;
    }

    public void setVelViento(double velViento) {
        this.velViento = velViento;
    }

    public Double getDirViento() {
        return this.dirViento;
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
