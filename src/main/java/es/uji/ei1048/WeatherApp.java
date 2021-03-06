package es.uji.ei1048;

import com.google.gson.*;
import es.uji.ei1048.gestionDB.GestionDB;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.object.LugarFavorito;
import es.uji.ei1048.service.IWeatherService;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMapTypeId;
import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.utils.Unit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.*;

public class WeatherApp {

    GestionDB gestionDB;

    public WeatherApp() {
        try {
            gestionDB = new GestionDB();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public CondicionesMeteorologicas getCurrentWeatherByCity(IWeatherService service, String ciudad) {
        try {
            Calendar fechaCondicion = Calendar.getInstance();
            Calendar fechaPeticion = Calendar.getInstance();
            long idCiudad = service.getIdByCity(ciudad);
            String[] place = new String[1];
            place[0] = String.valueOf(idCiudad);

            CondicionesMeteorologicas condicionesGuardadas;

            condicionesGuardadas = gestionDB.getCondicionesMeteorologicas(idCiudad);

            if (comparaFechaPeticiones(fechaPeticion, condicionesGuardadas)) {
                return condicionesGuardadas;
            }

            String jsonResult = service.getCurrentWeather(OpenWeatherMapTypeId.ID, place, Unit.CELSIUS);
            CondicionesMeteorologicas condiciones = fromJsonToObject(jsonResult);
            if (condicionesGuardadas == null) {
                insertDatabase(condiciones, idCiudad, Constants.PETITION_CURRENT, null);
            } else {
                updateDatabase(condiciones, idCiudad, Constants.PETITION_CURRENT, null);
            }
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CondicionesMeteorologicas getCurrentWeatherByCoordenates(IWeatherService service, Coordenadas coor) {
        try {
            Calendar fechaCondicion = Calendar.getInstance();
            Calendar fechaPeticion = Calendar.getInstance();
            String[] place = new String[2];
            place[0] = String.valueOf(coor.getLatitud());
            place[1] = String.valueOf(coor.getLongitud());

            CondicionesMeteorologicas condicionesGuardadas;

            condicionesGuardadas = gestionDB.getCondicionesMeteorologicas(coor);

            if (comparaFechaPeticiones(fechaPeticion, condicionesGuardadas)) {
                return condicionesGuardadas;
            }

            String jsonResult = service.getCurrentWeather(OpenWeatherMapTypeId.COORDENATES, place, Unit.CELSIUS);
            CondicionesMeteorologicas condiciones = fromJsonToObject(jsonResult);
            if (condicionesGuardadas == null) {
                insertDatabase(condiciones, Constants.NULL_CITY, Constants.PETITION_CURRENT, coor);
            } else {
                updateDatabase(condiciones, Constants.NULL_CITY, Constants.PETITION_CURRENT, coor);
            }
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CondicionesMeteorologicas> getPredictionWeatherByCity(IWeatherService service, String ciudad) {
        try {
            Calendar fechaPeticion = Calendar.getInstance();
            long idCiudad = service.getIdByCity(ciudad);
            String[] place = new String[1];
            place[0] = String.valueOf(idCiudad);

            List<CondicionesMeteorologicas> prediccionGuardada;
            prediccionGuardada = gestionDB.getPrediccion(idCiudad);

            if (prediccionGuardada.size()>0 && comparaFechaPeticiones(fechaPeticion, prediccionGuardada.get(0))) {
                return prediccionGuardada;
            }

            String jsonResult = service.getPredictionWeather(OpenWeatherMapTypeId.ID, place, Unit.CELSIUS);
            List<CondicionesMeteorologicas> condiciones = fromJsonToList(jsonResult);
            setPetitionDate(condiciones);

            for (CondicionesMeteorologicas condicion : condiciones) {
                insertDatabase(condicion, idCiudad, Constants.PETITION_PREDICTION, null);
            }
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CondicionesMeteorologicas> getPredictionWeatherByCoordenates(IWeatherService service, Coordenadas coor) {
        try {
            Calendar fechaPeticion = Calendar.getInstance();

            List<CondicionesMeteorologicas> prediccionGuardada;
            prediccionGuardada = gestionDB.getPrediccion(coor);
            String[] place = new String[2];
            place[0] = String.valueOf(coor.getLatitud());
            place[1] = String.valueOf(coor.getLongitud());


            if (!prediccionGuardada.isEmpty() && comparaFechaPeticiones(fechaPeticion, prediccionGuardada.get(0))) {
                return prediccionGuardada;
            }
            String jsonResult = service.getPredictionWeather(OpenWeatherMapTypeId.COORDENATES, place, Unit.CELSIUS);
            List<CondicionesMeteorologicas> condiciones = fromJsonToList(jsonResult);
            setPetitionDate(condiciones);

            for (CondicionesMeteorologicas condicion : condiciones) {
                insertDatabase(condicion, Constants.NULL_CITY, Constants.PETITION_PREDICTION, coor);
            }
            return condiciones;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LugarFavorito> getLugaresFavoritos(){
        return gestionDB.getLugaresFavoritos();
    }

    public boolean registraLugarFavorito(LugarFavorito lugarFavorito){
        if (lugarFavorito.getIdCiudad() == -500){
            return gestionDB.registrarLugarFavorito(lugarFavorito.getLongitud(), lugarFavorito.getLatitud(), lugarFavorito.getEtiqueta());
        }else{
            return gestionDB.registrarLugarFavorito(lugarFavorito.getIdCiudad(), lugarFavorito.getEtiqueta());
        }
    }

    public boolean eliminarLugarFavorito(LugarFavorito lugarFavorito){
        if(lugarFavorito.getIdCiudad() == -500){
            return gestionDB.eliminaLugarFavorito(lugarFavorito.getLongitud(), lugarFavorito.getLatitud(), lugarFavorito.getEtiqueta());
        }else{
            return gestionDB.eliminaLugarFavorito(lugarFavorito.getIdCiudad(), lugarFavorito.getEtiqueta());
        }
    }


    /**
     * Sorry
     *
     * @param jsonResult json with the data
     * @return list with all the data
     */
    private List<CondicionesMeteorologicas> fromJsonToList(String jsonResult) {
        JsonArray array = JsonParser.parseString(jsonResult).getAsJsonObject().get("list").getAsJsonArray();

        List<CondicionesMeteorologicas> prediction = new ArrayList<>();

        // Datos de las condiciones metereologicas
        List<Double> tempMin = new ArrayList<>();
        List<Double> tempMax = new ArrayList<>();
        List<Double> tempMean = new ArrayList<>();
        List<Double> windSpeed = new ArrayList<>();
        List<Double> windDeg = new ArrayList<>();
        List<Double> pressure = new ArrayList<>();
        List<Double> humidity = new ArrayList<>();
        Map<String, Integer> weatherState = new HashMap<>();
        boolean firstData = true;
        Calendar lastDate = null;

        for (JsonElement data : array) {
            Calendar date = getDate(data);
            lastDate = date;
            JsonElement main = data.getAsJsonObject().get("main");
            JsonElement wind = data.getAsJsonObject().get("wind");
            JsonElement weather = data.getAsJsonObject().get("weather");

            if (date.get(Calendar.HOUR_OF_DAY) == 0 || Calendar.HOUR_OF_DAY == 1 || Calendar.HOUR_OF_DAY == 2) {
                // Si es un dia nuevo
                // Calcular maximos, minimos y medias

                // Add the prediction of the last day to the list, if it's the first data, don't add
                if (!firstData) {
                    CondicionesMeteorologicas condiciones = new CondicionesMeteorologicas();
                    condiciones.setTemperaturaMin(new BigDecimal(Collections.min(tempMin)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setTemperaturaMax(new BigDecimal(Collections.max(tempMax)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setTemperaturaActual(new BigDecimal(getMean(tempMean)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setVelViento(new BigDecimal(getMean(windSpeed)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setDirViento(new BigDecimal(getMean(windDeg)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setPresion(new BigDecimal(getMean(pressure)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setHumedad(new BigDecimal(getMean(humidity)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    condiciones.setEstadoClima(getMostCommonWeather(weatherState));
                    condiciones.setFechaPeticion(Calendar.getInstance());
                    condiciones.setFechaCondiciones(date);
                    prediction.add(condiciones);
                }

                // Clear all the data
                tempMin.clear();
                tempMax.clear();
                tempMean.clear();
                windSpeed.clear();
                windDeg.clear();
                pressure.clear();
                humidity.clear();
                weatherState.clear();
            }
            firstData = false;
            // Add all the data to its lists
            tempMin.add(main.getAsJsonObject().get("temp_min").getAsDouble());
            tempMax.add(main.getAsJsonObject().get("temp_max").getAsDouble());
            tempMean.add(main.getAsJsonObject().get("temp").getAsDouble());
            pressure.add(main.getAsJsonObject().get("pressure").getAsDouble());
            humidity.add(main.getAsJsonObject().get("humidity").getAsDouble());
            windSpeed.add(wind.getAsJsonObject().get("speed").getAsDouble());
            windDeg.add(wind.getAsJsonObject().get("deg").getAsDouble());
            weatherState.put(weather.getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString(), weatherState.getOrDefault(weather.getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString(), 0) + 1);
        }

        // Save last day
        CondicionesMeteorologicas condiciones = new CondicionesMeteorologicas();

        condiciones.setTemperaturaMin(new BigDecimal(Collections.min(tempMin)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setTemperaturaMax(new BigDecimal(Collections.max(tempMax)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setTemperaturaActual(new BigDecimal(getMean(tempMean)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setVelViento(new BigDecimal(getMean(windSpeed)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setDirViento(new BigDecimal(getMean(windDeg)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setPresion(new BigDecimal(getMean(pressure)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setHumedad(new BigDecimal(getMean(humidity)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        condiciones.setEstadoClima(getMostCommonWeather(weatherState));
        condiciones.setFechaPeticion(Calendar.getInstance());
        condiciones.setFechaCondiciones(lastDate);

        // Add the prediction to the list
        prediction.add(condiciones);

        return prediction;
    }

    private String getMostCommonWeather(Map<String, Integer> weatherState) {
        int max = 0;
        String mostCommonWeather = "";

        for (String weatherType : weatherState.keySet()) {
            if (max < weatherState.get(weatherType)) {
                max = weatherState.get(weatherType);
                mostCommonWeather = weatherType;
            }
        }

        return mostCommonWeather;
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

        String hour = timeSplitted.split(":")[0];
        String minute = timeSplitted.split(":")[1];
        String second = timeSplitted.split(":")[2];

        date.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day),
                Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));

        return date;
    }

    private CondicionesMeteorologicas fromJsonToObject(String jsonResult) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement data = JsonParser.parseString(jsonResult).getAsJsonObject().get("main");

        CondicionesMeteorologicas condiciones = gson.fromJson(data, CondicionesMeteorologicas.class);
        setCurrentWind(condiciones, jsonResult);
        setCurrentWeatherState(condiciones, jsonResult);
        setPetitionDate(condiciones);
        return condiciones;
    }

    private void setCurrentWeatherState(CondicionesMeteorologicas condiciones, String jsonResult) {
        JsonElement data = JsonParser.parseString(jsonResult).getAsJsonObject().get("weather").getAsJsonArray().get(0);
        String currentWeatherState = data.getAsJsonObject().get("main").getAsString();
        condiciones.setEstadoClima(currentWeatherState);
    }

    private void setCurrentWind(CondicionesMeteorologicas condiciones, String jsonResult) {
        JsonElement wind = JsonParser.parseString(jsonResult).getAsJsonObject().get("wind");
        try {
            condiciones.setDirViento(wind.getAsJsonObject().get("deg").getAsDouble());
        } catch (NullPointerException e) {
            System.out.println("Error al obtener la direccion del viento del servidor");
            condiciones.setDirViento(0.0);
        }
        condiciones.setVelViento(wind.getAsJsonObject().get("speed").getAsDouble());
    }

    private void updateDatabase(CondicionesMeteorologicas condiciones, Long idCiudad, int tipoPeticion, Coordenadas coordenadas) {
        if (idCiudad.equals(Constants.NULL_CITY)) {
            gestionDB.modifyCondicionesMeteorologicas(condiciones, coordenadas);
        } else {
            gestionDB.modifyCondicionesMeteorologicas(condiciones, idCiudad);
        }
    }

    private void insertDatabase(CondicionesMeteorologicas condiciones, Long idCiudad, int tipoPeticion, Coordenadas coordenadas) {
        if (idCiudad.equals(Constants.NULL_CITY)) {
            gestionDB.registrarCondicionesMeteorologicas(condiciones, coordenadas, tipoPeticion);
        } else {
            gestionDB.registrarCondicionesMeteorologicas(condiciones, idCiudad, tipoPeticion);
        }
    }

    private void setPetitionDate(List<CondicionesMeteorologicas> condiciones) {
        for (CondicionesMeteorologicas condicion : condiciones) {
            setPetitionDate(condicion);
        }
    }

    private void setPetitionDate(CondicionesMeteorologicas condicion) {
        condicion.setFechaPeticion(Calendar.getInstance());
    }

    /**
     * Compara la fecha de la peticion que se quiere realizar con la fecha de las condiciones guardadas.
     *
     * @param fechaPeticion        fecha de la peticion que se quiere realizar.
     * @param condicionesGuardadas condiciones obtenidas de la base de datos.
     * @return Si la diferencia entre ambas fechas es de menos de media hora devuelve true en caso contrario, false.
     */
    private boolean comparaFechaPeticiones(Calendar fechaPeticion, CondicionesMeteorologicas condicionesGuardadas) {
        if (condicionesGuardadas != null) {
            Calendar fechaPeticionGuardada = condicionesGuardadas.getFechaPeticion();
            //Si la diferencia es de menos de media hora entonces se escogen las guardadas.
            return fechaPeticion.getTimeInMillis() - fechaPeticionGuardada.getTimeInMillis() <= 1800000;
        }
        return false;
    }

}