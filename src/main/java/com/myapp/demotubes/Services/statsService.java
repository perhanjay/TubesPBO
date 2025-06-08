package com.myapp.demotubes.Services;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class statsService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    public static int getTotalWarga(){
        String sql = "SELECT COUNT(*) FROM warga";
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()){
                return rs.getInt(1);
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalPengajuan() {
        String sql = "SELECT COUNT(*) AS total FROM pengajuan_dokumen";
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalPengajuanMenungguProses() {
        String sql = "SELECT COUNT(*) AS total FROM pengajuan_dokumen WHERE status = 'Diajukan'";
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalPengajuanHariIni() {
        String sql = "SELECT COUNT(*) AS total FROM pengajuan_dokumen WHERE DATE(tanggal_pengajuan) = DATE('now')";
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalPengajuanBulanIni() {
        String sql = """
        SELECT COUNT(*) AS total 
        FROM pengajuan_dokumen 
        WHERE strftime('%Y-%m', tanggal_pengajuan) = strftime('%Y-%m', 'now')
    """;
        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Map<String, Integer> getJumlahWargaByJenisKelamin() {
        Map<String, Integer> dataMap = new HashMap<>();
        String sql = "SELECT jenis_kelamin, COUNT(*) as jumlah FROM warga GROUP BY jenis_kelamin";

        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String jenisKelamin = rs.getString("jenis_kelamin");
                int jumlah = rs.getInt("jumlah");
                dataMap.put(jenisKelamin, jumlah);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    public static Map<String, Integer> getJumlahWargaByPekerjaan() {
        Map<String, Integer> hasil = new HashMap<>();
        String sql = "SELECT status_pekerjaan, COUNT(*) as jumlah " +
                "FROM warga " +
                "WHERE aktif = 1 " +
                "GROUP BY status_pekerjaan";

        try (Connection conn = DriverManager.getConnection(urlDB);){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String pekerjaan = rs.getString("status_pekerjaan");
                int jumlah = rs.getInt("jumlah");
                hasil.put(pekerjaan, jumlah);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasil;
    }

    public static Map<String, Integer> getJumlahWargaByKelompokUmur() {
        String sql = """
        SELECT
            CASE
                WHEN umur < 13 THEN 'Anak'
                WHEN umur BETWEEN 13 AND 17 THEN 'Remaja'
                WHEN umur BETWEEN 18 AND 59 THEN 'Dewasa'
                ELSE 'Lansia'
            END AS kelompok,
            COUNT(*) AS jumlah
        FROM (
            SELECT CAST((julianday('now') - julianday(tanggal_lahir)) / 365.25 AS INTEGER) AS umur
            FROM warga
            WHERE aktif = 1
        )
        GROUP BY kelompok
        """;

        Map<String, Integer> hasil = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(urlDB);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String kelompok = rs.getString("kelompok");
                int jumlah = rs.getInt("jumlah");
                hasil.put(kelompok, jumlah);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasil;
    }

    public static int[] getPengajuanHarianBulanIni() throws SQLException {
        int maxHari = LocalDate.now().lengthOfMonth();
        int[] data = new int[maxHari + 1]; // index 1..maxHari

        String query = """
           SELECT strftime('%d', tanggal_pengajuan) AS hari, COUNT(*) AS jumlah
                            FROM pengajuan_dokumen
                            WHERE strftime('%m', tanggal_pengajuan) = strftime('%m', 'now')
                              AND strftime('%Y', tanggal_pengajuan) = strftime('%Y', 'now')
                            GROUP BY hari
        """;

        try (Connection conn = DriverManager.getConnection(urlDB);){
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int hari = rs.getInt("hari");
                int jumlah = rs.getInt("jumlah");
                data[hari] = jumlah;
            }
        }

        return data;
    }


    public static void main(String[] args) {
    }
}
