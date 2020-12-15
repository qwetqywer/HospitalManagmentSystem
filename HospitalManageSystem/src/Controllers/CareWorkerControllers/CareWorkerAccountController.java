package Controllers.CareWorkerControllers;

import Configs.ChangeScene;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CareWorkerAccountController {
    @FXML
    private Label fullnameCareWorkerLabel;

    @FXML
    private Button desktopCareWorkerButton;
    @FXML
    private Button getSceduleCareWorkerButton;
    @FXML
    private Button getPatientsCareWorkerButton;
    @FXML
    private Button startAppointmentWithoutOrderButton;
    @FXML
    private Button editCareWorkerProfileButton;

    @FXML
    private Button returnBackButton;

    @FXML
    private Label surnameCareWorkerLabel;
    @FXML
    private Label nameCareWorkerLabel;
    @FXML
    private Label patronymicCareWorkerLabel;
    @FXML
    private Label birthdayDateCareWorkerLabel;
    @FXML
    private Label addressCareWorkerLabel;

    @FXML
    private Button issueAppointment;


    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() {


        updateCareWorkerAccountData();

        desktopCareWorkerButton.setOnAction(event -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopCareWorker");
            ChangeScene.change(FXMLConfigs.careWorkerAccount,getClass());

        });
        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
        });

        getSceduleCareWorkerButton.setOnAction((event -> {
            getSceduleCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("getSchedule");
            ChangeScene.change(FXMLConfigs.careWorkerSchedule,getClass());
        }));

        issueAppointment.setOnAction(actionEvent -> {
            issueAppointment.getScene().getWindow().hide();
            clientHandler.sendMessage("issueAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerIssueAppointment,getClass());
        });
        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            ChangeScene.change(FXMLConfigs.authorization,getClass());
        });


        startAppointmentWithoutOrderButton.setOnAction(actionEvent -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startWithoutOrder");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointmentWithoutOrder,getClass());

        });


        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointment,getClass());
        });



    }

    private void updateCareWorkerAccountData() {
        surnameCareWorkerLabel.setText(Employee.mainEmployee.getSurname());
        nameCareWorkerLabel.setText(Employee.mainEmployee.getName());
        patronymicCareWorkerLabel.setText(Employee.mainEmployee.getPatronymic());
        int age = Period.between(LocalDate.parse(Employee.mainEmployee.getBirthday(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.now() ).getYears();
        birthdayDateCareWorkerLabel.setText(Employee.mainEmployee.getBirthday() +" ("+ age +")");

    }


}
