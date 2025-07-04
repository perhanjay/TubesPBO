package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;
import com.myapp.demotubes.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin extends Akun{
    public Admin(int id, String username, String password, Roles role) {
        super(id, username, password, role);
    }

    public Admin(int id, String username, String password, Roles role, int id_warga) {
        super(id, username, password, role, id_warga);
    }

    @Override
    public void viewLoader(Stage stage, int index, String title) {
        String url;
        switch (index) {
            case 1 -> url = "view/AdminView/adminDashboardView.fxml";
            case 2 -> url = "view/AdminView/adminPendudukView.fxml";
            case 3 -> url = "view/AdminView/adminDokumenView.fxml";
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            System.out.println(loader);
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
