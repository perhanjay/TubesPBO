package com.myapp.demotubes.Controller.DocumentController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KeteranganUsahaViewController {
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextArea deskripsiSingkat;

    @FXML
    private Button kembaliBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Button unggahBerkasKTPMeninggalbtn;

    @FXML
    private Button unggahBerkasKKMeninggalBtn;

    @FXML
    private Button unggahBerkasSKKematianbtn;

    @FXML
    private Button unggahBerkasKTPPelaporbtn;

    @FXML
    private void userIsiBiodataOnClick(){
        if (alertConfirmation() == ButtonType.OK) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 1, "User Dashboard");
        } else{
            return;
        }
    }

    @FXML
    private void statusOnClick(){
        if (alertConfirmation() == ButtonType.OK) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
        } else{
            return;
        }
    }

    @FXML
    private void kembaliBtnOnClick() {
       SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 2, "Permohonan Dokumen");
    }

    //Konfirmasi pengguna ketika memencet tombol aneh aneh
    private ButtonType alertConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setContentText("Progress pengisian dokumen anda belum disimpan. \nApakah anda yakin");
        alert.showAndWait();
        return alert.getResult();
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(SessionAkun.getCurrentAkun().getUsername().toUpperCase());
        System.out.println("KeteranganKematianViewController initialized");
    }
}


