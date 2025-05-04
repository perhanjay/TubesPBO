package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Roles;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class LoginService {
     String urlDB = "jdbc:sqlite:/home/perhanjay/Documents/Programming/DBMS/playgroundPBO/kependudukan.db";

    public Akun getAkunByUsername(String username) throws SQLException {
        try {
            System.out.println("Executed getAkunByUsername");
            Connection conn = DriverManager.getConnection(urlDB);
            Statement stmt = conn.createStatement();
            System.out.println(username);
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM akun WHERE username = '" + username + "';");
            if (resultSet.next()) {
                return new Akun(resultSet.getInt("id_akun"), resultSet.getString("username"), resultSet.getString("password"), Roles.valueOf(resultSet.getString("role")));
            } else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("Username tidak ditemukan" + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username tidak ditemukan!");
            alert.show();
        }
        return null;
    }

    public Akun validateLogin(String username, String password) throws SQLException {
        Akun akun = getAkunByUsername(username);

        if (akun == null) {
            return null;
        }
        if (!(akun.getPassword().equals(password))) {
            return null;
        }
        return akun;
    }
}
