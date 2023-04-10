package screens;

import logic.PropertySaver;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;

public class StoreScreen {
    Properties properties = PropertySaver.loadProperties();
    private boolean clickedStarWarsBundle, clickedSpaceShuttleBundle, clickedAlienEyeBundle = false;
    private final LineBorder lineBorderGRAY = new LineBorder(Color.GRAY, 4);
    private final LineBorder lineBorderWHITE = new LineBorder(Color.WHITE, 4);
    private int price;
    private int totalScore = Integer.parseInt(properties.getProperty("totalScore"));
    private final int highScore = Integer.parseInt(properties.getProperty("highScore"));
    private boolean boughtStarWars = Boolean.parseBoolean(properties.getProperty("boughtStarWars"));
    private boolean boughtAlienEye = Boolean.parseBoolean(properties.getProperty("boughtAlienEye"));
    private final int storeVolume = Integer.parseInt(properties.getProperty("storeVolume"));

    public JLayeredPane addStore(JLayeredPane layeredPane) {
        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));

        JLabel labelStoreHeadline = new JLabel();
        JLabel labelTotalScore = new JLabel("points: " + totalScore);
        JLabel labelHighScore = new JLabel("high score: " + highScore);
        JLabel labelSkinBundle_StarWars = new JLabel();
        JLabel labelSkinBundle_SpaceShuttle = new JLabel();
        JLabel labelSkinBundle_AlienEye = new JLabel();
        JLabel labelCurrentSkin = new JLabel();
        JLabel labelReset = new JLabel("reset");
        JLabel labelBuyButton = new JLabel("buy", SwingUtilities.CENTER);
        JLabel labelEquipButton = new JLabel("equip", SwingUtilities.CENTER);

        ImageIcon imageStoreHeadline = new ImageIcon(".\\src\\Resources\\Sprites\\store_headline.png");
        ImageIcon imageDefaultBundle = new ImageIcon(properties.getProperty("lastSkin"));
        ImageIcon imageStarWarsBundle = new ImageIcon(".\\src\\Resources\\Sprites\\skins\\star_wars_bundle.png");
        ImageIcon imageAlienEyeBundle = new ImageIcon(".\\src\\Resources\\Sprites\\skins\\alien_eye_bundle.png");

        labelStoreHeadline.setIcon(imageStoreHeadline);
        labelCurrentSkin.setIcon(imageDefaultBundle);
        labelSkinBundle_StarWars.setIcon(imageStarWarsBundle);
        labelSkinBundle_AlienEye.setIcon(imageAlienEyeBundle);

        labelStoreHeadline.setBounds(0, 0, 500, 100);
        labelTotalScore.setBounds(30, 75, 250, 25);
        labelHighScore.setBounds(230, 75, 250, 25);
        labelSkinBundle_StarWars.setBounds(30, 100, 300, 50);
        labelSkinBundle_AlienEye.setBounds(30, 175, 300, 50);
        labelSkinBundle_SpaceShuttle.setBounds(30, 250, 300, 50);
        labelCurrentSkin.setBounds(30, 325, 300, 50);
        labelReset.setBounds(360, 338, 250, 25);
        labelBuyButton.setBounds(165, 400, 60, 25);
        labelEquipButton.setBounds(245, 400, 90, 25);

        labelCurrentSkin.setBorder(new LineBorder(Color.GREEN, 4));

        Font retro = new Font("Retro Computer", Font.BOLD, 20);
        labelTotalScore.setFont(retro);
        labelHighScore.setFont(retro);
        labelBuyButton.setFont(retro);
        labelEquipButton.setFont(retro);
        labelReset.setFont(retro);

        labelTotalScore.setForeground(Color.WHITE);
        labelHighScore.setForeground(Color.WHITE);
        labelBuyButton.setForeground(Color.WHITE);
        labelEquipButton.setForeground(Color.WHITE);
        labelReset.setForeground(Color.WHITE);

        layeredPane.add(labelStoreHeadline, Integer.valueOf(1));
        layeredPane.add(labelHighScore, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_StarWars, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_SpaceShuttle, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_AlienEye, Integer.valueOf(1));
        layeredPane.add(labelCurrentSkin, Integer.valueOf(1));
        layeredPane.add(labelReset, Integer.valueOf(1));
        layeredPane.add(labelBuyButton, Integer.valueOf(1));
        layeredPane.add(labelEquipButton, Integer.valueOf(1));
        layeredPane.add(labelTotalScore, Integer.valueOf(1));

        labelSkinBundle_StarWars.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                price = 1000000;
                labelSkinBundle_StarWars.setBorder(lineBorderGRAY);
                labelSkinBundle_SpaceShuttle.setBorder(null);
                labelSkinBundle_AlienEye.setBorder(null);
                clickedStarWarsBundle = true;
                clickedSpaceShuttleBundle = false;
                clickedAlienEyeBundle = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!clickedStarWarsBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_StarWars.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!clickedStarWarsBundle) {
                    labelSkinBundle_StarWars.setBorder(null);
                }
            }
        });

        labelSkinBundle_AlienEye.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                price = 69420;
                labelSkinBundle_StarWars.setBorder(null);
                labelSkinBundle_SpaceShuttle.setBorder(null);
                labelSkinBundle_AlienEye.setBorder(lineBorderGRAY);
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = false;
                clickedAlienEyeBundle = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!clickedAlienEyeBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_AlienEye.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!clickedAlienEyeBundle) {
                    labelSkinBundle_AlienEye.setBorder(null);
                }
            }
        });

        labelSkinBundle_SpaceShuttle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                labelSkinBundle_StarWars.setBorder(null);
                labelSkinBundle_SpaceShuttle.setBorder(lineBorderGRAY);
                labelSkinBundle_AlienEye.setBorder(null);
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = true;
                clickedAlienEyeBundle = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!clickedSpaceShuttleBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_SpaceShuttle.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!clickedSpaceShuttleBundle) {
                    labelSkinBundle_SpaceShuttle.setBorder(null);
                }
            }
        });

        labelBuyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickedStarWarsBundle && totalScore >= price && !boughtStarWars) {
                    playCashRegisterSound();
                    totalScore -= price;
                    boughtStarWars = true;

                    properties.setProperty("totalScore", String.valueOf(totalScore));
                    properties.setProperty("boughtStarWars", String.valueOf(boughtStarWars));
                    PropertySaver.saveProperties(properties);

                    labelTotalScore.setText("points: " + totalScore);

                } else if (clickedAlienEyeBundle && totalScore >= price && !boughtAlienEye) {
                    playCashRegisterSound();
                    totalScore -= price;
                    boughtAlienEye = true;

                    properties.setProperty("totalScore", String.valueOf(totalScore));
                    properties.setProperty("boughtAlienEye", String.valueOf(boughtAlienEye));
                    PropertySaver.saveProperties(properties);

                    labelTotalScore.setText("points: " + totalScore);
                } else {
                    playAccessDeniedSound();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
                labelBuyButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelBuyButton.setForeground(Color.WHITE);
            }
        });

        labelEquipButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickedStarWarsBundle && boughtStarWars) {
                    playButtonSelectSound();
                    properties.setProperty("playerPath", ".\\src\\Resources\\sprites\\skins\\star_wars_player.png");
                    properties.setProperty("enemyPath", ".\\src\\Resources\\sprites\\skins\\star_wars_enemy.png");
                    properties.setProperty("beamPath", ".\\src\\Resources\\sprites\\skins\\star_wars_beam.png");
                    properties.setProperty("lastSkin", ".\\src\\Resources\\sprites\\skins\\star_wars_bundle.png");
                    properties.setProperty("shotSound", ".\\src\\Resources\\sounds\\snd_star_wars_laser.wav");

                    PropertySaver.saveProperties(properties);
                    labelCurrentSkin.setIcon(imageStarWarsBundle);

                } else if (clickedAlienEyeBundle && boughtAlienEye) {
                    playButtonSelectSound();
                    properties.setProperty("playerPath", ".\\src\\Resources\\sprites\\skins\\alien_eye_player.png");
                    properties.setProperty("enemyPath", ".\\src\\Resources\\sprites\\skins\\alien_eye_enemy.png");
                    properties.setProperty("beamPath", ".\\src\\Resources\\sprites\\skins\\alien_eye_beam.png");
                    properties.setProperty("lastSkin", ".\\src\\Resources\\sprites\\skins\\alien_eye_bundle.png");
                    properties.setProperty("shotSound", ".\\src\\Resources\\sounds\\snd_laser.wav");

                    PropertySaver.saveProperties(properties);
                    labelCurrentSkin.setIcon(imageAlienEyeBundle);

                } else {
                    playAccessDeniedSound();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
                labelEquipButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelEquipButton.setForeground(Color.WHITE);
            }
        });

        labelReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();

                ImageIcon imageIconReset = new ImageIcon(".\\src\\Resources\\sprites\\skins\\default_skin_bundle.png");
                labelCurrentSkin.setIcon(imageIconReset);

                properties.setProperty("playerPath", ".\\src\\Resources\\sprites\\skins\\default_player.png");
                properties.setProperty("enemyPath", ".\\src\\Resources\\sprites\\skins\\default_enemy.png");
                properties.setProperty("beamPath", ".\\src\\Resources\\sprites\\skins\\default_beam.png");
                properties.setProperty("lastSkin", ".\\src\\Resources\\sprites\\skins\\default_skin_bundle.png");
                properties.setProperty("shotSound", ".\\src\\Resources\\sounds\\snd_laser.wav");

                PropertySaver.saveProperties(properties);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
                labelReset.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelReset.setForeground(Color.WHITE);
            }
        });

        return layeredPane;
    }

    public void playButtonSelectSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_button_select.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(storeVolume - 20);
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
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(storeVolume);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playCashRegisterSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_cash.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(storeVolume);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playAccessDeniedSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_access_denied.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(storeVolume);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

            g.drawLine(0, 76, 500, 76);
            g.drawLine(0, 100, 500, 100);
            g.drawLine(0, 390, 500, 390);
        }
    }
}