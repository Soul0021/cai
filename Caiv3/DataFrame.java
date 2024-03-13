import javax.swing.JFrame;
import javax.swing.*;

class DateFrame extends JFrame {
    public DateFrame(String date) {
        setTitle("Date: " + date);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200, 100);
        setLocationRelativeTo(null);
    }
}