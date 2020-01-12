package es.uji.ei1048.vista;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.facade.WeatherAppFacade;
import es.uji.ei1048.service.IWeatherService;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMap;
import es.uji.ei1048.vista.controlador.LandingPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private IWeatherService service = new OpenWeatherMap();
    private IWeatherAppFacade weatherAppFacade = new WeatherAppFacade(service);
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        // Creamos el loader de la pagina inicial.
        FXMLLoader landingPage = new FXMLLoader();
        landingPage.setLocation(MainApp.class.getResource("es.uji.ei1048/vista/escenas/landingPage.fxml"));
        System.out.println(landingPage.getLocation());

        this.primaryStage.setTitle("My Weather App");

        // Cargamos la escena.
        Scene landingScene = new Scene(landingPage.load());

        // Anyadimos la escena a la ventana de inicio.
        this.primaryStage.setScene(landingScene);

        // Pasamos la referencia del main al controlador.
        LandingPageController landingController = landingPage.getController();
        landingController.setMainApp(this);

        // Mostramos la ventana.
        this.primaryStage.show();
    }
}
