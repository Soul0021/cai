package addet;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassRec extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField searchtextField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClassRec frame = new ClassRec();
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
    public ClassRec() {
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
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Angel Jane D. Labuyo\\eclipse-workspace\\Caiv3\\addet\\Icon\\bg1.png");
        Image originalImage = originalIcon.getImage();
        // Scale it to fit the size of the panel
        Image scaledImage = originalImage.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton btnBack = new JButton("<- Back");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 9));
        btnBack.setBackground(new Color(153, 204, 153));
        btnBack.setForeground(Color.BLACK);
        btnBack.setBounds(0, 0, 71, 23);
        panel.add(btnBack);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(689, 40, 89, 23);
        panel.add(btnSearch);

        searchtextField = new JTextField();
        searchtextField.setText("StudentID:");
        searchtextField.setBounds(544, 41, 135, 20);
        panel.add(searchtextField);
        searchtextField.setColumns(10);
        searchtextField.setForeground(Color.GRAY); // Set the text color to gray
        searchtextField.addFocusListener(new FocusListener() { // Add a focus listener to the text field
            @Override
            public void focusGained(FocusEvent e) {
                if (searchtextField.getText().equals("StudentID:")) { // If the text field contains the prompt text
                    searchtextField.setText(""); // Clear the text field
                    searchtextField.setForeground(Color.BLACK); // Set the text color to black
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchtextField.getText().isEmpty()) { // If the text field is empty
                    searchtextField.setForeground(Color.GRAY); // Set the text color to gray
                    searchtextField.setText("StudentID:"); // Set the prompt text
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 75, 885, 500);
        panel.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel lblNewLabel = new JLabel("Class List");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel.setBounds(21, 11, 151, 50);
        panel.add(lblNewLabel);

        JButton btnaddcolunm = new JButton("Add Column");
        btnaddcolunm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnaddcolunm.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnaddcolunm.setBounds(293, 34, 116, 27);
        panel.add(btnaddcolunm);

        JButton UpdateButton = new JButton("Update");
        UpdateButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        // Set the button's position and size: x, y, width, height
        UpdateButton.setBounds(182, 34, 101, 27); // For example, width=150, height=40
        panel.add(UpdateButton);

        JLabel Background = new JLabel("");
        Background.setIcon(scaledIcon);
        Background.setBounds(0, 0, panel.getWidth(), panel.getHeight()); // Set the bounds to match the panel size
        panel.add(Background);
    }
}