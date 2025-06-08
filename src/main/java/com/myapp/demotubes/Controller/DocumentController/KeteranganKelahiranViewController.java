package com.myapp.demotubes.Controller.DocumentController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Services.PengajuanService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class KeteranganKelahiranViewController {
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextArea deskripsiSingkat;

//    @FXML
//    private Button kembaliBtn;
//
//    @FXML
//    private Button submitBtn;
//
//    @FXML
//    private Button unggahBerkasKTPbtn;
//
//    @FXML
//    private Button unggahBerkasBNikahBtn;
//
//    @FXML
//    private Button unggahBerkasKKbtn;
//
//    @FXML
//    private Button unggahBerkasFormbtn;

    private String berkasKTPPath = null;
    private String berkasKKPath = null;
    private String berkasBNikahPath = null;
    private String berkasFormPath = null;

    boolean isDone;

    public void setterberkasKTPPath(String path) {
        this.berkasKTPPath = path;
    }

    public void setterberkasKKPath(String path) {
        this.berkasKKPath = path;
    }

    public void setterberkasBNikahPath(String path) {
        this.berkasBNikahPath = path;
    }

    public void setterberkasFormPath(String path) {
        this.berkasFormPath = path;
    }

    @FXML
    private void userIsiBiodataOnClick(){
        if (isDone) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 1, "User Dashboard");
        }
        if (alertConfirmation() == ButtonType.OK) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 1, "User Dashboard");
        } else{
            return;
        }
    }

    @FXML
    private void statusOnClick(){
        if(isDone){
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
        }
        if (alertConfirmation() == ButtonType.OK) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
        } else{
            return;
        }
    }

    @FXML
    private void kembaliBtnOnClick() {
       SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 2, "Permohonan Dokumen");
    }

    //Konfirmasi pengguna ketika memencet tombol aneh aneh
    private ButtonType alertConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setContentText("Progress pengisian dokumen anda belum disimpan. \nApakah anda yakin");
        alert.showAndWait();
        return alert.getResult();
    }

    @FXML
    private void unggahBerkasKTPbtnOnClick() {
        try {
            setterberkasKTPPath(chooseFileBtnBuilder("KTP"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasKKbtnOnClick() {
        try {
            setterberkasKKPath(chooseFileBtnBuilder("KK"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasBNikahBtnOnClick() {
        try {
            setterberkasBNikahPath(chooseFileBtnBuilder("BukuNikah"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasFormBtnOnClick() {
        try {
            setterberkasFormPath(chooseFileBtnBuilder("FormPermohonan"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void submitBtnOnClick() {
        //get from pengajuan, idDokumen
        System.out.println("Submit button kelahiran clicked");
        Integer idAkun = SessionAkun.getCurrentAkun().getId();
        Integer idDokumen = 1;
        String deskripsi = deskripsiSingkat.getText();
        String lokasiBerkas = berkasKTPPath + "|" + berkasKKPath + "|" + berkasBNikahPath + "|" + berkasFormPath;

        checkEmptyFields();
        int isExist = PengajuanService.getPengajuanIdByAkunDokumen(idAkun, idDokumen);

        if (isExist != 0400) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Anda sudah mengajukan permohonan ini sebelumnya.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Pengajuan");
        alert.setContentText("Pastikan bahwa semua data anda sudah benar./nApakah anda yakin ingin mengirim permohonan ini?");
        alert.showAndWait();
        ButtonType confirmation = alert.getResult();

        if (confirmation != ButtonType.OK) {
            System.out.println("Pengajuan dibatalkan");
            return;
        }

        int isSukses = PengajuanService.insertPengajuanToDB(idAkun, idDokumen, deskripsi, lokasiBerkas);

        if (isSukses == 1) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Pengajuan Berhasil");
            successAlert.setContentText("Permohonan Keterangan Kelahiran anda telah berhasil dikirim. \nSilakan tunggu proses verifikasi dari petugas secara berkala.");
            successAlert.showAndWait();
        } else{
            System.out.println("Gagal mengirim permohonan" );
        }
    }

    private void checkEmptyFields(){
        if (berkasKTPPath == null || berkasKKPath == null || berkasBNikahPath == null || berkasFormPath == null || deskripsiSingkat.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mohon unggah semua berkas yang diperlukan sebelum mengirim permohonan.");
            alert.showAndWait();
            return;
        }
    }

    private String chooseFileBtnBuilder(String namaBerkas) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Berkas Pendukung Untuk Diunggah");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Berkas PDF", "*.pdf")
        );

        File selectedFile = fileChooser.showOpenDialog(rootAnchorPane.getScene().getWindow());
        if (selectedFile != null) {
            System.out.println("Berkas yang dipilih: " + selectedFile.getName());
            try {
                String namaPengguna = SessionAkun.getCurrentAkun().getUsername();
                new File("src/main/resources/com/myapp/demotubes/BerkasUnggahanPengguna/"+namaPengguna+"/KeteranganKelahiran").mkdirs();
                String pathToFile = "src/main/resources/com/myapp/demotubes/BerkasUnggahanPengguna/" + namaPengguna + "/KeteranganKelahiran/" + namaBerkas + ".pdf";
                Path target = Paths.get(pathToFile);
                Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                return pathToFile;
            } catch (IOException e) {
                System.err.println("Gagal mengunggah berkas: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Tidak ada berkas yang dipilih.");
        }
        return null;
    }

    @FXML
    private void initialize() {
        usernameLabel.setText(SessionAkun.getCurrentAkun().getUsername().toUpperCase());
        System.out.println("KeteranganKelahiranViewController initialized");
    }

}


