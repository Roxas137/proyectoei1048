package es.uji.ei1048;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class WeatherApp {

    public WeatherApp() {
    }

    public CondicionesMeteorologicas getCurrentWeather(IWeatherService service) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getCurrentWeather();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            CondicionesMeteorologicas condiciones = gson.fromJson(jsonResult, CondicionesMeteorologicas.class);
            setPetitionData(condiciones);
            // TODO actualizar la BBDD
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CondicionesMeteorologicas> getPredictionWeather(IWeatherService service) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getPredictionWeather();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<CondicionesMeteorologicas> condiciones = gson.fromJson(jsonResult, new TypeToken<List<CondicionesMeteorologicas>>(){}.getType());
            setPetitionDate(condiciones);
            // TODO: 27/12/2019 Convertir a una unica prediccion por dia
            // TODO actualizar la BBDD

            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setPetitionDate(List<CondicionesMeteorologicas> condiciones) {
        for (CondicionesMeteorologicas condicion : condiciones) {
            setPetitionData(condicion);
        }
    }

    private void setPetitionData(CondicionesMeteorologicas condicion) {
        condicion.setFechaPeticion(Calendar.getInstance());
    }
}