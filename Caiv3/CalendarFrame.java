package calendar;

import javax.swing.*;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarPlanner extends JFrame {

    private JCalendar calendar;
    private DefaultListModel<String> eventListModel;
    private List<Event> allEvents;
    static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Z32ab5x00eg4..";
    private String currentUserName;
    private EventDetailsFrame detailsFrame;

    public CalendarPlanner(String currentUserName, List<Event> events) {
        this.currentUserName = currentUserName;
        this.allEvents = events;
        setTitle("MEMU: A Task Reminder and Scheduler");
        getContentPane().setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initialize();
        showEventDetails();
    }

    private void initialize() {
        calendar = new JCalendar();
        calendar.getDayChooser().setWeekOfYearVisible(false);
        getContentPane().add(calendar, BorderLayout.CENTER);

        calendar.getDayChooser().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showEventDetailsForSelectedDate();
            }
        });

        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> openAddEventFrame());

        JButton viewEventListButton = new JButton("View Event List");
        viewEventListButton.addActionListener(e -> openListFrame());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEventButton);
        buttonPanel.add(viewEventListButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Initialize the eventListModel
        eventListModel = new DefaultListModel<>();
    }

    private void showEventDetailsForSelectedDate() {
        Date selectedDate = calendar.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        List<Event> eventsForDate = retrieveEventsForDate(formattedDate);

        // Create and display the EventDetailsFrame
        detailsFrame = new EventDetailsFrame(eventsForDate);

        // Add a toggle button to show/hide event details
        JButton toggleButton = new JButton("Toggle Details");
        toggleButton.addActionListener(e -> {
            if (detailsFrame.isVisible()) {
                detailsFrame.setVisible(false);
                toggleButton.setText("Show Details");
            } else {
                detailsFrame.setVisible(true);
                toggleButton.setText("Hide Details");
            }
        });

        // Add the toggle button to the detailsFrame
        detailsFrame.getContentPane().add(toggleButton, BorderLayout.SOUTH);

        // Display the detailsFrame
        detailsFrame.setVisible(true);
    }

    private List<Event> retrieveEventsForDate(String date) {
        List<Event> eventsForDate = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events WHERE date='" + date + "' AND username='" + currentUserName + "'");

            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setTitle(resultSet.getString("title"));
                event.setDescription(resultSet.getString("description"));
                event.setDate(resultSet.getString("date"));
                event.setStartTime(resultSet.getString("start_time"));
                event.setEndTime(resultSet.getString("end_time"));
                event.setUsername(resultSet.getString("username"));
                eventsForDate.add(event);
            }

            return eventsForDate;
        }

        private void showEventDetails() {
            Date selectedDate = calendar.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(selectedDate);

            List<Event> eventsForDate = retrieveEventsForDate(formattedDate);

            // Update the eventListModel (or any other UI component) with the new events
            eventListModel.clear();
            for (Event event : eventsForDate) {
                eventListModel.addElement(event.getTitle() + " - " + event.getDate());
            }
        }

        private void openAddEventFrame() {
            AddFrame addFrame = new AddFrame(this);
            addFrame.setVisible(true);
        }

        private void openListFrame() {
            ListFrame listFrame = new ListFrame(currentUserName, this);
            listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set close operation
            listFrame.setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                List<Event> dummyEvents = new ArrayList<>();
                CalendarPlanner frame = new CalendarPlanner("YourUserName", dummyEvents);
                frame.setVisible(true);
            });
        }

        public String getCurrentUserName() {
            return currentUserName;
        }

        public void addEvent(Event event) {
            allEvents.add(event);
            showEventDetails();
        }

        public void updateEvent(Event event) {
            for (int i = 0; i < allEvents.size(); i++) {
                if (allEvents.get(i).getId() == event.getId()) {
                    allEvents.set(i, event);
                    break;
                }
            }
            showEventDetails();
        }

        public void removeEvent(Event event) {
            allEvents.remove(event);
            showEventDetails();
        }
    }