import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Player extends JPanel implements KeyListener {
    //PLAYER
    private static final int PLAYER_SIZE = 20;
    private static final int PLAYER_SPEED = 5;

    private final Point playerPos;

    private int playerDirectionX;
    private int playerDirectionY;

    //ENEMY
    private static final int ENEMY_SIZE = 30;
    private static final int ENEMY_SPEED = 2;

    private final Point enemyPos_0;
    private final Point enemyPos_1;
    private final Point enemyPos_2;
    private final Point enemyPos_3;

    //THREADS
    Thread playerThread;
    Thread enemyThread;
    Thread collisonThread;

    private int score = 0;
    private boolean starter = true;


    public Player() {
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);


        playerPos = new Point(200, 200);
        playerDirectionX = 0;
        playerDirectionY = 0;

        enemyPos_0 = new Point((int) (Math.random() * 500), 0);
        enemyPos_1 = new Point((int) (Math.random() * 500), 0);
        enemyPos_2 = new Point((int) (Math.random() * 500), 0);
        enemyPos_3 = new Point((int) (Math.random() * 500), 0);

        playerThread = new Thread(this::playerMovement);
        enemyThread = new Thread(this::enemyMovement);
        collisonThread = new Thread(this::collision);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(playerPos.x, playerPos.y, PLAYER_SIZE, PLAYER_SIZE);

        g.setColor(Color.GRAY);
        g.fillRect(enemyPos_0.x, enemyPos_0.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_1.x, enemyPos_1.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_2.x, enemyPos_2.y, ENEMY_SIZE, ENEMY_SIZE);
        g.fillRect(enemyPos_3.x, enemyPos_3.y, ENEMY_SIZE, ENEMY_SIZE);

    }

    public void start() {
        playerThread.start();
        enemyThread.start();
        collisonThread.start();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> playerDirectionY = -1;
            case KeyEvent.VK_RIGHT -> playerDirectionX = 1;
            case KeyEvent.VK_DOWN -> playerDirectionY = 1;
            case KeyEvent.VK_LEFT -> playerDirectionX = -1;
            case KeyEvent.VK_ENTER -> {
                if (starter) {
                    start();
                    starter = false;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> playerDirectionY = 0;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> playerDirectionX = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void playerMovement() {
        while (playerThread.isAlive()) {
            playerPos.x += playerDirectionX * PLAYER_SPEED;
            playerPos.y += playerDirectionY * PLAYER_SPEED;
            if (playerPos.x < 0) {
                playerPos.x = 0;
            }
            if (playerPos.y < 0) {
                playerPos.y = 0;
            }
            if (playerPos.x > 465) {
                playerPos.x = 465;
            }
            if (playerPos.y > 440) {
                playerPos.y = 440;
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

            if (enemyPos_0.y > 440) {
                enemyPos_0.y = 0;
                enemyPos_0.x = (int) (Math.random() * 470);
                score++;
            }
            if (enemyPos_1.y > 440) {
                enemyPos_1.y = 0;
                enemyPos_1.x = (int) (Math.random() * 470);
                score++;
            }
            if (enemyPos_2.y > 440) {
                enemyPos_2.y = 0;
                enemyPos_2.x = (int) (Math.random() * 470);
                score++;
            }
            if (enemyPos_3.y > 440) {
                enemyPos_3.y = 0;
                enemyPos_3.x = (int) (Math.random() * 470);
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

        while (collisonThread.isAlive()) {
            panelPlayer.setBounds(playerPos.x, playerPos.y, PLAYER_SIZE, PLAYER_SIZE);

            panelEnemy_0.setBounds(enemyPos_0.x, enemyPos_0.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_1.setBounds(enemyPos_1.x, enemyPos_1.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_2.setBounds(enemyPos_2.x, enemyPos_2.y, ENEMY_SIZE, ENEMY_SIZE);
            panelEnemy_3.setBounds(enemyPos_3.x, enemyPos_3.y, ENEMY_SIZE, ENEMY_SIZE);

            if (panelPlayer.bounds().intersects(panelEnemy_0.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_1.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_2.getBounds()) || panelPlayer.bounds().intersects(panelEnemy_3.getBounds())) {
                System.exit(1);
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}