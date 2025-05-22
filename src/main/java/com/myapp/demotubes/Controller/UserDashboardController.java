package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Entities.Sessions.SessionWarga;
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
       try {
           FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/userAjukanDokumen.fxml"));
           Parent root = loader.load();
           Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
           stage.setScene(new Scene(root));
           stage.setTitle("AjukanDokumen");
           stage.centerOnScreen();
           stage.show();
       } catch (Exception e) {
           System.out.println("Error loading user dashboard: " + e.getMessage());
       }
    }
    @FXML
    private void statusOnClick(){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/userStatus.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Status");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading user dashboard: " + e.getMessage());
        }
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

