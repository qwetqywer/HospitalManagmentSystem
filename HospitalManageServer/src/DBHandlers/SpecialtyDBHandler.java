package DBHandlers;

import Configs.DBConst;
import Models.Specialty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialtyDBHandler  extends DBHandler{
    public String findRecordByID(int id) {

        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.SPECIALTY_TABLE + " WHERE "
                + DBConst.SPECIALTY_ID + " = " + id;
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
                    return  resSet.getString(DBConst.SPECIALTY_NAME);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }


    public int findRecordByName(String name){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + DBConst.SPECIALTY_TABLE + " WHERE "
                + DBConst.SPECIALTY_NAME + " = ?";
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            prSt.setString(1, String.valueOf(name));
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        int idSpecialty = -1;
        try {
            if(resSet != null) {
                while (resSet.next()){
                    idSpecialty = resSet.getInt(DBConst.SPECIALTY_ID);
                }
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idSpecialty;
    }


    public int addSpecialty(Specialty specialty){
        int idSpecialty = -1;
        String insertSpecialty = "INSERT INTO " + DBConst.SPECIALTY_TABLE + "("
                + DBConst.SPECIALTY_NAME + ")" + "VALUES(?)";

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(insertSpecialty);
            prSt.setString(1, String.valueOf(specialty.getName()));
            prSt.executeUpdate();

            String select = "SELECT * FROM " + DBConst.SPECIALTY_TABLE  + " ORDER BY "+ DBConst.SPECIALTY_ID + " DESC LIMIT 1";
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();

            try {
                if(resSet != null) {
                    while (resSet.next()){
                        idSpecialty = resSet.getInt(DBConst.SPECIALTY_ID);
                    }
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return idSpecialty;
    }

    public ResultSet getAllSpecialties() {

        ResultSet resSet = null;

        String select = "SELECT * FROM " + DBConst.SPECIALTY_TABLE;
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