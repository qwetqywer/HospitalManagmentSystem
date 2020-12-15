package Controllers.CareWorkerControllers;
import Configs.AlertScene;
import Configs.ChangeScene;
import Configs.FXMLConfigs;
import Models.Appointment;
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
    private ListView<String> diseaseHistory;

    private ClientHandler clientHandler = ClientHandler.getClient();


    @FXML
    void initialize() throws IOException {

        updateAppointmentTable();


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


        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointment,getClass());
        });


        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
        });


        startButton.setOnAction(actionEvent -> {
            if(appointmentTable.getSelectionModel().getSelectedItem() == null)
            {
                AlertScene.callAlert("Выберите прием");
            }else {
                clientHandler.sendMessage("start");
                try {
                    startAppointment();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        endButton.setOnAction(actionEvent -> {
            if(appointmentTable.getSelectionModel().getSelectedItem() == null)
            {
                AlertScene.callAlert("Выберите прием");
            }else {
                clientHandler.sendMessage("end");
               endAppointment();
            }
        });

        diseaseHistoryButton.setOnAction(actionEvent -> {
            if(patientTable.getSelectionModel().getSelectedItem() == null)
            {
                AlertScene.callAlert("Выберите пациента");
            }else {

                clientHandler.sendMessage("diseaseHistory");
                clientHandler.sendObject(patientTable.getSelectionModel().getSelectedItem());
                try {
                    showDiseaseHistory();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
    }

    private void endAppointment() {
        Appointment appointment = new Appointment(appointmentTable.getSelectionModel().getSelectedItem());
        String epicrisis = epicrisisField.getText().trim();
        int status = 2;
        appointment.setIntStatus(status);
        appointment.setEpicrisis(epicrisis);
        clientHandler.sendObject(appointment);
        boolean isEnd = (boolean) clientHandler.readObject();
        if(isEnd){
            AlertScene.callAlert("Прием закончен");
        }
        else {
            AlertScene.callAlert("Ошибка окончания приема");
        }
    }

    private void showDiseaseHistory() throws IOException {

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


    private void startAppointment() throws IOException {
        Appointment appointment = new Appointment(appointmentTable.getSelectionModel().getSelectedItem());
        clientHandler.sendObject(appointment);
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
                    AlertScene.callAlert("Нет пациента");
                }
            }
        }
        else {
            AlertScene.callAlert("Не удалось загрузить пациента");
        }


    }

    private void updateAppointmentTable() throws IOException {
        clientHandler.sendMessage("updateAppointmentTable");
        appointmentTable.getItems().clear();
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        Appointment.listAppointments.clear();
        if(isUpdateSuccessfully) {
            boolean isUpdate = (boolean) clientHandler.readObject();
            if(isUpdate){
                int size = clientHandler.read();

                for(int i=0; i<size ; i++){
                    Appointment item = new Appointment((Appointment) clientHandler.readObject());
                    appointmentArrayList.add(item);


                }

                Appointment.update(appointmentArrayList);
                idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                dateAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                timeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
                typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
                statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
                appointmentTable.setItems(Appointment.listAppointments);
                appointmentTable.refresh();
            }
            else {
                AlertScene.callAlert("Сегодня у вас нет приемов");
            }
        }
        else {
            AlertScene.callAlert("Не удалось загрузить расписание");
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
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}