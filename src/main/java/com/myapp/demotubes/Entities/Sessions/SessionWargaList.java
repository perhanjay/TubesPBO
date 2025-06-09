package com.myapp.demotubes.Entities.Sessions;

import com.myapp.demotubes.Entities.Warga;
import javafx.collections.ObservableList;

public class SessionWargaList {
    private static int counter;

    /**
     * Static list of Warga objects to be used across the application.
     * This list can be set and retrieved as needed.
     */



    public static ObservableList<Warga> wargaList;

    public static void setWargaList(ObservableList<Warga> wargaList) {
        SessionWargaList.wargaList = wargaList;
    }

    public static ObservableList<Warga> getWargaList() {
        return wargaList;
    }

    public static void removeWarga(Warga warga) {
        if (wargaList != null) {
            wargaList.remove(warga);
        }
    }

    public static void clear() {
        wargaList = null;
    }

    public static void addCounter() {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void resetCounter() {
        counter = 0;
    }


}
