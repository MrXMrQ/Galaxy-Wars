import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {
    public GameOverScreen() {
        setPreferredSize(new Dimension(400, 400));
        JLabel label = new JLabel("Label über gezeichneter Ebene");
        add(label);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,500,500);
    }
}
