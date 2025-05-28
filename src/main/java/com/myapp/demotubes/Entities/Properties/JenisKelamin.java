package com.myapp.demotubes.Entities.Properties;

public enum JenisKelamin {
    LAKI_LAKI("Laki-laki"), PEREMPUAN("Perempuan");

    private String displayName;

    JenisKelamin(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static JenisKelamin getJenisKelamin(String displayName) {
        for (JenisKelamin jenisKelamin : JenisKelamin.values()) {
            if (jenisKelamin.displayName.equalsIgnoreCase(displayName)) {
                return jenisKelamin;
            }
        }
        throw new IllegalArgumentException("Jenis Kelamin not found: " + displayName);
    }
}
