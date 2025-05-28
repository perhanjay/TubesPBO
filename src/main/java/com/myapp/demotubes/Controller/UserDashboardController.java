package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Entities.Sessions.SessionWarga;
import com.myapp.demotubes.Entities.Warga;
import com.myapp.demotubes.HelloApplication;
import com.myapp.demotubes.Services.UserPageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDashboardController {
    Akun akun = null;

    @FXML
    private Button submitBtn;

    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane sideDashPane;

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
    private void ajukanDokumenOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 2, "Permohonan Dokumen");
    }

    @FXML
    private void statusOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
    }

    @FXML
    private void submitButtonOnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setContentText("Mohon pastikan data yang anda masukkan sudah benar. Apakah anda yakin ingin mengirim data ini?");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String nama = textFieldNama.getText();
                    String nik = textFieldNIK.getText();
                    String jenisKelamin = JenisKelamin.getJenisKelamin(boxJenisKelamin.getValue().toString()).name();
                    String tanggalLahir = datePickerTglLahir.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String tempatLahir = textFieldTempatLahir.getText();
                    String alamat = textFieldAlamatLengkap.getText();
                    String agama = Agama.getAgama(boxAgama.getValue().toString()).name();
                    String statusPekerjaan = StatusPekerjaan.getStatusPekerjaan(boxStatusPekerjaan.getValue().toString()).name();
                    String statusKawin = StatusKawin.getStatusKawin(boxStatusKawin.getValue().toString()).name();

                    int wargaId = 0;

                    try{
                        //masukkan data warga ke db
                        UserPageService.insertWargaToDB(nama, jenisKelamin, tanggalLahir, tempatLahir, alamat, agama, nik, statusPekerjaan, statusKawin);
                        //ambil id warga dari db
                        wargaId = UserPageService.getWargaId(nik);
                        //Buat instance Warga
                        Warga warga = new Warga(wargaId, nama, JenisKelamin.valueOf(jenisKelamin), tanggalLahir, tempatLahir, alamat, Agama.valueOf(agama), nik, StatusPekerjaan.valueOf(statusPekerjaan), StatusKawin.valueOf(statusKawin));
                        //set session warga
                        SessionWarga.setCurrentWarga(warga);
                        //update akun dengan id warga / mengaitkan akun dengan warga menggunakna id
                        SessionAkun.getCurrentAkun().setIdWarga(wargaId);
                        //update akun di db
                        UserPageService.insertIdWargaToAkun(wargaId, akun.getUsername());
                        //nonaktifkan submit button
                        submitBtn.setDisable(true);

                    } catch (SQLException e) {
                        System.out.println("Failed to connect to the database or execute the query." + e.getMessage());
                    };

                } catch (Exception e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setContentText("Mohon lengkapi semua data sebelum mengirim.");
                    alert2.showAndWait();
                }
            } else {
                System.out.println("User cancelled the operation.");
            }
        });
    }

    @FXML
    private void initialize() {
        akun = SessionAkun.getCurrentAkun();
        boxInitiator();

        if (akun.getIdWarga() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Anda belum memasukkan data.");
            alert.show();

            textFieldNama.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches(".*\\d.*")) {
                    textFieldNama.setText(oldValue);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Peringatan");
                    alert1.setContentText("Nama tidak boleh mengandung angka!");
                    alert1.showAndWait();
                }
            });

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

        } else{
            if (SessionWarga.getCurrentWarga() == null) {
                int idWarga = akun.getIdWarga();
                SessionWarga.setCurrentWarga(UserPageService.getWargaById(idWarga));
                System.out.println("First Session Initialized");
            }
            accountDisplayInitiator();
        }
    }

    private void boxInitiator(){
        usernameLabel.setText(akun.getUsername().toUpperCase());
        boxJenisKelamin.setItems(FXCollections.observableArrayList(JenisKelamin.values()));
        boxAgama.setItems(FXCollections.observableArrayList(Agama.values()));
        boxStatusKawin.setItems(FXCollections.observableArrayList(StatusKawin.values()));
        boxStatusPekerjaan.setItems(FXCollections.observableArrayList(StatusPekerjaan.values()));
    }

    private void accountDisplayInitiator(){
        textFieldNama.setText(SessionWarga.getCurrentWarga().getName());
        textFieldNama.setEditable(false);
        textFieldNIK.setText(SessionWarga.getCurrentWarga().getNik());
        textFieldNIK.setEditable(false);
        textFieldAlamatLengkap.setText(SessionWarga.getCurrentWarga().getAlamat());
        textFieldAlamatLengkap.setEditable(false);
        datePickerTglLahir.setValue(LocalDate.parse(SessionWarga.getCurrentWarga().getTanggalLahir()));
        datePickerTglLahir.setFocusTraversable(false);
        datePickerTglLahir.setMouseTransparent(true);
        textFieldTempatLahir.setText(SessionWarga.getCurrentWarga().getTempatLahir());
        textFieldTempatLahir.setEditable(false);
        textFieldAlamatLengkap.setText(SessionWarga.getCurrentWarga().getAlamat());
        textFieldAlamatLengkap.setEditable(false);
        boxJenisKelamin.setValue(SessionWarga.getCurrentWarga().getJenisKelamin());
        boxJenisKelamin.setFocusTraversable(false);
        boxJenisKelamin.setMouseTransparent(true);
        boxAgama.setValue(SessionWarga.getCurrentWarga().getAgama());
        boxAgama.setFocusTraversable(false);
        boxAgama.setMouseTransparent(true);
        boxStatusPekerjaan.setValue(SessionWarga.getCurrentWarga().getStatusPekerjaan());
        boxStatusPekerjaan.setFocusTraversable(false);
        boxStatusPekerjaan.setMouseTransparent(true);
        boxStatusKawin.setValue(SessionWarga.getCurrentWarga().getStatusKawin());
        boxStatusKawin.setFocusTraversable(false);
        boxStatusKawin.setMouseTransparent(true);
        submitBtn.setDisable(true);
    }
}


