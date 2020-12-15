package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentType implements Serializable {
    public static ObservableList<AppointmentType> appointmentTypeList= FXCollections.observableArrayList();
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private String type;

    public AppointmentType(AppointmentType readObject) {
        this.id = readObject.id;
        this.type = readObject.type;
    }

    public static void update(ArrayList<AppointmentType> typeArrayList) {
        appointmentTypeList.clear();
        appointmentTypeList.addAll(typeArrayList);
    }

    @Override
    public String toString() {
        return  type ;
    }

    public int getId() {
        return id;
    }
}