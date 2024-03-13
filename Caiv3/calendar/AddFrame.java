package calendar;

import java.awt.Color;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
public class AddFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField titleField;
    private JTextField dateField;
    private JTextField descriptionField;
    private ListFrame listFrame;
    private CalendarPlanner calendarPlanner;
    private JDateChooser dateChooser;
    public AddFrame(ListFrame listFrame) {
        this.listFrame = listFrame;
        initialize();
    }

    public AddFrame(CalendarPlanner calendarPlanner) {
        this.calendarPlanner = calendarPlanner;
        initialize();
        
    }

    private void initialize() {
        setTitle("Add Event");
        setBounds(100, 100, 300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setBackground(new Color(191, 139, 255));
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setBounds(20, 20, 80, 25);
        panel.add(lblTitle);

        titleField = new JTextField();
        titleField.setBounds(120, 20, 150, 25);
        panel.add(titleField);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(20, 60, 150, 25);
        panel.add(lblDate);
        
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(120, 60, 150, 25);
        panel.add(dateChooser);
        
        dateField = new JTextField();
        dateField.setBounds(180, 60, 90, 25);
        panel.add(dateField);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(20, 100, 80, 25);
        panel.add(lblDescription);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 100, 150, 25);
        panel.add(descriptionField);

        RoundButton btnSave = new RoundButton("Save Event", 10, Color.magenta, Color.WHITE);
        btnSave.setBounds(80, 130, 150, 25);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEventToDatabase();
            }
        });
        panel.add(btnSave);
    }

    private void saveEventToDatabase() {
        String newTitle = titleField.getText();
        String newDescription = descriptionField.getText();

        // Get the selected date from the JDateChooser
        java.util.Date selectedDate = dateChooser.getDate();
        // Format the selected date to match the database date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = sdf.format(selectedDate);

        // Use the generated event ID
        int eventId = RandomUserIdGenerator.generateRandomEventId();

        try {
            // Ensure calendarPlanner is not null before using it
            if (calendarPlanner != null) {
                try (Connection connection = DriverManager.getConnection(CalendarPlanner.DB_URL,
                        CalendarPlanner.DB_USER, CalendarPlanner.DB_PASSWORD)) {
                    String query = "INSERT INTO events (id, email, title, date, description) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, eventId);
                        preparedStatement.setString(2, calendarPlanner.getCurrentUserName());
                        preparedStatement.setString(3, newTitle);
                        preparedStatement.setString(4, newDate);
                        preparedStatement.setString(5, newDescription);
                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Close the AddFrame after saving the event
                dispose();

                // Update the list frame to reflect changes
                if (listFrame != null) {
                    listFrame.updateEventTable();
                }
            } else {
                System.out.println("CalendarPlanner is null");
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}