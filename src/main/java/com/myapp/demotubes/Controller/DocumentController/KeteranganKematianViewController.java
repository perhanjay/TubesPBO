package com.myapp.demotubes.Controller.DocumentController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Services.PengajuanService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class KeteranganKematianViewController {
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextArea deskripsiSingkat;

    @FXML
    private Button kembaliBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Button unggahBerkasKTPMeninggalBtn;

    @FXML
    private Button unggahBerkasKKMeninggalBtn;

    @FXML
    private Button unggahBerkasSKKematianBtn;

    @FXML
    private Button unggahBerkasKTPPelaporBtn;

    private String berkasKTPMeninggalPath = null;
    private String berkasKKMeninggalPath = null;
    private String berkasSKKematianPath = null;
    private String berkasKTPPelaporPath = null;

    private boolean isDone;

    public void setterberkasKTPMeninggalPath(String path) {
        this.berkasKTPMeninggalPath = path;
    }

    public void setterberkasKKMeninggalPath(String path) {
        this.berkasKKMeninggalPath = path;
    }

    public void setterberkasSKKematianPath(String path) {
        this.berkasSKKematianPath = path;
    }

    public void setterberkasKTPPelaporPath(String path) {
        this.berkasKTPPelaporPath = path;
    }

    @FXML
    private void unggahBerkasKTPMeninggalBtnOnClick() {
        try {
            setterberkasKTPMeninggalPath(chooseFileBtnBuilder("KTPMeninggal"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasKKMeninggalbtnOnClick() {
        try {
            setterberkasKKMeninggalPath(chooseFileBtnBuilder("KKMeninggal"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasSKKematianBtnOnClick() {
        try {
            setterberkasSKKematianPath(chooseFileBtnBuilder("SuratKematian"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void unggahBerkasKTPPelaporBtn() {
        try {
            setterberkasKTPPelaporPath(chooseFileBtnBuilder("KTPPelapor"));
        }catch (Exception e){
            e.printStackTrace();
        }
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
        if (isDone) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
        }
        if (alertConfirmation() == ButtonType.OK) {
            SessionAkun.getCurrentAkun().viewLoader((Stage) rootAnchorPane.getScene().getWindow(), 3, "Status Permohonan");
        } else{
            return;
        }
    }

    @FXML
    private void submitOnClick(){

        System.out.println("Submit button clicked");
        //get from pengajuan, idDokumen
        System.out.println("Submit button kematian clicked");
        Integer idAkun = SessionAkun.getCurrentAkun().getId();
        Integer idDokumen = 2;
        String deskripsi = deskripsiSingkat.getText();
        String lokasiBerkas = berkasKTPMeninggalPath + "|" + berkasKKMeninggalPath + "|" + berkasSKKematianPath + "|" + berkasKTPPelaporPath;

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
        alert.setContentText("Pastikan bahwa semua data anda sudah benar.\nApakah anda yakin ingin mengirim permohonan ini?");
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
            successAlert.setContentText("Permohonan Keterangan Kematian anda telah berhasil dikirim. \nSilakan tunggu proses verifikasi dari petugas secara berkala.");
            successAlert.showAndWait();
            isDone = true;
        } else{
            System.out.println("Gagal mengirim permohonan" );
        }
    }

    private void checkEmptyFields(){
        if (berkasKTPMeninggalPath == null || berkasKKMeninggalPath == null || berkasSKKematianPath == null || berkasKTPPelaporPath == null || deskripsiSingkat.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mohon unggah semua berkas yang diperlukan sebelum mengirim permohonan.");
            alert.showAndWait();
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
                new File("src/main/resources/com/myapp/demotubes/BerkasUnggahanPengguna/"+namaPengguna+"/KeteranganKematian").mkdirs();
                String pathToFile = "src/main/resources/com/myapp/demotubes/BerkasUnggahanPengguna/" + namaPengguna + "/KeteranganKematian/" + namaBerkas + ".pdf";
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
        System.out.println("KeteranganKematianViewController initialized");
    }
}


