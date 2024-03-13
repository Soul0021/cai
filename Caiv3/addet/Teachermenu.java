package addet;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;

public class Teachermenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teachermenu frame = new Teachermenu();
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
	public Teachermenu() {
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
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\addet\\Icon\\studentmenu1.png");
        Image originalImage = originalIcon.getImage();
        // Scale it to fit the size of the panel
        Image scaledImage = originalImage.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton editclbutton = new JButton("Edit Class List");
        editclbutton.setFont(new Font("Tahoma", Font.BOLD, 20));
        editclbutton.setBounds(650, 225, 211, 85);
        panel.add(editclbutton);
        
        JButton viewclbutton = new JButton("View Class \r\nList");
        viewclbutton.setFont(new Font("Tahoma", Font.BOLD, 20));
        viewclbutton.setBounds(397, 225, 211, 85);
        panel.add(viewclbutton);
        
        JButton btnTeacher = new JButton("Teacher");
        btnTeacher.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnTeacher.setBounds(50, 321, 255, 50);
        panel.add(btnTeacher);
        
        JButton StudentButton = new JButton("Student");
        StudentButton.setFont(new Font("Tahoma", Font.BOLD, 15));
     // Set the button's position and size: x, y, width, height
     StudentButton.setBounds(50, 201, 255, 50); // For example, width=150, height=40
     panel.add(StudentButton);
     
        JLabel Background = new JLabel("");
        Background.setIcon(scaledIcon);
        Background.setBounds(0, 0, panel.getWidth(), panel.getHeight()); // Set the bounds to match the panel size
        panel.add(Background);
	}
}
