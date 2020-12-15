package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;

    public static ObservableList<Patient> listPatients =  FXCollections.observableArrayList();
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private Address address;
    private String gender;
    private String birthday;

    public Patient() {
    }

    public Patient(Patient patient) {
        this.surname = patient.surname;
        this.name = patient.name;
        this.patronymic = patient.patronymic;
        this.phoneNumber = patient.phoneNumber;
        this.address =patient.address;
        this.birthday = patient.birthday;
        this.gender = patient.gender;
        this.id = patient.id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setGender(String gender) { this.gender = gender; }

    public static void update(ArrayList<Patient> patientsArrayList) {
        listPatients.clear();
        listPatients.addAll(patientsArrayList);
    }
    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }
}