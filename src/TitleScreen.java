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
    JLayeredPane layeredPane;
    Thread gameOverThread, thread, scoreThread;
    JLabel labelReady, labelScore;

    public TitleScreen() {
        myScreen = new MyScreen();
        myScreen.setTitle("Menu");

        layeredPane = myScreen.getLayeredPane();

        addMenu();

        myScreen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (game != null) {
                    game.handleKeyReleased(e);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (game != null) {
                    game.handleKeyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && remove) {
                        remove = false;
                        layeredPane.remove(labelReady);
                    }
                }
            }
        });
        thread = new Thread(this::temp);
        thread.start();

        gameOverThread = new Thread(this::gameOver);

        scoreThread = new Thread(this::updateScore);
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

    public void temp() {
        while (thread.isAlive()) {
            if (game != null) {
                gameOverThread.start();
                return;
            }
        }
    }

    public void gameOver() {
        while (gameOverThread.isAlive()) {
            if (game.isGameOver()) {
                game.setGameOver(false);
                JLabel gameOver = new JLabel();
                ImageIcon imageGameOver = new ImageIcon(Objects.requireNonNull(getClass().getResource("GameOver.png")));
                gameOver.setIcon(imageGameOver);
                gameOver.setBounds(0, 0, 500, 500);
                layeredPane.add(gameOver, Integer.valueOf(2));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                layeredPane.removeAll();
                addGameOver();
            }
        }
    }

    public void addMenu() {
        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelHeadline = new JLabel();
        JLabel labelStartButton = new JLabel();
        JLabel labelButton_1 = new JLabel();
        JLabel labelButton_2 = new JLabel();
        labelReady = new JLabel();

        ImageIcon imageHeadline = new ImageIcon(Objects.requireNonNull(getClass().getResource("GalaxyWars.png")));
        ImageIcon imageStarButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Start.png")));
        ImageIcon imageReady = new ImageIcon(Objects.requireNonNull(getClass().getResource("Ready.png")));

        labelHeadline.setIcon(imageHeadline);
        labelStartButton.setIcon(imageStarButton);
        labelReady.setIcon(imageReady);

        labelHeadline.setBounds(0, 0, 500, 100);
        labelStartButton.setBounds(50, 230, 100, 50);
        labelButton_1.setBounds(200, 230, 100, 50);
        labelButton_2.setBounds(350, 230, 100, 50);
        labelReady.setBounds(0, 0, 500, 500);

        labelButton_1.setBackground(Color.YELLOW);
        labelButton_2.setBackground(Color.YELLOW);
        labelButton_1.setOpaque(true);
        labelButton_2.setOpaque(true);

        layeredPane.add(labelHeadline, Integer.valueOf(1));
        layeredPane.add(labelStartButton, Integer.valueOf(1));
        layeredPane.add(labelButton_1, Integer.valueOf(1));
        layeredPane.add(labelButton_2, Integer.valueOf(1));

        labelStartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layeredPane.removeAll();
                myScreen.setTitle("Galaxy wars - game");
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));
                layeredPane.add(labelReady, Integer.valueOf(1));

                JLabel labelScore = new JLabel("Score: " + game.getScore());
                labelScore.setFont(new Font("Retro Computer", Font.BOLD, 12));
                labelScore.setBounds(0, 0, 100, 50);
                layeredPane.add(labelScore, Integer.valueOf(2));

                layeredPane.repaint();
                remove = true;
            }
        });

        layeredPane.repaint();
    }

    public void updateScore() {

    }

    public void addGameOver() {
        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelGameOverHeadline = new JLabel();
        JLabel labelMenuButton = new JLabel();
        JLabel labelTryAgainButton = new JLabel();
        JLabel labelQuitButton = new JLabel();

        ImageIcon imageGameOverHeadline = new ImageIcon(Objects.requireNonNull(getClass().getResource("GameOverHeadline.png")));
        ImageIcon imageBackButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Menu.png")));
        ImageIcon imageTryAgainButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("TryAgain.png")));
        ImageIcon imageQuitButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("Quit.png")));

        labelGameOverHeadline.setIcon(imageGameOverHeadline);
        labelMenuButton.setIcon(imageBackButton);
        labelTryAgainButton.setIcon(imageTryAgainButton);
        labelQuitButton.setIcon(imageQuitButton);

        labelGameOverHeadline.setBounds(10, 0, 500, 100);
        labelMenuButton.setBounds(185, 150, 100, 50);
        labelTryAgainButton.setBounds(120, 250, 220, 50);
        labelQuitButton.setBounds(185, 350, 100, 50);

        layeredPane.add(labelGameOverHeadline, Integer.valueOf(1));
        layeredPane.add(labelMenuButton, Integer.valueOf(1));
        layeredPane.add(labelTryAgainButton, Integer.valueOf(1));
        layeredPane.add(labelQuitButton, Integer.valueOf(1));

        labelMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layeredPane.removeAll();
                layeredPane.repaint();
                addMenu();
            }
        });

        labelTryAgainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove = true;
                layeredPane.removeAll();
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));
                layeredPane.add(labelReady, Integer.valueOf(1));
                layeredPane.repaint();
            }
        });

        labelQuitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }
        });
    }
}