import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setVisible(true);
        setSize(new Dimension(500, 500));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new FlowLayout());
        panelCenter.setBackground(Color.BLACK);

        JButton startButton = new JButton("START");
        panelCenter.add(startButton);
        startButton.addActionListener(e -> {
            dispose();
            MyFrame myFrame = new MyFrame();
        });

        JButton saveButton = new JButton("SAVE");
        panelCenter.add(saveButton);

        JButton upgrade = new JButton("UPGRADE");
        panelCenter.add(upgrade);

        add(panelCenter, BorderLayout.CENTER);
    }
}
