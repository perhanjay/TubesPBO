package com.myapp.demotubes.Controller.UserController;

import com.myapp.demotubes.Entities.Properties.JenisDokumen;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserAjukanDokumenController {
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane rootAnchorPaneDokumen;

    @FXML
    private ComboBox<JenisDokumen> boxJenisDokumen;

    @FXML
    private Button continueBtn;


    @FXML
    private void userIsiBiodataOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPaneDokumen.getScene().getWindow(), 1, "Pengguna Dashboard");
    }

    @FXML
    private void statusOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPaneDokumen.getScene().getWindow(), 3, "Status Permohonan");
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(SessionAkun.getCurrentAkun().getUsername().toUpperCase());
        System.out.println("UserAjukanDokumenController initialized");
        ObservableList<JenisDokumen> jenisDokumenList = FXCollections.observableArrayList(JenisDokumen.values());
        boxJenisDokumen.setItems(jenisDokumenList);
        System.out.println(boxJenisDokumen);
    }

    @FXML
    private void continueBtnOnClick() {
        JenisDokumen jenisDokumen = boxJenisDokumen.getValue();
        if (jenisDokumen == null) {
            System.out.println("No Jenis Dokumen selected");
        }
        switch (jenisDokumen) {
            case KETERANGAN_KELAHIRAN -> viewLoader(1);
            case KETERANGAN_KEMATIAN -> viewLoader(2);
            case KETERANGAN_USAHA -> viewLoader(3);
            case KETERANGAN_TIDAK_MAMPU -> viewLoader(4);
            case KETERANGAN_DOMISILI -> viewLoader(5);
            default -> System.out.println("Jenis Dokumen tidak valid");
        }

    }


    public void viewLoader(int index) {
        String url;
        String title;
        switch (index) {
            case 1:
                url = "view/DokumenView/ajukanDokumenKeteranganKelahiran.fxml";
                title = "Ajukan Keterangan Kelahiran";
                break;
            case 2:
                url = "view/DokumenView/ajukanDokumenKeteranganKematian.fxml";
                title = "Ajukan Keterangan Kematian";
                break;
            case 3:
                url = "view/DokumenView/ajukanDokumenKeteranganUsaha.fxml";
                title = "Ajukan Keterangan Usaha";
                break;
            case 4:
                url = "view/DokumenView/ajukanDokumenKeteranganTidakMampu.fxml";
                title = "Ajukan Keterangan Tidak Mampu";
                break;
            case 5:
                url = "view/DokumenView/ajukanDokumenKeteranganDomisili.fxml";
                title = "Ajukan Keterangan Domisili";
                break;
            default:
                throw new IllegalArgumentException("Invalid index: " + index);
        }

        try {
            Stage stage = (Stage) rootAnchorPaneDokumen.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            System.out.println(loader);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        System.out.println(JenisDokumen.getJenisDokumen("Keterangan Kelahiran"));
//    }
}

