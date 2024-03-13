package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radius;
    private Color backgroundColor;
    private Color textColor;

    public RoundButton(String text, int radius, Color backgroundColor, Color textColor) {
        super(text);
        this.radius = radius;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        setContentAreaFilled(false); // Make content area transparent
        setFocusPainted(false);
        setForeground(textColor);
    }

    public void setRadius(int newRadius) {
        this.radius = newRadius;
        repaint(); // Repaint to reflect the new radius
    }

    public void setBackgroundColor(Color newColor) {
        this.backgroundColor = newColor;
        repaint(); // Repaint to reflect the new color
    }

    public void setTextColor(Color newColor) {
        this.textColor = newColor;
        setForeground(newColor); // Set the new text color
        repaint(); // Repaint to reflect the new text color
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Draw border (if needed)
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        g2.dispose();
    }
}
