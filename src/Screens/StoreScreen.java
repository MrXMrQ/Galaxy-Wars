package Screens;

import Logic.PropertySaver;

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

    public JLayeredPane addStore(JLayeredPane layeredPane) {
        JLabel labelStoreHeadline = new JLabel();
        JLabel labelSkinBundle_StarWars = new JLabel();
        JLabel labelSkinBundle_SpaceShuttle = new JLabel();
        JLabel labelSkinBundle_Organic = new JLabel();
        JLabel labelCurrentSkin = new JLabel();
        JLabel labelBuyButton = new JLabel("buy", SwingUtilities.CENTER);
        JLabel labelEquipButton = new JLabel("equip", SwingUtilities.CENTER);
        JLabel labelTotalScore = new JLabel("points: " + properties.getProperty("totalScore"));

        ImageIcon imageStoreHeadline = new ImageIcon("C:\\GitHub Projekte\\GalaxyWars\\src\\Resources\\Sprites\\Store_Headline.png");
        ImageIcon imageTemplateSkins = new ImageIcon("C:\\GitHub Projekte\\GalaxyWars\\src\\Resources\\Sprites\\TemplateSkins.png");

        labelStoreHeadline.setIcon(imageStoreHeadline);
        labelSkinBundle_StarWars.setIcon(imageTemplateSkins);
        labelSkinBundle_SpaceShuttle.setIcon(imageTemplateSkins);
        labelSkinBundle_Organic.setIcon(imageTemplateSkins);

        labelStoreHeadline.setBounds(0, 0, 500, 100);
        labelSkinBundle_StarWars.setBounds(30, 130, 300, 50);
        labelSkinBundle_SpaceShuttle.setBounds(30, 210, 300, 50);
        labelSkinBundle_Organic.setBounds(30, 290, 300, 50);
        labelCurrentSkin.setBounds(400, 130, 50, 210);
        labelBuyButton.setBounds(165, 400, 60, 25);
        labelEquipButton.setBounds(245, 400, 90, 25);
        labelTotalScore.setBounds(30,100,250,25);

        labelCurrentSkin.setBorder(new LineBorder(Color.WHITE));

        Font retro = new Font("Retro Computer", Font.BOLD, 20);
        labelBuyButton.setFont(retro);
        labelEquipButton.setFont(retro);
        labelTotalScore.setFont(retro);

        labelBuyButton.setForeground(Color.WHITE);
        labelEquipButton.setForeground(Color.WHITE);
        labelTotalScore.setForeground(Color.WHITE);

        layeredPane.add(labelStoreHeadline, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_StarWars, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_SpaceShuttle, Integer.valueOf(1));
        layeredPane.add(labelSkinBundle_Organic, Integer.valueOf(1));
        layeredPane.add(labelCurrentSkin, Integer.valueOf(1));
        layeredPane.add(labelBuyButton, Integer.valueOf(1));
        layeredPane.add(labelEquipButton, Integer.valueOf(1));
        layeredPane.add(labelTotalScore, Integer.valueOf(1));

        labelSkinBundle_StarWars.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
                labelSkinBundle_StarWars.setBorder(new LineBorder(Color.RED));
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
                    labelSkinBundle_StarWars.setBorder(new LineBorder(Color.WHITE));
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
                labelSkinBundle_SpaceShuttle.setBorder(new LineBorder(Color.RED));
                labelSkinBundle_Organic.setBorder(null);
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = true;
                clickedOrganicBundle = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clickedSpaceShuttleBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_SpaceShuttle.setBorder(new LineBorder(Color.WHITE));
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
                labelSkinBundle_Organic.setBorder(new LineBorder(Color.RED));
                clickedStarWarsBundle = false;
                clickedSpaceShuttleBundle = false;
                clickedOrganicBundle = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!clickedOrganicBundle) {
                    playButtonHoverSound();
                    labelSkinBundle_Organic.setBorder(new LineBorder(Color.WHITE));
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
                playButtonSelectSound();
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
                playButtonSelectSound();
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

        return layeredPane;
    }

    public void playButtonSelectSound() {
        try {
            File file = new File("C:\\GitHub Projekte\\GalaxyWars\\src\\Resources\\Sounds\\snd_button_select.wav");
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
            File file = new File("C:\\GitHub Projekte\\GalaxyWars\\src\\Resources\\Sounds\\snd_button_hover.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}