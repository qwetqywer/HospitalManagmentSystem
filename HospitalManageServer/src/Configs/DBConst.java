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
    public static final String APPOINTMENT_ID_OUTPATIENT_CARD = "idoutpatientcard";


    public static final String COMPLEX_PROGRAM_TABLE = "complex_program";
    public static final String COMPLEX_PROGRAM_ID = "idcomplex_program";
    public static final String COMPLEX_PROGRAM_NAME = "name";
    public static final String COMPLEX_PROGRAM_EVENT_DATE = "eventdate";
    public static final String COMPLEX_PROGRAM_EVENT_TIME = "eventtime";
    public static final String COMPLEX_PROGRAM_APPOINTMENT_DATE = "appointmentdate";
    public static final String COMPLEX_PROGRAM_STATUS = "status";
    public static final String COMPLEX_PROGRAM_ID_EMPLOYEE = "idemployee";
    public static final String COMPLEX_PROGRAM_ID_OUTPATIENT_CARD = "idoutpatientcard";


    public static final String EMPLOYEE_TABLE = "employee";
    public static final String EMPLOYEE_ID = "idemployee";
    public static final String EMPLOYEE_SURNAME = "surname";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_PATRONYMIC = "patronymic";
    public static final String EMPLOYEE_ID_SPECIALTY = "idspecialty";
    public static final String EMPLOYEE_BIRTHDAY = "birthdaydate";
    public static final String EMPLOYEE_PASSPORT_SERIES = "passportseries";
    public static final String EMPLOYEE_PASSPORT_NUMBER = "passportnumber";
    public static final String EMPLOYEE_ID_ADDRESS = "idaddress";
    public static final String EMPLOYEE_GENDER = "gender";


    public static final String MEDICAL_EXAMINATION_TABLE = "medical_examination";
    public static final String MEDICAL_EXAMINATION_ID = "idmedical_examination";
    public static final String MEDICAL_EXAMINATION_NAME = "name";
    public static final String MEDICAL_EXAMINATION_EVENT_DATE = "eventdate";
    public static final String MEDICAL_EXAMINATION_EVENT_TIME = "eventtime";
    public static final String MEDICAL_EXAMINATION_APPOINTMENT_DATE = "appointmentdate";
    public static final String MEDICAL_EXAMINATION_ID_EMPLOYEE = "idemployee";
    public static final String MEDICAL_EXAMINATION_STATUS = "status";
    public static final String MEDICAL_EXAMINATION_ID_OUTPATIENT_CARD = "idoutpatientcard";


    public static final String OUTPATIENT_CARD_TABLE = "outpatientcard";
    public static final String OUTPATIENT_CARD_ID = "idoutpatientcard";
    public static final String OUTPATIENT_CARD_DEPARTMENT = "department";


    public static final String PATIENT_TABLE = "patient";
    public static final String PATIENT_ID = "idpatient";
    public static final String PATIENT_SURNAME = "surname";
    public static final String PATIENT_NAME = "name";
    public static final String PATIENT_PATRONYMIC = "patronymic";
    public static final String PATIENT_BIRTHDAY = "birthdaydate";
    public static final String PATIENT_PHONE_NUMBER = "phonenumber";
    public static final String PATIENT_PASSPORT_SERIES = "passportseries";
    public static final String PATIENT_PASSPORT_NUMBER = "passportnumber";
    public static final String PATIENT_ID_ADDRESS = "idaddress";
    public static final String PATIENT_GENDER = "gender";


    public static final String PERSONAL_ACCOUNT_TABLE = "personal_account";
    public static final String PERSONAL_ACCOUNT_ID = "idpersonal_account";
    public static final String PERSONAL_LOGIN = "login";
    public static final String PERSONAL_PASSWORD = "password";


    public static final String PROCEDURE_TABLE = "procedure";
    public static final String PROCEDURE_ID = "idprocedure";
    public static final String PROCEDURE_NAME = "name";
    public static final String PROCEDURE_EVENT_DATE = "eventdate";
    public static final String PROCEDURE_EVENT_TIME = "eventtime";
    public static final String PROCEDURE_APPOINTMENT_DATE = "appointmentdate";
    public static final String PROCEDURE_ID_EMPLOYEE = "idemployee";
    public static final String PROCEDURE_STATUS = "status";
    public static final String PROCEDURE_ID_OUTPATIENT_CARD = "idoutpatientcard";


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
