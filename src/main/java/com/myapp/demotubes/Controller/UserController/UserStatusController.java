package com.myapp.demotubes.Controller.UserController;

import com.myapp.demotubes.Entities.Dokumen;
import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Services.PengajuanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserStatusController {
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button hapusBtn;

    @FXML
    private TableView<PengajuanDokumen> mainTable;

    @FXML
    TableColumn<PengajuanDokumen, String> jenisDokumenCol;

    @FXML
    TableColumn<PengajuanDokumen, String> tanggalDiajukanCol;

    @FXML
    TableColumn<PengajuanDokumen, String> statusCol;

    @FXML
    TableColumn<PengajuanDokumen, String> catatanAdminCol;

    @FXML
    private void ajukanDokumenOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 2, "Permohonan Dokumen");
    }
    @FXML
    private void userIsiBiodataOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 1, "Pengguna Dashboard");

    }

    @FXML
    private void hapusBtnOnClick(){
        PengajuanDokumen selectedPengajuanDokumen = mainTable.getSelectionModel().getSelectedItem();
        if (selectedPengajuanDokumen != null) {
            PengajuanService.deletePengajuan(selectedPengajuanDokumen.getId());
            mainTable.getItems().remove(selectedPengajuanDokumen);
            System.out.println("PengajuanDokumen deleted: " + selectedPengajuanDokumen.getId());
        } else {
            System.out.println("No PengajuanDokumen selected for deletion.");
        }
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(SessionAkun.getCurrentAkun().getUsername().toUpperCase());
        System.out.println("UserStatusController initialized");

        ObservableList<PengajuanDokumen> data = FXCollections.observableArrayList(PengajuanService.getPengajuanByIdAkunForUserView(SessionAkun.getCurrentAkun().getId()));

        jenisDokumenCol.setCellValueFactory(cellData -> {
            Dokumen dokumen = cellData.getValue().getDokumen();
            return new SimpleStringProperty(dokumen.getNamaDokumen());
        });

        tanggalDiajukanCol.setCellValueFactory(new PropertyValueFactory<>("tanggalPengajuan"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        catatanAdminCol.setCellValueFactory(new PropertyValueFactory<>("catatan"));

        mainTable.setItems(data);
//        mainTable.getColumns().addAll(jenisDokumenCol, tanggalDiajukanCol, statusCol, catatanAdminCol);
    }

}

