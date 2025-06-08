package com.myapp.demotubes.Controller.AdminController;

import com.myapp.demotubes.Entities.Sessions.SessionAkun;
import com.myapp.demotubes.Entities.Sessions.SessionWarga;
import com.myapp.demotubes.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import com.myapp.demotubes.Services.statsService;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class AdminDashboardController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button pendudukBtn;

    @FXML
    private Button dokumenBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label wargaTerdaftar;

    @FXML
    private Label totalDokumen;

    @FXML
    private Label dokMenungguProses;

    @FXML
    private Label permohonanHariIni;

    @FXML
    private Label permohonanBulanIni;


    //to be instantiated
    @FXML
    private Label wargaASN;

    @FXML
    private Label wargaSwasta;

    @FXML
    private Label wargaWiraswasta;

    @FXML
    private Label wargaPelajar;

    @FXML
    private Label wargaMenganggur;

    @FXML
    private Label wargaAnak;

    @FXML
    private Label wargaRemaja;

    @FXML
    private Label wargaDewasa;

    @FXML
    private Label wargaLansia;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart pieChartJenisKelamin;

    @FXML
    private Label lakik;

    @FXML
    private Label cewek;

    @FXML
    private Label totalSKDomisili;

    @FXML
    private Label totalSKTM;

    @FXML
    private Label totalSKU;

    @FXML
    private Label totalSKKM;

    @FXML
    private Label totalSKKL;



    @FXML
    private void pendudukBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dokumenBtn.getScene().getWindow(), 2, "Penduduk Dashboard");
    }

    @FXML
    private void dokumenBtnOnClick() {
        SessionAkun.getCurrentAkun().viewLoader((Stage) dokumenBtn.getScene().getWindow(), 3, "Dokumen Dashboard");
    }

    @FXML
    private void logoutBtnOnClick(){
        System.out.println("Logout button clicked");
        SessionAkun.clear();
        SessionWarga.setCurrentWarga(null);
        try{
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/loginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Initialize labels with data
        usernameLabel.setText(SessionAkun.getCurrentAkun().getUsername());

        int jumlahWarga = statsService.getTotalWarga();
        wargaTerdaftar.setText(paddingAdder(jumlahWarga));

        int jumlahDokumen = statsService.getTotalPengajuan();
        totalDokumen.setText(paddingAdder(jumlahDokumen));

        int dokumenMenunggu = statsService.getTotalPengajuanMenungguProses();
        dokMenungguProses.setText(paddingAdder(dokumenMenunggu));

        int permohonanHariIniCount = statsService.getTotalPengajuanHariIni();
        permohonanHariIni.setText(paddingAdder(permohonanHariIniCount));

        int permohonanBulanIniCount = statsService.getTotalPengajuanBulanIni();
        permohonanBulanIni.setText(paddingAdder(permohonanBulanIniCount));

        // Initialize PieChart with data
        Map<String, Integer> dataWargaKelamin = statsService.getJumlahWargaByJenisKelamin();
        pieChartJenisKelamin.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : dataWargaKelamin.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        lakik.setText(paddingAdder(dataWargaKelamin.getOrDefault("LAKI_LAKI", 0)));
        cewek.setText(paddingAdder(dataWargaKelamin.getOrDefault("PEREMPUAN", 0)));

        pieChartJenisKelamin.setData(pieChartData);

        Map<String, Integer> dataPekerjaan = statsService.getJumlahWargaByPekerjaan();
        wargaASN.setText(paddingAdder(dataPekerjaan.getOrDefault("ASN", 0)));
        wargaSwasta.setText(paddingAdder(dataPekerjaan.getOrDefault("KARYAWAN_SWASTA", 0)));
        wargaWiraswasta.setText(paddingAdder(dataPekerjaan.getOrDefault("WIRASWASTA", 0)));
        wargaPelajar.setText(paddingAdder(dataPekerjaan.getOrDefault("PELAJAR", 0)));
        wargaMenganggur.setText(paddingAdder(dataPekerjaan.getOrDefault("TIDAK_BEKERJA", 0)));

        Map<String, Integer> dataWargaUmur = statsService.getJumlahWargaByKelompokUmur();
        wargaAnak.setText(paddingAdder(dataWargaUmur.getOrDefault("Anak", 0)));
        wargaRemaja.setText(paddingAdder(dataWargaUmur.getOrDefault("Remaja", 0)));
        wargaDewasa.setText(paddingAdder(dataWargaUmur.getOrDefault("Dewasa", 0)));
        wargaLansia.setText(paddingAdder(dataWargaUmur.getOrDefault("Lansia", 0)));


        Map<Integer, Integer> dataDokumenByPengajuan = statsService.getJumlahPengajuanByJenisDokumen();
        totalSKDomisili.setText(paddingAdder(dataDokumenByPengajuan.getOrDefault(5, 0)));
        totalSKTM.setText(paddingAdder(dataDokumenByPengajuan.getOrDefault(4, 0)));
        totalSKU.setText(paddingAdder(dataDokumenByPengajuan.getOrDefault(3, 0)));
        totalSKKM.setText(paddingAdder(dataDokumenByPengajuan.getOrDefault(2, 0)));
        totalSKKL.setText(paddingAdder(dataDokumenByPengajuan.getOrDefault(1, 0)));

        muatDataStatistik();

    }

    private void muatDataStatistik() {
        int maxHari = LocalDate.now().lengthOfMonth();
        xAxis.getCategories().clear();
        lineChart.getData().clear();
        for (int i = 1; i <= maxHari; i++) {
            xAxis.getCategories().add(String.valueOf(i));
        }

        try {
            int[] data = statsService.getPengajuanHarianBulanIni();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Pengajuan per Hari");

            for (int i = 1; i <= maxHari; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), data[i]));
            }

            xAxis.setLabel("Tanggal dalam bulan ini");
            yAxis.setLabel("Jumlah Permohonan");
            lineChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String paddingAdder(int number) {
        // Add padding to the number if it's less than 10
        return number < 10 ? "0" + number : String.valueOf(number);
    }











    @FXML
    private Button jokesBtn;

    public void jokesBtnOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/demotubes/view/img/iAmGojo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ferhan adalah Gojo");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
