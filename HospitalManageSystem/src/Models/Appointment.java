package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Appointment implements Serializable {
    public static ObservableList<Appointment> listAppointments =  FXCollections.observableArrayList();
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private int idEmployee;
    private String date;
    private String time;
    private String epicrisis;
    private String status;
    private String nameType;
    private int intStatus;
    private int idPatient;

    public int getId() {
        return id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getNameType() {
        return nameType;
    }

    public Appointment(int id, int idEmployee, String date, String time, String epicrisis, String status, int intStatus) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.date = date;
        this.time = time;
        this.epicrisis = epicrisis;
        this.status = status;
        this.intStatus = intStatus;
    }

    public Appointment(Appointment appointment) {
        this.id = appointment.id;
        this.idEmployee = appointment.idEmployee;
        this.date = appointment.date;
        this.time = appointment.time;
        this.epicrisis = appointment.epicrisis;
        this.status = appointment.status;
        this.nameType = appointment.nameType;
        this.intStatus = appointment.intStatus;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public Appointment() {

    }

    public static void update(ArrayList<Appointment> appointmentArrayList) {
        listAppointments.clear();
        listAppointments.addAll(appointmentArrayList);
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEpicrisis() {
        return epicrisis;
    }

    public void setEpicrisis(String epicrisis) {
        this.epicrisis = epicrisis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String  status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", idEmployee=" + idEmployee +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
