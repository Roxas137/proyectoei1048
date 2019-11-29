package es.uji.ei1048;

public class CondicionesMeteorologicas {

    private double temperatura;
    private String estadoClima;
    private double velViento;
    private String dirViento;

    public CondicionesMeteorologicas() {
        this.temperatura = 0;
        this.estadoClima = "";
        this.velViento = 0;
        this.dirViento = "";
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
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
        return  this.temperatura == copyOther.getTemperatura() &&
                this.estadoClima.equals(copyOther.getEstadoClima()) &&
                this.dirViento.equals(copyOther.getDirViento()) &&
                this.velViento == copyOther.getVelViento();
    }
}
