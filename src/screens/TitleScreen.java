package screens;

import logic.Game;
import logic.PropertySaver;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;

public class TitleScreen {
    public MyScreen myScreen;
    public Game game;
    public boolean remove, inMenu = true;
    public Properties properties;

    public JLayeredPane layeredPane;
    private final Thread gameOverThread, thread, scoreThread;
    private JLabel labelReady, labelPause, labelScore;

    private Clip clip;


    public TitleScreen() {
        playBackgroundMusic();
        myScreen = new MyScreen();
        myScreen.setTitle("Menu");
        ImageIcon icon = new ImageIcon(".\\src\\Resources\\Sprites\\icon.png");
        myScreen.setIconImage(icon.getImage());

        layeredPane = myScreen.getLayeredPane();

        properties = PropertySaver.loadProperties();

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
                        ImageIcon imagePause = new ImageIcon(".\\src\\Resources\\Sprites\\pause.png");
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
        scoreThread.start();
    }

    private static class RectanglePanel extends JPanel {
        Point[] pixel;
        public RectanglePanel() {
             pixel = new Point[1000];

            for (int i = 0; i < pixel.length; i++) {
                pixel[i] = new Point((int) (Math.random() * 500), (int) (Math.random() * 500));
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 500, 500);

            g.setColor(Color.WHITE);
            for (Point point : pixel) {
                g.fillRect(point.x, point.y, 1, 1);
            }
        }
    }

    public void temp() {
        while (thread.isAlive()) {
            if (game != null) {
                gameOverThread.start();
                return;
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateScore() {
        while (scoreThread.isAlive()) {
            if (labelScore != null) labelScore.setText("Score: " + game.getScore());

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void gameOver() {
        while (gameOverThread.isAlive()) {
            if (game.isGameOver()) {
                game.setGameOver(false);
                JLabel gameOver = new JLabel();
                ImageIcon imageGameOver = new ImageIcon(".\\src\\Resources\\Sprites\\game_over.png");
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

        ImageIcon imageHeadline = new ImageIcon(".\\src\\Resources\\Sprites\\galaxy_wars.png");

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
                clip.stop();
                playButtonSelectSound();
                myScreen.setTitle("Galaxy wars - game");

                layeredPane.removeAll();
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));

                addReady();
                addScore();
                inMenu = false;
                remove = true;
                layeredPane.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelStartButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelStartButton.setForeground(Color.WHITE);
            }
        });

        labelOptionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelOptionsButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelOptionsButton.setForeground(Color.WHITE);
            }
        });

        labelStoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                playButtonSelectSound();
                layeredPane.removeAll();
                addStore();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelStoreButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelStoreButton.setForeground(Color.WHITE);
            }
        });

        layeredPane.repaint();
    }

    public void addStore() {
        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelBackButton = new JLabel("<-", SwingUtilities.CENTER);
        labelBackButton.setBounds(30, 400, 50, 25);
        labelBackButton.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelBackButton.setForeground(Color.WHITE);
        layeredPane.add(labelBackButton, Integer.valueOf(1));

        labelBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playBackgroundMusic();
                playButtonSelectSound();
                layeredPane.removeAll();
                addMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
                labelBackButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelBackButton.setForeground(Color.WHITE);
            }
        });

        layeredPane = new StoreScreen().addStore(layeredPane);
    }

    public void addGameOver() {
        inMenu = true;

        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelGameOverHeadline = new JLabel();
        JLabel labelMenuButton = new JLabel("menu", SwingUtilities.CENTER);
        JLabel labelTryAgainButton = new JLabel("try again", SwingUtilities.CENTER);
        JLabel labelQuitButton = new JLabel("quit", SwingUtilities.CENTER);

        ImageIcon imageGameOverHeadline = new ImageIcon(".\\src\\Resources\\Sprites\\game_over_headline.png");
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
                playBackgroundMusic();
                playButtonSelectSound();
                layeredPane.removeAll();
                layeredPane.repaint();
                addMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelMenuButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelMenuButton.setForeground(Color.WHITE);
            }
        });

        labelTryAgainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                remove = true;
                inMenu = false;
                layeredPane.removeAll();
                game = new Game();
                layeredPane.add(game, Integer.valueOf(0));

                addScore();

                addReady();
                layeredPane.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelTryAgainButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelTryAgainButton.setForeground(Color.WHITE);
            }
        });

        labelQuitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                System.exit(1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelQuitButton.setForeground(Color.YELLOW);
                playButtonHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelQuitButton.setForeground(Color.WHITE);
            }
        });
    }

    public void addReady() {
        labelReady = new JLabel();
        ImageIcon imageReady = new ImageIcon(".\\src\\Resources\\Sprites\\ready.png");
        labelReady.setIcon(imageReady);
        labelReady.setBounds(0, 0, 500, 500);
        layeredPane.add(labelReady, Integer.valueOf(1));
    }

    public void addScore() {
        labelScore = new JLabel("Score: " + game.getScore());
        labelScore.setForeground(Color.WHITE);
        labelScore.setFont(new Font("Retro Computer", Font.BOLD, 20));
        labelScore.setBounds(0, 0, 200, 50);
        layeredPane.add(labelScore, Integer.valueOf(2));
    }

    public void playButtonSelectSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_button_select.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playButtonHoverSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_button_hover.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        try {
            File file = new File(".\\src\\Resources\\Music\\msc_menu.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}