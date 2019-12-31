package e2e;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.service.OpenWeatherMap;
import es.uji.ei1048.facade.WeatherAppFacade;
import org.junit.Before;
import org.junit.After;

public abstract class E2ETestBed {
    protected IWeatherAppFacade weatherApp;

    @Before
    public void setUp() throws Exception {
        weatherApp = new WeatherAppFacade(new OpenWeatherMap());
    }

    @After
    public void tearDown() throws Exception {
        weatherApp = null;
    }
}
