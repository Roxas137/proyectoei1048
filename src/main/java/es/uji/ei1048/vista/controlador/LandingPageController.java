package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.vista.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    // Coordenadas o ciudad
    // Prediccion: 1,2,3
    public void consultaTiempo() {

    }

}
