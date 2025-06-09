package com.myapp.demotubes.Entities;

public class Dokumen {
    private int id;
    private String namaDokumen;
    private String deskripsi;

    public Dokumen(int id, String namaDokumen, String deskripsi) {
        this.id = id;
        this.namaDokumen = namaDokumen;
        this.deskripsi = deskripsi;
    }

    public Dokumen() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaDokumen() {
        return namaDokumen;
    }

    public void setNamaDokumen(String namaDokumen) {
        this.namaDokumen = namaDokumen;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }


    private boolean aktif;
}
