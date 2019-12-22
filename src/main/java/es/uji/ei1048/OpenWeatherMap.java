package es.uji.ei1048;

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
    private Unit unit;

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    // TODO hay que hacer cambios, la apicall no es la misma si es el tiempo actual que si es una prediccion
    public void getWeather(int typePetition, OpenWeatherMapTypeId typeId, String[] place, String days) throws IOException {
        // TODO Sin probar, no se si funcionara
        URL url = getUrl(typePetition);


        String readLine = "";
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();  // No hay situacion de NullPointerException porque se trabaja con constantes ya conocidas y sin margen de error
        conection.setRequestMethod("GET");
        setRequestProperties(conection, typeId, place);
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            // print result
            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
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

    private void setRequestProperties(HttpURLConnection conection, OpenWeatherMapTypeId type, String[] place) {
        setApiKey(conection);
        setPlace(conection, type, place);
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
}
