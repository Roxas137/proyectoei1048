package es.uji.ei1048;

import es.uji.ei1048.utils.Constants;
import es.uji.ei1048.utils.Unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMap implements IWeatherService {
    private String jsonResult;
    private Unit unit;

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    // TODO hay que hacer cambios, la apicall no es la misma si es el tiempo actual que si es una prediccion
    public void getWeather(OpenWeatherMapType type, String lugar, String days) throws IOException {
        // TODO Sin probar, no se si funcionara
        URL urlForGetRequest = new URL(Constants.APICALL);
        String readLine = "";
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty(type.toString(), lugar);
        conection.setRequestProperty("[PH]", days); // Cambiar
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
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

    private String getType(OpenWeatherMapType type) {
        String param = "";
        switch (type) {
            case ID:

                break;
            case NAME:

                break;
            case COORDENATES:
                break;
        }
        return param;
    }
}
