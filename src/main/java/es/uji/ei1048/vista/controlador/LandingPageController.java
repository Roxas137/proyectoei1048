package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.vista.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

import java.util.List;

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
    private List<String> ciudades;


    private String temperaturaActual;
    private String sensacionTermica;
    private String temperaturaMin;
    private String temperaturaMax;
    private String estadoClima;
    private String velViento;
    private String dirViento;
    private String presion;
    private String humedad;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize(){

    }


    // Coordenadas o ciudad
    // Prediccion: 1,2,3
    public void consultaTiempo() {
        String[] ciudadConsulta = ciudad.getText().split(", ");
        CondicionesMeteorologicas condicionesMeteorologicas = weatherAppFacade.getCondicionesActuales(ciudadConsulta[0] + "#" + ciudadConsulta[1] );
        GridPane condiciones = gridPaneConstructor(condicionesMeteorologicas);
        AnchorPane.setLeftAnchor(condiciones, 10.0);
        AnchorPane.setRightAnchor(condiciones, 10.0);
        AnchorPane.setTopAnchor(condiciones, 10.0);
        AnchorPane.setBottomAnchor(condiciones, 10.0);
        GridPane.setHgrow(condiciones, Priority.ALWAYS);
        GridPane.setVgrow(condiciones, Priority.ALWAYS);

        AnchorPane.setLeftAnchor(panelActual, 10.0);
        AnchorPane.setRightAnchor(panelActual, 10.0);
        AnchorPane.setTopAnchor(panelActual, 10.0);
        AnchorPane.setBottomAnchor(panelActual, 10.0);
        GridPane.setHgrow(panelActual, Priority.ALWAYS);
        GridPane.setVgrow(panelActual, Priority.ALWAYS);

        panelActual.getChildren().add(condiciones);
    }

    private GridPane gridPaneConstructor(CondicionesMeteorologicas condiciones) {
        GridPane panel = new GridPane();
        this.temperaturaActual = "Temperatura: " + condiciones.getTemperaturaActual();
        this.sensacionTermica = "Sensacion termica: " + condiciones.getSensacionTermica();
        this.temperaturaMin = "Temperatura minima: " + condiciones.getTemperaturaMin();
        this.temperaturaMax = "Temperatura maxima: " + condiciones.getTemperaturaMax();
        this.estadoClima = "Estado: " + condiciones.getEstadoClima();
        this.velViento = "Velocidad del viento: " + condiciones.getVelViento();
        this.dirViento = "Direccion del viento: " + condiciones.getDirViento();
        this.presion = "Presion: " + condiciones.getPresion();
        this.humedad = "Humedad: " + condiciones.getHumedad();

        panel.add(new Label(temperaturaActual), 0, 0);
        panel.add(new Label(temperaturaMin), 1, 0);
        panel.add(new Label(temperaturaMax), 2, 0);
        panel.add(new Label(sensacionTermica), 0, 1);
        panel.add(new Label(estadoClima), 0, 2);
        panel.add(new Label(velViento), 0, 3);
        panel.add(new Label(dirViento), 1, 3);
        panel.add(new Label(presion), 0, 4);
        panel.add(new Label(humedad), 1, 4);
        panel.setHgap(15);
        panel.setVgap(15);
        return panel;
    }

    public void setWeatherAppFacade(IWeatherAppFacade weatherAppFacade) {
        this.weatherAppFacade = weatherAppFacade;
    }

    public void setCiudades(List<String> listadoCiudadesPais) {
        ciudades = listadoCiudadesPais;
        TextFields.bindAutoCompletion(ciudad, ciudades);
    }
}
