package com.myapp.demotubes.Controller;

import com.myapp.demotubes.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserAjukanDokumenController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private void userIsiBiodataOnClick(){
       try {
           FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/userDashboard.fxml"));
           Parent root = loader.load();
           Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
           stage.setScene(new Scene(root));
           stage.setTitle("Isi Biodata");
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
}

