package calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import calendar.Event;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public ListFrame(String currentUserName, CalendarPlanner calendarPlanner) {
    	setResizable(false);
        this.currentUserName = currentUserName;
        this.calendarPlanner = calendarPlanner;
        initialize();
        updateEventTable();
        setBackground(new Color(191, 139, 255));
        setSize(960, 594);
    }

    private void initialize() {
        setTitle("Event List");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        eventTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBounds(0, 0, 946, 530);
        panel.add(scrollPane);

        btnDelete = new RoundButton("Delete Event", 20, Color.MAGENTA, Color.WHITE);
        btnDelete.addActionListener(e -> deleteEvent());
        btnDelete.setBounds(565, 530, 150, 27);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel.add(btnDelete);

        btnUpdate = new RoundButton("Update Event", 20, Color.magenta, Color.WHITE);
        btnUpdate.addActionListener(e -> updateEvent());
        btnUpdate.setBounds(367, 530, 150, 27);
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel.add(btnUpdate);
        
        RoundButton btnBack = new RoundButton("Home", 20, Color.MAGENTA, Color.WHITE);
        btnBack.addActionListener(e -> openCalendarFrame());
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnBack.setBounds(172, 530, 150, 27);
        panel.add(btnBack);
    }

    private void openCalendarFrame() {
    	SwingUtilities.invokeLater(() -> {
            List<Event> dummyEvents = new ArrayList<>();
            CalendarPlanner frame = new CalendarPlanner("YourUserName", dummyEvents);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); \
            dispose();
        });
      
        
    }
    private void updateEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow != -1) {
            Event selectedEvent = getSelectedEvent(selectedRow);
            if (selectedEvent != null) {
                EditFrame editFrame = new EditFrame(selectedEvent, this);
                editFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to retrieve selected event details.", "Update Event", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to update.", "Update Event", JOptionPane.WARNING_MESSAGE);
        }
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

    private Event getSelectedEvent(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) eventTable.getModel();
        int eventId = (int) model.getValueAt(rowIndex, 0); // Assuming id is stored in the first column

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM events WHERE id = ?")) {

            preparedStatement.setInt(1, eventId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Event event = new Event(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("date"),
                            resultSet.getString("description"),
                            resultSet.getString("email")
                    );
                    return event;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this event?", "Delete Event", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) eventTable.getModel();
                int eventId = (int) model.getValueAt(selectedRow, 0); // Assuming id is stored in the first column
                if (deleteEventFromDatabase(eventId)) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete event from the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.", "Delete Event", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean deleteEventFromDatabase(int eventId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM events WHERE id = ?")) {
            preparedStatement.setInt(1, eventId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    void updateEventTable() {
        try {
            List<Event> events = retrieveEventsForUser(currentUserName);
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Title");
            model.addColumn("Date");
            model.addColumn("Description");
            for (Event event : events) {
                model.addRow(new Object[]{event.getId(), event.getTitle(), event.getDate(), event.getDescription()});
            }
            eventTable.setModel(model);
            TableColumnModel columnModel = eventTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(20); // Adjust the width of the title column
            columnModel.getColumn(1).setPreferredWidth(50); // Adjust the width of the date column
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(650);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ListFrame frame = new ListFrame("Username", null);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
