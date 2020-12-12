package Controllers.CareWorkerControllers;

import Configs.FXMLConfigs;
import Models.Appointment;
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
            callAlert("Выберите дату");
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
        clientHandler.sendMessage("updateAppointmentTable");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        Appointment.listAppointments.clear();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();

            for(int i=0; i<size ; i++){
                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                appointmentArrayList.add(item);

            }

            Appointment.update(appointmentArrayList);
        }

        idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
        appointmentTable.setItems(Appointment.listAppointments);
        appointmentTable.refresh();
    }

    private void callAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }


    private void updateAppointmentTableByDate() throws IOException {
        clientHandler.sendMessage("updateAppointmentTableByDate");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        Appointment.listAppointments.clear();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();

            for(int i=0; i<size ; i++){
                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                if(item.getDate().equals(String.valueOf(dateAppointmentField.getValue()))){
                    appointmentArrayList.add(item);
                }


            }

            Appointment.update(appointmentArrayList);
        }

        idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("nameType"));
        appointmentTable.setItems(Appointment.listAppointments);
        appointmentTable.refresh();


    }

}