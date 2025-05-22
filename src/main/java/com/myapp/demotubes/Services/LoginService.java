package com.myapp.demotubes.Services;

import com.myapp.demotubes.Controller.UserPageController;
import com.myapp.demotubes.Entities.Admin;
import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Roles;
import com.myapp.demotubes.Entities.User;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class LoginService {
    String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    public Integer getIdWargaByNIK(String nik) throws SQLException {
        //transaksi ke db
        //Return id warga yang didapat
        try(Connection con = DriverManager.getConnection(urlDB)){
            Statement stmt = con.createStatement();
            String url = "SELECT * FROM warga WHERE nik = '" + nik + "';";
            ResultSet rs = stmt.executeQuery(url);
            if(rs.next()){
                return rs.getInt("id_warga");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Akun getAkunByUsername(String username) throws SQLException {
        try(Connection conn = DriverManager.getConnection(urlDB)){
            System.out.println("Executed getAkunByUsername");
            Statement stmt = conn.createStatement();
            System.out.println(username);
            String url = "SELECT * FROM akun WHERE username = '" + username + "';";
            System.out.println(url);
            ResultSet resultSet = stmt.executeQuery(url);
            if (resultSet.next()) {
               if (resultSet.getString("role").equals(Roles.USER.toString())) {
                   System.out.println(resultSet.getString("password"));
                   return new User(resultSet.getInt("id_akun"), resultSet.getString("username"), resultSet.getString("password"), Roles.USER, resultSet.getInt("id_warga"));
               } else if (resultSet.getString("role").equals(Roles.ADMIN.toString())) {
                   System.out.println(resultSet.getString("password"));
                   return new Admin(resultSet.getInt("id_akun"), resultSet.getString("username"), resultSet.getString("password"), Roles.ADMIN);
               }
            } else {
                System.out.println("Not found akun on getakunbyusername");
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

    public void registerAkun(String username, String password, Integer id_warga) throws SQLException {
        try(Connection conn = DriverManager.getConnection(urlDB)){
        Statement stmt = conn.createStatement();
        String url = "INSERT INTO akun (username, password, role, id_warga) VALUES ('" + username + "','" + password + "','USER','" + id_warga +"');";
        System.out.println(url);
        stmt.executeUpdate(url);

    } catch(SQLException e) {
        e.printStackTrace();
    }
}


}
