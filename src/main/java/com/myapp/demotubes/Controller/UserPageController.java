package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPageController {
    private Akun akun = SessionAkun.getCurrentAkun();

    @FXML
    private AnchorPane UserPagePane;

    @FXML
    private Label namaLabel;

    public void loaderInsertData() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/userDashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) UserPagePane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("User");
        stage.centerOnScreen();
        stage.show();
    }
}
