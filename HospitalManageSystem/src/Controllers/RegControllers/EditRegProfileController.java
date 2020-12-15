package Controllers.RegControllers;

import ClientHandlers.ClientHandler;
import Configs.FXMLConfigs;
import Models.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EditRegProfileController {

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
    private Button changePasswordButton;
    @FXML
    private Button changeLoginButton;
    @FXML
    private TextField newLoginChangeLoginTextField;
    @FXML
    private PasswordField passwordChangeLoginField;
    @FXML
    private PasswordField oldPasswordChangePasswordField;
    @FXML
    private PasswordField confirmPasswordChangePasswordField;
    @FXML
    private PasswordField newPasswordChangePasswordField;

    private final ClientHandler clientHandler = ClientHandler.getClient();

    @FXML
    void initialize(){
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

        changeLoginButton.setOnAction(event -> {
            clientHandler.sendMessage("changeLogin");
            Employee employee = new Employee();
            employee.setLogin(newLoginChangeLoginTextField.getText().trim());
            employee.setPassword(passwordChangeLoginField.getText().trim());
            clientHandler.sendObject(Employee.mainEmployee);
            clientHandler.sendObject(employee);
            boolean isEmployeeResSetFounded = (boolean) clientHandler.readObject();
            if(isEmployeeResSetFounded){
                boolean isEmployeeFounded = (boolean) clientHandler.readObject();
                if(isEmployeeFounded){
                    boolean isLoginChanged = (boolean) clientHandler.readObject();
                    if(isLoginChanged){
                        newLoginChangeLoginTextField.setText("");
                        passwordChangeLoginField.setText("");
                        callAlert("Логин успешно изменен.");
                    }
                    else callAlert("Ошибка изменения логина. Попробуйте снова.");
                }
                else callAlert("Ошибка изменения логина. Попробуйте снова.");

            }
            else callAlert("Ошибка изменения логина. Попробуйте снова.");

        });
        changePasswordButton.setOnAction(event -> {

            String oldPassword = oldPasswordChangePasswordField.getText().trim();
            String newPassword = newPasswordChangePasswordField.getText().trim();
            String confirmPassword = confirmPasswordChangePasswordField.getText().trim();
            if(oldPassword.equals(Employee.mainEmployee.getPassword()))
            {
                if(newPassword.equals(confirmPassword)){
                    clientHandler.sendMessage("changePassword");
                    Employee employee = new Employee();
                    employee.setPassword(newPassword);
                    clientHandler.sendObject(Employee.mainEmployee);
                    clientHandler.sendObject(employee);
                    boolean isEmployeeResSetFounded = (boolean) clientHandler.readObject();
                    if(isEmployeeResSetFounded){
                        boolean isEmployeeFounded = (boolean) clientHandler.readObject();
                        if(isEmployeeFounded){
                            boolean isLoginChanged = (boolean) clientHandler.readObject();
                            if(isLoginChanged){
                                oldPasswordChangePasswordField.setText("");
                                newPasswordChangePasswordField.setText("");
                                confirmPasswordChangePasswordField.setText("");
                                callAlert("Пароль успешно изменен.");
                            }
                            else callAlert("Ошибка изменения пароля. Попробуйте снова.");
                        }
                        else callAlert("Ошибка изменения пароля. Попробуйте снова.");

                    }
                    else callAlert("Ошибка изменения пароля. Попробуйте снова.");
                }
                else callAlert("Пароли не совпадают.");
            }
            else callAlert("Текущий пароль введен неверно.");

        });
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
