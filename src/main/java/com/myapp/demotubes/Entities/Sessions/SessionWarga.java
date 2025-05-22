package com.myapp.demotubes.Entities.Sessions;

import com.myapp.demotubes.Entities.Warga;

public class SessionWarga {
    private static Warga currentWarga;

    public static void setCurrentWarga(Warga warga) {
        currentWarga = warga;
    }

    public static Warga getCurrentWarga() {
        return currentWarga;
    }

    public static void clear() {
        currentWarga = null;
    }
}
