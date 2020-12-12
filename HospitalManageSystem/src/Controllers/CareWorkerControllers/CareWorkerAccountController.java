package Controllers.CareWorkerControllers;

import Configs.FXMLConfigs;
import Models.Employee;
import ServerHandlers.ClientHandler;
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

    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() {


        updateCareWorkerAccountData();
        System.out.println("зашло");
        desktopCareWorkerButton.setOnAction(event -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopCareWorker");
            changeScene(FXMLConfigs.careWorkerAccount);

        });


        getSceduleCareWorkerButton.setOnAction((event -> {
            getSceduleCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("getSchedule");
            changeScene(FXMLConfigs.careWorkerSchedule);
        }));




        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            changeScene(FXMLConfigs.authorization);
        });
        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            changeScene(FXMLConfigs.careWorkerStartAppointment);
        });



    }

    private void updateCareWorkerAccountData() {
        Employee employee = new Employee((Employee) clientHandler.readObject());
        surnameCareWorkerLabel.setText(employee.getSurname());
        nameCareWorkerLabel.setText(employee.getName());
        patronymicCareWorkerLabel.setText(employee.getPatronymic());
        int age = Period.between(LocalDate.parse(employee.getBirthday(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.now() ).getYears();
        birthdayDateCareWorkerLabel.setText(employee.getBirthday() + " (" + age + ")");

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
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
