package es.uji.ei1048;

import java.util.Calendar;

public class CondicionesMeteorologicas {

    private double temperaturaMax;
    private double temperaturaMin;
    private String estadoClima;
    private double velViento;
    private String dirViento;
    private Calendar fechaCondiciones;

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

    private transient Calendar fechaPeticion;


    public CondicionesMeteorologicas() {
        this.temperaturaMax = 0;
        this.estadoClima = "";
        this.velViento = 0;
        this.dirViento = "";
        fechaCondiciones = Calendar.getInstance();
        fechaPeticion = Calendar.getInstance();
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

    public String getDirViento() {
        return dirViento;
    }

    public void setDirViento(String dirViento) {
        this.dirViento = dirViento;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof CondicionesMeteorologicas))
            return false;
        CondicionesMeteorologicas copyOther = (CondicionesMeteorologicas) other;
        return  this.temperaturaMax == copyOther.getTemperaturaMax() &&
                this.temperaturaMin == copyOther.getTemperaturaMin() &&
                this.estadoClima.equals(copyOther.getEstadoClima()) &&
                this.dirViento.equals(copyOther.getDirViento()) &&
                this.velViento == copyOther.getVelViento() &&
                fechaCondiciones.equals(copyOther.getFechaCondiciones());
    }
}
