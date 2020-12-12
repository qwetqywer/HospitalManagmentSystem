import Configs.DBConst;
import DBHandlers.AddressDBHandler;
import DBHandlers.PatientDBHandler;
import Models.Patient;
import Models.Street;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            Patient patient = (Patient)request.readObject();
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
}