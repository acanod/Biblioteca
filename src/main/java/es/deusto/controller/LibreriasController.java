package es.deusto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import es.deusto.bd.gestor.GestorBD;
import es.deusto.bd.objetos.Libreria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LibreriasController implements Initializable {

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableView<Libreria> tableInfo;

    @FXML
    private TableColumn<Libreria, String> colNombre;

    @FXML
    private TableColumn<Libreria, String> colCiudad;

    @FXML
    private Button btnAinadir;

    static ObservableList<Libreria> masterData;

    @FXML
    void ainadirLibro(ActionEvent event) {
        String nombre = this.txtNombre.getText();
        String ciudad = this.txtCiudad.getText();
        Libreria aux = new Libreria(nombre, ciudad);
        if (!masterData.contains(aux)) {
            masterData.add(aux);
            this.tableInfo.setItems(masterData);
            GestorBD.storeObjectInDB(aux);
            this.tableInfo.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("El libro ya existe");
            alert.showAndWait();
        }
    }

    @FXML
    void editarLibro(ActionEvent event) {
        this.txtCiudad.setDisable(true);
        this.txtNombre.setDisable(true);
        Libreria l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una libreria");
            alert.showAndWait();
        } else {
            this.txtCiudad.setText(l.getCiudad());
            this.txtNombre.setText(l.getNombre());
        }
    }

    @FXML
    void seleccionar(MouseEvent event) {
        this.txtCiudad.setDisable(true);
        this.txtNombre.setDisable(true);
        Libreria l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l != null) {
            this.txtCiudad.setText(l.getCiudad());
            this.txtNombre.setText(l.getNombre());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un libro");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.txtCiudad.setDisable(false);
        this.txtNombre.setDisable(false);
        masterData = FXCollections.observableArrayList();
        masterData.addAll(GestorBD.getInstance().selectListaObjectos(Libreria.class));
        configurarTableView();
        FilteredList<Libreria> filteredData = new FilteredList<Libreria>(masterData, p -> true);
        txtBuscar.setOnKeyPressed(event -> {
            filteredData.setPredicate(libreria -> {
                String search = txtBuscar.getText();
                if (search == null || search.isEmpty()) {
                    return true;
                }
                search = search.toLowerCase();
                if (libreria.getNombre().toLowerCase().contains(search))
                    return true;
                if (libreria.getCiudad().toLowerCase().contains(search))
                    return true;
                return false;
            });
        });
        SortedList<Libreria> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tableInfo.comparatorProperty());
        tableInfo.setItems(sortedList);
    }

    private void configurarTableView() {
        colCiudad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCiudad()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
    }
}