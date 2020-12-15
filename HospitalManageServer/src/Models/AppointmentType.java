package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentType implements Serializable {
    public AppointmentType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public static ArrayList<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>();
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private String type;
}