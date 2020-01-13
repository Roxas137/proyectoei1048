package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.facade.IWeatherAppFacade;
import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.vista.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    @FXML
    public RadioButton botonCiudad;
    @FXML
    public RadioButton botonCoord;
    @FXML
    public Label ubicacion;
    @FXML
    public Button fav;


    private MainApp mainApp;
    private IWeatherAppFacade weatherAppFacade;
    private List<String> ciudades;
    private boolean esCiudad = true;


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
    public void initialize() {
        fav.setDisable(true);

        ObservableList<String> listaServicios = FXCollections.observableArrayList();
        listaServicios.add("OpenWeatherMap");
        servicio.setItems(listaServicios);
        servicio.setValue("OpenWeatherMap");

        ObservableList<String> opcionesPrediccion = FXCollections.observableArrayList();
        opcionesPrediccion.add("Hoy");
        opcionesPrediccion.add("Mañana");
        opcionesPrediccion.add("2 dias");
        opcionesPrediccion.add("3 dias");
        prediccion.setItems(opcionesPrediccion);
        prediccion.setValue("Hoy");

        botonCiudad.setUserData("Ciudad");
        botonCoord.setUserData("Coord");
        tipoBusqueda.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> seleccion(newValue));
    }

    private void seleccion(Toggle togle) {
        if (togle.getUserData().toString().equals("Ciudad")) {
            ciudad.setDisable(false);
            latitud.setDisable(true);
            longitud.setDisable(true);
            esCiudad = true;
        } else {
            ciudad.setDisable(true);
            latitud.setDisable(false);
            longitud.setDisable(false);
            esCiudad = false;
        }
    }

    // Coordenadas o ciudad
    // Prediccion: 1,2,3
    public void consultaTiempo() {
        try {
            String consulta = "";
            Coordenadas coordenadas = new Coordenadas();

            if (esCiudad) {
                consulta = ciudad.getText().replace(", ", "#");
            } else {
                double miLatitud;
                double miLongitud;
                try {
                    miLatitud = Double.parseDouble(latitud.getText());
                    miLongitud = Double.parseDouble(longitud.getText());
                } catch (Exception e) {
                    System.out.println("Latitud o longitud incorrectas");
                    return;
                }
                coordenadas.setLatitud(miLatitud);
                coordenadas.setLongitud(miLongitud);
            }
            String tipoPeticion = prediccion.getValue().toString();
            CondicionesMeteorologicas condicionesMeteorologicas = null;
            switch (tipoPeticion) {
                case "Hoy":
                    if (esCiudad) {
                        condicionesMeteorologicas = weatherAppFacade.getCondicionesActuales(consulta);
                    } else {
                        condicionesMeteorologicas = weatherAppFacade.getCondicionesActuales(coordenadas);
                    }
                    break;
                case "Mañana":
                    if (esCiudad) {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(consulta).get(5);
                    } else {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(coordenadas).get(5);
                    }
                    break;
                case "2 dias":
                    if (esCiudad) {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(consulta).get(4);
                    } else {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(coordenadas).get(4);
                    }
                    break;
                case "3 dias":
                    if (esCiudad) {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(consulta).get(3);
                    } else {
                        condicionesMeteorologicas = weatherAppFacade.getPrediccion(coordenadas).get(3);
                    }
                    break;
            }

            GridPane condiciones = gridPaneConstructor(condicionesMeteorologicas);
            ubicacion.setText(ciudad.getText().toUpperCase());
            if (panelActual.getChildren().size() == 3)
                panelActual.getChildren().remove(2);
            panelActual.getChildren().add(condiciones);
            fav.setDisable(false);
        } catch (NullPointerException e) {
            System.out.println("NullPointeException");
        } catch (InvalidCityException e) {
            System.out.println("La ciudad no es válida");
        } catch (InvalidCoordenatesException e){
            System.out.println("Las coordenadas no son válidas");
        }
    }


    private GridPane gridPaneConstructor(CondicionesMeteorologicas condiciones) {
        GridPane panel = new GridPane();
        this.temperaturaActual = condiciones.getTemperaturaActual() + "º";
        this.sensacionTermica = condiciones.getSensacionTermica() + "º";
        this.temperaturaMin = condiciones.getTemperaturaMin() + "º";
        this.temperaturaMax = condiciones.getTemperaturaMax() + "º";
        this.estadoClima = condiciones.getEstadoClima();
        this.velViento = condiciones.getVelViento() + "m/s";
        this.dirViento = condiciones.getDirViento() + "º";
        this.presion = condiciones.getPresion() + "hpa";
        this.humedad = condiciones.getHumedad() + "%";

        panel.add(new Label("Temperatura:"), 0, 0);
        panel.add(new Label(temperaturaActual), 1, 0);
        panel.add(new Label("Min:"), 2, 0);
        panel.add(new Label(temperaturaMin), 3, 0);
        panel.add(new Label("Max:"), 4, 0);
        panel.add(new Label(temperaturaMax), 5, 0);

        panel.add(new Label("Sensación:"), 0, 1);
        panel.add(new Label(sensacionTermica), 1, 1);

        panel.add(new Label("Estado:"), 0, 2);
        panel.add(new Label(estadoClima), 1, 2);

        panel.add(new Label("Vel. viento:"), 0, 3);
        panel.add(new Label(velViento), 1, 3);
        panel.add(new Label("Dir. viento:"), 0, 4);
        panel.add(new Label(dirViento), 1, 4);

        panel.add(new Label("Presion:"), 3, 3, 2, 1);
        panel.add(new Label(presion), 5, 3);
        panel.add(new Label("Humedad:"), 3, 4, 2, 1);
        panel.add(new Label(humedad), 5, 4);
        panel.setHgap(15);
        panel.setVgap(20);

        AnchorPane.setLeftAnchor(panel, 80.0);
        AnchorPane.setRightAnchor(panel, 50.0);
        AnchorPane.setTopAnchor(panel, 70.0);
        AnchorPane.setBottomAnchor(panel, 10.0);
        GridPane.setHgrow(panel, Priority.ALWAYS);
        GridPane.setVgrow(panel, Priority.ALWAYS);

        GridPane.setHgrow(panelActual, Priority.ALWAYS);
        GridPane.setVgrow(panelActual, Priority.ALWAYS);
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
