package es.uji.ei1048;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.utils.Unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherMap implements IWeatherService {
    private String jsonResult;

    public String getWeather(int typePetition, OpenWeatherMapTypeId typeId, String[] place, Unit unit) throws IOException {
        URL url = getUrl(typePetition);
        Gson g = new Gson();
        JsonObject j = new JsonObject();
        String readLine = "";
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        setRequestProperties(conection, typeId, place, unit);
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

    private URL getUrl(int typePetition) {
        try {
            if (typePetition == Constants.PETITION_PREDICTION) {
                return new URL(Constants.APICALL + Constants.PREDICTION);
            }
            return new URL(Constants.APICALL + Constants.CURRENT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setRequestProperties(HttpURLConnection conection, OpenWeatherMapTypeId type, String[] place, Unit unit) {
        setApiKey(conection);
        setPlace(conection, type, place);
        setUnit(conection, unit);
    }

    private void setApiKey(HttpURLConnection conection) {
        conection.setRequestProperty("APPID", Constants.APIKEY);
    }

    private void setPlace(HttpURLConnection conection, OpenWeatherMapTypeId type, String[] place) {
        switch (type) {
            case COORDENATES:
                conection.setRequestProperty(type.getName()[0], place[0]);
                conection.setRequestProperty(type.getName()[1], place[1]);
                break;
            case NAME:
                conection.setRequestProperty(type.getName()[0], place[0]);
                break;
            case ID:
                conection.setRequestProperty(type.getName()[0], place[0]);
                break;
        }
    }

    private void setUnit(HttpURLConnection conection, Unit unit) {
        // TODO Con kelvin no se si fallaria o no
        conection.setRequestProperty("units", unit.getName());
    }
}