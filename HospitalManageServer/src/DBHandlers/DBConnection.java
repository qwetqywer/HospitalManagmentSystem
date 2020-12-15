package DBHandlers;

import Configs.DBConfigs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection extends DBConfigs {

    private static Connection dbConnection;

    public static Connection getDbConnection() throws
            ClassNotFoundException, SQLException {
        if(dbConnection == null)
        {
            String connectionString = "jdbc:mysql://" + dbHost +
                    ":" + dbPort + "/" +dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConnection = DriverManager.getConnection(connectionString,
                    dbUser, dbPass);
        }

        return dbConnection;
    }

}