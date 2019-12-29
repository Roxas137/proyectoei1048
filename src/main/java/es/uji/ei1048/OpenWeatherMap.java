package es.uji.ei1048;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.utils.Unit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherMap implements IWeatherService {

    @Override
    public String getCurrentWeather() throws IOException {
        // Parametros por defecto para que no de errores
        // TODO Coger los parametros por JavaFX
        OpenWeatherMapTypeId typeId = OpenWeatherMapTypeId.ID;
        String[] place = new String[]{Constants.ID_VALLADOLID};
        Unit unit = Unit.CELSIUS;

        return getCurrentWeather(typeId, place, unit);
    }

    private String getCurrentWeather(OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws IOException {
        URL url = getUrl(Constants.PETITION_CURRENT, typeId, place, unit);
        String readLine = "";

        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");

        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();

            return response.toString();
        } else {
            System.out.println("GET NOT WORKED");
            return null;
        }
    }

    @Override
    public String getPredictionWeather() throws IOException {
        // Parametros por defecto para que no de errores
        // TODO Coger los parametros por JavaFX
        OpenWeatherMapTypeId typeId = OpenWeatherMapTypeId.ID;
        String[] place = new String[]{"512052"};
        Unit unit = Unit.CELSIUS;

        return getPredictionWeather(typeId, place, unit);
    }

    private String getPredictionWeather(OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws IOException {
        URL url = getUrl(Constants.PETITION_PREDICTION, typeId, place, unit);
        String readLine = "";

        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");

        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();

            return response.toString();
        } else {
            System.out.println("GET NOT WORKED");
            return null;
        }
    }

    private URL getUrl(int typePetition, OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws MalformedURLException {
        StringBuilder s = new StringBuilder();
        s.append(Constants.APICALL);

        if (typePetition == Constants.PETITION_PREDICTION) {
            s.append(Constants.PREDICTION);
        } else {
            s.append(Constants.CURRENT);
        }

        s.append("?");
        s.append(setApiKey());
        s.append("&");
        s.append(setPlace(typeId, place));
        s.append("&");
        s.append(setUnit(unit));
        return new URL(s.toString());

    }

    private String setApiKey() {
        return "APPID=" + Constants.APIKEY;
    }

    private String setPlace(OpenWeatherMapTypeId type, String[] place) {
        StringBuilder s = new StringBuilder();
        switch (type) {
            case COORDENATES:
                s.append(type.getName()[0])
                        .append("=")
                        .append(place[0])
                        .append("&")
                        .append(type.getName()[1])
                        .append("=")
                        .append(place[1]);
                break;
            case NAME:
                s.append(type.getName()[0])
                        .append("=")
                        .append(place[0]);
                break;
            case ID:
                s.append(type.getName()[0])
                        .append("=")
                        .append(place[0]);
                break;
        }
        return s.toString();
    }

    private String setUnit(Unit unit) {
        // TODO Con kelvin no se si fallaria o no
        return "units=" + unit.getName();
    }
}
