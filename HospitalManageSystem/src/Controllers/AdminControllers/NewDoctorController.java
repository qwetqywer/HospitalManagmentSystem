package Controllers.AdminControllers;

import Configs.FXMLConfigs;
import Models.Address;
import Models.Employee;
import Models.Specialty;
import Models.Street;
import ServerHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewDoctorController {

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField patronymicTextField;

    @FXML
    private ComboBox<Street> streetComboBox;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField flatNumberTextField;

    @FXML
    private TextField corpusNumberTextField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private RadioButton manRadio;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton womanRadio;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField specialtyTextField;
    private ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize() {
        deleteFocus();
        updateStreetComboBox();


        clearButton.setOnAction(actionEvent -> {
            clientHandler.sendMessage("clearButton");
            clear();
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
           // clientHandler.sendMessage("managePersonnel");
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });

        confirmButton.setOnAction(actionEvent -> {
            addEmployee();
        });

    }


    private void deleteFocus() {
        surnameTextField.setFocusTraversable(false);
        nameTextField.setFocusTraversable(false);
        birthdayDatePicker.setValue(LocalDate.now());
        patronymicTextField.setFocusTraversable(false);
        birthdayDatePicker.setFocusTraversable(false);
        loginField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        houseNumberTextField.setFocusTraversable(false);
        flatNumberTextField.setFocusTraversable(false);
        corpusNumberTextField.setFocusTraversable(false);
        streetComboBox.setFocusTraversable(false);
    }


    private void clear(){
        surnameTextField.setText("");
        nameTextField.setText("");
        patronymicTextField.setText("");
        birthdayDatePicker.setValue(LocalDate.now());
        houseNumberTextField.setText("");
        flatNumberTextField.setText("");
        corpusNumberTextField.setText("");
        loginField.setText("");
        passwordField.setText("");
    }


    private void addEmployee() {
        clientHandler.sendMessage("confirmAdd");
        String surnameEmployee = surnameTextField.getText().trim();
        String nameEmployee = nameTextField.getText().trim();
        String patronymicEmployee = patronymicTextField.getText().trim();
        String birthdayDate = birthdayDatePicker.getValue().toString();

        String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();

        String houseNumber = houseNumberTextField.getText().trim();
        String flatNumber = flatNumberTextField.getText().trim();
        String corpusNumber = corpusNumberTextField.getText().trim();
        Street street = streetComboBox.getValue();
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        String specialty = specialtyTextField.getText().trim();

        String regex = ".*\\d+.*";
        String regexP = ".*\\D+.*";
        if(surnameEmployee.equals("") || nameEmployee.equals("")
                || patronymicEmployee.equals("") || specialty.equals("")
                ||!birthdayDatePicker.getValue().isBefore(LocalDate.now())
                || houseNumber.equals("") || flatNumber.equals("")
                || corpusNumber.equals("")
                || surnameEmployee.matches(regex) || nameEmployee.matches(regex)
                || patronymicEmployee.matches(regex) || specialty.matches(regex)
                || houseNumber.matches(regexP)
                || flatNumber.matches(regexP) || corpusNumber.matches(regexP)){

            callAlert("Не все поля введены корректно!");
        }
        else {
            Employee employeeNew = new Employee();
            employeeNew.setSurname(surnameEmployee);
            employeeNew.setName(nameEmployee);
            employeeNew.setPatronymic(patronymicEmployee);
            employeeNew.setBirthday(birthdayDate);
            employeeNew.setGender(gender);
            Address address = new Address(street,Integer.parseInt(flatNumber),
                    Integer.parseInt(houseNumber),Integer.parseInt(corpusNumber));
            employeeNew.setAddress(address);
            employeeNew.setPassword(password);
            employeeNew.setLogin(login);
            Specialty specialtyObject = new Specialty();
            specialtyObject.setName(specialty);
            clientHandler.sendObject(employeeNew);
            clientHandler.sendObject(specialtyObject);
            boolean isEmployeeAdded = (boolean) clientHandler.readObject();
            if (isEmployeeAdded) callAlert("Новый работник регистратуры добавлен");
            else  callAlert("Новый работник регистратуры не был добавлен. Попробуйте снова.");

        }

    }


    private void updateStreetComboBox() {
        clientHandler.sendMessage("updateStreetComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
           // System.out.println("success");
            ArrayList<Street> streetArrayList = (ArrayList<Street>)clientHandler.readObject();
            Street.update(streetArrayList);
        }

        streetComboBox.setItems(Street.listStreets);
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