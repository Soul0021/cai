package calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RandomUserIdGenerator {

    private static final int ID_LENGTH = 6;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Z32ab5x00eg4..";

    public static void main(String[] args) {
        String randomUserId = String.valueOf(generateRandomUserId());
        System.out.println("Random User ID: " + randomUserId);
        insertUserId(randomUserId);

        int randomEventId = generateRandomEventId();
        System.out.println("Random Event ID: " + randomEventId);
        insertEventId(randomEventId);
    }

    private static int generateRandomUserId() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);  // Generates a random 6-digit integer
    }

    static int generateRandomEventId() {
        Random random = new Random();
        return 10000 + random.nextInt(90000); // Generates a random 5-digit integer
    }

    private static void insertUserId(String userId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (user_id) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userId);
                preparedStatement.executeUpdate();
                System.out.println("User ID inserted into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertEventId(int eventId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO events (id) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, eventId);
                preparedStatement.executeUpdate();
                System.out.println("Event ID inserted into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
