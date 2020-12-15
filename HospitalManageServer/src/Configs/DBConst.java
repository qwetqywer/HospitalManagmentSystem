package Configs;

public class DBConst {

    public static final String ADDRESS_TABLE = "address";
    public static final String ADDRESS_ID = "idaddress";
    public static final String ADDRESS_HOUSE_NUMBER = "housenumber";
    public static final String ADDRESS_FLAT_NUMBER = "flatnumber";
    public static final String ADDRESS_CORPUS = "corpus";
    public static final String ADDRESS_ID_STREET = "idstreet";


    public static final String APPOINTMENT_TABLE = "appointment";
    public static final String APPOINTMENT_ID = "idappointment";
    public static final String APPOINTMENT_ID_EMPLOYEE = "idemployee";
    public static final String APPOINTMENT_DATE = "appointmentdate";
    public static final String APPOINTMENT_TIME = "appointmenttime";
    public static final String APPOINTMENT_EPICRISIS = "epicrisis";
    public static final String APPOINTMENT_STATUS = "status";
    public static final String APPOINTMENT_ID_PATIENT = "idpatient";
    public static final String APPOINTMENT_ID_APPTYPE = "idAppType";



    public static final String APPOINTMENT_TYPE_TABLE = "appointmenttype";
    public static final String APPOINTMENT_TYPE_ID = "idappointmentType";
    public static final String APPOINTMENT_TYPE_NAME = "name";


    public static final String EMPLOYEE_TABLE = "employee";
    public static final String EMPLOYEE_ID = "idemployee";
    public static final String EMPLOYEE_SURNAME = "surname";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_PATRONYMIC = "patronymic";
    public static final String EMPLOYEE_ID_SPECIALTY = "idspecialty";
    public static final String EMPLOYEE_BIRTHDAY = "birthdaydate";
    public static final String EMPLOYEE_ID_ADDRESS = "idaddress";
    public static final String EMPLOYEE_GENDER = "gender";
    public static final String EMPLOYEE_LOGIN = "login";
    public static final String EMPLOYEE_PASSWORD = "password";
    public static final String EMPLOYEE_WORK_TIME = "worktime";
    public static final String EMPLOYEE_OFFICE_NUMBER = "officenumber";


    public static final String PATIENT_TABLE = "patient";
    public static final String PATIENT_ID = "idpatient";
    public static final String PATIENT_SURNAME = "surname";
    public static final String PATIENT_NAME = "name";
    public static final String PATIENT_PATRONYMIC = "patronymic";
    public static final String PATIENT_BIRTHDAY = "birthdaydate";
    public static final String PATIENT_PHONE_NUMBER = "phonenumber";
    public static final String PATIENT_ID_ADDRESS = "idaddress";
    public static final String PATIENT_GENDER = "gender";


    public static final String REGION_TABLE = "region";
    public static final String REGION_ID = "idregion";
    public static final String REGION_NAME = "name";


    public static final String SPECIALTY_TABLE = "specialty";
    public static final String SPECIALTY_ID = "idspecialty";
    public static final String SPECIALTY_NAME = "name";


    public static final String STREET_TABLE = "street";
    public static final String STREET_ID = "idstreet";
    public static final String STREET_NAME = "name";
    public static final String STREET_ID_REGION = "idregion";

}
