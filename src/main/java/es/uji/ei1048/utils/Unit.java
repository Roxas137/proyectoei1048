package es.uji.ei1048.utils;

public enum Unit {
    KELVIN, CELSIUS, FAHRENHEIT;

    public String getName() {
        switch (this) {
            case CELSIUS:
                return "metric";
            case FAHRENHEIT:
                return "imperial";
            default:
                return "";
        }
    }
}
