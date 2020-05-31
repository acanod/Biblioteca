package es.deusto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import es.deusto.bd.gestor.GestorBD;
import es.deusto.bd.objetos.Libro;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LibrosController implements Initializable {

    @FXML
    private TextField txtIdioma;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableView<Libro> tableInfo;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableColumn<Libro, String> colIdioma;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, String> colLibreria;

    @FXML
    private TableColumn<Libro, String> colReserva;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtLibreria;

    @FXML
    private ComboBox<String> comboReserva;

    @FXML
    private Button btnAinadir;

    static ObservableList<Libro> masterData;

    @FXML
    void seleccionar(MouseEvent event) {
        this.txtAutor.setDisable(true);
        this.txtIdioma.setDisable(true);
        this.txtTitulo.setDisable(true);
        this.txtLibreria.setDisable(true);
        Libro l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l != null) {
            this.txtTitulo.setText(l.getTitulo());
            this.txtIdioma.setText(l.getIdioma());
            this.txtAutor.setText(l.getAutor());
            this.txtLibreria.setText(l.getLibreria());
            if (l.isReservado()) {
                this.comboReserva.getSelectionModel().select(1);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un libro");
            alert.showAndWait();
        }
    }

    @FXML
    void ainadirLibro(ActionEvent event) {
        String titulo = this.txtTitulo.getText();
        String idioma = this.txtIdioma.getText();
        String autor = this.txtAutor.getText();
        String libreria = this.txtLibreria.getText();
        String reserva = comboReserva.getSelectionModel().getSelectedItem().toString();
        boolean res;
        if (reserva.equals("Reservado")) {
            res = true;
        } else {
            res = false;
        }
        Libro aux = new Libro(titulo, idioma, res, autor, libreria);
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
        this.txtAutor.setDisable(true);
        this.txtIdioma.setDisable(true);
        this.txtTitulo.setDisable(true);
        this.txtLibreria.setDisable(true);
        Libro l = this.tableInfo.getSelectionModel().getSelectedItem();
        if (l == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un libro");
            alert.showAndWait();
        } else {
            this.txtTitulo.setText(l.getTitulo());
            this.txtIdioma.setText(l.getIdioma());
            this.txtAutor.setText(l.getAutor());
            this.txtLibreria.setText(l.getLibreria());
            if (l.isReservado()) {
                this.comboReserva.getSelectionModel().select(1);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.txtAutor.setDisable(false);
        this.txtIdioma.setDisable(false);
        this.txtTitulo.setDisable(false);
        this.txtLibreria.setDisable(false);
        masterData = FXCollections.observableArrayList();
        masterData.addAll(GestorBD.getInstance().selectListaObjectos(Libro.class));
        configurarTableView();
        FilteredList<Libro> filteredData = new FilteredList<Libro>(masterData, p -> true);
        txtBuscar.setOnKeyPressed(event -> {
            filteredData.setPredicate(libro -> {
                String search = txtBuscar.getText();
                if (search == null || search.isEmpty()) {
                    return true;
                }
                search = search.toLowerCase();
                if (libro.getTitulo().toLowerCase().contains(search))
                    return true;
                if (libro.getIdioma().toLowerCase().contains(search))
                    return true;
                if (libro.getAutor().toLowerCase().contains(search))
                    return true;
                return false;
            });
        });
        SortedList<Libro> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tableInfo.comparatorProperty());
        tableInfo.setItems(sortedList);

        ObservableList<String> estado = FXCollections.observableArrayList("Reservado", "No Reservado");
        comboReserva.setItems(estado);
    }

    private void configurarTableView() {
        colAutor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAutor()));
        colIdioma.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdioma()));
        colLibreria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLibreria()));
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colReserva.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().isReservado() ? "Reservado" : "No Reservado"));
    }

}