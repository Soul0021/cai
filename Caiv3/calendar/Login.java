
package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Login extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel loginPrompt;
    private RoundButton loginButton;

    static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Z32ab5x00eg4..";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setLocationRelativeTo(null); 
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        setTitle("MEMU: A Task Reminder and Scheduler");
        setResizable(false);
        setAutoRequestFocus(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 540);
        getContentPane().setLayout(null);

        RoundButton signup = new RoundButton("Sign Up", 20, Color.MAGENTA, Color.WHITE);
        signup.addActionListener(e -> {
            Signup SignupFrame = new Signup();
            SignupFrame.setVisible(true);
            dispose();
        });

        loginPrompt = new JLabel("");
        loginPrompt.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(loginPrompt);
        loginPrompt.setBounds(356, 435, 233, 33);

        getContentPane().add(signup);
        signup.setFont(new Font("Tahoma", Font.BOLD, 22));
        signup.setBounds(785, 31, 151, 33);

        loginButton = new RoundButton("Log In", 20, Color.MAGENTA, Color.WHITE);
        getContentPane().add(loginButton);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 22));
        loginButton.setBounds(395, 391, 151, 33);
        loginButton.addActionListener(e -> {
            String enteredEmail = emailField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            if (!isValidCredentials(enteredEmail, enteredPassword)) {
                loginPrompt.setText("Wrong Email or password");
            } else {
                loginPrompt.setText("Login successful");
                openCalendarPlanner(enteredEmail);
            }
        });

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(passwordField);
        passwordField.setBounds(348, 347, 247, 33);
        passwordField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                handleTabKey(e, loginButton);
                handleEnterKey(e);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        JLabel lblPassword = new JLabel("Password:");
        getContentPane().add(lblPassword);
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPassword.setBounds(349, 311, 246, 33);

        JLabel lblEmail = new JLabel("Email:");
        getContentPane().add(lblEmail);
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEmail.setBounds(349, 242, 246, 33);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(emailField);
        emailField.setBounds(348, 275, 247, 33);
        emailField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                handleTabKey(e, passwordField);
                handleEnterKey(e);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        JLabel background = new JLabel(new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\calendar\\Icon\\login.png"));
        background.setBounds(0, 0, 960, 540);
        getContentPane().add(background);
    }

    private void handleTabKey(KeyEvent e, Component nextField) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            nextField.requestFocus();
        }
    }

    private void handleEnterKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButton.doClick();
        }
    }

    private void openCalendarPlanner(String email) {
        List<Event> userEvents = retrieveEventsForUser(email);
        CalendarPlanner calendarPlanner = new CalendarPlanner(email, userEvents);
        calendarPlanner.setLocationRelativeTo(null);
        calendarPlanner.setVisible(true);
        dispose(); // Close the Login frame
    }

    private List<Event> retrieveEventsForUser(String email) {
        List<Event> userEvents = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM events WHERE email=?"; // Modified to select by email
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String eventDate = resultSet.getString("date");
                String description = resultSet.getString("description");
                String userEmail = resultSet.getString("email"); // Modified to fetch by email

                Event event = new Event();
                userEvents.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userEvents;
    }


    private boolean isValidCredentials(String enteredEmail, String enteredPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM users WHERE Email=? AND password=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, enteredEmail);
            preparedStatement.setString(2, enteredPassword);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
