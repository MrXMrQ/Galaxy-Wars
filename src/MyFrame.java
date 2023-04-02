import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setBackground(Color.BLACK);
        setVisible(true);
        setSize(new Dimension(500,500));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Player player = new Player();
        add(player);
    }
}
