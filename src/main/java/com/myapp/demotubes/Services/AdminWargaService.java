package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Warga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AdminWargaService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDB);
    };

    public static ObservableList<Warga> getAllWarga() {
        // Implementasi untuk mengambil semua data warga dari database
        // Gunakan getConnection() untuk mendapatkan koneksi ke database
        // Lakukan query dan proses hasilnya sesuai kebutuhan
        ObservableList<Warga> wargaList = FXCollections.observableArrayList();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM warga;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                // Ambil data dari resultSet dan lakukan sesuatu, misalnya mencetaknya
                int idWarga = resultSet.getInt("id_warga");
                String nama = resultSet.getString("nama");
                String jenisKelamin = resultSet.getString("jenis_kelamin");
                String tanggalLahir = resultSet.getString("tanggal_lahir");
                String tempatLahir = resultSet.getString("tempat_lahir");
                String agama = resultSet.getString("agama");
                String nik = resultSet.getString("nik");
                String alamatLengkap = resultSet.getString("alamat_lengkap");
                String statusPekerjaan = resultSet.getString("status_pekerjaan");
                String statusKawin = resultSet.getString("status_kawin");

                Warga warga = new Warga(idWarga, nama, JenisKelamin.valueOf(jenisKelamin), tanggalLahir, tempatLahir, alamatLengkap, Agama.getAgama(agama), nik, StatusPekerjaan.getStatusPekerjaan(statusPekerjaan), StatusKawin.getStatusKawin(statusKawin));
                wargaList.add(warga);
//                System.out.println("ID: " + idWarga + ", Nama: " + nama + ", Jenis Kelamin: " + jenisKelamin +
//                                   ", Tanggal Lahir: " + tanggalLahir + ", Tempat Lahir: " + tempatLahir +
//                                   ", Agama: " + agama + ", NIK: " + nik + ", Alamat: " + alamatLengkap +
//                                   ", Status Pekerjaan: " + statusPekerjaan + ", Status Kawin: " + statusKawin);
            }
            return wargaList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wargaList;
    }

    public static void alterWarga(int idWarga, String nama, String jenisKelamin, String tanggalLahir,
                                  String tempatLahir, String alamat, String agama,
                                  String statusPekerjaan, String statusKawin, String nik) throws SQLException {

        String sql = """
        UPDATE warga
        SET nama = ?, jenis_kelamin = ?, tanggal_lahir = ?, tempat_lahir = ?, 
            alamat_lengkap = ?, agama = ?, status_pekerjaan = ?, status_kawin = ?, nik = ?
        WHERE id_warga = ?;
    """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nama);
            pstmt.setString(2, jenisKelamin);
            pstmt.setString(3, tanggalLahir);
            pstmt.setString(4, tempatLahir);
            pstmt.setString(5, alamat);
            pstmt.setString(6, agama);
            pstmt.setString(7, statusPekerjaan);
            pstmt.setString(8, statusKawin);
            pstmt.setString(9, nik); // gunakan nik di bagian SET
            pstmt.setInt(10, idWarga); // gunakan id_warga di bagian WHERE

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e; // biar bisa ditangani di level pemanggil kalau perlu
        }
    }

    public static void deleteWarga(int idWarga) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM warga WHERE id_warga = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idWarga);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getIdAkunByIdWarga(int idWarga) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT id_akun FROM akun WHERE id_warga = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idWarga);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_akun");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // jika tidak ditemukan
    }

    public static Integer checkPengajuanByIdAkun(int idAkun) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT 1 FROM pengajuan_dokumen WHERE id_akun = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkun);
            ResultSet resultSet = pstmt.executeQuery();
            Integer isExist = null;

            if (resultSet.next()) {
                isExist = resultSet.getInt(1);
            }
            return isExist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeIdWargaToNull(Integer idAkunIfExists) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE akun SET id_warga = NULL WHERE id_akun = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkunIfExists);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePengajuanByIdAkun(int idAkun) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM pengajuan_dokumen WHERE id_akun = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkun);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
