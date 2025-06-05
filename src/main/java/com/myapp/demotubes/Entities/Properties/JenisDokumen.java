package com.myapp.demotubes.Entities.Properties;

public enum JenisDokumen {
    KETERANGAN_KELAHIRAN("Keterangan Kelahiran"),
    KETERANGAN_KEMATIAN("Keterangan Kematian"),
    KETERANGAN_USAHA("Keterangan Usaha"),
    KETERANGAN_TIDAK_MAMPU("Keterangan Tidak Mampu"),
    KETERANGAN_DOMISILI("Keterangan Domisili");

    private String displayName;

    JenisDokumen(String statusKawin) {
        this.displayName = statusKawin;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static JenisDokumen getJenisDokumen(String displayName) {
        for (JenisDokumen jenisDokumen : JenisDokumen.values()) {
            if (jenisDokumen.displayName.equalsIgnoreCase(displayName)) {
                return jenisDokumen;
            }
        }
        throw new IllegalArgumentException("Jenis dokumen not found: " + displayName);
    }
}
