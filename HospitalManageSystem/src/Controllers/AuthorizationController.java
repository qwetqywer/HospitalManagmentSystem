package Controllers;

import ServerHandlers.ClientHandler;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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
        client = new ClientHandler("127.0.0.1", "3000");
    }

}
