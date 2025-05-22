package com.myapp.demotubes.Entities.Sessions;

import com.myapp.demotubes.Entities.Akun;

public class SessionAkun {
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
