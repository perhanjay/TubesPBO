package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Controller.AdminController.AdminDocumentTypesController.*;
import com.myapp.demotubes.Entities.Dokumen;
import com.myapp.demotubes.Entities.PengajuanDokumen;
import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Entities.Sessions.SessionPengajuanList;
import com.myapp.demotubes.Entities.Warga;
import com.myapp.demotubes.Services.AdminDokumenServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminDokumenController {

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button pendudukBtn;

    @FXML
    private Button dokumenBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableView<PengajuanDokumen> tableViewDokumen;

    @FXML
    private TableColumn<PengajuanDokumen, String> kolomNama;

    @FXML
    private TableColumn<PengajuanDokumen, String> kolomJenisDokumen;

    @FXML
    private TableColumn<PengajuanDokumen, String> kolomTglDiajukan;

    @FXML
    private TableColumn<PengajuanDokumen, String> kolomStatus;



    @FXML
    private void dashboardBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 1, "Admin Dashboard");
    }

    @FXML
    private void pendudukBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dashboardBtn.getScene().getWindow(), 2, "Penduduk Dashboard");
    }

    @FXML
    private void selengkapnyaBtnOnClick(){
        PengajuanDokumen pengajuanDokumen = tableViewDokumen.getSelectionModel().getSelectedItem();
        if (pengajuanDokumen == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mohon pilih pengajuan terlebih dahulu.");
            alert.showAndWait();
        }

        String url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcess.fxml";
//        switch (pengajuanDokumen.getDokumen().getId()) {
//            case 1 ->  url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcess.fxml";
//            case 2 ->  url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessKematian.fxml";
//            case 3 ->  url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessSKU.fxml";
//            case 4 ->  url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessSKTM.fxml";
//            case 5 -> url = "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessDomisili.fxml";
//            default -> System.out.println("invalid id dokumen on pengajuan ");
//        }



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent root = loader.load();

            AdminDocumentProcessController controller = loader.getController();
            controller.setPengajuanDokumen(pengajuanDokumen);

//            AdminProcessDocumentInterface controller = loader.getController();
//            AdminProcessDocumentInterface transformedController = (AdminProcessDocumentInterface) castController(url, controller);
//            transformedController.setPengajuanDokumen(pengajuanDokumen);

            Stage stage = new Stage();
            stage.setTitle("Detail Permohonan");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> this.initialize());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
    }

    @FXML
    private void initialize(){
        System.out.println("Admin Dokumen View Initialized");

        if(SessionPengajuanList.getCounter() == 0){
            System.out.println("Retrieving data from persistent storage");
            SessionPengajuanList.clear();
            SessionPengajuanList.setPengajuanList(AdminDokumenServices.getPengajuanDokumenListRingkas());

            init(SessionPengajuanList.getPengajuanList());

            SessionPengajuanList.addCounter();
        } else {
            System.out.println("Caching from memory");
            init(SessionPengajuanList.getPengajuanList());
        }
    }

    private void init(ObservableList<PengajuanDokumen> data){
        kolomNama.setCellValueFactory(cellData -> {
            Warga warga = cellData.getValue().getPemohon();
            return new SimpleStringProperty(warga.getNama());
        });

        kolomJenisDokumen.setCellValueFactory(cellData ->{
            Dokumen dokumen = cellData.getValue().getDokumen();
            return new SimpleStringProperty(dokumen.getNamaDokumen());
        });

        kolomTglDiajukan.setCellValueFactory(new PropertyValueFactory<>("tanggalPengajuan"));
        kolomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewDokumen.setItems(data);
    }

    private Object castController(String url, AdminProcessDocumentInterface controller) {
        switch (url) {
//            case "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessSKU.fxml":
//                return (AdminDocumentProcessSKUController) controller;
//            case "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessSKTM.fxml":
//                return (AdminDocumentProcessSKTMController) controller;
//            case "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessDomisili.fxml":
//                return (AdminDocumentProcessDomisiliController) controller;
            case "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcess.fxml":
                return (AdminDocumentProcessController) controller;
//            case "/com/myapp/demotubes/view/AdminView/AdminDokumenTypes/AdminDocumentProcessKematian.fxml":
//                return (AdminDocumentProcessKematianController) controller;
            default:
                throw new IllegalArgumentException("Unknown FXML URL: " + url);
        }
    }
}
