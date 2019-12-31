package es.uji.ei1048;

import com.google.gson.*;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.service.IWeatherService;

import java.io.IOException;
import java.util.*;

public class WeatherApp {

    public WeatherApp() {
    }

    public CondicionesMeteorologicas getCurrentWeather(IWeatherService service) {
        // TODO mirar en la BBDD

        try {
            String jsonResult = service.getCurrentWeather();
            CondicionesMeteorologicas condiciones = fromJsonToObject(jsonResult);
            updateDatabase(condiciones);
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
            List<CondicionesMeteorologicas> condiciones = fromJsonToList(jsonResult);
            setPetitionDate(condiciones);
            // TODO: 27/12/2019 Convertir a una unica prediccion por dia
            for (CondicionesMeteorologicas condicion : condiciones) {
                updateDatabase(condicion);
            }
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Sorry
     *
     * @param jsonResult json with the data
     * @return list with all the data
     */
    private List<CondicionesMeteorologicas> fromJsonToList(String jsonResult) {
        JsonArray array = JsonParser.parseString(jsonResult).getAsJsonObject().get("list").getAsJsonArray();

        List<CondicionesMeteorologicas> prediccion = new ArrayList<>();

        // Datos de las condiciones metereologicas
        List<Double> tempMin = new ArrayList<>();
        List<Double> tempMax = new ArrayList<>();
        List<Double> tempMean = new ArrayList<>();
        List<Double> windSpeed = new ArrayList<>();
        List<Double> windDeg = new ArrayList<>();
        List<Double> pressure = new ArrayList<>();
        List<Double> humidity = new ArrayList<>();


        for (JsonElement data : array) {
            Calendar date = getDate(data);
            JsonElement prediction = data.getAsJsonObject().get("main");
            JsonElement wind = data.getAsJsonObject().get("wind");

            if (date.get(Calendar.HOUR_OF_DAY) == 0 || Calendar.HOUR_OF_DAY == 1 || Calendar.HOUR_OF_DAY == 2) {
                // Si es un dia nuevo
                // Calcular maximos, minimos y medias
                CondicionesMeteorologicas condiciones = new CondicionesMeteorologicas();
                condiciones.setTemperaturaMin(Collections.min(tempMin));
                condiciones.setTemperaturaMax(Collections.max(tempMax));
                condiciones.setTemperaturaActual(getMean(tempMean));
                condiciones.setVelViento(getMean(windSpeed));
                condiciones.setDirViento(getMean(windDeg));
                condiciones.setPresion(getMean(pressure));
                condiciones.setHumedad(getMean(humidity));

                // Add the prediction to the list
                prediccion.add(condiciones);

                // Clear all the data
                tempMin.clear();
                tempMax.clear();
                tempMean.clear();
                windSpeed.clear();
                windDeg.clear();
                pressure.clear();
                humidity.clear();
            }
            // Add all the data to its lists
            tempMin.add(prediction.getAsJsonObject().get("temp_min").getAsDouble());
            tempMax.add(prediction.getAsJsonObject().get("temp_max").getAsDouble());
            tempMean.add(prediction.getAsJsonObject().get("temp").getAsDouble());
            pressure.add(prediction.getAsJsonObject().get("pressure").getAsDouble());
            humidity.add(prediction.getAsJsonObject().get("humidity").getAsDouble());
            windSpeed.add(wind.getAsJsonObject().get("speed").getAsDouble());
            windDeg.add(wind.getAsJsonObject().get("deg").getAsDouble());
        }
        // Save last day
        CondicionesMeteorologicas condiciones = new CondicionesMeteorologicas();
        condiciones.setTemperaturaMin(Collections.min(tempMin));
        condiciones.setTemperaturaMax(Collections.max(tempMax));
        condiciones.setTemperaturaActual(getMean(tempMean));
        condiciones.setVelViento(getMean(windSpeed));
        condiciones.setDirViento(getMean(windDeg));
        condiciones.setPresion(getMean(pressure));
        condiciones.setHumedad(getMean(humidity));

        // Add the prediction to the list
        prediccion.add(condiciones);

        return prediccion;
    }

    private double getMean(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    private Calendar getDate(JsonElement data) {
        Calendar date = GregorianCalendar.getInstance();
        String dateString = data.getAsJsonObject().get("dt_txt").getAsString();

        String dateSplitted = dateString.split(" ")[0];  // YY MM DD
        String timeSplitted = dateString.split(" ")[1];  // HH MM SS

        String year = dateSplitted.split("-")[0];
        String month = dateSplitted.split("-")[1];
        String day = dateSplitted.split("-")[2];

        String hour = timeSplitted.split("-")[0];
        String minute = timeSplitted.split("-")[1];
        String second = timeSplitted.split("-")[2];

        date.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
                Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));

        return date;
    }

    private CondicionesMeteorologicas fromJsonToObject(String jsonResult) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement data = JsonParser.parseString(jsonResult).getAsJsonObject().get("main");

        CondicionesMeteorologicas condiciones = gson.fromJson(data, CondicionesMeteorologicas.class);
        setPetitionDate(condiciones);
        return condiciones;
    }

    private void updateDatabase(CondicionesMeteorologicas condiciones) {
        // TODO: 30/12/2019 Adrian, esto es tarea tuya
    }

    private void setPetitionDate(List<CondicionesMeteorologicas> condiciones) {
        for (CondicionesMeteorologicas condicion : condiciones) {
            setPetitionDate(condicion);
        }
    }

    private void setPetitionDate(CondicionesMeteorologicas condicion) {
        condicion.setFechaPeticion(Calendar.getInstance());
    }
}