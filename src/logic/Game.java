package logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Properties;

public class Game extends JPanel {
    //PLAYER
    private static final int PLAYER_SIZE = 20;
    private static final int PLAYER_SPEED = 5;

    private int playerDirectionX;
    private int playerDirectionY;

    private int life;
    //ENEMY
    private static final int ENEMY_SIZE = 30;
    private static final int ENEMY_SPEED = 1;

    //Point
    private final Point enemyPos_0, enemyPos_1, enemyPos_2, enemyPos_3, enemyPos_4, enemyPos_5, beamPos, playerPos, lifePos;

    //THREADS
    public Thread playerThread, enemyThread, collisonThread;

    private int score = 0;
    public boolean starter = true;
    private boolean shot, gameOver = false;
    private boolean pause = false;
    private boolean game = false;

    private final Point[] pixel;
    private Properties properties = PropertySaver.loadProperties();

    private final Image imageLifes = Toolkit.getDefaultToolkit().getImage(".\\src\\Resources\\Sprites\\skins\\life.png");

    private final Image imagePlayer = Toolkit.getDefaultToolkit().getImage(properties.getProperty("playerPath"));
    private final Image imageEnemy = Toolkit.getDefaultToolkit().getImage(properties.getProperty("enemyPath"));
    private final Image imageBeam = Toolkit.getDefaultToolkit().getImage(properties.getProperty("beamPath"));


    public Game() {
        setBounds(0, 0, 500, 500);
        setFocusable(true);

        playerPos = new Point(230, 400);

        playerDirectionX = 0;
        playerDirectionY = 0;

        life = 3;
        lifePos = new Point(370, 10);

        enemyPos_0 = new Point((int) (Math.random() * 470), 0);
        enemyPos_1 = new Point((int) (Math.random() * 470), 0);
        enemyPos_2 = new Point((int) (Math.random() * 470), 0);
        enemyPos_3 = new Point((int) (Math.random() * 470), 0);
        enemyPos_4 = new Point((int) (Math.random() * 470), 0);
        enemyPos_5 = new Point((int) (Math.random() * 470), 0);

        beamPos = new Point(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y);

        playerThread = new Thread(this::playerMovement);
        enemyThread = new Thread(this::enemyMovement);
        collisonThread = new Thread(this::collision);

        pixel = new Point[1000];

        for (int i = 0; i < pixel.length; i++) {
            pixel[i] = new Point((int) (Math.random() * 500), (int) (Math.random() * 500));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.WHITE);
        for (Point point : pixel) {
            g.fillRect(point.x, point.y, 1, 1);
        }

        //beam
        g.drawImage(imageBeam, beamPos.x, beamPos.y - 30, null);

        //player
        g.drawImage(imagePlayer, playerPos.x, playerPos.y, null);

        //enemy
        g.setColor(Color.GRAY);
        g.drawImage(imageEnemy, enemyPos_0.x, enemyPos_0.y, null);
        g.drawImage(imageEnemy, enemyPos_1.x, enemyPos_1.y, null);
        g.drawImage(imageEnemy, enemyPos_2.x, enemyPos_2.y, null);
        g.drawImage(imageEnemy, enemyPos_3.x, enemyPos_3.y, null);
        g.drawImage(imageEnemy, enemyPos_4.x, enemyPos_4.y, null);
        g.drawImage(imageEnemy, enemyPos_5.x, enemyPos_5.y, null);

        //lifes
        if (life == 3) {
            g.drawImage(imageLifes, lifePos.x, lifePos.y, null);
            g.drawImage(imageLifes, lifePos.x + 40, lifePos.y, null);
            g.drawImage(imageLifes, lifePos.x + 80, lifePos.y, null);
        } else if (life == 2) {
            g.drawImage(imageLifes, lifePos.x + 40, lifePos.y, null);
            g.drawImage(imageLifes, lifePos.x + 80, lifePos.y, null);
        } else if (life == 1) {
            g.drawImage(imageLifes, lifePos.x + 80, lifePos.y, null);
        }

        if (game) {
            g.setColor(new Color(116, 108, 108, 38));
            g.fillRect(0, 0, 500, 500);
        }
    }

    public void start() {
        starter = false;
        playerThread.start();
        enemyThread.start();
        collisonThread.start();
    }

