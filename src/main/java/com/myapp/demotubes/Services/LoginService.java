package com.myapp.demotubes.Services;

import com.myapp.demotubes.Controller.UserPageController;
import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Roles;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class LoginService {
    String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";


    public Akun getAkunByUsername(String username) throws SQLException {
        try {
            System.out.println("Executed getAkunByUsername");
            Connection conn = DriverManager.getConnection(urlDB);
            Statement stmt = conn.createStatement();
            System.out.println(username);
            String url = "SELECT * FROM akun WHERE username = '" + username + "';";
            ResultSet resultSet = stmt.executeQuery(url);
            if (resultSet.next()) {
                return new Akun(resultSet.getInt("id_akun"), resultSet.getString("username"), resultSet.getString("password"), Roles.valueOf(resultSet.getString("role")));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Akun validateLogin(String username, String password) throws SQLException {
        Akun akun = getAkunByUsername(username);

        if (akun == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username tidak ditemukan!");
            alert.show();
            return null;
        }
        if (!(akun.getPassword().equals(password))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password salah!");
            alert.show();
            return null;
        }
        return akun;
    }

    public void registerAkun(String username, String password) throws SQLException {
        try{
        Connection conn = DriverManager.getConnection(urlDB);
        Statement stmt = conn.createStatement();
        String url = "INSERT INTO akun (username, password, role) VALUES ('" + username + "','" + password + "','USER');";
        stmt.executeUpdate(url);

    } catch(SQLException e) {
        e.printStackTrace();
    }
}


}
