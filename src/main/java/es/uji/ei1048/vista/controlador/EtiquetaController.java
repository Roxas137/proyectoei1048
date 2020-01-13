package es.uji.ei1048.vista.controlador;

import es.uji.ei1048.vista.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EtiquetaController {

    private MainApp mainApp;

    @FXML
    public TextField etiqueta;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void crearFav() {
        mainApp.registraFavorito(etiqueta.getText());
    }

}
