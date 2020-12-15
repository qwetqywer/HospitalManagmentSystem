package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;

    public static ObservableList<Employee> listEmployees =  FXCollections.observableArrayList();
    public static Employee mainEmployee = new Employee();
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
    private int idSpecialty;
    private Address address;
    private String officeNumber;
    private String workTime;
    private int amountOfAppointments;



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
        this.officeNumber = employee.officeNumber;
        this.nameSpecialty = employee.nameSpecialty;
        this.amountOfAppointments  = employee.amountOfAppointments;
    }

    public Employee(){}

    public int getAmountOfAppointments() {
        return amountOfAppointments;
    }

    public void setAmountOfAppointments(int amountOfAppointments) {
        this.amountOfAppointments = amountOfAppointments;
    }

    public static void update(ArrayList<Employee> employeesArrayList) {
        listEmployees.clear();
        listEmployees.addAll(employeesArrayList);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public String getNameSpecialty() {
        return nameSpecialty;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public void setNameSpecialty(String nameSpecialty) {
        this.nameSpecialty = nameSpecialty;
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

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public int getIdSpecialty() {
        return idSpecialty;
    }

    public void setIdSpecialty(int idSpecialty) {
        this.idSpecialty = idSpecialty;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return  surname + ' ' +
                name + ' ' +
                patronymic + ", " +
                "№ кабинета - " + officeNumber;
    }
}
