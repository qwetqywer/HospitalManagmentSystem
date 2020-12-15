package ServerHandlers;

import Configs.DBConst;
import DBHandlers.*;
import Models.*;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegServerHandler {
    ObjectInputStream request; //Принятие
    ObjectOutputStream respond; //Отправка


    public RegServerHandler(ObjectInputStream request, ObjectOutputStream respond) {
        this.request = request;
        this.respond = respond;
    }




    public void addOutPatientCard() {
        try {
            Patient patient = (Patient) request.readObject();
            PatientDBHandler patientDBHandler = new PatientDBHandler();
            boolean isPatientAdded = patientDBHandler.addRecord(patient);
            respond.writeObject(isPatientAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateStreetComboBox() {
        AddressDBHandler addressDBHandler = new AddressDBHandler();
        ResultSet resSet = addressDBHandler.getAllStreetRecords();
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {

                    Street.listStreets.clear();
                    while (resSet.next()) {
                        Street.listStreets.add(new Street(resSet.getInt(DBConst.STREET_ID),
                                resSet.getString(DBConst.STREET_NAME)));

                    }
                    respond.writeObject(Street.listStreets);

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

    public void updateSpecialtiesComboBox(String choice) {
        SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
        ResultSet resSet = specialtyDBHandler.getAllSpecialties();
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {

                    Specialty.listSpecialties.clear();
                    while (resSet.next()) {
                        if (choice.equals("all")) {
                            Specialty.listSpecialties.add(new Specialty(resSet.getInt(DBConst.SPECIALTY_ID),
                                    resSet.getString(DBConst.SPECIALTY_NAME)));
                        } else if (choice.equals("doctors")) {
                            if (resSet.getInt(DBConst.SPECIALTY_ID) > 2) {
                                Specialty.listSpecialties.add(new Specialty(resSet.getInt(DBConst.SPECIALTY_ID),
                                        resSet.getString(DBConst.SPECIALTY_NAME)));
                            }
                        }
                    }
                    respond.writeObject(Specialty.listSpecialties);

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
                try {

                    Employee.listEmployees.clear();
                    while (resSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resSet.getInt(DBConst.EMPLOYEE_ID));
                        employee.setSurname(resSet.getString(DBConst.EMPLOYEE_SURNAME));
                        employee.setName(resSet.getString(DBConst.EMPLOYEE_NAME));
                        employee.setPatronymic(resSet.getString(DBConst.EMPLOYEE_PATRONYMIC));
                        employee.setOfficeNumber(resSet.getString(DBConst.EMPLOYEE_OFFICE_NUMBER));
                        employee.setWorkTime(resSet.getString(DBConst.EMPLOYEE_WORK_TIME));
                        employee.setIdSpecialty(Integer.parseInt(resSet.getString(DBConst.EMPLOYEE_ID_SPECIALTY)));
                        Employee.listEmployees.add(employee);
                    }
                    respond.writeObject(Employee.listEmployees);
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

    public void sendALLAppointment(Employee doctor) {
        AppointmentDBHandler dataHandler = new AppointmentDBHandler();
        ResultSet resSet = dataHandler.findRecordsByDoctor(doctor);
        if (resSet != null) {
            try {
                respond.writeObject(true);
                try {

                    Appointment.listAppointments.clear();
                    while (resSet.next()) {
                        Appointment appointment = new Appointment();
                        appointment.setId(resSet.getInt(DBConst.APPOINTMENT_ID));
                        appointment.setIdEmployee(resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE));
                        appointment.setDate(resSet.getString(DBConst.APPOINTMENT_DATE));
                        appointment.setTime(resSet.getString(DBConst.APPOINTMENT_TIME));
                        int status = resSet.getInt(DBConst.APPOINTMENT_STATUS);
                        if (status == 0) appointment.setIntStatus(0);
                        else appointment.setIntStatus(1);
                        Appointment.listAppointments.add(appointment);
                    }
                    respond.writeObject(Appointment.listAppointments.size());
                    for (Appointment appointment : Appointment.listAppointments) {
                        respond.writeObject(appointment);
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

    public void findAppointments(Patient patient) {
        PatientDBHandler patientDBHandler = new PatientDBHandler();
        ResultSet patientsResSet = patientDBHandler.findPatientByFullName(patient);

        if (patientsResSet != null) {
            try {
                respond.writeObject(true);
                try {
                    while (patientsResSet.next()) {
                        patient.setId(patientsResSet.getInt(DBConst.PATIENT_ID));
                    }
                    AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
                    EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
                    Employee employee;
                    ResultSet appointmentsResSet = appointmentDBHandler.findRecordsByPatient(patient);

                    if (appointmentsResSet != null) {
                        try {
                            respond.writeObject(true);
                            try {
                                Appointment.listAppointments.clear();
                                Employee.listEmployees.clear();

                                while (appointmentsResSet.next()) {
                                    Appointment appointment = new Appointment();
                                    appointment.setIdPatient(appointmentsResSet.getInt(DBConst.APPOINTMENT_ID_PATIENT));
                                    appointment.setId(appointmentsResSet.getInt(DBConst.APPOINTMENT_ID));
                                    appointment.setIdEmployee(appointmentsResSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE));
                                    appointment.setDate(appointmentsResSet.getString(DBConst.APPOINTMENT_DATE));
                                    appointment.setTime(appointmentsResSet.getString(DBConst.APPOINTMENT_TIME));
                                    int status = appointmentsResSet.getInt(DBConst.APPOINTMENT_STATUS);
                                    if (status == 0) appointment.setIntStatus(0);
                                    else appointment.setIntStatus(1);
                                    employee = employeeDBHandler.findRecordByEmployeeId(appointment.getIdEmployee());

                                    Appointment.listAppointments.add(appointment);
                                    Employee.listEmployees.add(employee);
                                }
                                respond.writeObject(Appointment.listAppointments.size());
                                for (Appointment appointment : Appointment.listAppointments) {
                                    respond.writeObject(appointment);
                                }
                                respond.writeObject(Employee.listEmployees.size());
                                for (Employee employeeItem : Employee.listEmployees) {
                                    respond.writeObject(employeeItem);
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
                } catch (SQLException throwables) {
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

    public void generateAppointmentCard(Appointment appointment) {
        PatientDBHandler patientDBHandler = new PatientDBHandler();
        ResultSet patientsResSet = patientDBHandler.findById(appointment.getIdPatient());
        Patient patient = new Patient();

        if (patientsResSet != null) {
            try {
                while (patientsResSet.next()) {
                    patient.setId(patientsResSet.getInt(DBConst.PATIENT_ID));
                    patient.setSurname(patientsResSet.getString(DBConst.PATIENT_SURNAME));
                    patient.setName(patientsResSet.getString(DBConst.PATIENT_NAME));
                    patient.setPatronymic(patientsResSet.getString(DBConst.PATIENT_PATRONYMIC));
                    System.out.println(patient);
                }

                EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
                Employee employee = employeeDBHandler.findRecordByEmployeeId(appointment.getIdEmployee());
                System.out.println(employee);
                File file = new File("Талон");
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                    try {
                        out.println("Талон № " + appointment.getId());
                        out.println("Пациент:  " + patient.getSurname() + " "
                                + patient.getName() + " "
                                + patient.getPatronymic());
                        out.println("Врач:  " + employee.getSurname() + " "
                                + employee.getName() + " "
                                + employee.getPatronymic());
                        out.println("Дата приема:  " + appointment.getDate());
                        out.println("Время приема:  " + appointment.getTime());
                    } finally {
                        out.close();
                        try {
                            Process process = Runtime.getRuntime().exec("cmd /c notepad.exe " + file.getAbsolutePath());
                        } catch ( Exception ex ) {
                            ex.printStackTrace();
                        }

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}