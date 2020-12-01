package Controllers.AdminControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private TableView<?> receptionistTable;

    @FXML
    private TableColumn<?, ?> idReceptionistColumn;

    @FXML
    private TableColumn<?, ?> surnameReceptionistColumn;

    @FXML
    private TableColumn<?, ?> nameReceptionistColumn;

    @FXML
    private TableColumn<?, ?> patronymicReceptionistColumn;

    @FXML
    private TableColumn<?, ?> birthdayReceptionistColumn;

    @FXML
    private TableColumn<?, ?> addressReceptionistColumn;

    @FXML
    private TableColumn<?, ?> genderReceptionistColumn;

    @FXML
    private Button addReceptionistButton;

    @FXML
    private Button editReceptionistButton;

    @FXML
    private Button deleteReceptionistButton;

    @FXML
    private Button clearReceptionistButton;

    @FXML
    void initialize() {

    }
}