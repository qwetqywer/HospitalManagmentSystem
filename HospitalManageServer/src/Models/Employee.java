package Models;

import Configs.DBConst;
import DBHandlers.EmployeeDBHandler;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class Employee implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    public static Employee mainEmployee = new Employee();
    public static ArrayList<Employee> listEmployees = new ArrayList<>();
    private String surname;
    private String name;
    private String patronymic;
    private String gender;
    private int id;
    private String birthday;
    private String login;
    private String password;
    private int idAddress;
    private String nameSpecialty;
    private int amountOfAppointments;
    private int idSpecialty;
    private Address address;
    private String officeNumber;
    private String workTime;


    public Employee(Employee employee) {
        this.id= employee.id;
        this.surname = employee.surname;
        this.name = employee.name;
        this.patronymic = employee.patronymic;
        this.gender = employee.gender;
        this.birthday = employee.birthday;
        this.idSpecialty = employee.idSpecialty;
        this.login = employee.login;
        this.password = employee.password;
        this.workTime = employee.workTime;
    }



    public Employee() {

    }

    public Employee(String surname, String name, String patronymic, int amountOfAppointments) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.amountOfAppointments = amountOfAppointments;
    }

    public Employee(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public String getNameSpecialty() {
        return nameSpecialty;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }



    public Employee(int id, String surname, String name, String patronymic,
                    String birthday, String gender) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = gender;
        this.id = id;
        this.birthday = birthday;

    }

    public Employee(int id,String surname, String name, String patronymic,String birthday,
                    String gender,String officeNumber,String nameSpecialty, String workTime) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.officeNumber = officeNumber;
        this.nameSpecialty = nameSpecialty;
        this.workTime = workTime;

    }


    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getWorkTime() {
        return workTime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", gender='" + gender + '\'' +
                ", id=" + id +
                ", birthday='" + birthday + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", idAddress=" + idAddress +
                ", idSpecialty=" + idSpecialty +
                ", address=" + address +
                ", officeNumber='" + officeNumber + '\'' +
                ", workTime='" + workTime + '\'' +
                ", nameSpecialty=" + nameSpecialty +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }



    public int getIdSpecialty() {
        return idSpecialty;
    }

    public void setIdSpecialty(int idSpecialty) {
        this.idSpecialty = idSpecialty;
    }





    public boolean authorize() {
        EmployeeDBHandler dataHandler = new EmployeeDBHandler();
        ResultSet resultSet = dataHandler.getAllEmployees();
        if (resultSet == null) return false;
        else {
            try {
                while (resultSet.next()) {
                    if(this.login.equals(resultSet.getString(DBConst.EMPLOYEE_LOGIN))
                            && this.password.equals(resultSet.getString(DBConst.EMPLOYEE_PASSWORD)))
                    {
                        this.id = resultSet.getInt(DBConst.EMPLOYEE_ID);
                        this.idSpecialty = resultSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY);
                        this.login = resultSet.getString(DBConst.EMPLOYEE_LOGIN);
                        this.password = resultSet.getString(DBConst.EMPLOYEE_PASSWORD);
                        this.surname = resultSet.getString(DBConst.EMPLOYEE_SURNAME);
                        this.name = resultSet.getString(DBConst.EMPLOYEE_NAME);
                        this.patronymic = resultSet.getString(DBConst.EMPLOYEE_PATRONYMIC);
                        this.birthday = resultSet.getString(DBConst.EMPLOYEE_BIRTHDAY);
                        return true;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public Address getAddress() {
        return address;
    }

}
