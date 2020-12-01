package Controllers.AdminControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdministratorManageStaffController {

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
    private TableColumn<?, ?> birthdayDoctorColumn;

    @FXML
    private TableColumn<?, ?> addressDoctorColumn;

    @FXML
    private TableColumn<?, ?> specialtyDoctorColumn;

    @FXML
    private TableColumn<?, ?> RoomNumberDoctorColumn;

    @FXML
    private TableColumn<?, ?> genderDoctorColumn;

    @FXML
    private Button addDoctorButton;

    @FXML
    private Button editDoctorButton;

    @FXML
    private Button deleteDoctorButton;

    @FXML
    private Button clearDoctorButton;

    @FXML
    void initialize() {

    }

}
