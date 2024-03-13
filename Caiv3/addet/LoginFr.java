package addet;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class LoginFr extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	LoginFr frame = new LoginFr();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginFr() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 899, 598);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 891, 568);
        contentPane.add(panel);
        panel.setLayout(null); // Set the panel layout to null for absolute positioning
        
        // Load the background image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\addet\\Icon\\menu1.png");
        Image originalImage = originalIcon.getImage();
        // Scale it to fit the size of the panel
        Image scaledImage = originalImage.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPassword.setBackground(Color.WHITE);
        lblPassword.setBounds(597, 320, 101, 33);
        panel.add(lblPassword);
        
        JLabel lblUSername = new JLabel("Username:");
        lblUSername.setBackground(Color.WHITE);
        lblUSername.setForeground(Color.BLACK);
        lblUSername.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUSername.setBounds(597, 224, 101, 33);
        panel.add(lblUSername);
        
        JTextArea Username = new JTextArea();
        Username.setBounds(596, 367, 247, 33);
        panel.add(Username);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(597, 276, 247, 33);
        panel.add(passwordField);
        
        JButton Loginbut = new JButton("Login");
        Loginbut.setFont(new Font("Tahoma", Font.BOLD, 22));
        Loginbut.setBounds(664, 473, 101, 50);
        panel.add(Loginbut);
        
        JLabel Background = new JLabel("");
        Background.setIcon(scaledIcon);
        Background.setBounds(0, 0, panel.getWidth(), panel.getHeight()); // Set the bounds to match the panel size
        panel.add(Background);
    }
}