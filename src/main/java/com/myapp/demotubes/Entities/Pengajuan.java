package com.myapp.demotubes.Entities;

public class Pengajuan {
    private Integer id;
    private Warga pemohon;
    private Dokumen dokumen;
    private String deskripsiPengajuan;
    private String tanggalPengajuan;
    private String catatan;

    public String getStatusPengajuan() {
        return statusPengajuan;
    }

    public void setStatusPengajuan(String statusPengajuan) {
        this.statusPengajuan = statusPengajuan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(String tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getDeskripsiPengajuan() {
        return deskripsiPengajuan;
    }

    public void setDeskripsiPengajuan(String deskripsiPengajuan) {
        this.deskripsiPengajuan = deskripsiPengajuan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String statusPengajuan;

    public Pengajuan(Warga pemohon, Dokumen dokumen) {
        this.pemohon = pemohon;
        this.dokumen = dokumen;
    }

    public Warga getPemohon() {
        return pemohon;
    }

    public void setPemohon(Warga pemohon) {
        this.pemohon = pemohon;
    }

    public Dokumen getDokumen() {
        return dokumen;
    }

    public void setDokumen(Dokumen dokumen) {
        this.dokumen = dokumen;
    }
}
