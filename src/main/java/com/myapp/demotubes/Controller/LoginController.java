package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.HelloApplication;
import com.myapp.demotubes.Services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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
    private void buttonLoginOnClick(ActionEvent event) throws SQLException, IOException {
        LoginService loginService = new LoginService();
        String username = usernameField.getText();
        String password = passwordField.getText();
        Akun akun = loginService.validateLogin(username, password);
        if(akun != null) {
            System.out.println("Login Successful");
            switch (akun.getRole()){
                case ADMIN:
                    loadAdminPage(event);
                    break;
            }
        } else {
            System.out.println("Login Failed");
        }
    }

    private void loadAdminPage(ActionEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show()show;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/admin-view.fxml"));
        Parent root = loader.load();

        // Ambil current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();
    }

}