package Controllers.AdminControllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import Configs.FXMLConfigs;
import Models.Employee;
import ClientHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdministratorAccountController {

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
    private Label surnameAdministratorLabel;

    @FXML
    private Label nameAdministratorLabel;

    @FXML
    private Label patronymicAdministratorLabel;

    @FXML
    private Label birthdayAdministratorLabel;

    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() {

        updateAdminAccountData();

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

    private void updateAdminAccountData(){
        surnameAdministratorLabel.setText(Employee.mainEmployee.getSurname());
        nameAdministratorLabel.setText(Employee.mainEmployee.getName());
        patronymicAdministratorLabel.setText(Employee.mainEmployee.getPatronymic());
        int age = Period.between(LocalDate.parse(Employee.mainEmployee.getBirthday(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.now() ).getYears();
        birthdayAdministratorLabel.setText(Employee.mainEmployee.getBirthday() +" ("+ age +")");
    }


}

