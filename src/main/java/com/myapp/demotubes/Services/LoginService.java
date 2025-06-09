package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Admin;
import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Pengguna;
import com.myapp.demotubes.Entities.Properties.Roles;
import javafx.scene.control.Alert;

import java.sql.*;

public class LoginService {
    String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDB);
    };

    public Integer getIdWargaByNIK(String nik) {
        //transaksi ke db
        //Return id warga yang didapat
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM warga WHERE nik = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nik);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_warga");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Akun getAkunByUsername(String username) throws SQLException {
        try (Connection conn = getConnection()) {
            System.out.println("Executed getAkunByUsername");
            String sql = "SELECT * FROM akun WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            System.out.println(username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("role").equals(Roles.USER.toString())) {
                    return new Pengguna(resultSet.getInt("id_akun"), resultSet.getString("username"), resultSet.getString("password"), Roles.USER, resultSet.getInt("id_warga"));
                } else if (resultSet.getString("role").equals(Roles.ADMIN.toString())) {
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
        try (Connection conn = getConnection()) {
            String sql;
            if (id_warga == null) {
                sql = "INSERT INTO akun (username, password, role) VALUES (?,?,'USER');";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
            } else {
                sql = "INSERT INTO akun (username, password, role, id_warga) VALUES (?,?,'USER',?);";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setInt(3, id_warga);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
