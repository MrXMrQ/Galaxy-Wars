import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Enemy extends JPanel implements KeyListener, Runnable {
    private static final int ENEMY_SIZE = 20;
    private static final int ENEMY_SPEED = 5;

    private final Point enemyPos;
    private int enemyDirectionX;
    private int enemyDirectionY;

    public Enemy() {
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        enemyPos = new Point(40, 40);
        enemyDirectionX = 0;
        enemyDirectionY = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(enemyPos.x, enemyPos.y, ENEMY_SIZE, ENEMY_SIZE);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> enemyDirectionY = -1;
            case KeyEvent.VK_RIGHT -> enemyDirectionX = 1;
            case KeyEvent.VK_DOWN -> enemyDirectionY = 1;
            case KeyEvent.VK_LEFT -> enemyDirectionX = -1;

        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> enemyDirectionY = 0;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> enemyDirectionX = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        while (true) {
            enemyPos.x += enemyDirectionX * ENEMY_SPEED;
            enemyPos.y += enemyDirectionY * ENEMY_SPEED;
            if (enemyPos.x < 0) {
                enemyPos.x = 0;
            }
            if (enemyPos.y < 0) {
                enemyPos.y = 0;
            }
            if (enemyPos.x > 465) {
                enemyPos.x = 465;
            }
            if (enemyPos.y > 440) {
                enemyPos.y = 440;
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

