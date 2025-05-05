package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.HelloApplication;
import com.myapp.demotubes.Services.UserPageService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserPageController {
    private Akun akun;

    @FXML
    private AnchorPane UserPagePane;

    public void setAkun(Akun akun) {
       this.akun = akun;
    }

    public void loaderInsertData() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/insert-user-data-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) UserPagePane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("User");
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
        if (akun.getId_warga() == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information");
            alert.setContentText("Anda belum memasukkan data. Ingin memasukkan data ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                loaderInsertData();
            }
            alert.show();
        }

    }
}
