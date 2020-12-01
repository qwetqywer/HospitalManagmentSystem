package Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdministratorMakeScheduleController {


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
    private TableView<?> doctorTable;

    @FXML
    private TableColumn<?, ?> idDoctorColumn;

    @FXML
    private TableColumn<?, ?> FullNameDoctorColumn;

    @FXML
    private TableColumn<?, ?> specialtyDoctorColumn;

    @FXML
    private TableColumn<?, ?> RoomNumberDoctorColumn;

    @FXML
    private DatePicker dateAppointmentField;

    @FXML
    private ComboBox<?> specialtyCheckBox;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TextField timeAppointmentField;
    @FXML
    void initialize() {

    }

}