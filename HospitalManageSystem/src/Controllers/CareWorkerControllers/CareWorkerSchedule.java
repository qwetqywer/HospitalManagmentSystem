package Controllers.CareWorkerControllers;

import Configs.AlertScene;
import Configs.ChangeScene;
import Configs.FXMLConfigs;
import Models.Appointment;
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

public class CareWorkerSchedule {

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
    private DatePicker dateAppointmentField;

    @FXML
    private Button searchButton;

    @FXML
    private Button todaySearchButton;

    private final ClientHandler clientHandler = ClientHandler.getClient();

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
        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
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

        todaySearchButton.setOnAction(actionEvent -> {

                clientHandler.sendMessage("todaySearchButton");
                try {
                    updateTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        });


        searchButton.setOnAction(actionEvent -> {
        if(dateAppointmentField.getValue()==null)
        {
            AlertScene.callAlert("Выберите дату");
        }
        else {
            clientHandler.sendMessage("searchButton");
            try {
                updateAppointmentTableByDate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void updateTable() throws IOException {
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
                statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
                typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
                appointmentTable.setItems(Appointment.listAppointments);
                appointmentTable.refresh();
            }else{
                AlertScene.callAlert("Таких данных нет");
            }

        }else{
            AlertScene.callAlert("Не удалось загрузить данные");
        }

    }




    private void updateAppointmentTableByDate() throws IOException {

        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        Appointment.listAppointments.clear();
        if(isUpdateSuccessfully) {
            boolean isUpdate = (boolean) clientHandler.readObject();
            if(isUpdate){
                int size = clientHandler.read();

                for(int i=0; i<size ; i++){
                    Appointment item = new Appointment((Appointment) clientHandler.readObject());
                    if(item.getDate().equals(String.valueOf(dateAppointmentField.getValue()))){
                        appointmentArrayList.add(item);
                    }
                }

                Appointment.update(appointmentArrayList);
                idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                dateAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
                timeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
                statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
                typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
                appointmentTable.setItems(Appointment.listAppointments);
                appointmentTable.refresh();
            }else{
                AlertScene.callAlert("Таких данных нет");
            }


        }else{
            AlertScene.callAlert("Не удалось загрузить данные");
        }
    }

}