    public void playerMovement() {
        while (true) {
            if (playerThread.isAlive() && !pause && !game) {
                playerPos.x += playerDirectionX * PLAYER_SPEED;
                playerPos.y += playerDirectionY * PLAYER_SPEED;

                if (playerPos.x < 0) playerPos.x = 465;
                if (playerPos.y < 0) playerPos.y = 0;
                if (playerPos.x > 465) playerPos.x = 0;
                if (playerPos.y > 440) playerPos.y = 440;

                if (!shot) beamPos.setLocation(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y + PLAYER_SIZE);
                else {
                    beamPos.y -= 10;
                    if (beamPos.y < 0) shot = false;
                }
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void enemyMovement() {
        while (true) {
            if (enemyThread.isAlive() && !pause && !game) {
                enemyPos_0.y += ENEMY_SPEED;
                enemyPos_1.y += ENEMY_SPEED + 2;
                enemyPos_2.y += ENEMY_SPEED + 3;
                enemyPos_3.y += ENEMY_SPEED + 1;
                enemyPos_4.y += ENEMY_SPEED + 0.5;
                enemyPos_5.y += ENEMY_SPEED + 1.5;

                if (enemyPos_0.y > 440) {
                    enemyPos_0.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                if (enemyPos_1.y > 440) {
                    enemyPos_1.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                if (enemyPos_2.y > 440) {
                    enemyPos_2.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                if (enemyPos_3.y > 440) {
                    enemyPos_3.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                if (enemyPos_4.y > 440) {
                    enemyPos_4.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                if (enemyPos_5.y > 440) {
                    enemyPos_5.setLocation((int) (Math.random() * 470), 0);
                    score++;
                    playScore();
                }
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void collision() {
        JPanel panelPlayer = new JPanel();

        JPanel panelEnemy_0 = new JPanel();
        JPanel panelEnemy_1 = new JPanel();
        JPanel panelEnemy_2 = new JPanel();
        JPanel panelEnemy_3 = new JPanel();
        JPanel panelEnemy_4 = new JPanel();
        JPanel panelEnemy_5 = new JPanel();

        JPanel panelBeam = new JPanel();

        while (collisonThread.isAlive()) {
            panelPlayer.setBounds(playerPos.x, playerPos.y, PLAYER_SIZE, PLAYER_SIZE);

            panelEnemy_0.setBounds(enemyPos_0.x, enemyPos_0.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_1.setBounds(enemyPos_1.x, enemyPos_1.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_2.setBounds(enemyPos_2.x, enemyPos_2.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_3.setBounds(enemyPos_3.x, enemyPos_3.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_4.setBounds(enemyPos_4.x, enemyPos_4.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_5.setBounds(enemyPos_5.x, enemyPos_5.y, ENEMY_SIZE, ENEMY_SIZE);

            panelBeam.setBounds(beamPos.x, beamPos.y - 1, 5, 1);
            panelBeam.setBackground(Color.BLUE);

            if (panelBeam.bounds().intersects(panelEnemy_0.getBounds())) {
                enemyPos_0.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }
            if (panelBeam.bounds().intersects(panelEnemy_1.getBounds())) {
                enemyPos_1.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }
            if (panelBeam.bounds().intersects(panelEnemy_2.getBounds())) {
                enemyPos_2.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }
            if (panelBeam.bounds().intersects(panelEnemy_3.getBounds())) {
                enemyPos_3.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }
            if (panelBeam.bounds().intersects(panelEnemy_4.getBounds())) {
                enemyPos_4.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }
            if (panelBeam.bounds().intersects(panelEnemy_5.getBounds())) {
                enemyPos_5.setLocation((int) (Math.random() * 470), 0);
                asteroidHitAction();
            }

            if (panelPlayer.bounds().intersects(panelEnemy_0.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_1.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_2.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_3.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_4.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_5.getBounds())) {
                life--;

                enemyPos_0.setLocation((int) (Math.random() * 470), 0);
                enemyPos_1.setLocation((int) (Math.random() * 470), 0);
                enemyPos_2.setLocation((int) (Math.random() * 470), 0);
                enemyPos_3.setLocation((int) (Math.random() * 470), 0);
                enemyPos_4.setLocation((int) (Math.random() * 470), 0);
                enemyPos_5.setLocation((int) (Math.random() * 470), 0);

                playerPos.setLocation(230, 400);
                beamPos.setLocation(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y + PLAYER_SIZE);

                game = true;

                try {
                    File file = new File(".\\src\\Resources\\Sounds\\snd_hurt.wav");
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);

                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-16.0f);

                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (life == 0) {
                    try {
                        File file = new File(".\\src\\Resources\\Sounds\\snd_game_over.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-16.0f);
                        clip.start();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    game = false;
                    gameOver = true;

                    int totalScore = Integer.parseInt(properties.getProperty("totalScore")) + score;
                    int highScore = Integer.parseInt(properties.getProperty("highScore"));

                    if(score > highScore) {
                        properties.setProperty("highScore", String.valueOf(score));
                    }

                    properties.setProperty("totalScore", String.valueOf(totalScore));
                    PropertySaver.saveProperties(properties);
                    playerThread.stop();
                    enemyThread.stop();
                    collisonThread.stop();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                game = false;
            }
        }
    }

    public void asteroidHitAction() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_asteroid_explosion.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-22.0f);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shot = false;
        beamPos.setLocation(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y + PLAYER_SIZE);
        score += 10;
        playScore();
    }

    public void playScore() {
        if (score % 10 == 0 && score != 0) {
            try {
                File file = new File(".\\src\\Resources\\Sounds\\snd_Score.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-16.0f);
                clip.start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> playerDirectionY = -1;
            case KeyEvent.VK_RIGHT -> playerDirectionX = 1;
            case KeyEvent.VK_DOWN -> playerDirectionY = 1;
            case KeyEvent.VK_LEFT -> playerDirectionX = -1;
            case KeyEvent.VK_ENTER -> {
                if (starter) start();
            }
            case KeyEvent.VK_ESCAPE -> {
                if (!pause && !starter) {
                    pause = true;
                } else if (pause) {
                    pause = false;
                }
            }
            case KeyEvent.VK_SPACE -> {
                if (!shot) {
                    try {
                        File file = new File(".\\src\\Resources\\Sounds\\snd_laser.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-22.0f);
                        clip.start();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                shot = true;
            }
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> playerDirectionY = 0;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> playerDirectionX = 0;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public boolean isPause() {
        return pause;
    }
}