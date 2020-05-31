package es.deusto;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import es.deusto.bd.gestor.*;

/**
 * Hello world!
 *
 */
public class App extends Application {

    private static App app = null;
    private static Scene scene;
    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.stage = primaryStage;
        new GestorBD();
        FXMLLoader loginLoader = getFXMLLoader("index");
        scene = new Scene(loginLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public App() {
        // CrearDatos.generarAutores();
        // CrearDatos.generarEmpleados();
        // CrearDatos.generarLibrerias();
        // CrearDatos.generarLibros();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static App getInstance() {
        return app;
    }

    public void setScene(Scene scene) {
        App.stage.setScene(scene);
        App.stage.show();
    }
}
