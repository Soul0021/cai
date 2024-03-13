package calendar;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JCalendar;

public class Addframev2 extends JFrame {
    private JTextField taskNameField;
    private JTextField descriptionField;
    private JButton homeButton;
    private JButton listButton;
    private JButton setDateButton;
    private JButton remindersButton;
    private ListFrame listFrame;
    private CalendarPlanner calendarPlanner;
    JTextComponent dateField;
    static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Z32ab5x00eg4..";

    /**
     * @wbp.parser.constructor
     */
    public Addframev2(ListFrame listFrame) {
        this.listFrame = listFrame;
        initialize();
    }

    public Addframev2(CalendarPlanner calendarPlanner) {
        this.calendarPlanner = calendarPlanner;
        initialize();
    }

    private void initialize() {
        setTitle("Add Task");
        setBounds(100, 100, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblTaskName = new JLabel("Task Name:");
        lblTaskName.setBounds(20, 20, 100, 25);
        panel.add(lblTaskName);

        taskNameField = new JTextField();
        taskNameField.setBounds(130, 20, 200, 25);
        panel.add(taskNameField);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(20, 60, 100, 25);
        panel.add(lblDescription);

        descriptionField = new JTextField();
        descriptionField.setBounds(130, 60, 200, 25);
        panel.add(descriptionField);

        // Add labels for the text fields
        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(20, 100, 100, 25);
        panel.add(lblDate);

        JTextField dateField = new JTextField(); // Declare and initialize dateField
        dateField.setBounds(130, 100, 100, 25); // Adjust bounds as needed
        panel.add(dateField);

        JButton btnSaveTask = new JButton("Add Task");
        btnSaveTask.setBounds(130, 200, 150, 25);
        btnSaveTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTaskToDatabase();
            }
        });
        panel.add(btnSaveTask);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(300, 200, 100, 25);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancel);

        // Home Button
        homeButton = new JButton("Home");
        homeButton.setBounds(20, 136, 100, 25);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCalendarFrame();
            }
        });
        panel.add(homeButton);

        // Set Date Button
        setDateButton = new JButton("Set Date");
        setDateButton.setBounds(20, 200, 100, 25);
        setDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a date picker dialog
                JFrame dateFrame = new JFrame("Select Date");
                dateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dateFrame.setSize(300, 200);
                dateFrame.setLocationRelativeTo(null);

                JCalendar calendar = new JCalendar();
                calendar.addPropertyChangeListener("calendar", evt -> {
                    Date selectedDate = calendar.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = dateFormat.format(selectedDate);
                    dateField.setText(dateString);
                    dateFrame.dispose(); // Close the dialog after selecting the date
                });

                dateFrame.add(calendar);
                dateFrame.setVisible(true);
            }
        });
        panel.add(setDateButton);

        // Reminders Button
        remindersButton = new JButton("Reminders");
        remindersButton.setBounds(130, 140, 100, 25);
        remindersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
				// Get the selected date from the date field
                String dateString = dateField.getText(); // Assuming dateField is the JTextField where the user inputs the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date selectedDate = null;
                try {
                    selectedDate = dateFormat.parse(dateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    return;
                }

                // Assuming you have a reference to your calendar component
                JCalendar calendarComponent = calendarPlanner.getCalendarComponent();
                if (calendarComponent != null) {
                    // Set the selected date on the calendar component
                    calendarComponent.setDate(selectedDate);

                    // Highlight the selected date on the calendar
                    calendarComponent.setDecorationBackgroundColor(Color.RED); // You can change the color as needed
                    calendarComponent.repaint(); // Refresh the calendar to reflect changes
                } else {
                    System.err.println("Calendar component is null. Make sure it is properly initialized.");
                }
            }
        });

        panel.add(remindersButton);
    }

    private void openCalendarFrame() {
        CalendarPlanner frame = new CalendarPlanner("YourUserName", null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        dispose();
    }

    private void saveTaskToDatabase() {
        String newTaskName = taskNameField.getText();
        String newDescription = descriptionField.getText();

        // Use the generated task ID
        int randomEventId = RandomUserIdGenerator.generateRandomEventId();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO tasks (id, email, task_name, description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, randomEventId);
                preparedStatement.setString(2, "YourUserName");
                preparedStatement.setString(3, newTaskName);
                preparedStatement.setString(4, newDescription);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Close the AddFrame after saving the task
        dispose();

        // Update the list frame to reflect changes
        if (listFrame != null) {
            listFrame.updateEventTable();
        }
    }

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
            CalendarPlanner calendarPlanner = new CalendarPlanner("YourUserName", null); // Replace null with appropriate parameter
            Addframev2 frame = new Addframev2(calendarPlanner);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
