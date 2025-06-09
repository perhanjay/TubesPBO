package com.myapp.demotubes.Controller.AdminController.AdminDocumentTypesController;

import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.Status;
import com.myapp.demotubes.Entities.Sessions.SessionPengajuanList;
import com.myapp.demotubes.Services.AdminDokumenServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class AdminDocumentProcessController implements AdminProcessDocumentInterface {
    private PengajuanDokumen pengajuanDokumen;

    public void setPengajuanDokumen(PengajuanDokumen pengajuan) {
        this.pengajuanDokumen = pengajuan;
        initUi();
    }

    public PengajuanDokumen getPengajuanDokumen() {
        return this.pengajuanDokumen;
    }

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label dokName;

    @FXML
    private Label labelNama;

    @FXML
    private Label labelNik;

    @FXML
    private Label labelJenisKelamin;

    @FXML
    private Label labelTglDiajukan;

    @FXML
    private Label statusPengajuan;

    @FXML
    private TextArea catatanAdmin;

    @FXML
    private TextArea deskripsiSingkat;

    @FXML
    private Button setujuiPengajuanBtn;

    @FXML
    private Button prosesPengajuanBtn;

    @FXML
    private Button tolakPengajuanBtn;

    @FXML
    private Button hapusPengajuanBtn;

    //kemungkinan yang ini tidak berguna

    boolean isDone;

    @FXML
    private void setujuiPengajuanBtnOnClick(){
        System.out.println("Setujui Pengajuan Button Cicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Persetujuan");
        alert.setContentText("Apakah Anda yakin ingin menyetujui permohonan ini?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if(pengajuanDokumen.getStatus() == Status.DISETUJUI){
                    return;
                }
                String catatan = " ";
                if (!catatanAdmin.getText().isEmpty()) {
                    catatan = catatanAdmin.getText();
                }
                AdminDokumenServices.setujuiProsesTolakPengajuanDokumen(Status.DISETUJUI, pengajuanDokumen.getId(), catatan);
                SessionPengajuanList.resetCounter();
                System.out.println("Pengajuan disetujui");
            } else {
                System.out.println("Persetujuan dibatalkan");
            }
        });
    }

    @FXML
    private void prosesPengajuanBtnOnClick(){
        System.out.println("Proses Pengajuan Button Cicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Proses");
        alert.setContentText("Apakah Anda yakin ingin memproses permohonan ini?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if(pengajuanDokumen.getStatus() == Status.DIPROSES){
                    return;
                }
                String catatan = " ";
                if (!catatanAdmin.getText().isEmpty()) {
                    catatan = catatanAdmin.getText();
                }
                AdminDokumenServices.setujuiProsesTolakPengajuanDokumen(Status.DIPROSES,pengajuanDokumen.getId(), catatan);
                SessionPengajuanList.resetCounter();
                System.out.println("Pengajuan diproses");
            } else {
                System.out.println("Proses dibatalkan");
            }
        });
    }

    @FXML
    private void tolakPengajuanBtnOnClick(){
        System.out.println("Tolak Pengajuan Button Cicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Penolakan");
        alert.setContentText("Apakah Anda yakin ingin menolak permohonan ini?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if(pengajuanDokumen.getStatus() == Status.DIPROSES){
                    return;
                }
                String catatan = " ";
                if (!catatanAdmin.getText().isEmpty()) {
                    catatan = catatanAdmin.getText();
                }
                AdminDokumenServices.setujuiProsesTolakPengajuanDokumen(Status.DITOLAK,pengajuanDokumen.getId(), catatan);
                SessionPengajuanList.resetCounter();
                System.out.println("Pengajuan ditolak");
            } else {
                System.out.println("Proses dibatalkan");
            }
        });
    }

    @FXML
    private void hapusPengajuanBtnOnClick(){
        System.out.println("Hapus Pengajuan Button Cicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Penghapusan");
        alert.setContentText("Tindakan ini tidak dapat diubah.\nApakah Anda yakin ingin menghapus permohonan ini?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                AdminDokumenServices.hapusPengajuanDokumen(pengajuanDokumen.getId());
                SessionPengajuanList.resetCounter();
            } else {
                System.out.println("Proses dibatalkan");
            }
        });
    }


    private void initUi() {
        System.out.println("initialized");
        dokName.setText(pengajuanDokumen.getDokumen().getNamaDokumen());
        String nama = pengajuanDokumen.getPemohon().getNama();
        String nik = pengajuanDokumen.getPemohon().getNik();
        JenisKelamin jenisKelamin = pengajuanDokumen.getPemohon().getJenisKelamin();
        String tglDiajukan = pengajuanDokumen.getTanggalPengajuan();
        String deskripsi = pengajuanDokumen.getDeskripsiPengajuan();
        Status status = pengajuanDokumen.getStatus();
        String catatan = pengajuanDokumen.getCatatan();

        labelNama.setText(nama);
        labelNik.setText(nik);
        labelJenisKelamin.setText(jenisKelamin.toString());
        labelTglDiajukan.setText(tglDiajukan);
        statusPengajuan.setText(status.toString());
        deskripsiSingkat.setText(deskripsi);
        deskripsiSingkat.setEditable(false);

        if(catatan == null) {
            return;
        }
        catatanAdmin.setText(catatan);
    }
}


