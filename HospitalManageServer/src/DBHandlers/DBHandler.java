package DBHandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

abstract public class DBHandler {
    protected ResultSet resSet;
    protected String select;
    protected PreparedStatement prSt;
}
