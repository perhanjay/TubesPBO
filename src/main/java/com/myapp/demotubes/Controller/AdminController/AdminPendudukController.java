package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Entities.Sessions.SessionWargaList;
import com.myapp.demotubes.Entities.Warga;
import com.myapp.demotubes.Services.AdminWargaService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPendudukController {

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button pendudukBtn;

    @FXML
    private Button dokumenBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableColumn<Warga, String> namaCol;

    @FXML
    private TableColumn<Warga, String> nikCol;

    @FXML
    private TableColumn<Warga, JenisKelamin> jenisKelaminCol;

    @FXML
    private TableColumn<Warga, String> tglLahirCol;

    @FXML
    private TableColumn<Warga, String> agamaCol;

    @FXML
    private TableView<Warga> tableView;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Button selengkapnyaBtn;

    @FXML
    private Button tambahWargaBtn;

    @FXML
    private void dashboardBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 1, "Penduduk Dashboard");
    }

    @FXML
    private void dokumenBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dokumenBtn.getScene().getWindow(), 3, "Dokumen Dashboard");
    }

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
    }

    @FXML
    private void searchBtnOnClick() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Warga> allWarga = SessionWargaList.getWargaList();
        ObservableList<Warga> filtered = allWarga.filtered(warga ->
                warga.getNama().toLowerCase().contains(keyword) ||
                        warga.getNik().toLowerCase().contains(keyword)
        );
        tableView.setItems(filtered);
    }

    @FXML
    private void selengkapnyaBtnOnClick() {
        Warga selectedWarga = tableView.getSelectionModel().getSelectedItem();
        System.out.println(selectedWarga);
        if (selectedWarga != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/demotubes/view/AdminView/AdminPendudukContinueView.fxml"));
            Parent root = loader.load();

            AdminPendudukContinueController controller = loader.getController();
            controller.setWarga(selectedWarga);
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("Warga Details");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> this.initialize());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else {
            System.out.println("No Warga selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setContentText("Pilih warga untuk membuka detail.");
            alert.showAndWait();
        }
    }

    @FXML
    private void tambahWargaBtnOnClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/demotubes/view/AdminView/AdminPendudukAddView.fxml"));
            Parent root = loader.load();

            AdminPendudukAddController controller = loader.getController();
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("Add Warga");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> this.initialize());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        System.out.println("Admin Penduduk View initialized");

        if (SessionWargaList.getCounter() == 0) {
            System.out.println("Retrieving data from persistent storage");
            SessionWargaList.clear();
            SessionWargaList.setWargaList(AdminWargaService.getAllWarga());

            kindaInit(SessionWargaList.getWargaList());


            SessionWargaList.addCounter();

        }else{
            System.out.println("Using cached data");
            kindaInit(SessionWargaList.getWargaList());
        }
    }

    private void kindaInit(ObservableList<Warga> data) {
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        nikCol.setCellValueFactory(new PropertyValueFactory<>("nik"));
        jenisKelaminCol.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        tglLahirCol.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        agamaCol.setCellValueFactory(new PropertyValueFactory<>("agama"));

        tableView.setItems(data);
    }
}
