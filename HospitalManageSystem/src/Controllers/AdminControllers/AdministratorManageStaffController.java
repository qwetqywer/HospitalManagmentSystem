package Controllers.AdminControllers;
import Configs.AlertScene;
import Configs.FXMLConfigs;
import Models.Employee;
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

public class AdministratorManageStaffController {

    @FXML
    private Button returnBackButton;

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
    private Label fullNameAdministratorLabel;

    @FXML
    private TableView<Employee> doctorTable;

    @FXML
    private TableColumn<Employee, Integer> idDoctorColumn;

    @FXML
    private TableColumn<Employee, String> FullNameDoctorColumn;

    @FXML
    private TableColumn<Employee, String> birthdayDoctorColumn;

    @FXML
    private TableColumn<Employee, String> nameDoctorColumn;

    @FXML
    private TableColumn<Employee, String> specialtyDoctorColumn;

    @FXML
    private TableColumn<Employee, String> RoomNumberDoctorColumn;

    @FXML
    private TableColumn<Employee, String> genderDoctorColumn;


    @FXML
    private TableColumn<Employee, String> patronymicDoctorColumn;

    @FXML
    private Button addDoctorButton;

    @FXML
    private Button editDoctorButton;

    @FXML
    private Button deleteDoctorButton;

    @FXML
    private Button clearDoctorButton;

    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() throws IOException {

        //clientHandler.sendMessage("managePersonnel");

        updateTable();

        System.out.println("управлеие мед персоналом");
        registryManagementButton.setOnAction(event -> {
            registryManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("manageRegistry");
            changeScene(FXMLConfigs.adminManageRegistry);

        });


        desktopAdministratorButton.setOnAction((event -> {
            desktopAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopAdmin");
            changeScene(FXMLConfigs.adminAccount);
        }));


        settingsAdministratorButton.setOnAction(event -> {
            settingsAdministratorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("settingsAdmin");
            changeScene(FXMLConfigs.adminEditProfile);
        });



        viewStatisticsButton.setOnAction(event -> {
            viewStatisticsButton.getScene().getWindow().hide();
            clientHandler.sendMessage("viewStatistics");
            changeScene(FXMLConfigs.adminStatistics);
        });


        makeWorkScheduleButton.setOnAction(event -> {
            makeWorkScheduleButton.getScene().getWindow().hide();
            clientHandler.sendMessage("makeSchedule");
            changeScene(FXMLConfigs.adminMakeWorkSchedule);
        });


        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            changeScene(FXMLConfigs.authorization);
        });


        personnelManagementButton.setOnAction(event -> {
            personnelManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });

        addDoctorButton.setOnAction(actionEvent -> {
            addDoctorButton.getScene().getWindow().hide();
            clientHandler.sendMessage("addDoctorButton");
            changeScene(FXMLConfigs.newDoctor);
        });

        deleteDoctorButton.setOnAction(actionEvent -> {
            try {
                deleteDoctor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editDoctorButton.setOnAction(actionEvent -> {
            if(doctorTable.getSelectionModel().getSelectedItem() == null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Выберите запись для редактирования");
                alert.showAndWait();
            }else {
                clientHandler.sendMessage("editDoctorButton");
                Employee employee = new Employee(doctorTable.getSelectionModel().getSelectedItem());
                clientHandler.sendObject(employee);
                editDoctorButton.getScene().getWindow().hide();
                changeScene(FXMLConfigs.editDoctor);
            }
        });

    }


    private void updateTable() throws IOException {
        //System.out.println("зашло в обноавление");
        clientHandler.sendMessage("updateStaffTable");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {
            boolean isUpdate = (boolean) clientHandler.readObject();
            if(isUpdate){
                int size = clientHandler.read();
                for(int i=0; i<size ; i++){
                    Employee item = new Employee((Employee) clientHandler.readObject());
                    employeeArrayList.add(item);

                }

                Employee.update(employeeArrayList);
                idDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                FullNameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
                nameDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                patronymicDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
                genderDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
                RoomNumberDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
                specialtyDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("nameSpecialty"));
                birthdayDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));

                doctorTable.setItems(Employee.listEmployees);
                doctorTable.refresh();
            }else{
                AlertScene.callAlert("В программе нет врачей");
            }

        }else {
            AlertScene.callAlert("Нет записей");
        }


    }


    private void deleteDoctor() throws IOException {
        clientHandler.sendMessage("deleteDoctor");
        if(doctorTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Выберите запись для удаления");
            alert.showAndWait();
        }
        else{
            clientHandler.sendObject(doctorTable.getSelectionModel().getSelectedItem());
            updateTable();
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
}
