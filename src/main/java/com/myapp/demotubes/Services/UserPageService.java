package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Warga;

import java.sql.*;

public class UserPageService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDB);
    };
    public static Warga getWargaById(int idWarga){
        try(Connection conn = getConnection()){
            String sql = "Select * from warga where id_warga = ? ;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idWarga);
            ResultSet resultSet = pstmt.executeQuery();

            int id = resultSet.getInt("id_warga");
            String nama = resultSet.getString("nama");
            JenisKelamin jenisKelamin = JenisKelamin.valueOf(resultSet.getString("jenis_kelamin"));
            String tanggalLahir = resultSet.getString("tanggal_lahir");
            String tempatLahir = resultSet.getString("tempat_lahir");
            Agama agama = Agama.valueOf(resultSet.getString("agama"));
            String nik = resultSet.getString("nik");
            String alamatLengkap = resultSet.getString("alamat_lengkap");
            StatusPekerjaan statusPekerjaan = StatusPekerjaan.valueOf(resultSet.getString("status_pekerjaan"));
            StatusKawin statusKawin = StatusKawin.valueOf(resultSet.getString("status_kawin"));
            return new Warga(id, nama, jenisKelamin, tanggalLahir, tempatLahir,alamatLengkap, agama, nik, statusPekerjaan, statusKawin);

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void insertWargaToDB(String nama, String jenisKelamin, String tanggalLahir, String tempatLahir, String alamat, String agama, String nik, String statusPekerjaan, String statusKawin) throws SQLException {
        try(Connection conn = getConnection()){
        String sql = "INSERT INTO warga (nama, jenis_kelamin, tanggal_lahir, tempat_lahir, alamat_lengkap, agama, nik, status_pekerjaan, status_kawin) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, jenisKelamin);
        pstmt.setString(3, tanggalLahir);
        pstmt.setString(4, tempatLahir);
        pstmt.setString(5, alamat);
        pstmt.setString(6, agama);
        pstmt.setString(7, nik);
        pstmt.setString(8, statusPekerjaan);
        pstmt.setString(9, statusKawin);
        pstmt.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
        }
    }

    public static Integer getWargaId(String nik){
        try(Connection conn =getConnection()){
            String sql = "SELECT * FROM warga WHERE nik = ? ;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nik);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id_warga");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertIdWargaToAkun(int idWarga, String username) throws SQLException {
        try(Connection conn =getConnection()){
            String sql = "UPDATE akun SET id_warga = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idWarga);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
