package com.myapp.demotubes.Entities.Properties;

public enum StatusPekerjaan {
    WIRASWASTA("Wirauswasta"),
    KARYAWAN_SWASTA("Karyawan Swasta"),
    ASN("ASN / PNS"),
    PELAJAR("Pelajar / Mahasiswa"),
    TIDAK_BEKERJA("Tidak Bekerja");

    private String statusPekerjaan;

    StatusPekerjaan(String statusPekerjaan) {
        this.statusPekerjaan = statusPekerjaan;
    }

    @Override
    public String toString() {
        return statusPekerjaan;
    }
}
