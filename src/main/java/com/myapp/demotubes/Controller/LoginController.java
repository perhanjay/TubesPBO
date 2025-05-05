package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.HelloApplication;
import com.myapp.demotubes.Services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label usernameText;

    @FXML
    private Label usernamePasswordText;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private void buttonLoginOnClick() throws SQLException, IOException {
        LoginService loginService = new LoginService();
        String username = usernameField.getText();
        String password = passwordField.getText();
        Akun akun = loginService.validateLogin(username, password);
        if(akun != null) {
            System.out.println("Login Successful");
            switch (akun.getRole()){
                case ADMIN:
                    loadAdminPage();
                    break;
                case USER:
                    loadUserPage(akun);
                    break;
            }
        }
    }

    @FXML
    private void buttonRegisterOnClick() throws SQLException, IOException {
        LoginService loginService = new LoginService();
        String username = usernameField.getText();
        String password = passwordField.getText();
        Akun akun = loginService.getAkunByUsername(username);
        if(akun == null) {
            loginService.registerAkun(username, password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register Berhasil");
            alert.setContentText("Anda berhasil melakukan register. Silahkan Login.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Gagal");
            alert.setContentText("Register gagal. Ganti username.");
            alert.show();
        }
    }

    private void loadAdminPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/admin-view.fxml"));
        System.out.println(loader);
        Parent root = loader.load();

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();
    }

    private void loadUserPage(Akun akun) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/user-view.fxml"));
        Parent root = loader.load();

        UserPageController userPageController = loader.getController(); //NGOPER AKUN
        userPageController.setAkun(akun);

        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("User");
        stage.show();
    }



}