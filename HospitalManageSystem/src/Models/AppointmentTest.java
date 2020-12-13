package Models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AppointmentTest {

    @Test
    public void update() {
        Appointment appointment1 = new Appointment(1,1,"2020-12-12","11:00","Боль в горле");
        Appointment appointment2 = new Appointment(2,2,"2020-13-13","12:00","Боль в животе");
        Appointment appointment3 = new Appointment(3,3,"2020-14-14","13:00","Головная боль");


        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        appointmentArrayList.add(appointment1);
        appointmentArrayList.add(appointment2);
        appointmentArrayList.add(appointment3);

        Appointment.update(appointmentArrayList);

        Assert.assertEquals(appointmentArrayList,Appointment.listAppointments);
    }
}