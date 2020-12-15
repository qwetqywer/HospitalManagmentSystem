package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable {

    private static final long serialVersionUID = 1113799434508676095L;
    public static ArrayList<Patient> listPatients = new ArrayList<>();

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private Address address;
    private String birthday;
    private String gender;


    public Patient(Patient patient) {
        this.surname = patient.surname;
        this.name = patient.name;
        this.patronymic = patient.patronymic;
        this.phoneNumber = patient.phoneNumber;
        this.address =patient.address;
        this.birthday = patient.birthday;
        this.gender = patient.gender;
    }

    public Patient() {
    }

    public Patient(int id, String surname, String name, String patronymic, String phoneNumber) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }
    public String getPhoneNumber() { return phoneNumber; }
    public Address getAddress() { return address; }
    public String getBirthday() { return birthday; }
    public String getGender() { return gender; }
}