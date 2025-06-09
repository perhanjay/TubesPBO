package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Sessions.SessionWargaList;
import com.myapp.demotubes.Entities.Warga;
import com.myapp.demotubes.Services.AdminWargaService;
import com.myapp.demotubes.Services.UserPageService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminPendudukAddController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField textFieldNama;

    @FXML
    private TextField textFieldNIK;

    @FXML
    private ComboBox<JenisKelamin> boxJenisKelamin;

    @FXML
    private DatePicker datePickerTglLahir;

    @FXML
    private TextField textFieldTempatLahir;

    @FXML
    private TextField textFieldAlamatLengkap;

    @FXML
    private ComboBox<Agama> boxAgama;

    @FXML
    private ComboBox<StatusPekerjaan> boxStatusPekerjaan;

    @FXML
    private ComboBox<StatusKawin> boxStatusKawin;

    @FXML
    private Button tambahBtn;

    private AdminPendudukController parentController;

    public void setParentController(AdminPendudukController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void tambahBtnOnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Apakah Anda yakin ingin menambahkan warga baru?");
        alert.setContentText("Pastikan semua data yang dimasukkan sudah benar.");
        Optional<ButtonType> response =  alert.showAndWait();
        if (!(response.get() == ButtonType.OK)) {
            return;
        }
        System.out.println("Executing tambahBtnOnClick");

        String nama = textFieldNama.getText();
        String nik = textFieldNIK.getText();
        String jenisKelamin = JenisKelamin.getJenisKelamin(boxJenisKelamin.getValue().toString()).name();
        String tanggalLahir = datePickerTglLahir.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tempatLahir = textFieldTempatLahir.getText();
        String alamat = textFieldAlamatLengkap.getText();
        String agama = Agama.getAgama(boxAgama.getValue().toString()).name();
        String statusPekerjaan = StatusPekerjaan.getStatusPekerjaan(boxStatusPekerjaan.getValue().toString()).name();
        String statusKawin = StatusKawin.getStatusKawin(boxStatusKawin.getValue().toString()).name();

        try {
            UserPageService.insertWargaToDB(nama, jenisKelamin, tanggalLahir, tempatLahir, alamat, agama, nik, statusPekerjaan, statusKawin);
            SessionWargaList.resetCounter();


            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Sukses");
            alert1.setContentText("Warga baru berhasil ditambahkan. \nKlik OK untuk melanjutkan.");
            Optional<ButtonType> response1 =  alert1.showAndWait();
            if (!(response1.get() == ButtonType.OK)) {
                return;
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setContentText("Gagal menambahkan warga baru. \nPastikan NIK unik dan tidak ada kesalahan input.");
            alert1.showAndWait();
        }
    }

    private void boxInitiator(){
        boxJenisKelamin.setItems(FXCollections.observableArrayList(JenisKelamin.values()));
        boxAgama.setItems(FXCollections.observableArrayList(Agama.values()));
        boxStatusKawin.setItems(FXCollections.observableArrayList(StatusKawin.values()));
        boxStatusPekerjaan.setItems(FXCollections.observableArrayList(StatusPekerjaan.values()));
    }

    @FXML
    private void initialize() {
        boxInitiator();
        textFieldNIK.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // saat kehilangan fokus
                String input = textFieldNIK.getText();

                if(input.length() != 16 || !input.matches("\\d+")) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setContentText("NIK harus terdiri dari 16 digit angka!");
                    alert1.show();
                    textFieldNIK.setText(""); // mengosongkan field jika NIK tidak valid
                }
            }});

    }

}
