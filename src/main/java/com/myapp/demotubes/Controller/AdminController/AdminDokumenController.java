package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDokumenController {

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button pendudukBtn;

    @FXML
    private Button dokumenBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private void dashboardBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 1, "Admin Dashboard");
    }

    @FXML
    private void pendudukBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 2, "Penduduk Dashboard");
    }

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
    }
}
