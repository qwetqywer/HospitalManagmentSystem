package DBHandlers;

import Configs.DBConst;

import java.lang.constant.Constable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDBHandler  extends DBHandler {
    public ResultSet getAllStreetRecords(){

        ResultSet resSet = null;

        String select = "SELECT * FROM " + DBConst.STREET_TABLE;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return resSet;
    }

    public int findAddressByIdEmployee(int id){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_ID + " = " + id;
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
                    return  resSet.getInt(DBConst.EMPLOYEE_ID_ADDRESS);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;

    }

    public  ResultSet findAddressById(int id){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.ADDRESS_TABLE + " WHERE "
                + DBConst.ADDRESS_ID + " = " + id;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }

    public ResultSet findStreetById(int id){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.STREET_TABLE + " WHERE "
                + DBConst.STREET_ID + " = " + id;
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