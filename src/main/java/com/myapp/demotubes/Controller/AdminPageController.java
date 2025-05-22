package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminPageController {
        Akun akun = SessionAkun.getCurrentAkun();

        @FXML
        private Label adminLabel;

        @FXML
        private Label namaLabel;

}
