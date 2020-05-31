package es.deusto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import es.deusto.bd.gestor.GestorBD;
import es.deusto.bd.objetos.Empleado;
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

public class EmpleadosController implements Initializable {

    @FXML
    private TextField txtLibreria;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableView<Empleado> tableInfo;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colLibreria;

    @FXML
    private Button btnAinadir;

    static ObservableList<Empleado> masterData;

    @FXML
    void ainadirLibro(ActionEvent event) {
        String nombre = this.txtNombre.getText();
        String libreria = this.txtLibreria.getText();
        Empleado aux = new Empleado(nombre, libreria);
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
        this.txtLibreria.setDisable(true);
        this.txtNombre.setDisable(true);
        Empleado l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una libreria");
            alert.showAndWait();
        } else {
            this.txtLibreria.setText(l.getLibreria());
            this.txtNombre.setText(l.getNombre());
        }
    }

    @FXML
    void seleccionar(MouseEvent event) {
        this.txtLibreria.setDisable(true);
        this.txtNombre.setDisable(true);
        Empleado l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l != null) {
            this.txtLibreria.setText(l.getLibreria());
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
        this.txtLibreria.setDisable(false);
        this.txtNombre.setDisable(false);
        masterData = FXCollections.observableArrayList();
        masterData.addAll(GestorBD.getInstance().selectListaObjectos(Empleado.class));
        configurarTableView();
        FilteredList<Empleado> filteredData = new FilteredList<Empleado>(masterData, p -> true);
        txtBuscar.setOnKeyPressed(event -> {
            filteredData.setPredicate(libreria -> {
                String search = txtBuscar.getText();
                if (search == null || search.isEmpty()) {
                    return true;
                }
                search = search.toLowerCase();
                if (libreria.getNombre().toLowerCase().contains(search))
                    return true;
                if (libreria.getLibreria().toLowerCase().contains(search))
                    return true;
                return false;
            });
        });
        SortedList<Empleado> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tableInfo.comparatorProperty());
        tableInfo.setItems(sortedList);
    }

    private void configurarTableView() {
        colLibreria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLibreria()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
    }

}