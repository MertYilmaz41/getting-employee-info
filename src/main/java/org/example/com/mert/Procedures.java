package org.example.com.mert;


import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.JdbcSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Procedures {

    public static int countDepartments(Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM departments");
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public static int countEmployees(Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM employees");
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public static int countEmployeesByDepartmentId(Connection conn, int departmentId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM employees WHERE department_id = ?")) {
            statement.setInt(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }
}
