package DBHandlers;

import Models.Employee;

import java.sql.ResultSet;

public interface EmployeeSQL {
    ResultSet getAllEmployees();
    boolean addEmployee(Employee employee);
    void deleteEmployee(Employee employee);

}
