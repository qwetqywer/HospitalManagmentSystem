package Controllers.CareWorkerControllers;

import Configs.AlertScene;
import Configs.ChangeScene;
import Configs.FXMLConfigs;
import Models.*;
import ClientHandlers.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

public class IssueAppointmentController {
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
    private Button issueAppointment;


    @FXML
    private Button confirmAppointmentType;
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
    private ComboBox<Specialty> specialtyComboBox;
    @FXML
    private ComboBox<Employee> doctorComboBox;

    @FXML
    private Button confirmSpecialty;
    @FXML
    private Button confirmAC;
    @FXML
    private Button findPatientButton;
    @FXML
    private Button confirmDoctor;
    @FXML
    private ComboBox<AppointmentType> appointmentTypeComboBox;


    @FXML
    private Button confirmIssuesButton;
    private final ClientHandler clientHandler = ClientHandler.getClient();

    private Appointment mainAppointment = new Appointment();

    private Employee mainEmployee = new Employee();
    private AppointmentType mainAType;


    @FXML
    void initialize() throws IOException {
        updateSpecialtiesComboBox();
        updateAppointmentTypeComboBox();

        confirmSpecialty.setOnAction(event -> {
            clientHandler.sendMessage("confirmSpecialty");
            Specialty specialty = specialtyComboBox.getValue();
            if(specialty != null){
                clientHandler.sendObject(true);
                try {
                    updateDoctorsComboBox(specialty);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                AlertScene.callAlert("Cпециальность не была выбрана.");
                clientHandler.sendObject(true);
            }
        });
        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
        });

