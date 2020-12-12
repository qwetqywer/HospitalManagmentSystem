package DBHandlers;

import Models.Patient;
import javafx.scene.Parent;

import java.sql.ResultSet;

public interface PatientSQL {

    void deleteRecord(Patient patient);
    ResultSet getAllEmployees();
    ResultSet findRecord(Patient patient);
    void addRecord(Patient patient);
}
