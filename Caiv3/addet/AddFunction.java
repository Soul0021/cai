package addet;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class AddFunction extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textStudid;
    private JTextField textLname;
    private JTextField textMname;
    private JTextField textFname;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddFunction frame = new AddFunction();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AddFunction() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 331);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 300);
        contentPane.add(panel);
        panel.setLayout(null); // Set the panel layout to null for absolute positioning

        // Load the background image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\addet\\Icon\\bg1.png");
        Image originalImage = originalIcon.getImage();
        // Scale it to fit the size of the panel
        Image scaledImage = originalImage.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnAdd.setBounds(287, 240, 89, 23);
        panel.add(btnAdd);
        
        textLname = new JTextField();
        textLname.setColumns(10);
        textLname.setBounds(248, 105, 210, 23);
        panel.add(textLname);
        
        textMname = new JTextField();
        textMname.setColumns(10);
        textMname.setBounds(248, 142, 210, 23);
        panel.add(textMname);
        
        textFname = new JTextField();
        textFname.setColumns(10);
        textFname.setBounds(248, 174, 210, 23);
        panel.add(textFname);
        
        JLabel lblFirstname = new JLabel("First Name:");
        lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblFirstname.setBounds(131, 172, 91, 23);
        panel.add(lblFirstname);
        
        JLabel lblMiddleName = new JLabel("Middle Name:");
        lblMiddleName.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblMiddleName.setBounds(131, 140, 107, 23);
        panel.add(lblMiddleName);
        
        JLabel lblLastName = new JLabel("Last Name: ");
        lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblLastName.setBounds(131, 103, 107, 23);
        panel.add(lblLastName);
        
        JLabel Studidlbl = new JLabel("StudentID: ");
        Studidlbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        Studidlbl.setBounds(131, 62, 91, 25);
        panel.add(Studidlbl);
        
        textStudid = new JTextField();
        textStudid.setBounds(248, 65, 210, 23);
        panel.add(textStudid);
        textStudid.setColumns(10);

        JLabel Background = new JLabel("");
        Background.setIcon(scaledIcon);
        Background.setBounds(0, 0, 700, 300); // Set the bounds to match the panel size
        panel.add(Background);
    }
}