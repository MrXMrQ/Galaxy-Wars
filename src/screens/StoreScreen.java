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
    private boolean clickedStarWarsBundle, clickedSpaceShuttleBundle, clickedOrganicBundle = false;
    private final LineBorder lineBorderRED = new LineBorder(Color.GRAY, 4);
    private final LineBorder lineBorderWHITE = new LineBorder(Color.WHITE, 4);
    private int price;
    private int totalScore = Integer.parseInt(properties.getProperty("totalScore"));
    private boolean boughtStarWars = Boolean.parseBoolean(properties.getProperty("boughtStarWars"));

    public JLayeredPane addStore(JLayeredPane layeredPane) {
        JLabel labelStoreHeadline = new JLabel();
        JLabel labelSkinBundle_StarWars = new JLabel();
        JLabel labelSkinBundle_SpaceShuttle = new JLabel();
        JLabel labelSkinBundle_Organic = new JLabel();
        JLabel labelCurrentSkin = new JLabel();
        JLabel labelReset = new JLabel("reset");
        JLabel labelBuyButton = new JLabel("buy", SwingUtilities.CENTER);
        JLabel labelEquipButton = new JLabel("equip", SwingUtilities.CENTER);
        JLabel labelTotalScore = new JLabel("points: " + totalScore);

        ImageIcon imageStoreHeadline = new ImageIcon(".\\src\\Resources\\Sprites\\store_headline.png");
        ImageIcon imageDefaultBundle = new ImageIcon(properties.getProperty("lastSkin"));
        ImageIcon imageStarWarsBundle = new ImageIcon(".\\src\\Resources\\Sprites\\skins\\star_wars_bundle.png");

        ImageIcon imageTemplateSkins = new ImageIcon(".\\src\\Resources\\Sprites\\template_skins.png");

        labelStoreHeadline.setIcon(imageStoreHeadline);
        labelSkinBundle_StarWars.setIcon(imageStarWarsBundle);
        labelCurrentSkin.setIcon(imageDefaultBundle);

        labelSkinBundle_SpaceShuttle.setIcon(imageTemplateSkins);
        labelSkinBundle_Organic.setIcon(imageTemplateSkins);

        labelStoreHeadline.setBounds(0, 0, 500, 100);
        labelTotalScore.setBounds(30,75,250,25);
        labelSkinBundle_StarWars.setBounds(30, 100, 300, 50);
        labelSkinBundle_SpaceShuttle.setBounds(30, 175, 300, 50);
        labelSkinBundle_Organic.setBounds(30, 250, 300, 50);
        labelCurrentSkin.setBounds(30, 325, 300, 50);
        labelReset.setBounds(360,338,250,25);
        labelBuyButton.setBounds(165, 400, 60, 25);
        labelEquipButton.setBounds(245, 400, 90, 25);


        labelCurrentSkin.setBorder(new LineBorder(Color.GREEN, 4));

        Font retro = new Font("Retro Computer", Font.BOLD, 20);
        labelBuyButton.setFont(retro);
        labelEquipButton.setFont(retro);
        labelTotalScore.setFont(retro);
        labelReset.setFont(retro);

        labelBuyButton.setForeground(Color.WHITE);
        labelEquipButton.setForeground(Color.WHITE);
        labelTotalScore.setForeground(Color.WHITE);
        labelReset.setForeground(Color.WHITE);

        layeredPane.add(labelStoreHeadline, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_StarWars, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_SpaceShuttle, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_Organic, Integer.valueOf(1));
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
                labelSkinBundle_StarWars.setBorder(lineBorderRED);
                labelSkinBundle_SpaceShuttle.setBorder(null);
                labelSkinBundle_Organic.setBorder(null);
                clickedStarWarsBundle = true;
                clickedSpaceShuttleBundle = false;
                clickedOrganicBundle = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clickedStarWarsBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_StarWars.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!clickedStarWarsBundle) {
                    labelSkinBundle_StarWars.setBorder(null);
                }
            }
        });

        labelSkinBundle_SpaceShuttle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                labelSkinBundle_StarWars.setBorder(null);
                labelSkinBundle_SpaceShuttle.setBorder(lineBorderRED);
                labelSkinBundle_Organic.setBorder(null);
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = true;
                clickedOrganicBundle = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clickedSpaceShuttleBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_SpaceShuttle.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!clickedSpaceShuttleBundle) {
                    labelSkinBundle_SpaceShuttle.setBorder(null);
                }
            }
        });

        labelSkinBundle_Organic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                labelSkinBundle_StarWars.setBorder(null);
                labelSkinBundle_SpaceShuttle.setBorder(null);
                labelSkinBundle_Organic.setBorder(lineBorderRED);
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = false;
                clickedOrganicBundle = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clickedOrganicBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_Organic.setBorder(lineBorderWHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!clickedOrganicBundle) {
                    labelSkinBundle_Organic.setBorder(null);
                }
            }
        });

        labelBuyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(clickedStarWarsBundle && totalScore >= price && !boughtStarWars) {
                    playCashRegisterSound();
                    totalScore -= 1000000;
                    boughtStarWars = true;

                    properties.setProperty("totalScore", String.valueOf(totalScore));
                    properties.setProperty("boughtStarWars", String.valueOf(boughtStarWars));
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

                    PropertySaver.saveProperties(properties);

                    labelCurrentSkin.setIcon(imageStarWarsBundle);
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

    public void playCashRegisterSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_cash.wav");
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

    public void playAccessDeniedSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_access_denied.wav");
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
}