package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;
import com.myapp.demotubes.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Pengguna extends Akun{
    public Pengguna(int id, String username, String password, Roles role) {
        super(id, username, password, role);
    }

    public Pengguna(int id, String username, String password, Roles role, int id_warga) {
        super(id, username, password, role, id_warga);
    }

    @Override
    public void viewLoader(Stage stage, int index, String title) {
        String url;
        switch (index) {
            case 1 -> url = "view/UserView/userDashboardView.fxml";
            case 2 -> url = "view/UserView/userAjukanDokumenView.fxml";
            case 3 -> url = "view/UserView/userStatusView.fxml";
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
