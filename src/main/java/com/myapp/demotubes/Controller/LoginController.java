package com.myapp.demotubes.Controller;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
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
    private TextField nikField;

    @FXML
    private TextField usernameRegisterField;

    @FXML
    private PasswordField passwordRegisterField;

    @FXML
    private PasswordField passwordAgainField;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerLabel;

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
            SessionAkun.setCurrentAkun(akun);
            SessionAkun.getCurrentAkun().viewLoader((Stage) loginPane.getScene().getWindow(), 1, "Dashboard");
        }
    }

    @FXML
    private void buttonRegisterOnClick() throws SQLException, IOException {
        LoginService loginService = new LoginService();
        String username = usernameRegisterField.getText();
        String password = passwordRegisterField.getText();
        Akun akun = null;
        Integer idWarga = null;
        try {
            idWarga = loginService.getIdWargaByNIK(nikField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            akun = loginService.getAkunByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!(password.equals(passwordAgainField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Gagal");
            alert.setContentText("Password tidak sama.");
            alert.showAndWait();
        }else if (passwordRegisterField.getText() == "" || passwordAgainField.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Gagal");
            alert.setContentText("Password tidak boleh kosong.");
            alert.showAndWait();
        } else if (akun == null) {
            try{
                loginService.registerAkun(username, password, idWarga);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register Berhasil");
            alert.setContentText("Anda berhasil melakukan register. Silahkan Login.");
            alert.show();
        }  else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Gagal");
            alert.setContentText("Register gagal. Ganti username.");
            alert.showAndWait();
        }
    }

    private void pageLoader(String path, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(path));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerPageLoader(){
        pageLoader("view/registerView.fxml", "Register");
    }

    @FXML
    private void loginPageLoader(){
       pageLoader("view/loginView.fxml", "Login");
    }

    private void loadAdminPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/adminDashboardView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }

    private void loadUserPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/userDashboardView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("User");
        stage.centerOnScreen();
        stage.show();
    }
}