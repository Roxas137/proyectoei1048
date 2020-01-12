package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.vista.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private MainApp mainApp;


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

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    // Coordenadas o ciudad
    // Prediccion: 1,2,3
    public void consultaTiempo() {

    }

    private void gridPaneConstructor() {
        GridPane panel = new GridPane();
        panel.add(new Label("Temperatura: "),0,0);
        panel.add(new Label("Sensacion termica: "),0,0);
        panel.add(new Label("Temperatura minima: "),0,0);
        panel.add(new Label("Temperatura maxima: "),0,0);
        panel.add(new Label("Estado: "),0,0);
        panel.add(new Label("Velocidad del viento: "),0,0);
        panel.add(new Label("Direccion del viento: "),0,0);
        panel.add(new Label("Presion: "),0,0);
        panel.add(new Label("Humedad: "),0,0);
    }

}
