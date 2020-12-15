package Controllers.AdminControllers;

import Configs.AlertScene;
import Configs.FXMLConfigs;
import Models.Appointment;
import Models.Employee;
import ClientHandlers.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class AdministratorStatisticsController {

    @FXML
    private Button returnBackButton;

    @FXML
    private Button desktopAdministratorButton;

    @FXML
    private Button registryManagementButton;

    @FXML
    private Button personnelManagementButton;

    @FXML
    private Button makeWorkScheduleButton;

    @FXML
    private Button viewStatisticsButton;

    @FXML
    private Button settingsAdministratorButton;



    @FXML
    private BarChart<String, Integer> staticticChart;

    @FXML
    private CategoryAxis MonthAxis;
    @FXML
    private Button updateDiagram;

    @FXML
    private Button updateStatistic;
    @FXML
    private Label valueAppointments;
    @FXML
    private PieChart frequencyCirculationChart;

    private final ClientHandler clientHandler = ClientHandler.getClient();
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    @FXML
    void initialize()  {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));




        updateDiagram.setOnAction(actionEvent -> {
            try {
                showPieChart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        updateStatistic.setOnAction(actionEvent -> {
            try {
                setAppointmentData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        registryManagementButton.setOnAction(event -> {
            registryManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("manageRegistry");
            changeScene(FXMLConfigs.adminManageRegistry);

        });


        desktopAdministratorButton.setOnAction((event -> {
            desktopAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopAdmin");
            changeScene(FXMLConfigs.adminAccount);
        }));


        settingsAdministratorButton.setOnAction(event -> {
            settingsAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("settingsAdmin");
            changeScene(FXMLConfigs.adminEditProfile);
        });



        viewStatisticsButton.setOnAction(event -> {
            viewStatisticsButton.getScene().getWindow().hide();
            clientHandler.sendMessage("viewStatistics");
            changeScene(FXMLConfigs.adminStatistics);
        });


        makeWorkScheduleButton.setOnAction(event -> {
            makeWorkScheduleButton.getScene().getWindow().hide();
            clientHandler.sendMessage("makeSchedule");
            changeScene(FXMLConfigs.adminMakeWorkSchedule);
        });


        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            changeScene(FXMLConfigs.authorization);
        });


        personnelManagementButton.setOnAction(event -> {
            personnelManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });
    }

    private void showPieChart() throws IOException {
        clientHandler.sendMessage("sendPieChart");
        boolean isSetSuccesfully = (boolean) clientHandler.readObject();
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        int size = 0;
        MonthAxis.setCategories(monthNames);

        if(isSetSuccesfully) {

            frequencyCirculationChart.getData().clear();
            size = clientHandler.read();
            Employee.listEmployees.clear();
            for(int i=0; i<size ; i++){
                Employee item = new Employee((Employee) clientHandler.readObject());
                employeeArrayList.add(item);
            }

            for(int i = employeeArrayList.size()-1 ; i > 0 ; i--){
                for(int j = 0 ; j < i ; j++) {
                    if (employeeArrayList.get(j).getAmountOfAppointments() < employeeArrayList.get(j + 1).getAmountOfAppointments()) {
                        Employee temp  = employeeArrayList.get(j);
                        employeeArrayList.set(j, employeeArrayList.get(j + 1));
                        employeeArrayList.set(j + 1, temp);
                    }
                }
            }
            Employee.update(employeeArrayList);

            if(employeeArrayList.size()<4){
            }
            else{
                PieChart.Data slice1 = new PieChart.Data(employeeArrayList.get(0).getSurname(),employeeArrayList.get(0).getAmountOfAppointments());
                PieChart.Data slice2 = new PieChart.Data(employeeArrayList.get(1).getSurname(),employeeArrayList.get(1).getAmountOfAppointments());
                PieChart.Data slice3 = new PieChart.Data(employeeArrayList.get(2).getSurname(),employeeArrayList.get(2).getAmountOfAppointments());
                PieChart.Data slice4 = new PieChart.Data(employeeArrayList.get(3).getSurname(),employeeArrayList.get(3).getAmountOfAppointments());


                frequencyCirculationChart.getData().add(slice1);
                frequencyCirculationChart.getData().add(slice2);
                frequencyCirculationChart.getData().add(slice3);
                frequencyCirculationChart.getData().add(slice4);
                frequencyCirculationChart.setLegendSide(Side.LEFT);



            }
        }else
        {
            AlertScene.callAlert("Не удалось загрузить данные");
        }
    }


    private void changeScene(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage primaryStage = new Stage();
        assert root != null;
        primaryStage.setTitle("Медицинская клиника");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public void setAppointmentData() throws IOException {

        clientHandler.sendMessage("sendStatictic");

        boolean isSetSuccesfully = (boolean) clientHandler.readObject();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        if(isSetSuccesfully) {
            staticticChart.getData().clear();


            int size = clientHandler.read();
            for(int i=0; i<size ; i++){
                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                appointmentArrayList.add(item);
            }

            Appointment.update(appointmentArrayList);
            int[] monthCounter = new int[12];
            for (Appointment p : appointmentArrayList) {
                System.out.println(p.getDate());
                String[] subStr;
                String delimeter = "-";
                subStr = p.getDate().split(delimeter);
                int month = Integer.valueOf(subStr[1]) - 1;
                monthCounter[month]++;
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<>();


            for (int i = 0; i < monthCounter.length; i++) {
                series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
            }

            staticticChart.getData().add(series);
            staticticChart.setLegendVisible(false);
        }else {
            AlertScene.callAlert("Не удалось загрузить данные");
        }
    }
}
