package com.myapp.demotubes.Entities.Sessions;

import com.myapp.demotubes.Entities.PengajuanDokumen;
import javafx.collections.ObservableList;

public class SessionPengajuanList {

    private static int counter = 0;
    private static ObservableList<PengajuanDokumen> pengajuanList;

    public static int getCounter() {
        return counter;
    }

    public static void addCounter(){
        counter++;
    }

    public static void resetCounter(){
        counter = 0;
    }

    public static void setPengajuanList(ObservableList<PengajuanDokumen> pengajuan) {
        pengajuanList = pengajuan;
    }

    public static ObservableList<PengajuanDokumen> getPengajuanList() {
        return pengajuanList;
    }

    public static void removePengajuan(ObservableList<PengajuanDokumen> pengajuan) {
        if (pengajuanList != null) {
            pengajuanList.remove(pengajuan);
        }
    }

    public static void clear() {
        pengajuanList = null;
    }

}
