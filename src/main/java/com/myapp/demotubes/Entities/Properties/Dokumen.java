package com.myapp.demotubes.Entities.Properties;

public class Dokumen {
    private int id;
    private String namaDokumen;
    private String deskripsiDokumen;
    private boolean isAktif;

    public Dokumen(int id, String namaDokumen, String deskripsiDokumen, boolean isAktif) {
        this.id = id;
        this.namaDokumen = namaDokumen;
        this.deskripsiDokumen = deskripsiDokumen;
        this.isAktif = isAktif;
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

    public String getDeskripsiDokumen() {
        return deskripsiDokumen;
    }

    public void setDeskripsiDokumen(String deskripsiDokumen) {
        this.deskripsiDokumen = deskripsiDokumen;
    }

    public boolean isAktif() {
        return isAktif;
    }

    public void setAktif(boolean aktif) {
        isAktif = aktif;
    }
}