        desktopCareWorkerButton.setOnAction(event -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopCareWorker");
            ChangeScene.change(FXMLConfigs.careWorkerAccount,getClass());

        });
        startAppointmentWithoutOrderButton.setOnAction(actionEvent -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startWithoutOrder");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointmentWithoutOrder,getClass());


        });
        getSceduleCareWorkerButton.setOnAction((event -> {
            getSceduleCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("getSchedule");
            ChangeScene.change(FXMLConfigs.careWorkerSchedule,getClass());

        }));
        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            ChangeScene.change(FXMLConfigs.authorization,getClass());

        });

        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointment,getClass());
        });
        issueAppointment.setOnAction(actionEvent -> {
            issueAppointment.getScene().getWindow().hide();
            clientHandler.sendMessage("issueAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerIssueAppointment,getClass());
        });

        confirmAC.setOnAction(event -> {
            if( AppointmentTable.getSelectionModel().getSelectedItem() != null){
                mainAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
                AlertScene.callAlert("Талон выбран.");
            }
            else AlertScene.callAlert("Талон не был выбран.");
        });

        findPatientButton.setOnAction(event -> findPatient());

        confirmDoctor.setOnAction(event -> {
            Employee employee = doctorComboBox.getValue();
            if(employee!=null) {
                try {
                    updateAppointmentTable(employee);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else AlertScene.callAlert("Доктор не был выбран.");

        });

        confirmAppointmentType.setOnAction(event -> {
            AppointmentType appointmentType = appointmentTypeComboBox.getValue();
            if(appointmentType!=null) {
                mainAppointment.setIdType(appointmentType.getId());
                AlertScene.callAlert("Тип направления выбран.");
            }
            else AlertScene.callAlert("Тип направления не был выбран.");
        });

        confirmIssuesButton.setOnAction(event -> {

            if(mainAppointment == null){
                AlertScene.callAlert("Талон не был выбран.");
            }
            else if(mainAppointment.getIdType()==0)
            {
                AlertScene.callAlert("Тип направления не был выбран.");
            }
            else {
                if (patientTable.getSelectionModel().getSelectedItem()!=null)
                {
                    clientHandler.sendMessage("confirmIssuesButton");
                    Patient patient = patientTable.getSelectionModel().getSelectedItem();
                    clientHandler.sendObject(mainAppointment);
                    clientHandler.sendObject(patient);
                    AlertScene.callAlert("Талон успешно оформлен.");
                    try {
                        updateAppointmentTable(mainEmployee);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {

                    AlertScene.callAlert("Пациент не был выбран.");
                }
            }
        });

    }

    private void updateAppointmentTypeComboBox() {
        clientHandler.sendMessage("updateAppointmentTypeComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
            ArrayList<AppointmentType> typeArrayList = new ArrayList<>();
            AppointmentType.appointmentTypeList.clear();
            int size = (int) clientHandler.readObject();
            for(int i=0; i< size;i++){
                AppointmentType item = new AppointmentType((AppointmentType) clientHandler.readObject());
                typeArrayList.add(item);
            }
            AppointmentType.update(typeArrayList);
        }
        appointmentTypeComboBox.setItems(AppointmentType.appointmentTypeList);

    }

    private void updateSpecialtiesComboBox() throws IOException {
        clientHandler.sendMessage("updateSpecialtiesComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Specialty> specialtyArrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();
            for(int i=0; i<size ; i++){
                Specialty item = new Specialty((Specialty) clientHandler.readObject());
                specialtyArrayList.add(item);
            }
            Specialty.update(specialtyArrayList);
            specialtyComboBox.setItems(Specialty.listSpecialties);
        }
    }
    private void updateDoctorsComboBox(Specialty specialty) throws IOException {
        clientHandler.sendMessage("updateDoctorsComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Employee> arrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();
            System.out.println(size);
            for(int i=0; i<size ; i++){
                Employee item = new Employee((Employee) clientHandler.readObject());
                System.out.println(item.getIdSpecialty());
                if(item.getIdSpecialty() == specialty.getId()){
                    arrayList.add(item);
                }
            }
            System.out.println(arrayList.size() + specialty.getName());
            Employee.update(arrayList);
            doctorComboBox.setItems(Employee.listEmployees);
        }else{
            AlertScene.callAlert("На данной специальности нет врачей");
        }
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
            AlertScene.callAlert("Не все поля введены корректно!");
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
                    if(Patient.listPatients.size() == 0)  AlertScene.callAlert("Пациент не найден.");
                    patientTable.refresh();
                    surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
                    patientTable.setItems(Patient.listPatients);

                }
                else  AlertScene.callAlert("Пациент не найден.");
            }
            else  AlertScene.callAlert("Данные не найдены. Повторите снова.");
        }
    }

    private void updateAppointmentTable( Employee employee ) throws IOException {

        clientHandler.sendMessage("updateAppointmentTable");
        clientHandler.sendObject(employee);
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();

        if(isUpdateSuccessfully) {

            Appointment.listAppointments.clear();
            ObservableList<Employee> newEmployee = FXCollections.observableArrayList();
            ObservableList<Appointment> newArr= FXCollections.observableArrayList();
            ArrayList<Appointment> appointments = new ArrayList<>();
            newEmployee.clear();
            int size = clientHandler.read();
            for(int i=0;i<size;i++)
            {

                Appointment item = new Appointment((Appointment) clientHandler.readObject());
                if(item.getIdEmployee() ==employee.getId()){
                    if(item.getIntStatus()==0){

                        appointments.add(item);
                        newEmployee.add(employee);
                    }
                }

                Appointment.update(appointments);

            }

            doctorsTable.getItems().removeAll(newEmployee);
            AppointmentTable.getItems().removeAll(appointments);
            doctorsTable.refresh();
            fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            officeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
            doctorsTable.refresh();
            doctorsTable.setItems(newEmployee);
            AppointmentTable.refresh();
            eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            AppointmentTable.setItems(Appointment.listAppointments);
            System.out.println(appointments.size());
            if(appointments.size() == 0 || newEmployee.size() == 0) {
                AlertScene.callAlert("Талоны не найдены");
            }
            mainEmployee = employee;
        }
        else AlertScene.callAlert("Данные не найдены. Повторите снова.");



    }
}