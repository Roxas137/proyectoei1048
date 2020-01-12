package e2e;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.gestionDB.GestionDB;
import es.uji.ei1048.jsonTreatment.CityListReader;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMap;
import es.uji.ei1048.facade.WeatherAppFacade;
import org.junit.Before;
import org.junit.After;

import java.util.Map;

public abstract class E2ETestBed {
    protected IWeatherAppFacade weatherApp;
    protected GestionDB gestionDB;
    protected Map<Long, String> cities;

    @Before
    public void setUp() throws Exception {
        weatherApp = new WeatherAppFacade(new OpenWeatherMap());
        gestionDB = new GestionDB();
        cities = CityListReader.initialize();

    }

    @After
    public void tearDown() throws Exception {
        weatherApp = null;
    }
}
