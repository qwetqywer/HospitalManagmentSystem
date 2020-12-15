package ServerHandlers;

import DBHandlers.AppointmentDBHandler;
import Models.Appointment;
import Models.Employee;
import Models.Patient;
import ServerHandlers.AdminServerHandler;
import ServerHandlers.CareWorkerServerHandler;
import ServerHandlers.RegServerHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ServerHandler implements Runnable {
    static protected Socket clientSocket = null;
    static ObjectInputStream request; //Принятие
    static ObjectOutputStream respond; //Отправка

    public static Socket getClientSocket() {
        return clientSocket;
    }

    public ServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            request = new ObjectInputStream(clientSocket.getInputStream());
            respond = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                Employee personalAccount = new Employee();
                personalAccount.setLogin((String) request.readObject());
                personalAccount.setPassword((String) request.readObject());

                boolean isAuthorize = personalAccount.authorize();
                Employee.mainEmployee.setId(personalAccount.getId());
                Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                respond.writeObject(isAuthorize);
                if (isAuthorize) {
                    respond.writeObject(personalAccount);
                    RegServerHandler regServerHandler = new RegServerHandler(request,respond);
                    System.out.println(personalAccount.getIdSpecialty());
                    switch (personalAccount.getIdSpecialty()) {
                        case 1 :{
                            String action = "";
                            action = request.readObject().toString();
                            do {
                                switch (action){
                                    case "regIssueOutpatientCard": {
                                        if(request.readObject().toString().equals("updateStreetComboBox"))
                                        {
                                            regServerHandler.updateStreetComboBox();
                                        }
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "addOutPatientCard":  regServerHandler.addOutPatientCard();break;
                                                case "clearFields" : break;
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while(!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "desktopRegButton": {break;}
                                    case "regDoctorScedule":{
                                        if(request.readObject().toString().equals("updateSpecialtiesComboBox"))
                                        {
                                            regServerHandler.updateSpecialtiesComboBox("all");
                                        }
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "getDoctorSchedule": {
                                                    regServerHandler.sendAllEmployeeRecords();
                                                    break;
                                                }
                                                default:action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while(!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "regEditProfile" : {
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "changeLogin": {
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    regServerHandler.changeLogin(employeeLP);
                                                    break;
                                                }
                                                case "changePassword":{
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    regServerHandler.changePassword(employeeLP);
                                                    break;
                                                }
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while(!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "regIssueAppointmentCard": {
                                        if(request.readObject().toString().equals("updateSpecialtiesComboBox"))
                                        {
                                            regServerHandler.updateSpecialtiesComboBox("doctors");
                                        }

                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "confirmSpecialty": {
                                                    if((Boolean)request.readObject()){
                                                        if(request.readObject().toString().equals("updateDoctorsComboBox"))
                                                        {
                                                            regServerHandler.sendAllEmployeeRecords();
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "updateAppointmentTable":{
                                                    Employee doctor = (Employee) request.readObject();
                                                    regServerHandler.sendALLAppointment(doctor);
                                                    break;
                                                }
                                                case "findPatient":{
                                                    if((Boolean)request.readObject()){
                                                        Patient patient = (Patient) request.readObject();
                                                        regServerHandler.sendAllPatients();
                                                    }
                                                    break;
                                                }
                                                case "confirmIssuesButton":{
                                                    if((Boolean) request.readObject())
                                                    {
                                                        if((Boolean) request.readObject()){
                                                            Appointment appointment = (Appointment) request.readObject();
                                                            Patient patient = (Patient) request.readObject();

                                                            AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
                                                            appointmentDBHandler.updateAppointment(patient,appointment);
                                                        }
                                                    }
                                                    break;
                                                }
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while( !actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "regGiveAppointmentCard":{
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "confirmPatient":{
                                                    if((Boolean)request.readObject())
                                                    {
                                                        Patient patient = (Patient) request.readObject();
                                                        regServerHandler.findAppointments(patient);
                                                    }
                                                    break;
                                                }
                                                case "generateAppointmentCard":{
                                                    Appointment appointment = (Appointment) request.readObject();
                                                    regServerHandler.generateAppointmentCard(appointment);
                                                    break;
                                                }
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while( !actionDetailed.equals("exit"));
                                        break;
                                    }
                                }
                            }while(!action.equals("returnBack"));
                        }
                        case 2: {
                            AdminServerHandler adminServerHandler = new AdminServerHandler(request, respond);
                            String action = "";
                            action = request.readObject().toString();
                            System.out.println("action =" + action);
                            do {
                                switch (action) {
                                    case "desktopAdmin": {

                                        break;
                                    }
                                    case "manageRegistry": {
                                        System.out.println("УПРАВЛЕНИЕ РЕГИСТРАТУРОЙ");

                                        if (request.readObject().toString().equals("updateReceptionistTable")) {
                                            adminServerHandler.updateReceptionistTable();
                                        }
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();

                                            switch (actionDetailed) {
                                                case "addReceptionistButton": {
                                                    if (request.readObject().toString().equals("updateStreetComboBox")) {
                                                        adminServerHandler.updateStreetComboBox();
                                                    }
                                                    String actionButton = "";
                                                    do {
                                                        System.out.println("ждет");
                                                        actionButton = request.readObject().toString();
                                                        switch (actionButton) {
                                                            case "confirmAdd":
                                                                adminServerHandler.addEmployeeReceptionist();
                                                                break;
                                                            case "clearButton":
                                                                break;
                                                            case "manageRegistry":
                                                                action = actionDetailed;
                                                                actionDetailed = "exit";
                                                                action = "manageRegistry";

                                                                break;
                                                        }
                                                    } while (!actionButton.equals("manageRegistry"));
                                                    break;
                                                }
                                                case "deleteReceptionist": {
                                                    adminServerHandler.deleteEmployee();
                                                    if (request.readObject().toString().equals("updateReceptionistTable")) {
                                                        adminServerHandler.updateReceptionistTable();
                                                    }
                                                    break;
                                                }
                                                case "editReceptionistButton": {
                                                    Employee prevEmployee = (Employee) request.readObject();
                                                    adminServerHandler.setFields(prevEmployee);
                                                    if (request.readObject().toString().equals("updateStreetComboBox")) {
                                                        adminServerHandler.updateStreetComboBox();
                                                    }
                                                    String actionButton = "";
                                                    do {
                                                        actionButton = request.readObject().toString();
                                                        switch (actionButton) {
                                                            case "confirmEdit":
                                                                adminServerHandler.editEmployeeReceptionist();
                                                                break;
                                                            case "clearButton":
                                                                break;
                                                            case "manageRegistry":
                                                                actionDetailed = "exit";
                                                                action = "manageRegistry";
                                                                System.out.println("выходим");
                                                                break;
                                                        }
                                                    } while (!actionButton.equals("manageRegistry"));
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }

                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }

                                    case "makeSchedule": {
                                        if (request.readObject().toString().equals("updateSpecialtyComboBox")) {
                                            adminServerHandler.updateSpecialtyComboBox();
                                        }

                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            System.out.println("actionDetailed = " + actionDetailed);
                                            switch (actionDetailed) {
                                                case "searchButton": {
                                                    if (request.readObject().toString().equals("updateDoctorTable")) {
                                                        adminServerHandler.updateDoctorTableBySpecialty();
                                                    }
                                                    break;
                                                }
                                                case "addWorkTimeButton": {
                                                    adminServerHandler.addWorkTimeAndOfficeNumber();
                                                    break;
                                                }
                                                case "addAppointmentButton": {

                                                    adminServerHandler.addAppointment();
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }

                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }

                                    case "managePersonnel": {

                                        String actionDetailed = "";
                                        if (request.readObject().toString().equals("updateStaffTable")) {
                                            adminServerHandler.updateDoctorTable();
                                        }
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            System.out.println(actionDetailed);
                                            switch (actionDetailed) {
                                                case "addDoctorButton": {
                                                    if (request.readObject().toString().equals("updateStreetComboBox")) {
                                                        adminServerHandler.updateStreetComboBox();
                                                    }
                                                    String actionButton = "";
                                                    do {
                                                        System.out.println("ждет");
                                                        actionButton = request.readObject().toString();
                                                        switch (actionButton) {
                                                            case "confirmAdd":
                                                                adminServerHandler.addEmployeeDoctor();
                                                                break;
                                                            case "clearButton":
                                                                break;
                                                            case "managePersonnel":
                                                                action = actionDetailed;
                                                                actionDetailed = "exit";
                                                                action = "managePersonnel";

                                                                break;
                                                        }
                                                    } while (!actionButton.equals("managePersonnel"));
                                                    break;
                                                }
                                                case "deleteDoctor": {
                                                    adminServerHandler.deleteEmployee();
                                                    if (request.readObject().toString().equals("updateStaffTable")) {
                                                        adminServerHandler.updateDoctorTable();
                                                    }
                                                    break;
                                                }
                                                case "editDoctorButton": {
                                                    Employee prevEmployee = (Employee) request.readObject();
                                                    adminServerHandler.setFields(prevEmployee);
                                                    if (request.readObject().toString().equals("updateStreetComboBox")) {
                                                        adminServerHandler.updateStreetComboBox();
                                                    }
                                                    String actionButton = "";
                                                    do {
                                                        actionButton = request.readObject().toString();
                                                        switch (actionButton) {
                                                            case "confirmEdit":

                                                                adminServerHandler.editEmployeeDoctor();
                                                                break;
                                                            case "clearButton":
                                                                break;
                                                            case "managePersonnel":
                                                                actionDetailed = "exit";

                                                                break;
                                                        }
                                                    } while (!actionButton.equals("managePersonnel"));
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }

                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }

                                    case "viewStatistics": {
                                        System.out.println("статистики");
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed) {
                                                case "sendStatictic": {
                                                    adminServerHandler.sendStatictic();
                                                    break;
                                                }
                                                case "sendPieChart": {
                                                    adminServerHandler.sendPieChart();
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }
                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }

                                    case "settingsAdmin": {
                                        System.out.println("настройки");
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed) {
                                                case "changeLogin": {
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    adminServerHandler.changeLogin(employeeLP);
                                                    break;
                                                }
                                                case "changePassword":{
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    adminServerHandler.changePassword(employeeLP);
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }
                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }
                                }
                            } while (!action.equals("returnBack"));
                            break;
                        }
                        default: {
                            CareWorkerServerHandler careWorkerServerHandler = new CareWorkerServerHandler(request, respond);
                            String action = "";
                            action = request.readObject().toString();

                            do {
                                switch (action) {
                                    case "desktopCareWorker": {
                                        break;
                                    }
                                    case "getSchedule": {
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed) {
                                                case "todaySearchButton": {
                                                    careWorkerServerHandler.updateAppointmentTable();
                                                    break;
                                                }
                                                case "searchButton": {
                                                    careWorkerServerHandler.updateAppointmentTableByDate();
                                                    break;
                                                }
                                                default: action = actionDetailed;actionDetailed = "exit";break;
                                            }
                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "startAppointment": {
                                        if (request.readObject().toString().equals("updateAppointmentTable")) {
                                            careWorkerServerHandler.updateAppointmentTable();
                                        }
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed) {
                                                case "start": {
                                                    careWorkerServerHandler.getPatient();
                                                    break;
                                                }
                                                case "end": {
                                                    careWorkerServerHandler.updateAppointment();
                                                    break;
                                                }
                                                case "diseaseHistory": {
                                                    careWorkerServerHandler.getDiseaseHistory();
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }
                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "startWithoutOrder": {
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed) {
                                                case "search": {
                                                    careWorkerServerHandler.searchPatient();
                                                    break;
                                                }
                                                case "end": {
                                                    careWorkerServerHandler.addAppointment();
                                                    break;
                                                }
                                                case "diseaseHistory": {
                                                    careWorkerServerHandler.getDiseaseHistory();
                                                    break;
                                                }
                                                default:
                                                    action = actionDetailed;
                                                    actionDetailed = "exit";
                                                    break;
                                            }
                                        } while (!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "editCareWorkerProfile":{
                                        String actionDetailed = "";
                                        do {
                                            actionDetailed = request.readObject().toString();
                                            switch (actionDetailed){
                                                case "changeLogin": {
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    careWorkerServerHandler.changeLogin(employeeLP);
                                                    break;
                                                }
                                                case "changePassword":{
                                                    Employee.mainEmployee = (Employee) request.readObject();
                                                    Employee.mainEmployee.setId(personalAccount.getId());
                                                    Employee.mainEmployee.setIdSpecialty(personalAccount.getIdSpecialty());
                                                    Employee employeeLP = (Employee) request.readObject();
                                                    careWorkerServerHandler.changePassword(employeeLP);
                                                    break;
                                                }
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while(!actionDetailed.equals("exit"));
                                        break;
                                    }
                                    case "issueAppointment":{
                                        if(request.readObject().toString().equals("updateSpecialtiesComboBox"))
                                        {
                                            careWorkerServerHandler.updateSpecialtiesComboBox();
                                        }
                                        if(request.readObject().toString().equals("updateAppointmentTypeComboBox"))
                                        {
                                            careWorkerServerHandler.updateAppointmentTypeComboBox();
                                        }
                                        String actionDetailed = "";
                                        do {

                                            actionDetailed = request.readObject().toString();
                                            System.out.println(actionDetailed);
                                            switch (actionDetailed){
                                                case "confirmSpecialty": {
                                                    if((boolean)request.readObject()){
                                                        if(request.readObject().toString().equals("updateDoctorsComboBox"))
                                                        {
                                                            careWorkerServerHandler.sendAllEmployeeRecords();
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "updateAppointmentTable":{

                                                    careWorkerServerHandler.sendALLAppointment();
                                                    break;
                                                }
                                                case "findPatient":{
                                                    if((boolean)request.readObject()){
                                                        Patient patient = (Patient) request.readObject();
                                                        careWorkerServerHandler.sendAllPatients();
                                                    }
                                                    break;
                                                }
                                                case "confirmIssuesButton":{

                                                    Appointment appointment = (Appointment) request.readObject();
                                                    Patient patient = (Patient) request.readObject();
                                                    AppointmentDBHandler appointmentDBHandler = new AppointmentDBHandler();
                                                    appointmentDBHandler.updateAppointment(patient,appointment);

                                                    break;
                                                }
                                                default: action = actionDetailed; actionDetailed = "exit";
                                            }
                                        }while(!actionDetailed.equals("exit"));
                                        break;
                                    }

                                }
                            }while(!action.equals("returnBack"));
                            break;
                        }
                    }
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


