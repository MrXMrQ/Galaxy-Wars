package screens;

import logic.PropertySaver;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;

public class MySlider extends JSlider {
    private final Properties properties = PropertySaver.loadProperties();
    private final int optionVolume = Integer.parseInt(properties.getProperty("menuVolume"));

    public MySlider() {
        setMinorTickSpacing(5);
        setMajorTickSpacing(20);
        setPaintTicks(true);
        setPaintLabels(true);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Retro Computer", Font.BOLD, 14));
        setMinimum(-70);
        setMaximum(6);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonSelectSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
                setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.WHITE);
            }
        });
    }

    public void playButtonSelectSound() {
        try {
            File file = new File(".\\src\\Resources\\Sounds\\snd_button_select.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(optionVolume - 20);
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
            gainControl.setValue(optionVolume);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
