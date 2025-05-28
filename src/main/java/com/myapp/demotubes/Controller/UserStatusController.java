package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserStatusController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private void ajukanDokumenOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 2, "Permohonan Dokumen");
    }
    @FXML
    private void userIsiBiodataOnClick(){
        SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 1, "User Dashboard");

    }
}

