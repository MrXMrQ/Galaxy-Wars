import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects ;

public class TitleScreen {
    public static MyScreen myScreen;
    public Game game;

    public TitleScreen() {
        myScreen = new MyScreen();
        myScreen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(game != null) {
                    game.handleKeyReleased(e);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(game != null) {
                    game.handleKeyPressed(e);
                }
            }
        });

        JLayeredPane layeredPane = myScreen.getLayeredPane();

        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelHeadline = new JLabel();
        JLabel labelButton_0 = new JLabel();
        JLabel labelButton_1 = new JLabel();
        JLabel labelButton_2 = new JLabel();

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("GalaxyWars.png")));
        ImageIcon imageIcon_0 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Start.png")));
        ImageIcon imageIcon_1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Save.png")));

        labelHeadline.setIcon(imageIcon);
        labelButton_0.setIcon(imageIcon_0);
        labelButton_1.setIcon(imageIcon_1);

        labelHeadline.setBounds(0, 0, 500, 100);
        labelButton_0.setBounds(50, 230, 100, 50);
        labelButton_1.setBounds(200, 230, 100, 50);
        labelButton_2.setBounds(350, 230, 100, 50);

        layeredPane.add(labelHeadline, Integer.valueOf(1));
        layeredPane.add(labelButton_0, Integer.valueOf(1));
        layeredPane.add(labelButton_1, Integer.valueOf(1));
        layeredPane.add(labelButton_2, Integer.valueOf(1));

        labelButton_0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layeredPane.removeAll();
                game = new Game();
                layeredPane.add(game, Integer.valueOf(1));
                layeredPane.repaint();
            }
        });
    }

    private class RectanglePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 500, 500);

            g.setColor(Color.WHITE);
            for (int i = 0; i < 1200; i++) {
                g.fillRect((int) (Math.random() * 500), (int) (Math.random() * 500), 1, 1);
            }
        }
    }
}