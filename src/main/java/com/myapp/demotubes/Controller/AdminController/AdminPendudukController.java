package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPendudukController {

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
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 1, "Penduduk Dashboard");
    }

    @FXML
    private void dokumenBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dokumenBtn.getScene().getWindow(), 3, "Dokumen Dashboard");
    }

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
    }
}
