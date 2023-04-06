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
    JLabel labelReady, labelPause;
    private boolean inMenu = true;

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
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && game.isPause() && !inMenu) {
                        labelPause = new JLabel();
                        ImageIcon imagePause = new ImageIcon(Objects.requireNonNull(getClass().getResource("Pause.png")));
                        labelPause.setIcon(imagePause);
                        labelPause.setBounds(0, 0, 500, 500);
                        layeredPane.add(labelPause, Integer.valueOf(1));

                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && labelPause != null) {
                        layeredPane.remove(labelPause);
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
        inMenu = true;

        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelHeadline = new JLabel();
        JLabel labelStartButton = new JLabel("start", SwingConstants.CENTER);
        JLabel labelOptionsButton = new JLabel("options", SwingConstants.CENTER);
        JLabel labelStoreButton = new JLabel("store", SwingConstants.CENTER);

        ImageIcon imageHeadline = new ImageIcon(Objects.requireNonNull(getClass().getResource("GalaxyWars.png")));

        labelHeadline.setIcon(imageHeadline);

        labelStartButton.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelStoreButton.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelOptionsButton.setFont(new Font("Retro Computer", Font.BOLD, 20));

        labelStartButton.setForeground(Color.WHITE);
        labelStoreButton.setForeground(Color.WHITE);
        labelOptionsButton.setForeground(Color.WHITE);

        labelHeadline.setBounds(0, 0, 500, 100);
        labelStartButton.setBounds(50, 230, 100, 50);
        labelOptionsButton.setBounds(150, 230, 200, 50);
        labelStoreButton.setBounds(350, 230, 100, 50);


        layeredPane.add(labelHeadline, Integer.valueOf(1));
        layeredPane.add(labelStartButton, Integer.valueOf(1));
        layeredPane.add(labelOptionsButton, Integer.valueOf(1));
        layeredPane.add(labelStoreButton, Integer.valueOf(1));

        labelStartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layeredPane.removeAll();
                myScreen.setTitle("Galaxy wars - game");
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));

                addReady();
                inMenu = false;

                JLabel labelScore = new JLabel("Score: " + game.getScore());
                labelScore.setFont(new Font("Retro Computer", Font.BOLD, 12));
                labelScore.setBounds(0, 0, 100, 50);
                layeredPane.add(labelScore, Integer.valueOf(2));

                layeredPane.repaint();
                remove = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelStartButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelStartButton.setForeground(Color.WHITE);
            }
        });

        labelOptionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelOptionsButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelOptionsButton.setForeground(Color.WHITE);
            }
        });

        labelStoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelStoreButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelStoreButton.setForeground(Color.WHITE);
            }
        });

        layeredPane.repaint();
    }

    public void updateScore() {

    }

    public void addGameOver() {
        inMenu = true;

        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelGameOverHeadline = new JLabel();
        JLabel labelMenuButton = new JLabel("menu", SwingUtilities.CENTER);
        JLabel labelTryAgainButton = new JLabel("Try again", SwingUtilities.CENTER);
        JLabel labelQuitButton = new JLabel("Quit", SwingUtilities.CENTER);

        ImageIcon imageGameOverHeadline = new ImageIcon(Objects.requireNonNull(getClass().getResource("GameOverHeadline.png")));
        labelGameOverHeadline.setIcon(imageGameOverHeadline);

        labelMenuButton.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelTryAgainButton.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelQuitButton.setFont(new Font("Retro Computer", Font.BOLD, 20));

        labelMenuButton.setForeground(Color.WHITE);
        labelTryAgainButton.setForeground(Color.WHITE);
        labelQuitButton.setForeground(Color.WHITE);

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

            @Override
            public void mouseEntered(MouseEvent e) {
                labelMenuButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelMenuButton.setForeground(Color.WHITE);
            }
        });

        labelTryAgainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove = true;
                inMenu = false;
                layeredPane.removeAll();
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));
                addReady();
                layeredPane.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelTryAgainButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelTryAgainButton.setForeground(Color.WHITE);
            }
        });

        labelQuitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelQuitButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelQuitButton.setForeground(Color.WHITE);
            }
        });
    }

    public void addReady() {
        labelReady = new JLabel();
        ImageIcon imageReady = new ImageIcon(Objects.requireNonNull(getClass().getResource("Ready.png")));
        labelReady.setIcon(imageReady);
        labelReady.setBounds(0, 0, 500, 500);
        layeredPane.add(labelReady, Integer.valueOf(1));
    }
}