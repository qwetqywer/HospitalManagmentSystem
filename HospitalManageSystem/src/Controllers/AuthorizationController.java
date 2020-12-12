package Controllers;

import Configs.FXMLConfigs;
import Models.Employee;
import ServerHandlers.ClientHandler;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AuthorizationController {

    private ClientHandler client;

    @FXML

    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button authorizationButton;

    @FXML

    public void initialize(){
        loginField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        authorizationButton.setOnAction(event -> {



            Employee.mainEmployee.setLogin(loginField.getText().trim());
            Employee.mainEmployee.setPassword(passwordField.getText().trim());
            ClientHandler clientHandler = ClientHandler.getClient();

            clientHandler.sendObject(Employee.mainEmployee);
            /*Авторизовался или нет - принимает ответ с сервера*/
            boolean isAuthorize = (boolean)clientHandler.readObject();

            if(isAuthorize) {
                /*Найден ли пользователь с таким персональным аккаунтом*/
                boolean isEmployeeFound = (boolean)clientHandler.readObject();
                if(isEmployeeFound){
                    Employee.mainEmployee = new Employee((Employee) clientHandler.readObject());
                    Employee.mainEmployee.setLogin(loginField.getText().trim());
                    Employee.mainEmployee.setPassword(passwordField.getText().trim());
                    System.out.println(Employee.mainEmployee.getId());

                    switch (Employee.mainEmployee.getIdSpecialty()){
                        case 1 :{
                            authorizationButton.getScene().getWindow().hide();
                            changeScene("../Views/Reg/regAccount.fxml");
                            break;
                        }
                        case 2:{
                            authorizationButton.getScene().getWindow().hide();
                            changeScene("../Views/Admin/administratorAccount.fxml");
                            break;
                        }
                        default: {
                            authorizationButton.getScene().getWindow().hide();
                            changeScene("../Views/CareWorker/careWorkerAccount.fxml");
                            break;
                        }
                    }
                }
                else callAlert("Не удалось определить категорию работника");
            }
            else  callAlert("Ошибка авторизации. Проверьте правильность введенных логина и пароля.");
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
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
