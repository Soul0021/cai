package calendar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Signup extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JLabel registerprompt;
    private JLabel lblname;
    private JLabel lblEmail;
    private JTextArea EnterYourName;
    private JTextArea Email;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Z32ab5x00eg4..";
    private JLabel backgroundsign;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signup frame = new Signup();
                    frame.setLocationRelativeTo(null); 
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Signup() {
    	setTitle("SignUp");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 971, 573);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 960, 540);
        contentPane.add(panel);
        panel.setLayout(null);

        lblname = new JLabel("Your Name:");
        lblname.setBackground(Color.WHITE);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblname.setBounds(322, 160, 345, 22);
        panel.add(lblname);

        EnterYourName = new JTextArea();
        EnterYourName.setText("Enter your name");
        EnterYourName.setForeground(Color.GRAY);
        EnterYourName.setFont(new Font("Tahoma", Font.BOLD, 20));
        EnterYourName.setBounds(322, 182, 345, 41);
        panel.add(EnterYourName);

        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblEmail.setBackground(Color.WHITE);
        lblEmail.setBounds(322, 236, 345, 22);
        panel.add(lblEmail);

        Email = new JTextArea();
        Email.setFont(new Font("Tahoma", Font.BOLD, 20));
        Email.setForeground(Color.GRAY);
        Email.setText("Enter your email");
        Email.setBounds(322, 255, 345, 41);
        panel.add(Email);


        Email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Email.getText().equals("Enter your email")) {
                    Email.setText("");
                    Email.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Email.getText().isEmpty()) {
                    Email.setText("Enter your email");
                    Email.setForeground(Color.GRAY);
                }
            }
        });

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPassword.setBackground(Color.WHITE);
        lblPassword.setBounds(322, 296, 345, 22);
        panel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 20));
        passwordField.setForeground(Color.GRAY);
        passwordField.setText("Enter your password");
        passwordField.setBounds(322, 317, 345, 33);
        panel.add(passwordField);

       
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String passwordText = new String(passwordField.getPassword());
                if (passwordText.equals("Enter your password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String passwordText = new String(passwordField.getPassword());
                if (passwordText.isEmpty()) {
                    passwordField.setText("Enter your password");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        RoundButton signup = new RoundButton("Sign Up", 20, Color.MAGENTA, Color.WHITE);
        signup.setFont(new Font("Tahoma", Font.BOLD, 22));
        signup.setBounds(410, 361, 151, 33);
        panel.add(signup);

      
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredName = EnterYourName.getText();
                String enteredEmail = Email.getText();
                String enteredPassword = new String(passwordField.getPassword());

               
                if (isEmailInUse(enteredEmail)) {
                    registerprompt.setText("Account with this email already exists.");
                    registerprompt.setForeground(Color.RED);
                } else {
                   
                    int generatedUserId = generateRandomUserId();
                    insertUserToDatabase(enteredName, enteredEmail, enteredPassword, generatedUserId);
                    registerprompt.setText("Registration Successful. User ID: " + generatedUserId);
                    registerprompt.setForeground(Color.BLACK);

                   
                    Timer timer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();  
                            Login loginFrame = new Login();
                            loginFrame.setLocationRelativeTo(null);
                            loginFrame.setVisible(true);
                        }
                    });
                    timer.setRepeats(false);  // Ensure it only fires once
                    timer.start();
                }
            }
        });

        registerprompt = new JLabel("");
        registerprompt.setBounds(347, 400, 320, 33);
        panel.add(registerprompt);

        backgroundsign = new JLabel("");
        backgroundsign.setIcon(new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\calendar\\Icon\\signup.png"));
        backgroundsign.setBounds(0, 0, 960, 540);
        panel.add(backgroundsign);

       
        EnterYourName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (EnterYourName.getText().equals("Enter your name")) {
                    EnterYourName.setText("");
                    EnterYourName.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (EnterYourName.getText().isEmpty()) {
                    EnterYourName.setText("Enter your name");
                    EnterYourName.setForeground(Color.GRAY);
                }
            }
        });

        
        EnterYourName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                  
                    Email.requestFocus();
                }
            }
        });

        Email.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                   
                    EnterYourName.requestFocus();
                }
            }
        });
    }
    // for searching if the email has match in data base 
    private boolean isEmailInUse(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT * FROM users WHERE email=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
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
    // random user id generator
    private static int generateRandomUserId() {
        Random random = new Random();
        return random.nextInt(1000000);  // Adjust the range as needed
    }

    
 //sql table 
    private void insertUserToDatabase(String name, String email, String password, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO users (name, email, password, user_id) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, userId);  // Set the userId as an int
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
