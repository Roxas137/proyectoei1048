package es.uji.ei1048.vista;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.facade.WeatherAppFacade;
import es.uji.ei1048.jsonTreatment.CityListReader;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.service.IWeatherService;
import es.uji.ei1048.service.openWeatherMap.OpenWeatherMap;
import es.uji.ei1048.vista.controlador.EtiquetaController;
import es.uji.ei1048.vista.controlador.LandingPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainApp extends Application {

    private IWeatherService service = new OpenWeatherMap();
    private IWeatherAppFacade weatherAppFacade = new WeatherAppFacade(service);
    private Stage primaryStage;
    Map<Long, String> cities;
    private String ciudadFav;
    private Coordenadas coordFav;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Preparamos la lista de las ciudades
        cities = service.getCities();
        List<String> listadoCiudadesPais = new ArrayList<>();
        for (String cityCountry : cities.values()) {
            listadoCiudadesPais.add(cityCountry.replace("#", ", "));
        }

        this.primaryStage = primaryStage;

        // Creamos el loader de la pagina inicial.
        FXMLLoader landingPage = new FXMLLoader();
        landingPage.setLocation(MainApp.class.getClassLoader().getResource("es.uji.ei1048/vista/escenas/landingPage.fxml"));

        this.primaryStage.setTitle("My Weather App");
        this.primaryStage.setResizable(false);

        // Cargamos la escena.
        Scene landingScene = new Scene(landingPage.load());

        // Anyadimos la escena a la ventana de inicio.
        this.primaryStage.setScene(landingScene);

        // Pasamos la referencia del main al controlador.
        LandingPageController landingController = landingPage.getController();
        landingController.setMainApp(this);
        landingController.setWeatherAppFacade(weatherAppFacade);
        landingController.setCiudades(listadoCiudadesPais);

        // Mostramos la ventana.
        this.primaryStage.show();
    }

    public void error(String mensaje) {
        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
        dialogoAlerta.setTitle("Error");
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.setContentText(mensaje);
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
    }

    public String getNombreCiudad(long idCiudad){
        return cities.get(idCiudad);
    }

    public void registraFavorito(String etiqueta) {
        weatherAppFacade.registraLugarFavorito(ciudadFav,coordFav, etiqueta);
    }

    public void registraEtiqueta(String ciudadFav, Coordenadas coordFav) {
        try {
            this.ciudadFav = ciudadFav;
            this.coordFav = coordFav;

            FXMLLoader favLoader = new FXMLLoader();
            favLoader.setLocation(MainApp.class.getClassLoader().getResource("es.uji.ei1048/vista/escenas/registraFav.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Nuevo lugar favorito");
            stage.setResizable(false);

            // Cargamos la escena.
            Scene favScene = new Scene(favLoader.load());

            // Anyadimos la escena a la ventana de inicio.
            stage.setScene(favScene);

            // Pasamos la referencia del main al controlador.
            EtiquetaController favController = favLoader.getController();
            favController.setMainApp(this);

            // Mostramos la ventana.
            stage.showAndWait();
            stage.close();
        } catch (Exception e) {
            error("Error al registrar un lugar favorito");
        }
    }
}
