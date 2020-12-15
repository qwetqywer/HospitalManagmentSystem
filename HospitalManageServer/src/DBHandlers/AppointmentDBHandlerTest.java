package DBHandlers;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentDBHandlerTest {
    private static AppointmentDBHandler appointmentDBHandler;
    private static int idAppointment;
    private static int idPatient;

    @BeforeClass
    public static void init(){
        appointmentDBHandler = new AppointmentDBHandler();
        idAppointment = 2;
        idPatient = 1;
    }

    @Test
    public void findPatientbyIdAppointment() {
        assertEquals("findPatientbyIdAppointment",idPatient,
                appointmentDBHandler.findPatientbyIdAppointment(idAppointment));
    }
}