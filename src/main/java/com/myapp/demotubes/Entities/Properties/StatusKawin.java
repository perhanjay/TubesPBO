package com.myapp.demotubes.Entities.Properties;

public enum StatusKawin {
    BELUM_MENIKAH("Belum Menikah"),
    MENIKAH("Menikah"),
    CERAI("Cerai / Cerai Mati");

    private String statusKawin;

    StatusKawin(String statusKawin) {
        this.statusKawin = statusKawin;
    }

    @Override
    public String toString() {
        return statusKawin;
    }

}
