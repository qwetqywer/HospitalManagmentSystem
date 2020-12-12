import Models.Appointment;
import Models.Employee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ServerHandler implements Runnable {
    protected Socket clientSocket = null;
    ObjectInputStream request; //Принятие
    ObjectOutputStream respond; //Отправка

    public ServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            request = new ObjectInputStream(clientSocket.getInputStream());
            respond = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                System.out.println("авторизация");

                Employee.mainEmployee = (Employee) request.readObject();
                boolean isAuthorize = Employee.mainEmployee.authorize();
                respond.writeObject(isAuthorize);
                if (isAuthorize) {
                    //Employee employee = new Employee();
                    boolean isEmployeeFound =Employee.mainEmployee.findEmployeebyPassword(Employee.mainEmployee.getPassword());

                    //boolean isEmployeeFound = employee.findEmployee(Employee.mainEmployee.getId());
                    respond.writeObject(isEmployeeFound);

                    if (isEmployeeFound)
                    {
                       /* employee.setLogin(Employee.mainEmployee.getLogin());
                        employee.setLogin(Employee.mainEmployee.getPassword());
                        employee.setIdSpecialty(Employee.mainEmployee.getIdSpecialty());*/
                        respond.writeObject(Employee.mainEmployee);
                        switch (Employee.mainEmployee.getIdSpecialty()){
                            case 1 :{
                                RegServerHandler regServerHandler = new RegServerHandler(request,respond);
                                String action = "";
                                do {
                                    action = request.readObject().toString();
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
                                                    default:actionDetailed = "exit";
                                                }
                                            }while(!actionDetailed.equals("exit"));
                                            break;
                                        }
                                        case "desktopRegButton": {break;}
                                    }
                                }while(!action.equals("returnBack"));
                                break;
                            }
                            case 2:{
                                respond.writeObject(Employee.mainEmployee);
                                AdminServerHandler adminServerHandler = new AdminServerHandler(request,respond);
                                String action = "";
                                action = request.readObject().toString();
                                System.out.println("action =" + action);
                                do {

                                    switch (action){
                                        case "desktopAdmin": {
                                            System.out.println("Рабочий стол");
                                            respond.writeObject(Employee.mainEmployee);
                                            break;
                                        }
                                        case "manageRegistry": {
                                            System.out.println("УПРАВЛЕНИЕ РЕГИСТРАТУРОЙ");

                                            if(request.readObject().toString().equals("updateReceptionistTable"))
                                            {
                                                adminServerHandler.updateReceptionistTable();
                                            }
                                            String actionDetailed = "";
                                            do {
                                                actionDetailed = request.readObject().toString();
                                                System.out.println(actionDetailed);
                                                switch (actionDetailed){
                                                    case "addReceptionistButton":  {
                                                        if(request.readObject().toString().equals("updateStreetComboBox"))
                                                        {
                                                            adminServerHandler.updateStreetComboBox();
                                                        }
                                                        String actionButton = "";
                                                        do {
                                                            System.out.println("ждет");
                                                            actionButton = request.readObject().toString();
                                                            switch (actionButton){
                                                                case "confirmAdd":  adminServerHandler.addEmployeeReceptionist();break;
                                                                case "clearButton": System.out.println("чистим");break;
                                                                case "manageRegistry" :action = actionDetailed; actionDetailed = "exit"; action = "manageRegistry";System.out.println("выходим"); break;
                                                            }
                                                        }while(!actionButton.equals("manageRegistry"));
                                                        break;
                                                    }
                                                    case "deleteReceptionist":  {
                                                        adminServerHandler.deleteEmployee();
                                                        if(request.readObject().toString().equals("updateReceptionistTable"))
                                                        {
                                                            adminServerHandler.updateReceptionistTable();
                                                        }
                                                        break;
                                                    }
                                                    case "editReceptionistButton":  {
                                                        Employee prevEmployee = (Employee) request.readObject();
                                                        adminServerHandler.setFields(prevEmployee);
                                                        if(request.readObject().toString().equals("updateStreetComboBox"))
                                                        {
                                                            adminServerHandler.updateStreetComboBox();
                                                        }
                                                        String actionButton = "";
                                                        do {
                                                            actionButton = request.readObject().toString();
                                                            switch (actionButton){
                                                                case "confirmEdit":  adminServerHandler.editEmployeeReceptionist();break;
                                                                case "clearButton": break;
                                                                case "manageRegistry" : actionDetailed = "exit"; System.out.println("выходим"); break;
                                                            }
                                                        }while(!actionButton.equals("manageRegistry"));
                                                        break;
                                                    }
                                                    default:action = actionDetailed; actionDetailed = "exit";break;
                                                }

                                            }while(!actionDetailed.equals("exit"));
                                            break;
                                        }

                                        case "makeSchedule" : {
                                            if(request.readObject().toString().equals("updateSpecialtyComboBox"))
                                            {
                                                adminServerHandler.updateSpecialtyComboBox();
                                            }
                                            System.out.println("расписание");
                                            String actionDetailed = "";
                                            do {
                                                actionDetailed = request.readObject().toString();
                                                System.out.println("actionDetailed = " + actionDetailed);
                                                switch (actionDetailed){
                                                    case "searchButton":  {
                                                        if(request.readObject().toString().equals("updateDoctorTable"))
                                                        {
                                                            adminServerHandler.updateDoctorTableBySpecialty();
                                                        }
                                                        break;
                                                    }
                                                    case "addWorkTimeButton":  {
                                                        adminServerHandler.addWorkTimeAndOfficeNumber();
                                                        break;
                                                    }
                                                    case "addAppointmentButton":  {

                                                        adminServerHandler.addAppointment();
                                                        break;
                                                    }
                                                    default:action = actionDetailed; actionDetailed = "exit";break;
                                                }

                                            }while(!actionDetailed.equals("exit"));
                                            break;
                                        }

                                        case "managePersonnel" : {
                                            System.out.println("управление мед.персоналом");
                                            String actionDetailed = "";
                                            if(request.readObject().toString().equals("updateStaffTable"))
                                            {
                                                adminServerHandler.updateDoctorTable();
                                            }
                                            do {
                                                actionDetailed = request.readObject().toString();
                                                System.out.println(actionDetailed);
                                                switch (actionDetailed){
                                                    case "addDoctorButton":  {
                                                       if(request.readObject().toString().equals("updateStreetComboBox"))
                                                        {
                                                            adminServerHandler.updateStreetComboBox();
                                                        }
                                                        String actionButton = "";
                                                        do {
                                                            System.out.println("ждет");
                                                            actionButton = request.readObject().toString();
                                                            switch (actionButton){
                                                                case "confirmAdd":  adminServerHandler.addEmployeeDoctor();break;
                                                                case "clearButton": System.out.println("чистим");break;
                                                                case "managePersonnel" :action = actionDetailed; actionDetailed = "exit"; action = "managePersonnel";System.out.println("выходим"); break;
                                                            }
                                                        }while(!actionButton.equals("managePersonnel"));
                                                        break;
                                                    }
                                                    case "deleteDoctor":  {
                                                        adminServerHandler.deleteEmployee();
                                                        if(request.readObject().toString().equals("updateStaffTable"))
                                                        {
                                                            adminServerHandler.updateDoctorTable();
                                                        }
                                                        break;
                                                    }
                                                    case "editDoctorButton":  {
                                                        Employee prevEmployee = (Employee) request.readObject();
                                                        adminServerHandler.setFields(prevEmployee);
                                                        if(request.readObject().toString().equals("updateStreetComboBox"))
                                                        {
                                                            adminServerHandler.updateStreetComboBox();
                                                        }
                                                        String actionButton = "";
                                                        do {
                                                            actionButton = request.readObject().toString();
                                                            switch (actionButton){
                                                                case "confirmEdit":   System.out.println("зашел"); adminServerHandler.editEmployeeDoctor();break;
                                                                case "clearButton": break;
                                                                case "managePersonnel" : actionDetailed = "exit"; System.out.println("выходим"); break;
                                                            }
                                                        }while(!actionButton.equals("managePersonnel"));
                                                        break;
                                                    }
                                                    default:action = actionDetailed;actionDetailed = "exit";break;
                                                }

                                            }while(!actionDetailed.equals("exit"));
                                            break;
                                        }

                                        case "viewStatistics" : {
                                            System.out.println("статистики");
                                            adminServerHandler.sendStatictic();
                                            adminServerHandler.sendPieChart();
                                            break;
                                        }

                                        case "settingsAdmin" : {
                                            System.out.println("настройки");
                                            String actionButton = "";
                                            do {
                                                actionButton = request.readObject().toString();
                                                switch (actionButton){
                                                    case "changeLogin": {
                                                        System.out.println("изменение логина");
                                                        //Employee.mainEmployee = (Employee) request.readObject();
                                                        Employee employeeLP = (Employee) request.readObject();
                                                        adminServerHandler.changeLogin(employeeLP);
                                                        break;
                                                    }
                                                    case "changePassword":{
                                                        System.out.println("изменение пароля");
                                                       // Employee.mainEmployee = (Employee) request.readObject();
                                                        Employee employeeLP = (Employee) request.readObject();
                                                        adminServerHandler.changePassword(employeeLP);
                                                        break;
                                                    }
                                                    default: action = actionButton;actionButton = "exit";break;
                                                }
                                            }while(!actionButton.equals("exit"));
                                            break;

                                        }

                                        case "returnBack" : {
                                            System.out.println("главное меню");
                                            break;
                                        }


                                    }
                                }while(!action.equals("returnBack"));
                                break;
                            }
                            default:{
                                System.out.println("МедРаботник");
                                respond.writeObject(Employee.mainEmployee);
                                CareWorkerServerHandler careWorkerServerHandler = new CareWorkerServerHandler(request,respond);
                                String action = "";
                                action = request.readObject().toString();
                                System.out.println("action =" + action);
                                do {

                                    switch (action){
                                        case "desktopCareWorker": {
                                            respond.writeObject(Employee.mainEmployee);
                                            break;

                                        }
                                        case "getSchedule": {
                                            System.out.println("Расписание");
                                            String actionButton = "";
                                            do {
                                                actionButton = request.readObject().toString();
                                                switch (actionButton){
                                                    case "todaySearchButton": {
                                                        if(request.readObject().toString().equals("updateAppointmentTable"))
                                                        {
                                                            careWorkerServerHandler.updateAppointmentTable();
                                                        }
                                                        break;
                                                    }
                                                    case "searchButton":{
                                                        if(request.readObject().toString().equals("updateAppointmentTableByDate"))
                                                        {
                                                            careWorkerServerHandler.updateAppointmentTableByDate();
                                                        }
                                                        break;
                                                    }
                                                    default: action = actionButton;actionButton = "exit";break;
                                                }
                                            }while(!actionButton.equals("exit"));

                                            break;
                                        }

                                        case "startAppointment" : {
                                            System.out.println("начать прием");
                                            if(request.readObject().toString().equals("updateAppointmentTable"))
                                            {
                                                careWorkerServerHandler.updateAppointmentTable();
                                            }

                                            String actionButton = "";
                                            do {
                                                actionButton = request.readObject().toString();
                                                switch (actionButton){
                                                    case "start": {
                                                        System.out.println("start");
                                                        Appointment appointment = (Appointment) request.readObject();
                                                        careWorkerServerHandler.getPatient(appointment);
                                                        break;
                                                    }
                                                    case "searchButton":{
                                                        if(request.readObject().toString().equals("updateAppointmentTableByDate"))
                                                        {
                                                            careWorkerServerHandler.updateAppointmentTableByDate();
                                                        }
                                                        break;
                                                    }
                                                    default: action = actionButton;actionButton = "exit";break;
                                                }
                                            }while(!actionButton.equals("exit"));
                                            break;

                                        }

                                        case "settingsAdmin" : {
                                            System.out.println("настройки");

                                            break;

                                        }

                                        case "returnBack" : {
                                            System.out.println("главное меню");
                                            break;
                                        }


                                    }
                                }while(!action.equals("returnBack"));

                                break;
                            }
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
