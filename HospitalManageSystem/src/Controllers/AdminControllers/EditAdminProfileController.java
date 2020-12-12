package Controllers.AdminControllers;

import Configs.FXMLConfigs;
import Models.Employee;
import ServerHandlers.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EditAdminProfileController {

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
        changeLoginButton.setOnAction(event -> {
            clientHandler.sendMessage("changeLogin");
            Employee employee = new Employee();
            employee.setLogin(newLoginChangeLoginTextField.getText().trim());
            employee.setPassword(passwordChangeLoginField.getText().trim());
            //clientHandler.sendObject(Employee.mainEmployee);
            clientHandler.sendObject(employee);
            boolean isEmployeeResSetFounded = (boolean) clientHandler.readObject();
            if(isEmployeeResSetFounded){
                boolean isEmployeeFounded = (boolean) clientHandler.readObject();
                if(isEmployeeFounded){
                    boolean isLoginChanged = (boolean) clientHandler.readObject();
                    if(isLoginChanged){
                        Employee.mainEmployee.setLogin(newLoginChangeLoginTextField.getText().trim());
                        newLoginChangeLoginTextField.setText("");
                        passwordChangeLoginField.setText("");
                        callAlert("Логин успешно изменен.");
                    }
                    else callAlert("Ошибка изменения логина. Попробуйте снова.!!!");
                }
                else callAlert("Ошибка изменения логина. Попробуйте снова.!!");

            }
            else callAlert("Ошибка изменения логина. Попробуйте снова.!");

        });
        changePasswordButton.setOnAction(event -> {

            String oldPassword = oldPasswordChangePasswordField.getText().trim();
            String newPassword = newPasswordChangePasswordField.getText().trim();
            String confirmPassword = confirmPasswordChangePasswordField.getText().trim();
            System.out.println(Employee.mainEmployee);
            if(oldPassword.equals(Employee.mainEmployee.getPassword()))
            {
                if(newPassword.equals(confirmPassword)){
                    clientHandler.sendMessage("changePassword");
                    Employee employee = new Employee();
                    employee.setPassword(newPassword);
                    //clientHandler.sendObject(Employee.mainEmployee);
                    clientHandler.sendObject(employee);
                    boolean isEmployeeResSetFounded = (boolean) clientHandler.readObject();
                    if(isEmployeeResSetFounded){
                        boolean isEmployeeFounded = (boolean) clientHandler.readObject();
                        if(isEmployeeFounded){
                            boolean isLoginChanged = (boolean) clientHandler.readObject();
                            if(isLoginChanged){
                                Employee.mainEmployee.setPassword(newPasswordChangePasswordField.getText().trim());
                                oldPasswordChangePasswordField.setText("");
                                newPasswordChangePasswordField.setText("");
                                confirmPasswordChangePasswordField.setText("");
                                callAlert("Пароль успешно изменен.");
                            }
                            else callAlert("Ошибка изменения пароля. Попробуйте снова!!!.");
                        }
                        else callAlert("Ошибка изменения пароля. Попробуйте снова!!.");

                    }
                    else callAlert("Ошибка изменения пароля. Попробуйте снова!.");
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
