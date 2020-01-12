package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.vista.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LandingPageController {

    @FXML
    public ToggleGroup tipoBusqueda;
    @FXML
    public ChoiceBox servicio;
    @FXML
    public TextField ciudad;
    @FXML
    public TextField latitud;
    @FXML
    public TextField longitud;
    @FXML
    public ChoiceBox prediccion;
    @FXML
    public Button botonConsulta;
    @FXML
    public AnchorPane panelActual;
    @FXML
    public TabPane tabPane;


    private MainApp mainApp;
    private IWeatherAppFacade weatherAppFacade;

    //temp
    private Double temperaturaActual;
    //feels_like
    private Double sensacionTermica;
    //temp_min
    private Double temperaturaMin;
    //temp_max
    private Double temperaturaMax;
    //description
    private String estadoClima;
    //speed
    private Double velViento;
    //deg
    private Double dirViento;
    //pressure
    private Double presion;
    //humidity
    private Double humedad;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // Coordenadas o ciudad
    // Prediccion: 1,2,3
    public void consultaTiempo() {
        CondicionesMeteorologicas condicionesMeteorologicas = weatherAppFacade.getCondicionesActuales(ciudad.getText() + "#es");
        GridPane condiciones = gridPaneConstructor(condicionesMeteorologicas);
        panelActual.getChildren().add(condiciones);

    }

    private GridPane gridPaneConstructor(CondicionesMeteorologicas condiciones) {
        GridPane panel = new GridPane();
        panel.add(new Label("Temperatura: " + condiciones.getTemperaturaActual()), 0, 0);
        panel.add(new Label("Sensacion termica: " + condiciones.getSensacionTermica()), 0, 1);
        panel.add(new Label("Temperatura minima: " + condiciones.getTemperaturaMin()), 0, 2);
        panel.add(new Label("Temperatura maxima: " + condiciones.getTemperaturaMax()), 1, 0);
        panel.add(new Label("Estado: " + condiciones.getEstadoClima()), 1, 1);
        panel.add(new Label("Velocidad del viento: " + condiciones.getVelViento()), 1, 2);
        panel.add(new Label("Direccion del viento: " + condiciones.getDirViento()), 2, 0);
        panel.add(new Label("Presion: " + condiciones.getPresion()), 2, 1);
        panel.add(new Label("Humedad: " + condiciones.getHumedad()), 2, 2);
        return panel;
    }

    public void setWeatherAppFacade(IWeatherAppFacade weatherAppFacade) {
        this.weatherAppFacade = weatherAppFacade;
    }
}
