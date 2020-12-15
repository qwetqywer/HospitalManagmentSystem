package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Appointment implements Serializable {
    public static ArrayList<Appointment> listAppointments = new ArrayList<Appointment>();
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private int idEmployee;
    private int idPatient;
    private int idType;
    private String date;
    private String time;
    private String epicrisis;
    private String status;
    private String nameType;
    private int intStatus;



    public Appointment(int id, int idEmployee, String date, String time, String epicrisis, String status, int intStatus) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.date = date;
        this.time = time;
        this.epicrisis = epicrisis;
        this.status = status;
        this.intStatus = intStatus;
    }

    public Appointment() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdType() {
        return idType;
    }

    public Appointment(String date, String epicrisis) {
        this.date = date;
        this.epicrisis = epicrisis;
    }

    public int getId() {
        return id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public Appointment(Appointment appointment) {
        this.id = appointment.id;
        this.idEmployee = appointment.idEmployee;
        this.date = appointment.date;
        this.time = appointment.time;
        this.epicrisis = appointment.epicrisis;
        this.status = appointment.status;
        this.intStatus = appointment.intStatus;
    }

    public Appointment(String date) {
        this.date = date;
    }



    public Appointment(int id, String date, String time, String status, String nameType) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.nameType = nameType;


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

    public void setStatus(String status) {
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
