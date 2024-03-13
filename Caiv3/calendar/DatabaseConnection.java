package calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Z32ab5x00eg4..";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establish a connection
            connection = DatabaseConnection.getConnection();

            // Perform your database operations here

            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            // Close the connection in the finally block to ensure it gets closed even if an exception occurs
            DatabaseConnection.closeConnection(connection);
        }
    }
}
