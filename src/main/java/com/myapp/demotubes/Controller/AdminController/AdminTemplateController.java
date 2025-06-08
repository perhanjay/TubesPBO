package com.myapp.demotubes.Controller.AdminController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminTemplateController {

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button pendudukBtn;

    @FXML
    private Button dokumenBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
    }
}
