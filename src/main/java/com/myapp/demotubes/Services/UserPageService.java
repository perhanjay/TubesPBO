package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Warga;

import java.sql.*;

public class UserPageService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    public static Warga getWargaById(int idWarga){
        try(Connection conn = DriverManager.getConnection(urlDB)){
            Statement stmt = conn.createStatement();
            String url = "Select * from warga where id_warga="+idWarga+";";
            ResultSet resultSet = stmt.executeQuery(url);

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
        try(Connection conn = DriverManager.getConnection(urlDB)){
        String sql = "INSERT INTO warga (nama, jenis_kelamin, tanggal_lahir, tempat_lahir, alamat_lengkap, agama, nik, status_pekerjaan, status_kawin) VALUES ('"+nama+"','"+jenisKelamin+"','"+tanggalLahir+"','"+tempatLahir+"','"+alamat+"','"+agama+"','"+nik+"','"+statusPekerjaan+"','"+statusKawin+"');";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }catch (Exception e){
        e.printStackTrace();
        }
    }

    public static Integer getWargaId(String nik){
        try(Connection conn = DriverManager.getConnection(urlDB)){
            String sql = "SELECT * FROM warga WHERE nik='"+nik+"';";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("id_warga");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertIdWargaToAkun(int idWarga, String username) throws SQLException {
        try(Connection conn = DriverManager.getConnection(urlDB)){
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
