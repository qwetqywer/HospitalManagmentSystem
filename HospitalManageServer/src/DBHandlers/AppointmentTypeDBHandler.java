package DBHandlers;

import Configs.DBConfigs;
import Configs.DBConst;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentTypeDBHandler  extends DBHandler {


    public ResultSet getAllTypes(){
        resSet = null;
        select = "SELECT * FROM " + DBConst.APPOINTMENT_TYPE_TABLE;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }

    public String findRecordByID(int id) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.APPOINTMENT_TYPE_TABLE + " WHERE "
                + DBConst.APPOINTMENT_TYPE_ID + " = " + id;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        if(resSet!=null)
        {
            try {
                while (resSet.next()) {
                    return  resSet.getString(DBConst.APPOINTMENT_TYPE_NAME);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
