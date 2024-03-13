package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditFrame extends JFrame {
    private Event event;
    private ListFrame parentFrame;
    private JTextField txtTitle;
    private JTextField txtDate;
    private JTextArea txtDescription;
    private JButton btnSave;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/calendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Z32ab5x00eg4..";

    public EditFrame(Event event, ListFrame parentFrame) {
        this.event = event;
        this.parentFrame = parentFrame;
        initialize();
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void initialize() {
        setTitle("Edit Event");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setBounds(30, 30, 80, 20);
        panel.add(lblTitle);

        txtTitle = new JTextField(event.getTitle());
        txtTitle.setBounds(120, 30, 200, 20);
        panel.add(txtTitle);

        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(30, 70, 80, 20);
        panel.add(lblDate);

        txtDate = new JTextField(event.getDate());
        txtDate.setBounds(120, 70, 200, 20);
        panel.add(txtDate);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(30, 110, 80, 20);
        panel.add(lblDescription);

        txtDescription = new JTextArea(event.getDescription());
        txtDescription.setBounds(120, 110, 200, 80);
        panel.add(txtDescription);

        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        btnSave.setBounds(150, 210, 80, 30);
        panel.add(btnSave);
    }

    private void saveChanges() {
        String newTitle = txtTitle.getText().trim();
        String newDate = txtDate.getText().trim();
        String newDescription = txtDescription.getText().trim();

        if (newTitle.isEmpty() || newDate.isEmpty() || newDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE events SET title = ?, date = ?, description = ? WHERE id = ?")) {

            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newDate);
            preparedStatement.setString(3, newDescription);
            preparedStatement.setInt(4, event.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Event updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.updateEventTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update event.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
