package com.myapp.demotubes.Controller.AdminController.AdminDocumentTypesController;

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

public class AdminDocumentTypesTemplateController {
    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label labelNama;

    @FXML
    private Label labelNik;

    @FXML
    private Label labelJenisKelamin;

    @FXML
    private Label labelTglLahir;

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

    @FXML
    private Button unggahBerkasKTPbtn;

    @FXML
    private Button unggahBerkasKKbtn;

    @FXML
    private Button unggahBerkasBuktiTglbtn;

    @FXML
    private Button unggahBerkasFormBtn;

    private String berkasKTPPath = null;
    private String berkasKTP2Path = null;
    private String berkasKKPath = null;
    private String berkasBuktiTinggalPath = null;
    private String berkasFormPath = null;

    //kemungkinan yang ini tidak berguna

    boolean isDone;

    public void setterberkasKTP2Path(String path) {
        this.berkasKTPPath = path;
    }

    public void setterberkasKTPPath(String path) {
        this.berkasKTPPath = path;
    }

    public void setterberkasKKPath(String path) {
        this.berkasKKPath = path;
    }

    public void setterberkasBuktiTinggalPath(String path) {
        this.berkasBuktiTinggalPath = path;
    }

    public void setterberkasFormPath(String path) {
        this.berkasFormPath = path;
    }

    @FXML
    private void setujuiPengajuanBtnOnClick(){
        System.out.println("Setujui Pengajuan Button Cicked");
    }

    @FXML
    private void prosesPengajuanBtnOnClick(){
        System.out.println("Proses Pengajuan Button Cicked");
    }

    @FXML
    private void tolakPengajuanBtnOnClick(){
        System.out.println("Tolak Pengajuan Button Cicked");
    }

    @FXML
    private void hapusPengajuanBtnOnClick(){
        System.out.println("Hapus Pengajuan Button Cicked");
    }


    @FXML
    private void initialize() {
        System.out.println("initialized");
    }
}


