package Logic;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Game extends JPanel {
    //PLAYER
    private static final int PLAYER_SIZE = 20;
    private static final int PLAYER_SPEED = 5;

    private int playerDirectionX;
    private int playerDirectionY;

    //ENEMY
    private static final int ENEMY_SIZE = 30;
    private static final int ENEMY_SPEED = 3;

    //Point
    private final Point enemyPos_0, enemyPos_1, enemyPos_2, enemyPos_3, enemyPos_4, enemyPos_5, beamPos, playerPos;

    //THREADS
    public Thread playerThread, enemyThread, collisonThread;

    private int score = 0;
    public boolean starter = true;
    private boolean shot = false;
    private boolean gameOver = false;

    private final Point[] pixel;

    public Game() {
        setBounds(0, 0, 500, 500);
        setFocusable(true);

        playerPos = new Point(230, 400);
        playerDirectionX = 0;
        playerDirectionY = 0;

        enemyPos_0 = new Point((int) (Math.random() * 470), 0);
        enemyPos_1 = new Point((int) (Math.random() * 470), 0);
        enemyPos_2 = new Point((int) (Math.random() * 470), 0);
        enemyPos_3 = new Point((int) (Math.random() * 470), 0);
        enemyPos_4 = new Point((int) (Math.random() * 470), 0);
        enemyPos_5 = new Point((int) (Math.random() * 470), 0);

        beamPos = new Point(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y + PLAYER_SIZE);

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

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.WHITE);
        for (Point point : pixel) {
            g.fillRect(point.x, point.y, 1, 1);
        }

        g.setColor(Color.YELLOW);
        g.fillRect(beamPos.x, beamPos.y, 5, -30);

        g.setColor(Color.RED);
        g.fillRect(playerPos.x, playerPos.y, PLAYER_SIZE, PLAYER_SIZE);

        g.setColor(Color.GRAY);
        g.fillRect(enemyPos_0.x, enemyPos_0.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_1.x, enemyPos_1.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_2.x, enemyPos_2.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_3.x, enemyPos_3.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_4.x, enemyPos_4.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_5.x, enemyPos_5.y, ENEMY_SIZE, ENEMY_SIZE);

    }

    public void start() {
        starter = false;
        playerThread.start();
        enemyThread.start();
        collisonThread.start();
    }

    public void playerMovement() {
        while (playerThread.isAlive()) {
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

    public void enemyMovement() {
        while (enemyThread.isAlive()) {
            enemyPos_0.y += ENEMY_SPEED;
            enemyPos_1.y += ENEMY_SPEED + 2;
            enemyPos_2.y += ENEMY_SPEED + 3;
            enemyPos_3.y += ENEMY_SPEED + 1;
            enemyPos_4.y += ENEMY_SPEED + 0.5;
            enemyPos_5.y += ENEMY_SPEED + 1.5;

            if (enemyPos_0.y > 440) {
                enemyPos_0.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            if (enemyPos_1.y > 440) {
                enemyPos_1.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            if (enemyPos_2.y > 440) {
                enemyPos_2.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            if (enemyPos_3.y > 440) {
                enemyPos_3.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            if (enemyPos_4.y > 440) {
                enemyPos_4.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            if (enemyPos_5.y > 440) {
                enemyPos_5.setLocation((int) (Math.random() * 470), 0);
                score++;
            }
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

            panelBeam.setBounds(beamPos.x, beamPos.y, 5, 1);
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
                playerThread.stop();
                enemyThread.stop();
                gameOver = true;
                collisonThread.stop();
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void asteroidHitAction() {
        shot = false;
        beamPos.setLocation(playerPos.x + PLAYER_SIZE / 2 - 2, playerPos.y + PLAYER_SIZE);
        score += 10;
        System.out.println(score);
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
            case KeyEvent.VK_SPACE -> shot = true;
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
}