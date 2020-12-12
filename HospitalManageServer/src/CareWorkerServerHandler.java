import Configs.DBConst;
import DBHandlers.AppointmentDBHandler;
import DBHandlers.AppointmentTypeDBHandler;
import DBHandlers.PatientDBHandler;
import Models.Appointment;
import Models.Employee;
import Models.Patient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CareWorkerServerHandler {

    ObjectInputStream request; //Принятие
    ObjectOutputStream respond; //Отправка


    public CareWorkerServerHandler(ObjectInputStream request, ObjectOutputStream respond) {
        this.request = request;
        this.respond = respond;
    }

    public void updateAppointmentTable() {
        int check=0;
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        AppointmentTypeDBHandler appointmentTypeDBHandler = new AppointmentTypeDBHandler();
        ResultSet resSet = appointmentDBHandler.getAllAppointments();
        String nameType ="";
        String status ="";
        if(resSet != null){

            try {
                while (resSet.next())
                {
                    if(resSet.getDate(DBConst.APPOINTMENT_DATE).toLocalDate().equals(LocalDate.now())) {
                        System.out.println(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE));
                        if(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE) == Employee.mainEmployee.getId()){
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==1){
                                status = "Занят";
                            }
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==0){
                                status = "Свободен";
                            }
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==2){
                                status = "Закрыт";
                            }
                            nameType = appointmentTypeDBHandler.findRecordByID(resSet.getInt(DBConst.APPOINTMENT_ID_APPTYPE));
                            Appointment.listAppointments.add(new Appointment(resSet.getInt(DBConst.APPOINTMENT_ID),
                                    resSet.getString(DBConst.APPOINTMENT_DATE), resSet.getString(DBConst.APPOINTMENT_TIME),status
                                    ,nameType));
                            check = 1;

                        }
                    }
                }

                if(check == 0){

                   respond.writeObject(false);
                }
                else{
                    respond.writeObject(true);
                    respond.write(Appointment.listAppointments.size());

                    for(Appointment appointment : Appointment.listAppointments) {

                        respond.writeObject(appointment);
                    }
                  }


            }
            catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }


    }


    public void updateAppointmentTableByDate() {

        int check=0;
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        AppointmentTypeDBHandler appointmentTypeDBHandler = new AppointmentTypeDBHandler();
        ResultSet resSet = appointmentDBHandler.getAllAppointments();
        String nameType ="";
        String status ="";
        System.out.println("DEBUG"  + Employee.mainEmployee.getId());
        if(resSet != null){
            try {

                Appointment.listAppointments.clear();
                while (resSet.next())
                {

                        System.out.println(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE));
                        if(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE) == Employee.mainEmployee.getId()){
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==1){
                                status = "Занят";
                            }
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==0){
                                status = "Свободен";
                            }
                            if(resSet.getInt(DBConst.APPOINTMENT_STATUS)==2){
                                status = "Закрыт";
                            }
                            nameType = appointmentTypeDBHandler.findRecordByID(resSet.getInt(DBConst.APPOINTMENT_ID_APPTYPE));
                            Appointment.listAppointments.add(new Appointment(resSet.getInt(DBConst.APPOINTMENT_ID),
                                    resSet.getString(DBConst.APPOINTMENT_DATE), resSet.getString(DBConst.APPOINTMENT_TIME),status
                                    ,nameType));
                            check = 1;

                        }

                }

                if(check == 0){

                    respond.writeObject(false);
                }
                else{
                    respond.writeObject(true);

                    respond.write(Appointment.listAppointments.size());

                    for(Appointment appointment : Appointment.listAppointments) {

                        respond.writeObject(appointment);
                    }
                }


            }
            catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void updatePatientTable() {
    }

    public void getPatient(Appointment appointment) {

        PatientDBHandler patientDBHandler = new PatientDBHandler();
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        int idPatient = appointmentDBHandler.findPatientbyIdAppointment(appointment.getId());
        ResultSet resSet = patientDBHandler.getPatientById(idPatient);



        if(resSet != null){
            try {
                respond.writeObject(true);
                System.out.println("все оки");
                try {
                    Patient.listPatients.clear();
                    int i=0;
                    while (resSet.next())
                    {
                        Patient.listPatients.add(new Patient(resSet.getInt(DBConst.PATIENT_ID),
                            resSet.getString(DBConst.PATIENT_SURNAME), resSet.getString(DBConst.PATIENT_NAME),
                                resSet.getString(DBConst.PATIENT_PATRONYMIC),resSet.getString(DBConst.PATIENT_PHONE_NUMBER)));

                    }
                    System.out.println(Patient.listPatients.size());

                    if(Patient.listPatients.size() == 1)
                    {
                        respond.writeObject(true);
                        respond.write(Patient.listPatients.size());

                        for(Patient patient : Patient.listPatients) {

                            respond.writeObject(patient);
                        }

                    }
                    else respond.writeObject(false);
                }
                catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
