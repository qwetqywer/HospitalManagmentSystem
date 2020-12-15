package Controllers.RegControllers;

import Configs.FXMLConfigs;
import Models.Employee;
import Models.Specialty;
import ClientHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DoctorsSceduleController {

    @FXML
    private Button desktopRegButton;
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
    private ComboBox<Specialty> specialtyComboBox;
    @FXML
    private TableView<Employee> doctorsTable;
    @FXML
    private TableColumn<Employee, String> surnameColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> patronymicColumn;
    @FXML
    private TableColumn<Employee, String> officeNumberColumn;
    @FXML
    private TableColumn<Employee, String> operatingTimeColumn;
    @FXML
    private Button confirmButton;

    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize(){
        updateSpecialtiesComboBox();
        desktopRegButton.setOnAction(event -> {
            desktopRegButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopRegButton");
            changeScene(FXMLConfigs.regAccount);
        });
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

        confirmButton.setOnAction(event -> {
            getDoctorSchedule();
        });
    }

    private void getDoctorSchedule() {
        clientHandler.sendMessage("getDoctorSchedule");
        Specialty specialty = specialtyComboBox.getValue();

        boolean isGetDoctorSchedule = (boolean) clientHandler.readObject();
        if(isGetDoctorSchedule){
            Employee.listEmployees.clear();
            ArrayList<Employee> arrayList = (ArrayList<Employee>)clientHandler.readObject();
            ArrayList<Employee> finish = new ArrayList<>();

            for (Employee employee : arrayList) {
                if (employee.getIdSpecialty() == specialty.getId()) finish.add(employee);
            }
            Employee.listEmployees.addAll(finish);
            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
            officeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
            operatingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("workTime"));
            doctorsTable.refresh();
            doctorsTable.setItems(Employee.listEmployees);
        }
        else{
            callAlert("Ошибка загрузки данных. Попробуйте снова.");
        }
    }
    private void updateSpecialtiesComboBox() {
        clientHandler.sendMessage("updateSpecialtiesComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
            ArrayList<Specialty> specialtyArrayList = (ArrayList<Specialty>)clientHandler.readObject();
            Specialty.update(specialtyArrayList);
        }
        specialtyComboBox.setItems(Specialty.listSpecialties);
    }
    private void callAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
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
