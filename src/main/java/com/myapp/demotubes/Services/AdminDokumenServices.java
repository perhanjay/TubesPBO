package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Dokumen;
import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.Status;
import com.myapp.demotubes.Entities.Warga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AdminDokumenServices {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDB);
    };


    public static ObservableList<PengajuanDokumen> getPengajuanDokumenListRingkas() {
        ObservableList<PengajuanDokumen> list = FXCollections.observableArrayList();
        String sql = """
        SELECT 
            pd.id_pengajuan,
            w.id_warga,
            w.nama,
            w.jenis_kelamin,
            w.nik,
            d.id_dokumen,
            d.nama_dokumen,
            pd.tanggal_pengajuan,
            pd.deskripsi_pengajuan,
            pd.status,
            pd.catatan_admin
        FROM pengajuan_dokumen pd
        JOIN akun a ON pd.id_akun = a.id_akun
        JOIN warga w ON a.id_warga = w.id_warga
        JOIN dokumen d ON pd.id_dokumen = d.id_dokumen
        ORDER BY pd.tanggal_pengajuan DESC
    """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Buat objek Warga
                Warga warga = new Warga();
                warga.setId(rs.getInt("id_warga"));
                warga.setNama(rs.getString("nama"));
                warga.setJenisKelamin(JenisKelamin.getJenisKelamin(rs.getString("jenis_kelamin")));
                warga.setNik(rs.getString("nik"));

                // Buat objek Dokumen
                Dokumen dokumen = new Dokumen();
                dokumen.setId(rs.getInt("id_dokumen"));
                dokumen.setNamaDokumen(rs.getString("nama_dokumen"));

                // Buat objek PengajuanDokumen
                PengajuanDokumen pd = new PengajuanDokumen();
                pd.setId(rs.getInt("id_pengajuan"));
                pd.setPemohon(warga);
                pd.setDokumen(dokumen);
                pd.setTanggalPengajuan(rs.getString("tanggal_pengajuan"));
                pd.setDeskripsiPengajuan(rs.getString("deskripsi_pengajuan"));
                pd.setStatus(Status.valueOf(rs.getString("status")));
                pd.setCatatan(rs.getString("catatan_admin"));

                list.add(pd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void setujuiProsesTolakPengajuanDokumen(Status state, Integer id, String catatan) {
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE pengajuan_dokumen SET status = ?, catatan_admin = ? WHERE id_pengajuan = ?");) {

            pstmt.setString(1, state.toString());
            pstmt.setString(2, catatan);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusPengajuanDokumen(Integer id) {
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pengajuan_dokumen WHERE id_pengajuan = ?");) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static PengajuanDokumen completePengajuanData(PengajuanDokumen pengajuan) {
//        //selain daripada yang ada dibawah
//        String sql = """
//        SELECT
//            w.jenis_kelamin,
//            w.tanggal_lahir,
//            pd.lokasi_berkas
//        FROM pengajuan_dokumen pd
//        JOIN akun a ON pd.id_akun = a.id_akun
//        JOIN warga w ON a.id_warga = w.id_warga
//        WHERE pd.id_pengajuan = ?;
//    """;
//
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                // Buat objek Warga
//                Warga warga = new Warga();
//                warga.setId(rs.getInt("id_warga"));
//                warga.setNama(rs.getString("nama"));
//                warga.setNik(rs.getString("nik"));
//
//                // Buat objek Dokumen
//                Dokumen dokumen = new Dokumen();
//                dokumen.setId(rs.getInt("id_dokumen"));
//                dokumen.setNamaDokumen(rs.getString("nama_dokumen"));
//
//                // Buat objek PengajuanDokumen
//                PengajuanDokumen pd = new PengajuanDokumen();
//                pd.setId(rs.getInt("id_pengajuan"));
//                pd.setPemohon(warga);
//                pd.setDokumen(dokumen);
//                pd.setTanggalPengajuan(rs.getString("tanggal_pengajuan"));
//                pd.setDeskripsiPengajuan(rs.getString("deskripsi_pengajuan"));
//                pd.setStatus(Status.valueOf(rs.getString("status")));
//                pd.setCatatan(rs.getString("catatan_admin"));
//
//                list.add(pd);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }

}
