


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventDetailsFrame extends JFrame {

    public EventDetailsFrame(List<Event> events) {
        setTitle("Event Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(events.size(), 1));

        for (Event event : events) {
            JLabel titleLabel = new JLabel("Title: " + event.getTitle());
            JLabel dateLabel = new JLabel("Date: " + event.getDate());
            JLabel descriptionLabel = new JLabel("Description: " + event.getDescription());

            panel.add(titleLabel);
            panel.add(dateLabel);
            panel.add(descriptionLabel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane);
    }
}
