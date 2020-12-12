package DBHandlers;

import Configs.DBConfigs;
import Configs.DBConst;
import Models.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDBHandler extends DBConfigs {
    private ResultSet resSet;
    private String select;
    PreparedStatement prSt;

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
}
