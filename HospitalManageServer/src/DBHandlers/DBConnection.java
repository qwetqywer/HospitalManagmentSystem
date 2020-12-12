package DBHandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    protected static final String dbHost = "localhost";
    protected static final String dbPort = "3306";
    protected static final String dbUser = "root";
    protected static final String dbPass = "15432";
    protected static final String dbName = "hospitalsystem";

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