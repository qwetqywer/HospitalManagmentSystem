package ServerHandlers;

import Configs.DBConst;
import DBHandlers.*;
import Models.*;

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

    public void updateAppointmentTable() throws IOException {
        int check=0;
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        AppointmentTypeDBHandler appointmentTypeDBHandler = new AppointmentTypeDBHandler();
        ResultSet resSet = appointmentDBHandler.getAllAppointments();
        String nameType ="";
        String status ="";
        if(resSet != null){
            try {
                respond.writeObject(true);
                Appointment.listAppointments.clear();
                while (resSet.next())
                {
                    if(resSet.getDate(DBConst.APPOINTMENT_DATE).toLocalDate().equals(LocalDate.now())) {
                        System.out.println(Employee.mainEmployee.getId());
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
        }else{
            respond.writeObject(false);
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
                respond.writeObject(true);
                Appointment.listAppointments.clear();
                while (resSet.next())
                {
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



    public void getPatient() throws IOException, ClassNotFoundException {
        Appointment appointment = (Appointment) request.readObject();
        PatientDBHandler patientDBHandler = new PatientDBHandler();
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        int idPatient = appointmentDBHandler.findPatientbyIdAppointment(appointment.getId());
        ResultSet resSet = patientDBHandler.getPatientById(idPatient);



        if(resSet != null){
            try {
                respond.writeObject(true);
                try {
                    Patient.listPatients.clear();
                    int i=0;
                    while (resSet.next())
                    {
                        Patient.listPatients.add(new Patient(resSet.getInt(DBConst.PATIENT_ID),
                            resSet.getString(DBConst.PATIENT_SURNAME), resSet.getString(DBConst.PATIENT_NAME),
                                resSet.getString(DBConst.PATIENT_PATRONYMIC),resSet.getString(DBConst.PATIENT_PHONE_NUMBER)));

                    }

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

    public void updateAppointment() throws IOException, ClassNotFoundException {
        Appointment appointment = (Appointment) request.readObject();
        try {
            System.out.println(appointment.getId() + appointment.getEpicrisis() + appointment.getIntStatus());
            AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
            boolean isAppointmentEdited = appointmentDBHandler.editAppointment(appointment);
            respond.writeObject(isAppointmentEdited);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getDiseaseHistory() throws IOException, ClassNotFoundException {
        Patient patient = (Patient) request.readObject();
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        ResultSet resultSet = appointmentDBHandler.getAllAppointments();
        if(resultSet != null){
            try {
                respond.writeObject(true);
                Appointment.listAppointments.clear();
                while (resultSet.next())
                {
                    if (resultSet.getInt(DBConst.APPOINTMENT_ID_PATIENT) == patient.getId()){
                        Appointment.listAppointments.add(new Appointment(resultSet.getString(DBConst.APPOINTMENT_DATE),
                                resultSet.getString(DBConst.APPOINTMENT_EPICRISIS)));
                    }
                }
                    respond.write(Appointment.listAppointments.size());
                    for(Appointment appointment : Appointment.listAppointments) {
                        respond.writeObject(appointment);
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

    public void searchPatient() throws IOException, ClassNotFoundException {
        Patient patient = (Patient) request.readObject();
        PatientDBHandler patientDBHandler = new PatientDBHandler();
        ResultSet resSet = patientDBHandler.getAllPatients();
        if(resSet != null){
            try {
                respond.writeObject(true);
                Patient.listPatients.clear();
                while (resSet.next())
                {
                    if(resSet.getString(DBConst.PATIENT_SURNAME).equals(patient.getSurname())){
                        if(resSet.getString(DBConst.PATIENT_NAME).equals(patient.getName())){
                            if(resSet.getString(DBConst.PATIENT_PATRONYMIC).equals(patient.getPatronymic())){
                                Patient.listPatients.add(new Patient(resSet.getInt(DBConst.PATIENT_ID),
                                        resSet.getString(DBConst.PATIENT_SURNAME), resSet.getString(DBConst.PATIENT_NAME),
                                        resSet.getString(DBConst.PATIENT_PATRONYMIC),resSet.getString(DBConst.PATIENT_PHONE_NUMBER)));
                            }
                        }
                    }
                }

                if(Patient.listPatients.size() == 1){
                    respond.writeObject(true);
                    respond.write(Patient.listPatients.size());

                    for(Patient patients : Patient.listPatients) {

                        respond.writeObject(patients);
                    }
                }
                else
                {
                    respond.writeObject(false);
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

    public void addAppointment() throws IOException, ClassNotFoundException {
        Appointment appointment = (Appointment) request.readObject();
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        boolean isAdd = appointmentDBHandler.addAppointmentWithoutOrder(appointment);
        respond.writeObject(isAdd);
    }

    public void updateSpecialtiesComboBox() {
        SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
        ResultSet resSet = specialtyDBHandler.getAllSpecialties();
        if(resSet != null){
            try {
                respond.writeObject(true);
                Specialty.listSpecialties.clear();
                while (resSet.next())
                {
                    if(resSet.getInt(DBConst.SPECIALTY_ID)>2){
                        Specialty.listSpecialties.add(new Specialty(resSet.getInt(DBConst.SPECIALTY_ID),
                                resSet.getString(DBConst.SPECIALTY_NAME)));
                    }

                }

                respond.write(Specialty.listSpecialties.size());

                for(Specialty specialty : Specialty.listSpecialties) {
                    respond.writeObject(specialty);
                }

            }catch (IOException | SQLException e) {
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

    public void updateAppointmentTypeComboBox() {
        AppointmentTypeDBHandler appointmentTypeDBHandler = new AppointmentTypeDBHandler();
        ResultSet resSet =  appointmentTypeDBHandler.getAllTypes();
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {
                    AppointmentType.appointmentTypeList.clear();
                    while (resSet.next()) {
                        AppointmentType.appointmentTypeList.add(new AppointmentType(resSet.getInt(DBConst.APPOINTMENT_TYPE_ID),
                                resSet.getString(DBConst.APPOINTMENT_TYPE_NAME)));
                    }
                    respond.writeObject(AppointmentType.appointmentTypeList.size());
                    for (AppointmentType item: AppointmentType.appointmentTypeList ){
                        respond.writeObject(item);
                    }

                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendAllEmployeeRecords() {
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        ResultSet resSet = employeeDBHandler.getAllEmployees();
        if (resSet != null) {
            try {
                respond.writeObject(true);
                Employee.listEmployees.clear();
                while (resSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resSet.getInt(DBConst.EMPLOYEE_ID));
                    employee.setSurname(resSet.getString(DBConst.EMPLOYEE_SURNAME));
                    employee.setName(resSet.getString(DBConst.EMPLOYEE_NAME));
                    employee.setPatronymic(resSet.getString(DBConst.EMPLOYEE_PATRONYMIC));
                    employee.setOfficeNumber(resSet.getString(DBConst.EMPLOYEE_OFFICE_NUMBER));
                    employee.setWorkTime(resSet.getString(DBConst.EMPLOYEE_WORK_TIME));
                    employee.setIdSpecialty(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY));
                    Employee.listEmployees.add(employee);
                }
                    System.out.println(Employee.listEmployees.size());
                    respond.write(Employee.listEmployees.size());

                    for (Employee item: Employee.listEmployees ){
                        respond.writeObject(item);
                    }
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }

        } else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendALLAppointment() throws IOException, ClassNotFoundException {
        Employee doctor = (Employee) request.readObject();
        System.out.println(doctor.getSurname());
        AppointmentDBHandler dataHandler = new AppointmentDBHandler();
        ResultSet resSet = dataHandler.findRecordsByDoctor(doctor);
        if (resSet != null) {
            try {
                respond.writeObject(true);
                Appointment.listAppointments.clear();
                while (resSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(resSet.getInt(DBConst.APPOINTMENT_ID));
                    appointment.setIdEmployee(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE));
                    appointment.setDate(resSet.getString(DBConst.APPOINTMENT_DATE));
                    appointment.setTime(resSet.getString(DBConst.APPOINTMENT_TIME));
                    int status = resSet.getInt(DBConst.APPOINTMENT_STATUS);
                    if (status == 0) {
                        appointment.setIntStatus(0);
                    }
                    else {
                        appointment.setIntStatus(1);
                    }
                    Appointment.listAppointments.add(appointment);
                }
                respond.write(Appointment.listAppointments.size());
                for (Appointment appointment : Appointment.listAppointments) {
                    respond.writeObject(appointment);
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAllPatients() {

        PatientDBHandler patientDBHandler = new PatientDBHandler();
        ResultSet resSet = patientDBHandler.getAllPatients();
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {
                    Patient.listPatients.clear();
                    int a = 0;
                    while (resSet.next()) {
                        Patient patientFounded = new Patient();
                        patientFounded.setSurname(resSet.getString(DBConst.PATIENT_SURNAME));
                        patientFounded.setName(resSet.getString(DBConst.PATIENT_NAME));
                        patientFounded.setPatronymic(resSet.getString(DBConst.PATIENT_PATRONYMIC));
                        patientFounded.setId(resSet.getInt(DBConst.PATIENT_ID));
                        Patient.listPatients.add(patientFounded);
                        System.out.println(patientFounded);
                        a++;

                    }
                    if (a == 0) {
                        respond.writeObject(false);
                    } else {
                        respond.writeObject(true);
                        respond.writeObject(Patient.listPatients);
                    }
                    System.out.println(a);

                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeLogin(Employee employeeLP) {
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        ResultSet resSet = employeeDBHandler.findRecordByLogin(Employee.mainEmployee.getLogin());
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {
                    Employee.listEmployees.clear();
                    while (resSet.next()) {
                        Employee employee = new Employee();
                        employee.setPassword(resSet.getString(DBConst.EMPLOYEE_PASSWORD));
                        employeeLP.setId(resSet.getInt(DBConst.EMPLOYEE_ID));
                        Employee.listEmployees.add(employee);
                    }
                    System.out.println(Employee.listEmployees.size());
                    if (Employee.listEmployees.size() == 1) {
                        respond.writeObject(true);

                        if (Employee.listEmployees.get(0).getPassword().equals(employeeLP.getPassword())) {
                            respond.writeObject(true);
                            employeeDBHandler.editLoginRecord(employeeLP);
                        } else respond.writeObject(false);
                    } else respond.writeObject(false);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void changePassword(Employee employeeLP) {
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        ResultSet resSet = employeeDBHandler.findRecordByLogin(Employee.mainEmployee.getLogin());
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {
                    Employee.listEmployees.clear();
                    while (resSet.next()) {
                        Employee employee = new Employee();
                        employee.setPassword(resSet.getString(DBConst.EMPLOYEE_PASSWORD));
                        employeeLP.setId(resSet.getInt(DBConst.EMPLOYEE_ID));
                        Employee.listEmployees.add(employee);
                    }
                    if (Employee.listEmployees.size() == 1) {
                        respond.writeObject(true);

                        if (Employee.listEmployees.get(0).getPassword().equals(Employee.mainEmployee.getPassword())) {
                            respond.writeObject(true);
                            employeeDBHandler.editPasswordRecord(employeeLP);
                        } else respond.writeObject(false);
                    } else respond.writeObject(false);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                respond.writeObject(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
