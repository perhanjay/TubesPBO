package com.myapp.demotubes.Services;

import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Warga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserPageService {
    static String urlDB = "jdbc:sqlite:src/main/resources/com/myapp/demotubes/db/kependudukan.db";

    public static Warga getWargaById(int idWarga){
        try{

            Connection conn = DriverManager.getConnection(urlDB);
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
}
