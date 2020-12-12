package Controllers.AdminControllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import Configs.FXMLConfigs;
import Controllers.RegControllers.IssueOutpatientCardController;
import Models.Employee;
import Models.Street;
import ServerHandlers.ClientHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdministratorManageRegistryController {


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
    private TableView<Employee> receptionistTable;

    @FXML
    private TableColumn<Employee, Integer> idReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> surnameReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> nameReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> patronymicReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> birthdayReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> addressReceptionistColumn;

    @FXML
    private TableColumn<Employee, String> genderReceptionistColumn;

    @FXML
    private Button addReceptionistButton;

    @FXML
    private Button editReceptionistButton;

    @FXML
    private Button deleteReceptionistButton;


    private  ClientHandler clientHandler = ClientHandler.getClient();


    @FXML
    void initialize() throws IOException {
        //clientHandler.sendMessage("manageRegistry");
        System.out.println("управлеие регистратурой");
        updateTable();

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

        deleteReceptionistButton.setOnAction(actionEvent -> {
            try {
                deleteReceptionist();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        personnelManagementButton.setOnAction(event -> {
            personnelManagementButton.getScene().getWindow().hide();
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });


        addReceptionistButton.setOnAction(actionEvent -> {
            addReceptionistButton.getScene().getWindow().hide();
            clientHandler.sendMessage("addReceptionistButton");
            changeScene(FXMLConfigs.newReg);
        });

        editReceptionistButton.setOnAction(actionEvent -> {
            if(receptionistTable.getSelectionModel().getSelectedItem() == null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Выберите запись для редактирования");
                alert.showAndWait();
            }else {
                clientHandler.sendMessage("editReceptionistButton");
                Employee employee = new Employee(receptionistTable.getSelectionModel().getSelectedItem());
                clientHandler.sendObject(employee);
                editReceptionistButton.getScene().getWindow().hide();
                changeScene(FXMLConfigs.editReg);
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
        //System.out.println("зашло в обноавление");
        clientHandler.sendMessage("updateReceptionistTable");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        if(isUpdateSuccessfully) {

            int size = clientHandler.read();
            for(int i=0; i<size ; i++){
                Employee item = new Employee((Employee) clientHandler.readObject());
                employeeArrayList.add(item);
            }

            Employee.update(employeeArrayList);
        }

        idReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnameReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthdayReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        genderReceptionistColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));


        receptionistTable.setItems(Employee.listEmployees);
        System.out.println(employeeArrayList.size()+" size bedore");
        receptionistTable.refresh();
        System.out.println(employeeArrayList.size()+" size after");
    }


    private void deleteReceptionist() throws IOException {
        clientHandler.sendMessage("deleteReceptionist");
        if(receptionistTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Выберите запись для удаления");
            alert.showAndWait();
        }
        else{
            clientHandler.sendObject(receptionistTable.getSelectionModel().getSelectedItem());
            updateTable();
        }
    }

}