import Logic.Game;
import Screens.MyScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class TitleScreen {
    public static MyScreen myScreen;
    public Game game;
    public boolean remove = true;

    public TitleScreen() {
        myScreen = new MyScreen();


        JLayeredPane layeredPane = myScreen.getLayeredPane();

        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelHeadline = new JLabel();
        JLabel labelButton_0 = new JLabel();
        JLabel labelButton_1 = new JLabel();
        JLabel labelButton_2 = new JLabel();
        JLabel labelReady = new JLabel();

        ImageIcon imageHeadline = new ImageIcon(Objects.requireNonNull(getClass().getResource("GalaxyWars.png")));
        ImageIcon imageButton_0 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Start.png")));
        ImageIcon imageButton_1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Save.png")));
        ImageIcon imageReady = new ImageIcon(Objects.requireNonNull(getClass().getResource("Ready.png")));

        labelHeadline.setIcon(imageHeadline);
        labelButton_0.setIcon(imageButton_0);
        labelButton_1.setIcon(imageButton_1);
        labelReady.setIcon(imageReady);

        labelHeadline.setBounds(0, 0, 500, 100);
        labelButton_0.setBounds(50, 230, 100, 50);
        labelButton_1.setBounds(200, 230, 100, 50);
        labelButton_2.setBounds(350, 230, 100, 50);
        labelReady.setBounds(0, 0, 500, 500);

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
                layeredPane.add(labelReady, Integer.valueOf(2));
                layeredPane.repaint();
            }
        });

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
                    if(e.getKeyCode() == KeyEvent.VK_ENTER && remove) {
                        layeredPane.remove(labelReady);
                        remove = false;
                    }
                }
            }
        });
    }

    private static class RectanglePanel extends JPanel {
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