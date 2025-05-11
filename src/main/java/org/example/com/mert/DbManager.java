package org.example.com.mert;

import java.sql.*;

public class DbManager {

    private final String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public void initializeDatabase() throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE departments (id INT PRIMARY KEY, department_name VARCHAR(125))");
            statement.execute("CREATE TABLE employees (id INT PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), department_id INT, FOREIGN KEY (department_id) REFERENCES departments(id))");

            statement.execute("CREATE ALIAS COUNT_DEPARTMENTS FOR \"org.example.com.mert.Procedures.countDepartments\"");
            statement.execute("CREATE ALIAS COUNT_EMPLOYEES FOR \"org.example.com.mert.Procedures.countEmployees\"");
            statement.execute("CREATE ALIAS COUNT_EMPLOYEES_BY_DEPARTMENT_ID FOR \"org.example.com.mert.Procedures.countEmployeesByDepartmentId\"");

            statement.execute("INSERT INTO departments VALUES (1, 'IT'), (2, 'HR')");
            statement.execute("INSERT INTO employees VALUES (1, 'Mert', 'Yılmaz', 1), (2, 'Başar', 'Abak', 1), (3, 'İlker', 'Kaya', 2)");
        }
    }

    public int callCountDepartments() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{? = call COUNT_DEPARTMENTS()}")) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            return statement.getInt(1);
        }
    }

    public int callCountEmployees() throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{? = call COUNT_EMPLOYEES()}")) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            return statement.getInt(1);
        }
    }

    public int callCountEmployeesByDepartmentId(int departmentId) throws SQLException {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{? = call COUNT_EMPLOYEES_BY_DEPARTMENT_ID(?)}")) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setInt(2, departmentId);
            statement.execute();
            return statement.getInt(1);
        }
    }
}
