package Controllers.CareWorkerControllers;
import Configs.FXMLConfigs;
import Models.Appointment;
import Models.Employee;
import Models.Patient;
import ServerHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CareWorkerStartAppointment {

    @FXML
    private Button desktopCareWorkerButton;

    @FXML
    private Button getSceduleCareWorkerButton;

    @FXML
    private Button getPatientsCareWorkerButton;

    @FXML
    private Button startAppointmentWithoutOrderButton;

    @FXML
    private Button issueAppointment;

    @FXML
    private Button editCareWorkerProfileButton;

    @FXML
    private Button returnBackButton;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> idAppointmentColumn;

    @FXML
    private TableColumn<Appointment, String> dateAppointmentColumn;

    @FXML
    private TableColumn<Appointment, String> timeAppointmentColumn;

    @FXML
    private TableColumn<Appointment, String> typeAppointmentColumn;

    @FXML
    private TableColumn<Appointment, String> statusAppointmentColumn;

    @FXML
    private Button startButton;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Integer> idAppointmentColumn1;

    @FXML
    private TableColumn<Patient, String> surnamePatient;

    @FXML
    private TableColumn<Patient, String> namePatient;

    @FXML
    private TableColumn<Patient, String> paronymicPatient;

    @FXML
    private TableColumn<Patient, String> phonePatient;

    @FXML
    private TextArea epicrisisField;

    @FXML
    private Button endButton;

    @FXML
    private Button diseaseHistoryButton;

    @FXML
    private TextArea diseaseHistory;

    private ClientHandler clientHandler = ClientHandler.getClient();


    @FXML
    void initialize() throws IOException {

        updateAppointmentTable();
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
        startButton.setOnAction(actionEvent -> {
            if(appointmentTable.getSelectionModel().getSelectedItem() == null)
            {
                callAlert("Выберите прием");
            }else {
                clientHandler.sendMessage("start");
                Appointment appointment = new Appointment(appointmentTable.getSelectionModel().getSelectedItem());
                clientHandler.sendObject(appointment);
                try {
                    startAppointment();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startAppointment() throws IOException {  clientHandler.sendMessage("updatePatientTable");
        System.out.println("zashli");

        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();

        ArrayList<Patient> patientArrayList = new ArrayList<>();
        Patient.listPatients.clear();
        if(isUpdateSuccessfully) {
            boolean isOne = (boolean) clientHandler.readObject();{
                if(isOne){
                    int size = clientHandler.read();

                    for(int i=0; i<size ; i++){
                        Patient item = new Patient((Patient) clientHandler.readObject());
                        patientArrayList.add(item);

                    }

                    Patient.update(patientArrayList);
                }
                else{
                    callAlert("Найдено несколько пациентов");
                }
            }
        }
        else {
            callAlert("Не удалось загрузить пациента");
        }

        idAppointmentColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnamePatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
        namePatient.setCellValueFactory(new PropertyValueFactory<>("name"));
        paronymicPatient.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        phonePatient.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        patientTable.setItems(Patient.listPatients);
        patientTable.refresh();

    }

    private void updateAppointmentTable() throws IOException {
        clientHandler.sendMessage("updateAppointmentTable");
        System.out.println("zashli");

        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();

        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        Appointment.listAppointments.clear();
        if(isUpdateSuccessfully) {


            int size = clientHandler.read();

            for(int i=0; i<size ; i++){
                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                appointmentArrayList.add(item);
                System.out.println("read");

            }

            Appointment.update(appointmentArrayList);
        }
        else {
            callAlert("Не удалось загрузить расписание");
        }

        idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
        statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        appointmentTable.setItems(Appointment.listAppointments);
        appointmentTable.refresh();

    }

    private void callAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
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