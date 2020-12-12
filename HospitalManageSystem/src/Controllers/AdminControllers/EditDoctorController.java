package Controllers.AdminControllers;

import Configs.FXMLConfigs;
import Models.Address;
import Models.Employee;
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

public class EditDoctorController {

    @FXML
    private TextField specialtyTextField;

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
    private Button confirmButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField surnameTextField;
    private ClientHandler clientHandler = ClientHandler.getClient();

    static int idEmployee;

    @FXML
    void initialize()  {
        deleteFocus();

        setFields();
        updateStreetComboBox();

        clearButton.setOnAction(actionEvent -> {
            clientHandler.sendMessage("clearButton");
            clear();
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
          //  clientHandler.sendMessage("managePersonnel");
            clientHandler.sendMessage("managePersonnel");
            changeScene(FXMLConfigs.adminManageMedicalStaff);
        });

        confirmButton.setOnAction(actionEvent -> {
            editEmployee();
        });
    }

    private void setFields() {

        Street street = (Street) clientHandler.readObject();
        Address address = (Address) clientHandler.readObject();
        Employee employee = (Employee) clientHandler.readObject();
        surnameTextField.setText(employee.getSurname());
        nameTextField.setText(employee.getName());
        patronymicTextField.setText(employee.getPatronymic());
        birthdayDatePicker.setValue(LocalDate.parse(employee.getBirthday()));
        specialtyTextField.setText(employee.getNameSpecialty());
        String gender = employee.getGender();
        if(gender.equals("Мужской")){
            manRadio.fire();
        }
        else{
            womanRadio.fire();
        }
        flatNumberTextField.setText(Integer.toString(address.getFlatNumber()));
        houseNumberTextField.setText(Integer.toString(address.getHouseNumber()));
        corpusNumberTextField.setText(Integer.toString(address.getCorpus()));

        streetComboBox.getSelectionModel().select(street);
        idEmployee = employee.getId();
    }


    private void deleteFocus() {
        surnameTextField.setFocusTraversable(false);
        nameTextField.setFocusTraversable(false);
        birthdayDatePicker.setValue(LocalDate.now());
        patronymicTextField.setFocusTraversable(false);
        birthdayDatePicker.setFocusTraversable(false);
        houseNumberTextField.setFocusTraversable(false);
        flatNumberTextField.setFocusTraversable(false);
        corpusNumberTextField.setFocusTraversable(false);
        streetComboBox.setFocusTraversable(false);
        specialtyTextField.setFocusTraversable(false);
    }


    private void clear(){
        surnameTextField.setText("");
        nameTextField.setText("");
        patronymicTextField.setText("");
        birthdayDatePicker.setValue(LocalDate.now());
        houseNumberTextField.setText("");
        flatNumberTextField.setText("");
        corpusNumberTextField.setText("");
        streetComboBox.getSelectionModel().clearSelection();
        specialtyTextField.setText("");

    }


    private void editEmployee() {
        clientHandler.sendMessage("confirmEdit");
        String surnameEmployee = surnameTextField.getText().trim();
        String nameEmployee = nameTextField.getText().trim();
        String birthdayDate = birthdayDatePicker.getValue().toString();

        String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();
        String patronymicEmployee = patronymicTextField.getText().trim();
        String houseNumber = houseNumberTextField.getText().trim();
        String flatNumber = flatNumberTextField.getText().trim();
        String corpusNumber = corpusNumberTextField.getText().trim();
        Street street = streetComboBox.getValue();
        String specialty = specialtyTextField.getText().trim();


        String regex = ".*\\d+.*";
        String regexP = ".*\\D+.*";
        if(surnameEmployee.equals("") || nameEmployee.equals("")
                || patronymicEmployee.equals("")
                ||!birthdayDatePicker.getValue().isBefore(LocalDate.now())
                || houseNumber.equals("") || flatNumber.equals("")
                || corpusNumber.equals("") || specialty.equals("")
                || surnameEmployee.matches(regex) || nameEmployee.matches(regex)
                || patronymicEmployee.matches(regex) || houseNumber.matches(regexP)
                || flatNumber.matches(regexP) || corpusNumber.matches(regexP)
                || specialty.matches(regex)){

            callAlert("Не все поля введены корректно!");
        }
        else {
            Employee employeeNew = new Employee();
            employeeNew.setSurname(surnameEmployee);
            employeeNew.setName(nameEmployee);
            employeeNew.setPatronymic(patronymicEmployee);
            employeeNew.setBirthday(birthdayDate);
            employeeNew.setGender(gender);
            employeeNew.setId(idEmployee);
            employeeNew.setNameSpecialty(specialty);
            Address address = new Address(street,Integer.parseInt(flatNumber),
                    Integer.parseInt(houseNumber),Integer.parseInt(corpusNumber));
            employeeNew.setAddress(address);
            System.out.println(employeeNew);
            clientHandler.sendObject(employeeNew);
            boolean isEmployeeAdded = (boolean) clientHandler.readObject();
            if (isEmployeeAdded) callAlert("Новый работник регистратуры добавлен");
            else  callAlert("Новый работник регистратуры не был добавлен. Попробуйте снова.");

        }

    }


    private void updateStreetComboBox() {
        clientHandler.sendMessage("updateStreetComboBox");
        boolean isUpdateSuccessfully = (boolean) clientHandler.readObject();
        if(isUpdateSuccessfully) {
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
