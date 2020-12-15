package Controllers.RegControllers;

import ClientHandlers.ClientHandler;
import Configs.FXMLConfigs;
import Models.Address;
import Models.Patient;
import Models.Street;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class IssueOutpatientCardController {

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
    private TextField surnamePatientTextField;
    @FXML
    private TextField namePatientTextField;
    @FXML
    private TextField patronymicPatientTextField;

    @FXML
    private Button confirmOutPatientCardButton;
    @FXML
    private Button clearButton;

    @FXML
    private TextField phoneTextField;
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
    private ToggleGroup genderGroup;
    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize(){
        updateStreetComboBox();
        birthdayDatePicker.setValue(LocalDate.now());

        clearButton.setOnAction(event -> clearFields());

        confirmOutPatientCardButton.setOnAction(event -> addOutPatientCard());

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
    private void addOutPatientCard() {
        clientHandler.sendMessage("addOutPatientCard");
        String surnamePatient = surnamePatientTextField.getText().trim();
        String namePatient = namePatientTextField.getText().trim();
        String patronymicPatient = patronymicPatientTextField.getText().trim();
        String birthdayDate = birthdayDatePicker.getValue().toString();

        String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();
        Street street = streetComboBox.getValue();

        String houseNumber = houseNumberTextField.getText().trim();
        String flatNumber = flatNumberTextField.getText().trim();
        String corpusNumber = corpusNumberTextField.getText().trim();

        String phone = phoneTextField.getText().trim();

        String regex = ".*\\d+.*";
        String regexP = ".*\\D+.*";
        String regexPhone ="^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$";
        if(surnamePatient.equals("") || namePatient.equals("")
                || patronymicPatient.equals("") //|| street == null//
                ||!birthdayDatePicker.getValue().isBefore(LocalDate.now())
                || houseNumber.equals("") || flatNumber.equals("")
                || corpusNumber.equals("")  || phone.equals("")
                || surnamePatient.matches(regex) || namePatient.matches(regex)
                || patronymicPatient.matches(regex) || houseNumber.matches(regexP)
                || flatNumber.matches(regexP) || corpusNumber.matches(regexP)
                || phone.length() !=13 || !phone.matches(regexPhone)){

            callAlert("Не все поля введены корректно!");
        }
        else {
            Patient patient = new Patient();
            patient.setSurname(surnamePatient);
            patient.setName(namePatient);
            patient.setPatronymic(patronymicPatient);
            patient.setBirthday(birthdayDate);
            patient.setGender(gender);
            patient.setPhoneNumber(phone);
            Address address = new Address(street,Integer.parseInt(flatNumber),
                    Integer.parseInt(houseNumber),Integer.parseInt(corpusNumber));
            patient.setAddress(address);
            clientHandler.sendObject(patient);
            boolean isPatientAdded = (boolean) clientHandler.readObject();
            if (isPatientAdded) callAlert("Амбулаторная карта заведена");
            else  callAlert("Амбулаторная карта не была заведена. Попробуйте снова.");
        }
    }
    private void clearFields() {
        clientHandler.sendMessage("clearFields");
        surnamePatientTextField.setText("");
        namePatientTextField.setText("");
        patronymicPatientTextField.setText("");
        birthdayDatePicker.setValue(LocalDate.now());
        houseNumberTextField.setText("");
        flatNumberTextField.setText("");
        corpusNumberTextField.setText("");
        phoneTextField.setText("");
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
}
