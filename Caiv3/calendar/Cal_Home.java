package calendar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

public class Cal_Home extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cal_Home frame = new Cal_Home();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cal_Home() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 540);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 960,551);
        contentPane.add(panel);
        panel.setLayout(null);

        RoundButton loginButton = new RoundButton("Log In", 20, Color.MAGENTA, Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Login frame when the "Log In" button is clicked
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                dispose();
            }
        });
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 22));
        loginButton.setBounds(746, 34, 189, 35);
        panel.add(loginButton);

        RoundButton signup = new RoundButton("Start for free", 20, Color.MAGENTA, Color.WHITE);
        signup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle signup action
            	Signup SignupFrame = new Signup();
            	SignupFrame.setVisible(true);
                dispose();
            }
        });
        signup.setFont(new Font("Tahoma", Font.BOLD, 22));
        signup.setBounds(365, 395, 189, 33);
        panel.add(signup);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\calendar\\Icon\\calhome.png"));
        lblNewLabel.setBounds(0, 0, 976, 540);
        panel.add(lblNewLabel);
    }
}
