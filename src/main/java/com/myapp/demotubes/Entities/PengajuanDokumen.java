package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Status;

public class PengajuanDokumen {
    private Integer id;
    private Warga pemohon;
    private Dokumen dokumen;
    private String deskripsiPengajuan;
    private String tanggalPengajuan;
    private Status status;
    private String catatan;

    public PengajuanDokumen() {
    }

    public PengajuanDokumen(Warga pemohon, Dokumen dokumen) {
        this.pemohon = pemohon;
        this.dokumen = dokumen;
    }

    public PengajuanDokumen(int idPengajuan, Warga pemohon, Dokumen dokumen, String tanggalPengajuan, Status status, String catatan) {
        this.id = idPengajuan;
        this.pemohon = pemohon;
        this.dokumen = dokumen;
        this.tanggalPengajuan = tanggalPengajuan;
        this.status = status;
        this.catatan = catatan;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
