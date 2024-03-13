package calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import calendar.DatabaseConnection;
import calendar.EditFrame;
import calendar.Event;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListFrame extends JFrame {
    private JTable eventTable;
    private JButton btnEdit;
    private JButton btnDelete;
    private CalendarPlanner calendarPlanner;
    private String currentUserName;
    static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Z32ab5x00eg4..";

    public ListFrame(String currentUserName, CalendarPlanner calendarPlanner) {
        this.currentUserName = currentUserName;
        this.calendarPlanner = calendarPlanner;
        initialize();
        updateEventTable();
        setBackground(new Color(191, 139, 255));
        setSize(960, 540);
    }

    private void initialize() {
        setTitle("Event List");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BorderLayout());

        eventTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(eventTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnEdit = new RoundButton("Edit Event", 20, Color.MAGENTA, Color.WHITE);
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnEdit.addActionListener(e -> openEditEventFrame());
        panel.add(btnEdit, BorderLayout.SOUTH);

        btnDelete = new RoundButton("Delete Event", 20, Color.MAGENTA, Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnDelete.addActionListener(e -> deleteEvent());
        panel.add(btnDelete, BorderLayout.SOUTH);
    }

    public List<Event> retrieveEventsForUser(String userName) throws SQLException {
        List<Event> events = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM events " +
                             "WHERE email = ?")) {

            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("date"),
                            resultSet.getString("description"),
                            resultSet.getString("email")
                    );
                    events.add(event);
                }
            }
        }

        return events;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void openEditEventFrame() {
        String selectedEventName = eventList.getSelectedValue();
        if (selectedEventName != null) {
            Event selectedEvent = getSelectedEvent(selectedEventName);
            EditFrame editFrame = new EditFrame(selectedEvent, this);
            editFrame.setVisible(true);
        }
    }

    private Event getSelectedEvent(String eventName) {
        // Implement this method to retrieve the selected event from the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM events WHERE title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, eventName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String date = resultSet.getString("date");
                        String description = resultSet.getString("description");
                        String email = resultSet.getString("email");
                        return new Event(id, title, date, description, email);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void deleteEvent() {
        String selectedEventName = eventList.getSelectedValue();
        if (selectedEventName != null) {
            int confirm = javax.swing.JOptionPane.showOptionDialog(null, "Are You Sure to Delete?", "Delete Event",
                    javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, null,
                    null);

            if (confirm == 0) {
                Event selectedEvent = getSelectedEvent(selectedEventName);
                if (deleteEventFromDatabase(selectedEvent.getId())) {
                    // Event deleted successfully from the database
                    // Update the event list in the UI
                    updateEventList();
                } else {
                    // Failed to delete event from the database
                    javax.swing.JOptionPane.showMessageDialog(null, "Failed to delete event from the database",
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    private boolean deleteEventFromDatabase(int eventId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "DELETE FROM events WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, eventId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEventList() {
        try {
            List<Event> events = retrieveEventsForUser(currentUserName);

            // Check if events is null before attempting to use it
            if (events == null) {
                return;
            }

            String[] eventNames = events.stream().map(Event::getTitle).toArray(String[]::new);
            eventList.setListData(eventNames);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately (show error message, log, etc.)
        }
    }

    public static void main(String[] args) {
        ListFrame frame = new ListFrame("Username", null);
        frame.setVisible(true);
    }
}
