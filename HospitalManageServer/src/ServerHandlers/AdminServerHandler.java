package ServerHandlers;

import Configs.DBConst;
import DBHandlers.*;
import Models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminServerHandler {
    ObjectInputStream request; //Принятие
    ObjectOutputStream respond; //Отправка


    public AdminServerHandler(ObjectInputStream request, ObjectOutputStream respond) {
        this.request = request;
        this.respond = respond;
    }

    public void addEmployeeReceptionist() {
        try {
            System.out.println("зашел");
            Employee employee = (Employee)request.readObject();
            employee.setIdSpecialty(1);
            System.out.println(employee);
            EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
            boolean isEmployeeAdded = employeeDBHandler.addEmployee(employee);
            respond.writeObject(isEmployeeAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateStreetComboBox() {
        AddressDBHandler addressDBHandler = new AddressDBHandler();
        ResultSet resSet = addressDBHandler.getAllStreetRecords();
        if(resSet != null){
            try {
                respond.writeObject(true);
                try {

                    Street.listStreets.clear();
                    while (resSet.next())
                    {
                        Street.listStreets.add(new Street(resSet.getInt(DBConst.STREET_ID),
                                resSet.getString(DBConst.STREET_NAME)));
                    }
                    respond.writeObject(Street.listStreets);

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

    public void updateReceptionistTable() {
        int check = 0;
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        ResultSet resSet = employeeDBHandler.getAllEmployees();
        if(resSet != null){

            try {
                respond.writeObject(true);
                Employee.listEmployees.clear();
                while (resSet.next())
                {
                    if(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY)==1){
                        Employee.listEmployees.add(new Employee(resSet.getInt(DBConst.EMPLOYEE_ID),
                                resSet.getString(DBConst.EMPLOYEE_SURNAME),resSet.getString(DBConst.EMPLOYEE_NAME),
                                resSet.getString(DBConst.EMPLOYEE_PATRONYMIC), resSet.getString(DBConst.EMPLOYEE_BIRTHDAY),
                                resSet.getString(DBConst.EMPLOYEE_GENDER)));
                        check=1;
                    }

                }

                if(check == 0){

                    respond.writeObject(false);
                }
                else{
                    respond.writeObject(true);

                    respond.write(Employee.listEmployees.size());

                    for(Employee employee : Employee.listEmployees) {
                        respond.writeObject(employee);
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

    public void deleteEmployee() throws IOException, ClassNotFoundException {
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        Employee employee = (Employee) request.readObject();
        employeeDBHandler.deleteEmployee(employee);
    }

    public void editEmployeeReceptionist() {
        try {

            Employee employee = (Employee)request.readObject();

            AddressDBHandler addressDBHandler = new AddressDBHandler();
            int idAddress = addressDBHandler.findAddressByIdEmployee(employee.getId());
            employee.setIdAddress(idAddress);
            EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
            boolean isEmployeeAdded = employeeDBHandler.editEmployee(employee);
            respond.writeObject(isEmployeeAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addEmployeeDoctor() {
        try {

            Employee employee = (Employee)request.readObject();
            Specialty specialty = (Specialty)request.readObject();
            SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
            int specialtyId = specialtyDBHandler.findRecordByName(specialty.getName());

            boolean isEmployeeAdded;
            EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
            if(specialtyId==-1){

                isEmployeeAdded = employeeDBHandler.addEmployeeWithNewSpecialty(employee,specialty);
            }
            else{
                employee.setIdSpecialty(specialtyId);
                isEmployeeAdded = employeeDBHandler.addEmployee(employee);
            }
            respond.writeObject(isEmployeeAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void updateDoctorTable() {
        int check=0;
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
        ResultSet resSet = employeeDBHandler.getAllEmployees();
        String nameSpecialty = null;
        if(resSet != null){
            try {
                respond.writeObject(true);
                Employee.listEmployees.clear();
                while (resSet.next())
                {
                    if(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY)>2) {
                        nameSpecialty = specialtyDBHandler.findRecordByID(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY));
                        Employee.listEmployees.add(new Employee(resSet.getInt(DBConst.EMPLOYEE_ID),
                                resSet.getString(DBConst.EMPLOYEE_SURNAME), resSet.getString(DBConst.EMPLOYEE_NAME),
                                resSet.getString(DBConst.EMPLOYEE_PATRONYMIC),resSet.getString(DBConst.EMPLOYEE_BIRTHDAY),
                                resSet.getString(DBConst.EMPLOYEE_GENDER),
                                resSet.getString(DBConst.EMPLOYEE_OFFICE_NUMBER), nameSpecialty,
                                resSet.getString(DBConst.EMPLOYEE_WORK_TIME)));
                        check = 1;
                    }
                }

                if(check == 0){

                    respond.writeObject(false);
                }
                else{
                    respond.writeObject(true);
                    respond.write(Employee.listEmployees.size());

                    for(Employee employee : Employee.listEmployees) {

                        respond.writeObject(employee);
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
    public void setFields(Employee prevEmployee) throws IOException, SQLException {

        AddressDBHandler addressDBHandler = new AddressDBHandler();
        int idAddress = addressDBHandler.findAddressByIdEmployee(prevEmployee.getId());
        ResultSet resSet = addressDBHandler.findAddressById(idAddress);
        Address address = null;
        while (resSet.next()){
            address = new Address(resSet.getInt(DBConst.ADDRESS_ID_STREET),resSet.getInt(DBConst.ADDRESS_FLAT_NUMBER),
                    resSet.getInt(DBConst.ADDRESS_HOUSE_NUMBER),resSet.getInt(DBConst.ADDRESS_CORPUS));
        }

        resSet = addressDBHandler.findStreetById(address.getIdStreet());
        Street street = null;
        while (resSet.next()){
            street = new Street(resSet.getInt(DBConst.STREET_ID),resSet.getString(DBConst.STREET_NAME));
        }

        respond.writeObject(street);
        respond.writeObject(address);
        respond.writeObject(prevEmployee);

    }

    public void editEmployeeDoctor() {

        try {
            Employee employee = (Employee)request.readObject();
            SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
            int specialtyId = specialtyDBHandler.findRecordByName(employee.getNameSpecialty());
            boolean isEmployeeAdded;
            AddressDBHandler addressDBHandler = new AddressDBHandler();
            int idAddress = addressDBHandler.findAddressByIdEmployee(employee.getId());
            employee.setIdAddress(idAddress);
            EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
            if(specialtyId==-1){
                Specialty specialty = new Specialty(employee.getNameSpecialty());
                employee.setIdSpecialty(specialtyDBHandler.addSpecialty(specialty));
                isEmployeeAdded = employeeDBHandler.editEmployee(employee);

            }
            else{
                employee.setIdSpecialty(specialtyId);
                isEmployeeAdded = employeeDBHandler.editEmployee(employee);
            }

            respond.writeObject(isEmployeeAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateSpecialtyComboBox() {
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

    public void updateDoctorTableBySpecialty() throws IOException, ClassNotFoundException {
        System.out.println("зашел в обновление");
        Specialty specialty  = new Specialty((Specialty) request.readObject());

        int check=0;
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        SpecialtyDBHandler specialtyDBHandler = new SpecialtyDBHandler();
        int idSelectedSpecialty = specialtyDBHandler.findRecordByName(specialty.getName());
        ResultSet resSet = employeeDBHandler.getAllEmployees();
        String nameSpecialty = null;
        if(resSet != null){
            try {

                Employee.listEmployees.clear();
                while (resSet.next())
                {
                    if(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY)== idSelectedSpecialty) {
                        nameSpecialty = specialtyDBHandler.findRecordByID(resSet.getInt(DBConst.EMPLOYEE_ID_SPECIALTY));
                        Employee.listEmployees.add(new Employee(resSet.getInt(DBConst.EMPLOYEE_ID),
                                resSet.getString(DBConst.EMPLOYEE_SURNAME), resSet.getString(DBConst.EMPLOYEE_NAME),
                                resSet.getString(DBConst.EMPLOYEE_PATRONYMIC),resSet.getString(DBConst.EMPLOYEE_BIRTHDAY),
                                resSet.getString(DBConst.EMPLOYEE_GENDER),
                                resSet.getString(DBConst.EMPLOYEE_OFFICE_NUMBER), nameSpecialty,
                                resSet.getString(DBConst.EMPLOYEE_WORK_TIME)));
                        check = 1;
                    }
                }

                System.out.println("check = " + check);
                if(check == 0){

                    respond.writeObject(false);
                }
                else{
                    respond.writeObject(true);

                    respond.write(Employee.listEmployees.size());

                    for(Employee employee : Employee.listEmployees) {

                        respond.writeObject(employee);
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

    public void addWorkTimeAndOfficeNumber() {
        try {
            Employee employee = (Employee)request.readObject();
            EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
            boolean isEmployeeAdded = employeeDBHandler.updateWorkTimeAndOfficeNumber(employee);
            respond.writeObject(isEmployeeAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addAppointment() {
        try {
            System.out.println("зашел");
            Appointment appointment = (Appointment)request.readObject();
            AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
            boolean isAppointmentAdded = appointmentDBHandler.addAppointment(appointment);

            respond.writeObject(isAppointmentAdded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void sendStatictic() throws SQLException, IOException {

        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        ResultSet resSet = appointmentDBHandler.getAllAppointments();
        if(resSet != null){
            respond.writeObject(true);
                Appointment.listAppointments.clear();
                Employee.listEmployees.clear();
                while (resSet.next())
                {
                    Appointment.listAppointments.add(new Appointment(resSet.getString(DBConst.APPOINTMENT_DATE)));
                }

                respond.write(Appointment.listAppointments.size());

                for(Appointment appointment : Appointment.listAppointments) {

                   respond.writeObject(appointment);
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

    public void sendPieChart() throws IOException, SQLException {
        AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
        EmployeeDBHandler employeeDBHandler = new EmployeeDBHandler();
        ResultSet resSet = appointmentDBHandler.getAllAppointments();
        ResultSet resultSetEmployees = employeeDBHandler.getAllEmployees();
        int idEmployee = 0;
        int counter;
        if(resSet != null){
            respond.writeObject(true);
            Employee.listEmployees.clear();
            Appointment.listAppointments.clear();
            while (resultSetEmployees.next()){
                counter = 0;
                if(resultSetEmployees.getInt(DBConst.EMPLOYEE_ID_SPECIALTY)>2){
                    idEmployee = resultSetEmployees.getInt(DBConst.EMPLOYEE_ID);
                    resSet = appointmentDBHandler.getAllAppointments();
                    while(resSet.next()){
                        if(idEmployee == resSet.getInt(DBConst.APPOINTMENT_ID_EMPLOYEE)){
                            counter++;
                        }
                    }
                    Employee.listEmployees.add( new Employee(resultSetEmployees.getString(DBConst.EMPLOYEE_SURNAME), resultSetEmployees.getString(DBConst.EMPLOYEE_NAME),
                            resultSetEmployees.getString(DBConst.EMPLOYEE_PATRONYMIC), counter));

                }
            }

            respond.write(Employee.listEmployees.size());

            for(Employee employee : Employee.listEmployees) {
                respond.writeObject(employee);
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