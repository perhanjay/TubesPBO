package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Dokumen;
import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Properties.Status;
import com.myapp.demotubes.Entities.Sessions.SessionWarga;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PengajuanService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDB);
    };

    //Insert pengajuan ke db
    public static int insertPengajuanToDB(int idAkun, int idDokumen, String deskripsi, String lokasiBerkas) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO pengajuan_dokumen (id_akun, id_dokumen, deskripsi_pengajuan, tanggal_pengajuan, lokasi_berkas) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkun);
            pstmt.setInt(2, idDokumen);
            pstmt.setString(3, deskripsi);
            pstmt.setString(4, LocalDate.now().toString());
            pstmt.setString(5, lokasiBerkas);
            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        //Lokasi berkas kita pakai delimiter aja, nanti plannya pas di admin bakalan di tampilin pas admin pencet selengkapnya
        //tombol selengkapnya bakal dipasang di, dimana ya, pengajuan aja kali ya,
        //abis pencet selengkapnya bakal muncul window baru yang nampilin detail pengajuan. Bakal ada something untuk nandain kalo admin ngadain perubahan ga dari status
//        }
    }

    public static int getPengajuanIdByAkunDokumen(int idAkun, int idDokumen) {
       try(Connection conn = getConnection()) {
            String sql = "SELECT * FROM pengajuan_dokumen WHERE id_akun = ? AND id_dokumen = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkun);
            pstmt.setInt(2, idDokumen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pengajuan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0400;
    }

    public static ObservableList<PengajuanDokumen> getPengajuanByIdAkunForUserView(int idAkun) {
        ObservableList<PengajuanDokumen> pengajuanDokumenList = FXCollections.observableArrayList();

        try (Connection conn = getConnection()) {
            String sql = "SELECT pd.*, d.nama_dokumen, d.deskripsi " +
                    "FROM pengajuan_dokumen pd " +
                    "JOIN dokumen d ON pd.id_dokumen = d.id_dokumen " +
                    "WHERE pd.id_akun = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAkun);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idPengajuan = rs.getInt("id_pengajuan");
                int idDokumen = rs.getInt("id_dokumen");
                String tanggalPengajuan = rs.getString("tanggal_pengajuan");
                Status status = Status.valueOf(rs.getString("status"));
                String catatanAdmin = rs.getString("catatan_admin");

                Dokumen dokumen = new Dokumen(idDokumen, rs.getString("nama_dokumen"), rs.getString("deskripsi"));
                //ubah metodenya jadi
                PengajuanDokumen pengajuanDokumen = new PengajuanDokumen(idPengajuan, SessionWarga.getCurrentWarga(), dokumen, tanggalPengajuan, status, catatanAdmin);
                pengajuanDokumenList.add(pengajuanDokumen);
            }
            return pengajuanDokumenList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pengajuanDokumenList;
    }

    public static void deletePengajuan(int idPengajuan) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM pengajuan_dokumen WHERE id_pengajuan = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idPengajuan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
