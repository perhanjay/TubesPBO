package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Akun;
import com.myapp.demotubes.Entities.Properties.Agama;
import com.myapp.demotubes.Entities.Properties.JenisKelamin;
import com.myapp.demotubes.Entities.Properties.StatusKawin;
import com.myapp.demotubes.Entities.Properties.StatusPekerjaan;
import com.myapp.demotubes.Entities.Sessions.SessionPengajuanList;
import com.myapp.demotubes.Entities.Sessions.SessionWarga;
import com.myapp.demotubes.Entities.Sessions.SessionWargaList;
import com.myapp.demotubes.Entities.Warga;
import com.myapp.demotubes.Services.AdminDokumenServices;
import com.myapp.demotubes.Services.AdminWargaService;
import com.myapp.demotubes.Services.UserPageService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminPendudukContinueController {

    public Warga warga;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField textFieldNama;

    @FXML
    private TextField textFieldNIK;

    @FXML
    private ComboBox<JenisKelamin> boxJenisKelamin;

    @FXML
    private DatePicker datePickerTglLahir;

    @FXML
    private TextField textFieldTempatLahir;

    @FXML
    private TextField textFieldAlamatLengkap;

    @FXML
    private ComboBox<Agama> boxAgama;

    @FXML
    private ComboBox<StatusPekerjaan> boxStatusPekerjaan;

    @FXML
    private ComboBox<StatusKawin> boxStatusKawin;

    @FXML
    private Button ubahBtn;

    @FXML
    private Button hapusBtn;

    private AdminPendudukController parentController;

    public void setParentController(AdminPendudukController parentController) {
        this.parentController = parentController;
    }

    public void setWarga(Warga warga) {
        this.warga = warga;
        boxInitiator();
        setUi();
    }

    @FXML
    private void ubahBtnOnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Apakah Anda yakin ingin mengubah data warga ini?");
        Optional<ButtonType> response =  alert.showAndWait();
        if (!(response.get() == ButtonType.OK)) {
            return;
        }
        int idWarga = warga.getId();
        String nama = textFieldNama.getText();
        String nik = textFieldNIK.getText();
        String jenisKelamin = JenisKelamin.getJenisKelamin(boxJenisKelamin.getValue().toString()).name();
        String tanggalLahir = datePickerTglLahir.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tempatLahir = textFieldTempatLahir.getText();
        String alamat = textFieldAlamatLengkap.getText();
        String agama = Agama.getAgama(boxAgama.getValue().toString()).name();
        String statusPekerjaan = StatusPekerjaan.getStatusPekerjaan(boxStatusPekerjaan.getValue().toString()).name();
        String statusKawin = StatusKawin.getStatusKawin(boxStatusKawin.getValue().toString()).name();

        try {
            AdminWargaService.alterWarga(idWarga, nama, jenisKelamin, tanggalLahir, tempatLahir, alamat, agama, statusPekerjaan, statusKawin, nik);
        }catch (Exception e){
            e.printStackTrace();
        }
        SessionWargaList.resetCounter();
    }

    @FXML
    private void hapusBtnOnClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Apakah Anda yakin ingin menghapus data warga ini?");
        Optional<ButtonType> response =  alert.showAndWait();
        if (!(response.get() == ButtonType.OK)) {
                return;
        }

        boolean deletePengajuan = false;
        boolean isChangeIdWargaToNull = false;

        try {
            System.out.println("Executing hapusBtnOnClick");
            Integer idAkunIfExists = AdminWargaService.getIdAkunByIdWarga(warga.getId());
            System.out.println("IdAkun check successfully\nIdAkun: " + idAkunIfExists);

            if (idAkunIfExists != null) {
                System.out.println("IdAkun exists, checking for pengajuan");
                Integer pengajuanIfExist = AdminWargaService.checkPengajuanByIdAkun(idAkunIfExists);
                System.out.println("Id Pengajuan check successfully\nIdPengajuan: " + pengajuanIfExist);
                if (pengajuanIfExist != null) {
                    System.out.println("Pengajuan exists, prompting for confirmation to delete");
                    Alert alert11 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert11.setTitle("Konfirmasi");
                    alert11.setContentText("Warga ini memiliki daftar pengajuan yang belum selesai diproses. \nAksi ini juga akan menghapus pengajuan.\nTetap lanjutkan ?");
                    Optional<ButtonType> response110 =  alert11.showAndWait();

                    if (!(response110.get() == ButtonType.OK)) {
                        return;
                    }
                    System.out.println("Pengajuan exists, proceeding to delete");
                    deletePengajuan = true;
                }
                isChangeIdWargaToNull = true;
            }

            if(deletePengajuan) {
                System.out.println("Deleting Warga, changing IdWarga to null, and deleting Pengajuan");
                AdminWargaService.changeIdWargaToNull(idAkunIfExists);
                AdminWargaService.deleteWarga(warga.getId());
                AdminWargaService.deletePengajuanByIdAkun(idAkunIfExists);
                SessionPengajuanList.resetCounter();
                System.out.println("Id pengajuan deleted");
            } else if (isChangeIdWargaToNull){
                System.out.println("Deleting Warga, changing IdWarga to null");
                AdminWargaService.changeIdWargaToNull(idAkunIfExists);
                AdminWargaService.deleteWarga(warga.getId());
            }else {
                System.out.println("Deleting Warga");
                AdminWargaService.deleteWarga(warga.getId());
            }

            SessionWargaList.resetCounter();
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Sukses");
            alert1.setContentText("Warga berhasil dihapus. \nKlik OK untuk melanjutkan.");
            Optional<ButtonType> response2 =  alert1.showAndWait();

            if (response2.get() == ButtonType.OK) {
                Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
                stage.close();
                return;
            }

        } catch (Exception e){
            e.printStackTrace();
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setContentText("Gagal menghapus data warga. ");
            alert2.showAndWait();
        }

    }

    @FXML
    private void setUi(){
        textFieldNama.setText(warga.getNama());
        textFieldNIK.setText(warga.getNik());
        textFieldAlamatLengkap.setText(warga.getAlamat());
        datePickerTglLahir.setValue(LocalDate.parse(warga.getTanggalLahir()));
        textFieldTempatLahir.setText(warga.getTempatLahir());
        textFieldAlamatLengkap.setText(warga.getAlamat());
        boxJenisKelamin.setValue(warga.getJenisKelamin());
        boxAgama.setValue(warga.getAgama());
        boxStatusPekerjaan.setValue(warga.getStatusPekerjaan());
        boxStatusKawin.setValue(warga.getStatusKawin());
    }

    private void boxInitiator(){
        boxJenisKelamin.setItems(FXCollections.observableArrayList(JenisKelamin.values()));
        boxAgama.setItems(FXCollections.observableArrayList(Agama.values()));
        boxStatusKawin.setItems(FXCollections.observableArrayList(StatusKawin.values()));
        boxStatusPekerjaan.setItems(FXCollections.observableArrayList(StatusPekerjaan.values()));
    }

    @FXML
    private void initialize() {

    }

}
