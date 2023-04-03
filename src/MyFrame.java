import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public static Game game;

    public MyFrame() {
        setVisible(true);
        setSize(new Dimension(500, 500));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        game = new Game();
        add(game);

    }
}
