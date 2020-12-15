package Controllers.RegControllers;

import Configs.FXMLConfigs;
import Models.*;
import ClientHandlers.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class IssueAppointmentCardController {

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
    private ComboBox<Employee> doctorComboBox;


    @FXML
    private TableView<Appointment> AppointmentTable;
    @FXML
    private TableColumn<Employee,String> eventDateColumn;
    @FXML
    private TableColumn<Employee,String> eventTimeColumn;
    @FXML
    private TableColumn<Employee,String> statusColumn;


    @FXML
    private TableColumn<Employee,String> officeNumberColumn;
    @FXML
    private TableView<Employee> doctorsTable;
    @FXML
    private TableColumn<Employee,String> fullnameColumn;


    @FXML
    private TextField surnamePatientTextField;
    @FXML
    private TextField namePatientTextField;
    @FXML
    private TextField patronymicPatientTextField;

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> surnameColumn;
    @FXML
    private TableColumn<Patient, String> nameColumn;
    @FXML
    private TableColumn<Patient, String> patronymicColumn;


    @FXML
    private Button confirmAC;

    @FXML
    private Button confirmIssuesButton;
    @FXML
    private Button confirmSpecialty;

    @FXML
    private Button confirmDoctor;
    @FXML
    private Button findPatientButton;

    private Employee mainEmployee;
    private Appointment mainAppointment;
    private final ClientHandler clientHandler = ClientHandler.getClient();
    @FXML
    void initialize(){
        updateSpecialtiesComboBox();

        confirmSpecialty.setOnAction(event -> {
            clientHandler.sendMessage("confirmSpecialty");
            Specialty specialty = specialtyComboBox.getValue();
            if(specialty != null){
                clientHandler.sendObject(true);
                updateDoctorsComboBox(specialty);
            }
            else
            {
                callAlert("Cпециальность не была выбрана.");
                clientHandler.sendObject(true);
            }
        });

        confirmAC.setOnAction(event -> {
            if( AppointmentTable.getSelectionModel().getSelectedItem() != null){
                mainAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
                callAlert("Талон выбран.");
            }
            else callAlert("Талон не был выбран.");
        });

        findPatientButton.setOnAction(event -> findPatient());

        confirmIssuesButton.setOnAction(event -> {
            clientHandler.sendMessage("confirmIssuesButton");
            if(mainAppointment == null){
                callAlert("Талон не был выбран.");
                clientHandler.sendObject(false);
            }
            else {
                clientHandler.sendObject(true);
                if (patientTable.getSelectionModel().getSelectedItem()!=null)
                {
                    clientHandler.sendObject(true);
                    Patient patient = patientTable.getSelectionModel().getSelectedItem();
                    mainAppointment.setIdType(1);
                    clientHandler.sendObject(mainAppointment);
                    clientHandler.sendObject(patient);
                    callAlert("Талон успешно оформлен.");
                    updateAppointmentTable(mainEmployee);
                }
                else {
                    clientHandler.sendObject(false);
                    callAlert("Пациент не был выбран.");
                }
            }
        });
        confirmDoctor.setOnAction(event -> {
            Employee employee = doctorComboBox.getValue();
            if(employee!=null) {
                updateAppointmentTable(employee);
            }
            else callAlert("Доктор не был выбран.");

        });

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
    }

    private void findPatient() {
        clientHandler.sendMessage("findPatient");
        Patient patient = new Patient();
        String surnamePatient = surnamePatientTextField.getText().trim();
        String namePatient = namePatientTextField.getText().trim();
        String patronymicPatient = patronymicPatientTextField.getText().trim();
        String regex = ".*\\d+.*";
        if(surnamePatient.equals("") || namePatient.equals("")
                || patronymicPatient.equals("") || surnamePatient.matches(regex)
                || namePatient.matches(regex) || patronymicPatient.matches(regex))
        {
            callAlert("Не все поля введены корректно!");
            clientHandler.sendObject(false);
        }
        else {
            clientHandler.sendObject(true);
            patient.setSurname(surnamePatient);
            patient.setName(namePatient);
            patient.setPatronymic(patronymicPatient);
            clientHandler.sendObject(patient);
            boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
            if(isUpdateSuccessfully) {
                boolean isNotEmpty = (boolean) clientHandler.readObject();
                if(isNotEmpty)
                {
                    ArrayList<Patient> arrayList = (ArrayList< Patient>)clientHandler.readObject();
                    Patient.listPatients.clear();
                    for (Patient patientS : arrayList) {
                        if (patientS.getSurname().equals(patient.getSurname())
                                && patientS.getName().equals(patient.getName())
                                && patientS.getPatronymic().equals(patient.getPatronymic()))
                        {
                            Patient.listPatients.add(patientS);
                        }
                    }
                    if(Patient.listPatients.size() == 0) callAlert("Пациент не найден.");
                    patientTable.refresh();
                    surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
                    patientTable.setItems(Patient.listPatients);

                }
                else callAlert("Пациент не найден.");
            }
            else callAlert("Данные не найдены. Повторите снова.");
        }
    }

    private void updateAppointmentTable( Employee employee ) {

        clientHandler.sendMessage("updateAppointmentTable");
        clientHandler.sendObject(employee);
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();

        if(isUpdateSuccessfully) {

            Appointment.listAppointments.clear();
            ObservableList<Employee> newEmployee = FXCollections.observableArrayList();
            ObservableList<Appointment> newArr= FXCollections.observableArrayList();
            ArrayList<Appointment> appointments = new ArrayList<>();
            newEmployee.clear();
            newArr.clear();
            int size = (int) clientHandler.readObject();
            for(int i=0;i<size;i++)
            {
                appointments.add((Appointment) clientHandler.readObject());
            }
            for (Appointment appointment : appointments) {
               if(appointment.getIdEmployee()==employee.getId())
               {
                   if (appointment.getIntStatus() ==0)
                   {
                       newArr.add(appointment);
                       newEmployee.add(employee);
                   }
               }
            }
            doctorsTable.getItems().removeAll(newEmployee);
            AppointmentTable.getItems().removeAll(newArr);
            doctorsTable.refresh();
            fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            officeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
            doctorsTable.refresh();
            doctorsTable.setItems(newEmployee);
            AppointmentTable.refresh();
            eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            AppointmentTable.setItems(newArr);
            if(newArr.size() == 0 || newEmployee.size() == 0) {
                callAlert("Талоны не найдены");
            }
            mainEmployee = employee;
        }
        else callAlert("Данные не найдены. Повторите снова.");
    }

    private void updateDoctorsComboBox(Specialty specialty) {
        clientHandler.sendMessage("updateDoctorsComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
            Employee.listEmployees.clear();
            ArrayList<Employee> arrayList = (ArrayList<Employee>)clientHandler.readObject();
            ArrayList<Employee> newArr = new ArrayList<>();
            for (Employee employee : arrayList) {
                if (employee.getIdSpecialty() == specialty.getId()) newArr.add(employee);
            }
            Employee.listEmployees.addAll(newArr);
            doctorComboBox.setItems(Employee.listEmployees);
        }
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

    private void updateSpecialtiesComboBox() {
        clientHandler.sendMessage("updateSpecialtiesComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
            ArrayList<Specialty> specialtyArrayList = (ArrayList<Specialty>)clientHandler.readObject();
            Specialty.update(specialtyArrayList);
        }
        specialtyComboBox.setItems(Specialty.listSpecialties);
    }
}
