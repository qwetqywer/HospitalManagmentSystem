package DBHandlers;

import Models.Specialty;

import java.sql.ResultSet;

public interface SpecialtySQL {
    String findRecordByID(int id);
    int findRecordByName(String name);
    int addSpecialty(Specialty specialty);
    ResultSet getAllSpecialties();

}
