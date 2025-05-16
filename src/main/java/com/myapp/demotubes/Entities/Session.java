package com.myapp.demotubes.Entities;

public class Session {
    private static Akun currentAkun;

    public static void setCurrentAkun(Akun akun) {
        currentAkun = akun;
    }

    public static Akun getCurrentAkun() {
        return currentAkun;
    }

    public static void clear() {
        currentAkun = null;
    }
}
