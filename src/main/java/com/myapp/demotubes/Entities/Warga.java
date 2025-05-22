package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.*;

import java.time.LocalDate;

public class Warga {
    private int id;
    private String name;
    private JenisKelamin jenisKelamin;
    private String tanggalLahir;
    private String tempatLahir;
    private String alamatLengkap;
    private Agama agama;
    private String nik;
    private StatusPekerjaan statusPekerjaan;
    private StatusKawin statusKawin;

    public Warga(int id, String name, JenisKelamin jenisKelamin, String tanggalLahir,String tempatLahir, String alamatLengkap, Agama agama, String nik, StatusPekerjaan statusPekerjaan, StatusKawin statusKawin) {
        this.id = id;
        this.name = name;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.tempatLahir = tempatLahir;
        this.alamatLengkap = alamatLengkap;
        this.agama = agama;
        this.nik = nik;
        this.statusPekerjaan = statusPekerjaan;
        this.statusKawin = statusKawin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public Agama getAgama() {
        return agama;
    }

    public void setAgama(Agama agama) {
        this.agama = agama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }


    public StatusPekerjaan getStatusPekerjaan() {
        return statusPekerjaan;
    }

    public void setStatusPekerjaan(StatusPekerjaan statusPekerjaan) {
        this.statusPekerjaan = statusPekerjaan;
    }

    public StatusKawin getStatusKawin() {
        return statusKawin;
    }

    public void setStatusKawin(StatusKawin statusKawin) {
        this.statusKawin = statusKawin;
    }

    public String getAlamat() {
        return alamatLengkap;
    }

    public void setAlamat(String alamat) {
        this.alamatLengkap = alamat;
    }

}
