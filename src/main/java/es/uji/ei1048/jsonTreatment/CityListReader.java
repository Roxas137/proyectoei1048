package es.uji.ei1048.jsonTreatment;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CityListReader {

    public static Map<Long, String> initialize(){
        String ruta = "src/main/data/city.list.json";
        Map<Long, String> cities = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(ruta));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator iterator1 = jsonArray.iterator();
            List<String> cityCountry = new ArrayList<>();

            while (iterator1.hasNext()) {
                JSONObject jsonObject = (JSONObject) iterator1.next();
                String cityNames = ((String) jsonObject.get("name")).toLowerCase();
                Long cityId = (Long) jsonObject.get("id");
                String country =  ((String) jsonObject.get("country")).toLowerCase();


                cities.putIfAbsent(cityId, cityNames+"#"+country);

                cityCountry.clear();
            }
        } catch (IOException e) {
            System.out.println("A fatal error ocurred while reading json file.");
        } catch (ParseException e) {
            System.out.println("A fatal error ocurred while parsing json file.");
        }
        return cities;
    }
}


