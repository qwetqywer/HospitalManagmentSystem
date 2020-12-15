package Controllers.RegControllers;

import ClientHandlers.ClientHandler;
import Configs.FXMLConfigs;
import Models.Employee;

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
public class RegAccountController {

    @FXML
    private Button issueAppointmentCardButton;
    @FXML
    private Button giveAppointmentCardButton;
    @FXML
    private Button issueOutpatientCardButton;
    @FXML
    private Button getDoctorScheduleButton;
    @FXML
    private Button editReqProfileButton;
    @FXML
    private Button returnBackButton;

    @FXML
    private Label surnameRegLabel;
    @FXML
    private Label nameRegLabel;
    @FXML
    private Label patronymicRegLabel;
    @FXML
    private Label birthdayDateRegLabel;


    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() {
        updateRegAccountData();
        getDoctorScheduleButton.setOnAction(event -> {
            getDoctorScheduleButton.getScene().getWindow().hide();
            clientHandler.sendMessage("regDoctorScedule");
            changeScene(FXMLConfigs.regDoctorScedule);
        });
        editReqProfileButton.setOnAction((event -> {
            editReqProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("regEditProfile");
            changeScene(FXMLConfigs.regEditProfile);
        }));
        giveAppointmentCardButton.setOnAction(event -> {
            giveAppointmentCardButton.getScene().getWindow().hide();
            clientHandler.sendMessage("regGiveAppointmentCard");
            changeScene(FXMLConfigs.regGiveAppointmentCard);
        });
        issueAppointmentCardButton.setOnAction(event -> {
            issueAppointmentCardButton.getScene().getWindow().hide();
            clientHandler.sendMessage("regIssueAppointmentCard");
            changeScene(FXMLConfigs.regIssueAppointmentCard);
        });
        issueOutpatientCardButton.setOnAction(event -> {
            issueOutpatientCardButton.getScene().getWindow().hide();
            clientHandler.sendMessage("regIssueOutpatientCard");
            changeScene(FXMLConfigs.regIssueOutpatientCard);
        });
        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            changeScene(FXMLConfigs.authorization);
        });
    }

    private void updateRegAccountData()  {
        surnameRegLabel.setText(Employee.mainEmployee.getSurname());
        nameRegLabel.setText(Employee.mainEmployee.getName());
        patronymicRegLabel.setText(Employee.mainEmployee.getPatronymic());
        int age = Period.between(LocalDate.parse(Employee.mainEmployee.getBirthday(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.now() ).getYears();
        birthdayDateRegLabel.setText(Employee.mainEmployee.getBirthday() +" ("+ age +")");

    }

    private void changeScene(String fxmlPath) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        assert root != null;
        primaryStage.setTitle("Медицинская клиника");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
