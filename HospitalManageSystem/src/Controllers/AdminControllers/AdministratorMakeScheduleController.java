package Controllers.AdminControllers;

import Configs.AlertScene;
import Configs.FXMLConfigs;
import Models.*;
import ClientHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdministratorMakeScheduleController {


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
    private TableView<Employee> doctorTable;

    @FXML
    private TableColumn<Employee, Integer> idDoctorColumn;

    @FXML
    private TableColumn<Employee, String> surnameDoctorColumn;

    @FXML
    private TableColumn<Employee, String> nameDoctorColumn;

    @FXML
    private TableColumn<Employee, String> patronymicDoctorColumn;

    @FXML
    private TableColumn<Employee, String> specialtyDoctorColumn;

    @FXML
    private TableColumn<Employee, String> workTimeDoctorColumn;

    @FXML
    private TableColumn<Employee, String> RoomNumberDoctorColumn;

    @FXML
    private DatePicker dateAppointmentField;

    @FXML
    private ComboBox<Specialty> specialtyCheckBox;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TextField timeAppointmentField;

    @FXML
    private Button returnBackButton;

    @FXML
    private Button addWorkTimeButton;

    @FXML
    private TextField workTimeField;

    @FXML
    private Button searchButton;


    @FXML
    private TextField RoomNumberField;

    private final ClientHandler clientHandler = ClientHandler.getClient();
    @FXML
    void initialize() throws IOException {
        deleteFocus();
        updateSpecialtyComboBox();


        registryManagementButton.setOnAction(actionEvent -> {
            registryManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("manageRegistry");
            changeScene(FXMLConfigs.adminManageRegistry);

        });


        desktopAdministratorButton.setOnAction((actionEvent -> {
            desktopAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopAdmin");
            changeScene(FXMLConfigs.adminAccount);
        }));


        settingsAdministratorButton.setOnAction(actionEvent -> {
            settingsAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("settingsAdmin");
            changeScene(FXMLConfigs.adminEditProfile);
        });



        viewStatisticsButton.setOnAction(actionEvent -> {
            viewStatisticsButton.getScene().getWindow().hide();
            clientHandler.sendMessage("viewStatistics");
            changeScene(FXMLConfigs.adminStatistics);
        });




        returnBackButton.setOnAction(actionEvent -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            changeScene(FXMLConfigs.authorization);
        });


        personnelManagementButton.setOnAction(actionEvent -> {
            personnelManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });

        searchButton.setOnAction(actionEvent -> {
            if(specialtyCheckBox.getSelectionModel()==null)
            {
                AlertScene.callAlert("Выберите специальность");
            }
            else {
                clientHandler.sendMessage("searchButton");

                try {
                    updateTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addWorkTimeButton.setOnAction(actionEvent -> {
            if(doctorTable.getSelectionModel().getSelectedItem() == null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Выберите запись!");
                alert.showAndWait();
            }else {
                clientHandler.sendMessage("addWorkTimeButton");
                addWorkTime();
            }
        });

        addAppointmentButton.setOnAction(actionEvent -> {
            if(doctorTable.getSelectionModel().getSelectedItem() == null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Выберите запись!");
                alert.showAndWait();
            }else {
                clientHandler.sendMessage("addAppointmentButton");
                addAppointment();
            }

        });
    }

    private void updateTable() throws IOException {
        clientHandler.sendMessage("updateDoctorTable");
        clientHandler.sendObject(specialtyCheckBox.getValue());
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        Employee.listEmployees.clear();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();

            for(int i=0; i<size ; i++){
                Employee item = new Employee((Employee) clientHandler.readObject());
                employeeArrayList.add(item);

            }

            Employee.update(employeeArrayList);
        }

        idDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        specialtyDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("nameSpecialty"));
        workTimeDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("workTime"));
        RoomNumberDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));

        doctorTable.setItems(Employee.listEmployees);
        doctorTable.refresh();
    }


    private void addWorkTime(){
        String workTime = workTimeField.getText().trim();
        String roomNumber= RoomNumberField.getText().trim();


        String regexWorkTime ="^(\\d{2}).(\\d{2})-(\\d{2}).(\\d{2})$";
        String regexRoomNumber ="^(\\d{3})$";
        if(!workTime.matches(regexWorkTime) || !roomNumber.matches(regexRoomNumber) ){

            AlertScene.callAlert("Не все поля введены корректно!");
        }
        else {
            Employee employee = new Employee(doctorTable.getSelectionModel().getSelectedItem());
            employee.setWorkTime(workTime);
            employee.setOfficeNumber(roomNumber);
            clientHandler.sendObject(employee);
            boolean isEmployeeAdded = (boolean) clientHandler.readObject();
            if (isEmployeeAdded) {
                AlertScene.callAlert("Рабочее время успешно назначено");
                workTimeField.setText("");
                RoomNumberField.setText("");
            }
            else  AlertScene.callAlert("Рабочее время не было назначено. Попробуйте снова.");

        }

    }


    private void addAppointment(){
        String dateAppointment = dateAppointmentField.getValue().toString();
        String timeAppointment= timeAppointmentField.getText().trim();


        String regexTime ="^(\\d{2}):(\\d{2})$";

        if(!timeAppointment.matches(regexTime) || dateAppointmentField.getValue().isBefore(LocalDate.now())){
            AlertScene.callAlert("Не все поля введены корректно!");
        }
        else {

            Employee employee = new Employee(doctorTable.getSelectionModel().getSelectedItem());
            Appointment appointment = new Appointment();
            appointment.setDate(dateAppointment);
            appointment.setIntStatus(1);
            appointment.setTime(timeAppointment);
            appointment.setIdEmployee(employee.getId());
            clientHandler.sendObject(appointment);
            boolean isEmployeeAdded = (boolean) clientHandler.readObject();
            if (isEmployeeAdded) {
                AlertScene.callAlert("Талон оформлен");
                timeAppointmentField.setText("");
                dateAppointmentField.setValue(LocalDate.now());
            }
            else  AlertScene.callAlert("Талон не был оформлен. Попробуйте снова.");

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
        primaryStage.setTitle("Медицинская клиника");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void updateSpecialtyComboBox() throws IOException {
        clientHandler.sendMessage("updateSpecialtyComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Specialty> specialtyArrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();
            for(int i=0; i<size ; i++){
                Specialty item = new Specialty((Specialty) clientHandler.readObject());
                specialtyArrayList.add(item);
            }
            Specialty.update(specialtyArrayList);
            specialtyCheckBox.setItems(Specialty.listSpecialties);
        }

    }


    private void deleteFocus() {

        workTimeField.setFocusTraversable(false);
        RoomNumberField.setFocusTraversable(false);
        timeAppointmentField.setFocusTraversable(false);
        dateAppointmentField.setFocusTraversable(false);
    }



}