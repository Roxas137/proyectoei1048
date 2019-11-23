package e2e;

import es.uji.ei1048.IWeatherAppFacade;
import es.uji.ei1048.WeatherAppFacade;
import org.junit.Before;
import org.junit.After;

public abstract class E2ETestBed {
    protected IWeatherAppFacade weatherApp;

    @Before
    public void setUp() throws Exception {
        weatherApp = new WeatherAppFacade();
    }

    @After
    public void tearDown() throws Exception {
        weatherApp = null;
    }
}
