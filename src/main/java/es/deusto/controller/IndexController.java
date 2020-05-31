package es.deusto.controller;

import java.io.IOException;

import es.deusto.App;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IndexController extends Application {

    @FXML
    private Button btnLibros;

    @FXML
    private Button btnAutores;

    @FXML
    private Button btnLibrerias;

    @FXML
    private Button btnEmpleados;

    private void cambiarVentana(String nombre) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/deusto/" + nombre + ".fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void autores(ActionEvent event) throws IOException {
        cambiarVentana("autores");
    }

    @FXML
    void empleados(ActionEvent event) {
        cambiarVentana("empleados");
    }

    @FXML
    void librerias(ActionEvent event) {
        cambiarVentana("librerias");
    }

    @FXML
    void libros(ActionEvent event) {
        cambiarVentana("libros");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

    }

}