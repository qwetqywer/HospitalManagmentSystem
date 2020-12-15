package Controllers.CareWorkerControllers;

import Configs.AlertScene;
import Configs.ChangeScene;
import Configs.FXMLConfigs;
import Models.Appointment;
import Models.Employee;
import Models.Patient;
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

public class CareWorkerStartAppointmentWithoutOrder {

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
    private Button endButton;

    @FXML
    private Button diseaseHistoryButton;

    @FXML
    private ListView<String> diseaseHistory;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField patronymicField;

    @FXML
    private TextField nameField;

    @FXML
    private Button searchButton;

    @FXML
    private TextArea epicrisisField;

    private ClientHandler clientHandler = ClientHandler.getClient();


    @FXML
    void initialize() {


        desktopCareWorkerButton.setOnAction(event -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopCareWorker");
            ChangeScene.change(FXMLConfigs.careWorkerAccount,getClass());

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
        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
        });

        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointment,getClass());
        });

        searchButton.setOnAction(actionEvent -> {
            clientHandler.sendMessage("search");
            try {
                search();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        endButton.setOnAction(actionEvent -> {
            if(patientTable.getSelectionModel().getSelectedItem() == null)
            {
                AlertScene.callAlert("Выберите пациента");
            }else {
                clientHandler.sendMessage("end");
                endNewAppointment();
            }
        });

        diseaseHistoryButton.setOnAction(actionEvent -> {
            if(patientTable.getSelectionModel().getSelectedItem() == null)
            {
                AlertScene.callAlert("Выберите пациента");
            }else {
                clientHandler.sendMessage("diseaseHistory");
                try {
                    showDiseaseHistory();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void endNewAppointment() {
        Appointment appointment = new Appointment();
        String epicrisis = epicrisisField.getText().trim();
        int status = 2;
        appointment.setIntStatus(status);
        appointment.setEpicrisis(epicrisis);
        appointment.setIdEmployee(Employee.mainEmployee.getId());
        appointment.setIdPatient(patientTable.getSelectionModel().getSelectedItem().getId());
        System.out.println(Employee.mainEmployee);
        clientHandler.sendObject(appointment);
        boolean isEnd = (boolean) clientHandler.readObject();
        if(isEnd){
            AlertScene.callAlert("Прием оформлен и закончен");
        }
        else {
            AlertScene.callAlert("Ошибка окончания или оформления приема");
        }
    }

    private void search() throws IOException {

        String surname = surnameField.getText().trim();
        String name = nameField.getText().trim();
        String patronymic = patronymicField.getText().trim();

        Patient patient = new Patient();
        patient.setPatronymic(patronymic);
        patient.setName(name);
        patient.setSurname(surname);
        clientHandler.sendObject(patient);

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
                    idAppointmentColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                    surnamePatient.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    namePatient.setCellValueFactory(new PropertyValueFactory<>("name"));
                    paronymicPatient.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
                    phonePatient.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                    patientTable.setItems(Patient.listPatients);
                    patientTable.refresh();
                }
                else{
                    AlertScene.callAlert("Не удалось найти пациента");
                }
            }
        }
        else {
            AlertScene.callAlert("В программе нет пациентов");
        }


    }

    private void showDiseaseHistory() throws IOException {
        clientHandler.sendObject(patientTable.getSelectionModel().getSelectedItem());
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();

        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();
            for(int i=0; i<size ; i++){
                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                appointmentArrayList.add(item);

            }
            diseaseHistory.getItems().clear();
            for(int i = 0; i < size; i++){
                if(appointmentArrayList.get(i).getEpicrisis()!=null ){
                    diseaseHistory.getItems().addAll(appointmentArrayList.get(i).getDate(),
                            appointmentArrayList.get(i).getEpicrisis());
                }
            }
        }
        else {
            AlertScene.callAlert("Не удалось загрузить историю болезни");
        }
    }







}