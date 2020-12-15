package DBHandlers;


import Configs.DBConfigs;
import Configs.DBConst;
import Models.Address;
import Models.Employee;
import Models.Specialty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDBHandler  extends DBHandler  {




    public ResultSet getAllEmployees() {

        resSet = null;
        select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE;
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }



    public boolean addEmployee(Employee employee) {
        String insertAddress = "INSERT INTO " + DBConst.ADDRESS_TABLE + "("
                + DBConst.ADDRESS_ID_STREET + "," + DBConst.ADDRESS_FLAT_NUMBER+ ","
                + DBConst.ADDRESS_HOUSE_NUMBER + "," + DBConst.ADDRESS_CORPUS+
                ")" + "VALUES(?,?,?,?)";

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(insertAddress);
            prSt.setString(1, String.valueOf(employee.getAddress().getStreet().getId()));
            prSt.setString(2, String.valueOf(employee.getAddress().getFlatNumber()));
            prSt.setString(3, String.valueOf(employee.getAddress().getHouseNumber()));
            prSt.setString(4, String.valueOf(employee.getAddress().getCorpus()));
            prSt.executeUpdate();

            String select = "SELECT * FROM " + DBConst.ADDRESS_TABLE  + " ORDER BY "+ DBConst.ADDRESS_ID + " DESC LIMIT 1";
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            int idAddress = -1;
            try {
                if(resSet != null) {
                    while (resSet.next()){
                        idAddress = resSet.getInt(DBConst.ADDRESS_ID);
                    }
                }

            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            String insertEmployee = "INSERT INTO " + DBConst.EMPLOYEE_TABLE + "("
                    + DBConst.EMPLOYEE_SURNAME + "," + DBConst.EMPLOYEE_NAME + ","
                    + DBConst.EMPLOYEE_PATRONYMIC + "," + DBConst.EMPLOYEE_ID_SPECIALTY + ","
                    + DBConst.EMPLOYEE_BIRTHDAY + "," + DBConst.EMPLOYEE_ID_ADDRESS + ","
                    + DBConst.EMPLOYEE_GENDER + "," + DBConst.EMPLOYEE_LOGIN + ","
                    + DBConst.EMPLOYEE_PASSWORD
                    + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";
            prSt = DBConnection.getDbConnection().prepareStatement(insertEmployee);
            prSt.setString(1, employee.getSurname());
            prSt.setString(2, employee.getName());
            prSt.setString(3, employee.getPatronymic());
            prSt.setString(4, String.valueOf(employee.getIdSpecialty()));
            prSt.setString(5, employee.getBirthday());
            prSt.setString(6, String.valueOf(idAddress));
            prSt.setString(7, employee.getGender());
            prSt.setString(8, employee.getLogin());
            prSt.setString(9, employee.getPassword());
            prSt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }



    public boolean addEmployeeWithNewSpecialty(Employee employee, Specialty specialty){
        String insertAddress = "INSERT INTO " + DBConst.ADDRESS_TABLE + "("
                + DBConst.ADDRESS_ID_STREET + "," + DBConst.ADDRESS_FLAT_NUMBER+ ","
                + DBConst.ADDRESS_HOUSE_NUMBER + "," + DBConst.ADDRESS_CORPUS+
                ")" + "VALUES(?,?,?,?)";

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(insertAddress);
            prSt.setString(1, String.valueOf(employee.getAddress().getStreet().getId()));
            prSt.setString(2, String.valueOf(employee.getAddress().getFlatNumber()));
            prSt.setString(3, String.valueOf(employee.getAddress().getHouseNumber()));
            prSt.setString(4, String.valueOf(employee.getAddress().getCorpus()));
            prSt.executeUpdate();

            String select = "SELECT * FROM " + DBConst.ADDRESS_TABLE + " ORDER BY " + DBConst.ADDRESS_ID + " DESC LIMIT 1";
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            int idAddress = -1;
            try {
                if (resSet != null) {
                    while (resSet.next()) {
                        idAddress = resSet.getInt(DBConst.ADDRESS_ID);
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            String insertSpecialty = "INSERT INTO " + DBConst.SPECIALTY_TABLE + "("
                    + DBConst.SPECIALTY_NAME + ")" + "VALUES(?)";

                prSt = DBConnection.getDbConnection().prepareStatement(insertSpecialty);
                prSt.setString(1, String.valueOf(specialty.getName()));
                prSt.executeUpdate();

                String selectOrder = "SELECT * FROM " + DBConst.SPECIALTY_TABLE + " ORDER BY " + DBConst.SPECIALTY_ID + " DESC LIMIT 1";
                prSt = DBConnection.getDbConnection().prepareStatement(selectOrder);
                ResultSet resultSet = prSt.executeQuery();
                int idSpecialty = -1;
                try {
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            idSpecialty = resultSet.getInt(DBConst.SPECIALTY_ID);
                        }
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                String insertEmployee = "INSERT INTO " + DBConst.EMPLOYEE_TABLE + "("
                        + DBConst.EMPLOYEE_SURNAME + "," + DBConst.EMPLOYEE_NAME + ","
                        + DBConst.EMPLOYEE_PATRONYMIC + "," + DBConst.EMPLOYEE_ID_SPECIALTY + ","
                        + DBConst.EMPLOYEE_BIRTHDAY + "," + DBConst.EMPLOYEE_ID_ADDRESS + ","
                        + DBConst.EMPLOYEE_GENDER + "," + DBConst.EMPLOYEE_LOGIN + ","
                        + DBConst.EMPLOYEE_PASSWORD
                        + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";
                prSt = DBConnection.getDbConnection().prepareStatement(insertEmployee);
                prSt.setString(1, employee.getSurname());
                prSt.setString(2, employee.getName());
                prSt.setString(3, employee.getPatronymic());
                prSt.setString(4, String.valueOf(idSpecialty));
                prSt.setString(5, employee.getBirthday());
                prSt.setString(6, String.valueOf(idAddress));
                prSt.setString(7, employee.getGender());
                prSt.setString(8, employee.getLogin());
                prSt.setString(9, employee.getPassword());
                prSt.executeUpdate();
                return true;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public ResultSet findRecordByID(int id) {
        resSet = null;
        select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_ID_SPECIALTY + " = " + id;;
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }



    public  boolean editEmployee(Employee employee){
        String updateAddress = "UPDATE " + DBConst.ADDRESS_TABLE +  " SET "
                + DBConst.ADDRESS_CORPUS + "= " + employee.getAddress().getCorpus() + " , "
                + DBConst.ADDRESS_FLAT_NUMBER + "= " + employee.getAddress().getFlatNumber() + " , "
                + DBConst.ADDRESS_HOUSE_NUMBER + "= " + employee.getAddress().getHouseNumber() + " , "
                + DBConst.ADDRESS_ID_STREET + "= " +  employee.getAddress().getIdStreet() +
                " WHERE " + DBConst.ADDRESS_ID + " = " + employee.getAddress().getId();

        PreparedStatement prSt;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(updateAddress);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String update = "UPDATE " + DBConst.EMPLOYEE_TABLE +  " SET "
                + DBConst.EMPLOYEE_SURNAME + "='" + employee.getSurname() + "', "
                + DBConst.EMPLOYEE_NAME + "='" + employee.getName() + "', "
                + DBConst.EMPLOYEE_PATRONYMIC + "='" + employee.getPatronymic() + "', "
                + DBConst.EMPLOYEE_GENDER + "='" + employee.getGender() + "', "
                + DBConst.EMPLOYEE_BIRTHDAY + "='" + employee.getBirthday()
                + "' WHERE " + DBConst.EMPLOYEE_ID + " = " + employee.getId();
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
    public void deleteEmployee(Employee employee) {
        String deleteEmployee = "DELETE FROM " +DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_ID + " = " + employee.getId();
        PreparedStatement prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(deleteEmployee);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editPasswordRecord(Employee employeeLP) {
        String update = "UPDATE " + DBConst.EMPLOYEE_TABLE + " SET "
                + DBConst.EMPLOYEE_PASSWORD + "='" + employeeLP.getPassword() + "'"
                + " WHERE " + DBConst.EMPLOYEE_ID  + " = " + employeeLP.getId();
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet findRecordByLogin(String login) {
        resSet = null;
        select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_LOGIN + " = '" + login + "'";
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }

    public void editLoginRecord(Employee employeeLP) {
        String update = "UPDATE " + DBConst.EMPLOYEE_TABLE + " SET "
                + DBConst.EMPLOYEE_LOGIN + "='" + employeeLP.getLogin() + "'"
                + " WHERE " + DBConst.EMPLOYEE_ID  + " = " + employeeLP.getId();
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean updateWorkTimeAndOfficeNumber(Employee employee) {

        String update = "UPDATE " + DBConst.EMPLOYEE_TABLE + " SET "
                + DBConst.EMPLOYEE_OFFICE_NUMBER + "='" +  employee.getOfficeNumber() + "', "
                + DBConst.EMPLOYEE_WORK_TIME + " ='" + employee.getWorkTime()
                + "' WHERE " + DBConst.EMPLOYEE_ID  + " = " + employee.getId();
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public ResultSet findRecordByPassword(String password) {

        resSet = null;
        select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_PASSWORD + " = '" + password + "'";
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        return resSet;
    }

    public Employee findRecordByEmployeeId(int idEmployee) {
        resSet = null;
        select = "SELECT * FROM " + DBConst.EMPLOYEE_TABLE + " WHERE "
                + DBConst.EMPLOYEE_ID + " = " + idEmployee;
        prSt = null;
        try {
            prSt = DBConnection.getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }
        if (resSet != null) {
            try {
                Employee employee = new Employee();
                while (resSet.next()) {
                    employee.setId(resSet.getInt(DBConst.EMPLOYEE_ID));
                    employee.setSurname(resSet.getString(DBConst.EMPLOYEE_SURNAME));
                    employee.setName(resSet.getString(DBConst.EMPLOYEE_NAME));
                    employee.setPatronymic(resSet.getString(DBConst.EMPLOYEE_PATRONYMIC));
                    employee.setOfficeNumber(resSet.getString(DBConst.EMPLOYEE_OFFICE_NUMBER));
                }
                return employee;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}



