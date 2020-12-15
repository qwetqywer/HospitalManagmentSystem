package DBHandlers;

import Configs.DBConfigs;
import Configs.DBConst;
import Models.Appointment;
import Models.Employee;
import Models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDBHandler extends DBHandler  {

    public ResultSet getAllAppointments(){
        resSet = null;
        select = "SELECT * FROM " + DBConst.APPOINTMENT_TABLE;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }


    public boolean addAppointment(Appointment appointment) throws  ClassNotFoundException {
        String insert = "INSERT INTO " + DBConst.APPOINTMENT_TABLE + "("
                + DBConst.APPOINTMENT_ID_EMPLOYEE + "," + DBConst.APPOINTMENT_DATE+ ","
                + DBConst.APPOINTMENT_TIME + "," + DBConst.APPOINTMENT_STATUS+
                ")" + "VALUES(?,?,?,?)";

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, appointment.getIdEmployee());
            prSt.setString(2, String.valueOf(appointment.getDate()));
            prSt.setString(3, String.valueOf(appointment.getTime()));
            prSt.setString(4, String.valueOf(appointment.getIntStatus()));
            prSt.executeUpdate();

            return true;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public int findPatientbyIdAppointment(int id){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.APPOINTMENT_TABLE + " WHERE "
                + DBConst.APPOINTMENT_ID + " = " + id;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        int idPatient = -1;
        try {
            if(resSet != null) {
                while (resSet.next()){
                    idPatient = resSet.getInt(DBConst.APPOINTMENT_ID_PATIENT);
                }
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idPatient;


    }

    public boolean editAppointment(Appointment appointment) {

        String update = "UPDATE " + DBConst.APPOINTMENT_TABLE +  " SET "
                + DBConst.APPOINTMENT_EPICRISIS + "='" + appointment.getEpicrisis() + "', "
                + DBConst.APPOINTMENT_STATUS + "=" + appointment.getIntStatus()
                +  " WHERE " + DBConst.APPOINTMENT_ID + " = " + appointment.getId();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DBConnection.getDbConnection().prepareStatement(update);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean addAppointmentWithoutOrder(Appointment appointment) {
        String insert = "INSERT INTO " + DBConst.APPOINTMENT_TABLE + "("
                + DBConst.APPOINTMENT_ID_EMPLOYEE + "," + DBConst.APPOINTMENT_DATE+ ","
                + DBConst.APPOINTMENT_TIME + "," + DBConst.APPOINTMENT_STATUS + ","
                + DBConst.APPOINTMENT_EPICRISIS + "," + DBConst.APPOINTMENT_ID_PATIENT+
                ")" + "VALUES(?,?,?,?,?,?)";

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(insert);
            prSt.setInt(1, appointment.getIdEmployee());
            prSt.setString(2, String.valueOf(LocalDate.now()));
            prSt.setString(3, String.valueOf(LocalTime.now()));
            prSt.setString(4, String.valueOf(appointment.getIntStatus()));
            prSt.setString(5, String.valueOf(appointment.getEpicrisis()));
            prSt.setString(6, String.valueOf(appointment.getIdPatient()));
            prSt.executeUpdate();

            return true;
        }
        catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void updateAppointment(Patient patient, Appointment appointment) {
        String update = "UPDATE " + DBConst.APPOINTMENT_TABLE + " SET "
                + DBConst.APPOINTMENT_STATUS + "=" + 1 + ", "
                + DBConst.APPOINTMENT_ID_PATIENT + "=" + patient.getId()+", "
                + DBConst.APPOINTMENT_ID_APPTYPE + "=" + appointment.getIdType()
                + " WHERE " + DBConst.APPOINTMENT_ID+ " = " + appointment.getId();

        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet findRecordsByDoctor(Employee doctor) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + DBConst.APPOINTMENT_TABLE + " WHERE "
                + DBConst.APPOINTMENT_ID_EMPLOYEE + " = " + doctor.getId();

        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }

    public ResultSet findRecordsByPatient(Patient patient) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + DBConst.APPOINTMENT_TABLE + " WHERE "
                + DBConst.APPOINTMENT_ID_PATIENT + " = " + patient.getId();

        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }
}
