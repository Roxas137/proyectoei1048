package es.uji.ei1048;

public class Main {
    public static void main(String[] args) {
        IWeatherService service = new OpenWeatherMap();
        IWeatherAppFacade weatherAppFacade = new WeatherAppFacade(service);

        weatherAppFacade.getCondicionesActuales();
    }
}
