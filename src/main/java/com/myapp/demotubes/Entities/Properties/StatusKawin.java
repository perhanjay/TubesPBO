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

    public static StatusKawin getStatusKawin(String statusKawin) {
        for (StatusKawin sk : StatusKawin.values()) {
            if (sk.statusKawin.equalsIgnoreCase(statusKawin) ||
                    sk.name().equalsIgnoreCase(statusKawin)) {
                return sk;
            }
        }
        throw new IllegalArgumentException("Status Kawin not found: " + statusKawin);
    }
}
