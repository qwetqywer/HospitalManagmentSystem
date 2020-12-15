package Controllers.CareWorkerControllers;

import Configs.AlertScene;
import Configs.ChangeScene;
import Configs.FXMLConfigs;
import Models.Employee;
import ClientHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditCareWorkerProfileController {
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

        desktopCareWorkerButton.setOnAction(event -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("desktopCareWorker");
            ChangeScene.change(FXMLConfigs.careWorkerAccount,getClass());

        });


        getSceduleCareWorkerButton.setOnAction((event -> {
            getSceduleCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("getSchedule");
            ChangeScene.change(FXMLConfigs.careWorkerSchedule,getClass());
        }));


        issueAppointment.setOnAction(actionEvent -> {
            issueAppointment.getScene().getWindow().hide();
            clientHandler.sendMessage("issueAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerIssueAppointment,getClass());
        });


        returnBackButton.setOnAction(event -> {
            returnBackButton.getScene().getWindow().hide();
            clientHandler.sendMessage("returnBack");
            ChangeScene.change(FXMLConfigs.authorization,getClass());
        });


        startAppointmentWithoutOrderButton.setOnAction(actionEvent -> {
            desktopCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startWithoutOrder");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointmentWithoutOrder,getClass());

        });
        editCareWorkerProfileButton.setOnAction(actionEvent -> {
            editCareWorkerProfileButton.getScene().getWindow().hide();
            clientHandler.sendMessage("editCareWorkerProfile");
            ChangeScene.change(FXMLConfigs.careWorkerEditAccount,getClass());
        });

        getPatientsCareWorkerButton.setOnAction(actionEvent -> {
            getPatientsCareWorkerButton.getScene().getWindow().hide();
            clientHandler.sendMessage("startAppointment");
            ChangeScene.change(FXMLConfigs.careWorkerStartAppointment,getClass());
        });

        changePasswordButton.setOnAction(event -> changeCWPassword());
        changeLoginButton.setOnAction(event -> changeCWLogin());

    }

    private void changeCWLogin() {
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
                    AlertScene.callAlert("Логин успешно изменен.");
                }
                else  AlertScene.callAlert("Ошибка изменения логина. Попробуйте снова.");
            }
            else  AlertScene.callAlert("Ошибка изменения логина. Попробуйте снова.");

        }
        else  AlertScene.callAlert("Ошибка изменения логина. Попробуйте снова.");
    }

    private void changeCWPassword() {
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
                            AlertScene.callAlert("Пароль успешно изменен.");
                        }
                        else AlertScene.callAlert("Ошибка изменения пароля. Попробуйте снова.");
                    }
                    else AlertScene.callAlert("Ошибка изменения пароля. Попробуйте снова.");

                }
                else AlertScene.callAlert("Ошибка изменения пароля. Попробуйте снова.");
            }
            else AlertScene.callAlert("Пароли не совпадают.");
        }
        else AlertScene.callAlert("Текущий пароль введен неверно.");
    }

}

