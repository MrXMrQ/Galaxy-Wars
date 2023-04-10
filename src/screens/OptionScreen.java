package screens;

import logic.PropertySaver;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.Properties;

public class OptionScreen {
    private final Properties properties = PropertySaver.loadProperties();
    private final Font retroFont = new Font("Retro Computer", Font.BOLD, 14);
    private static final Point sliderPoint = new Point(30, 100);

    public JLayeredPane addOption(JLayeredPane layeredPane) {
        RectanglePanel rectanglePanel = new RectanglePanel();
        rectanglePanel.setBounds(0, 0, 500, 500);
        layeredPane.add(rectanglePanel, Integer.valueOf(0));


        JLabel labelHeadline = new JLabel("options", SwingConstants.CENTER);
        JLabel labelHeadlineGameVolume = new JLabel("game volume: " + properties.getProperty("gameVolume"));
        JLabel labelHeadlineMenuVolume = new JLabel("menu volume: " + properties.getProperty("menuVolume"));
        JLabel labelHeadlineStoreVolume = new JLabel("store volume: " + properties.getProperty("storeVolume"));

        labelHeadline.setForeground(Color.WHITE);
        labelHeadlineGameVolume.setForeground(Color.WHITE);
        labelHeadlineMenuVolume.setForeground(Color.WHITE);
        labelHeadlineStoreVolume.setForeground(Color.WHITE);

        labelHeadline.setFont(new Font("Retro Computer", Font.BOLD, 50));
        labelHeadlineGameVolume.setFont(retroFont);
        labelHeadlineMenuVolume.setFont(retroFont);
        labelHeadlineStoreVolume.setFont(retroFont);


        MySlider sliderGameVolume = new MySlider();
        sliderGameVolume.setUI(new SliderUI());
        sliderGameVolume.setBounds(sliderPoint.x, sliderPoint.y + 20, 430, 50);
        sliderGameVolume.setValue(Integer.parseInt(properties.getProperty("gameVolume")));

        sliderGameVolume.addChangeListener(e -> {
            properties.setProperty("gameVolume", String.valueOf(sliderGameVolume.getValue()));
            PropertySaver.saveProperties(properties);
            labelHeadlineGameVolume.setText("game volume: " + properties.getProperty("gameVolume"));

        });

        MySlider sliderMenuVolume = new MySlider();
        sliderMenuVolume.setUI(new SliderUI());
        sliderMenuVolume.setBounds(sliderPoint.x, sliderPoint.y + 120, 430, 50);
        sliderMenuVolume.setValue(Integer.parseInt(properties.getProperty("menuVolume")));

        sliderMenuVolume.addChangeListener(e -> {
            properties.setProperty("menuVolume", String.valueOf(sliderMenuVolume.getValue()));
            PropertySaver.saveProperties(properties);
            labelHeadlineMenuVolume.setText("menu volume: " + properties.getProperty("menuVolume"));

        });

        MySlider sliderStoreVolume = new MySlider();
        sliderStoreVolume.setUI(new SliderUI());
        sliderStoreVolume.setBounds(sliderPoint.x, sliderPoint.y + 220, 430, 50);
        sliderStoreVolume.setValue(Integer.parseInt(properties.getProperty("storeVolume")));

        sliderStoreVolume.addChangeListener(e -> {
            properties.setProperty("storeVolume", String.valueOf(sliderStoreVolume.getValue()));
            PropertySaver.saveProperties(properties);
            labelHeadlineStoreVolume.setText("store volume: " + properties.getProperty("storeVolume"));

        });

        labelHeadline.setBounds(0, 0, 500, 100);
        labelHeadlineGameVolume.setBounds(35, sliderGameVolume.getY() - 35, 200, 50);
        labelHeadlineMenuVolume.setBounds(35, sliderGameVolume.getY() + 65, 200, 50);
        labelHeadlineStoreVolume.setBounds(35, sliderGameVolume.getY() + 165, 200, 50);

        layeredPane.add(labelHeadline, Integer.valueOf(1));
        layeredPane.add(labelHeadlineGameVolume, Integer.valueOf(1));
        layeredPane.add(labelHeadlineMenuVolume, Integer.valueOf(1));
        layeredPane.add(labelHeadlineStoreVolume, Integer.valueOf(1));
        layeredPane.add(sliderGameVolume, Integer.valueOf(1));
        layeredPane.add(sliderMenuVolume, Integer.valueOf(1));
        layeredPane.add(sliderStoreVolume, Integer.valueOf(1));

        return layeredPane;
    }

    // Benutzerdefinierte Schieberegler-UI
    private static class SliderUI extends BasicSliderUI {
        private static final int PIXEL_WIDTH = 6;
        private static final int PIXEL_HEIGHT = 16;

        public SliderUI() {
            super(null);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            int x = thumbRect.x + (thumbRect.width - PIXEL_WIDTH) / 2;
            int y = thumbRect.y + (thumbRect.height - PIXEL_HEIGHT) / 2;
            g2d.fillRect(x, y, PIXEL_WIDTH, PIXEL_HEIGHT);
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

            g.setColor(Color.GRAY);
            g.fillRect(sliderPoint.x, sliderPoint.y, 430, 70);
            g.fillRect(sliderPoint.x, sliderPoint.y + 100, 430, 70);
            g.fillRect(sliderPoint.x, sliderPoint.y + 200, 430, 70);
        }
    }
}
