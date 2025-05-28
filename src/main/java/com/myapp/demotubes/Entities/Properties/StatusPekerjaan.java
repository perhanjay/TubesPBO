package com.myapp.demotubes.Entities.Properties;

public enum StatusPekerjaan {
    WIRASWASTA("Wiraswasta"),
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

    public static StatusPekerjaan getStatusPekerjaan(String statusPekerjaan) {
        for (StatusPekerjaan sp : StatusPekerjaan.values()) {
            if (sp.statusPekerjaan.equalsIgnoreCase(statusPekerjaan)) {
                return sp;
            }
        }
        throw new IllegalArgumentException("Status Pekerjaan not found: " + statusPekerjaan);
    }
}
