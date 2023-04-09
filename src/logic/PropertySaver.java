package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertySaver extends Properties{
    private static final String PROPERTIES_FILE = "config.properties";
    private static final int score = 0;
    private static final int highScore = 0;
    private static final String  playerPath = ".\\src\\Resources\\sprites\\skins\\default_player.png";
    private static final String enemyPath = ".\\src\\Resources\\sprites\\skins\\default_enemy.png";
    private static final String beamPath = ".\\src\\Resources\\sprites\\skins\\default_beam.png";
    private static final String lastSkin = ".\\src\\Resources\\sprites\\skins\\default_skin_bundle.png";
    private static final String shotSound = ".\\src\\Resources\\sounds\\snd_laser.wav";
    private static final boolean boughtStarWars = false;
    private static final boolean boughtAlienEye = false;

    public static Properties loadProperties() {
        Properties properties = new Properties();
        File file = new File(PROPERTIES_FILE);
        if (file.exists()) {
            try (FileInputStream input = new FileInputStream(file)) {
                properties.load(input);
            } catch (IOException e) {
                // Failed to load properties, ignore
            }
        } else {
            properties.setProperty("totalScore", String.valueOf(score));
            properties.setProperty("highScore", String.valueOf(highScore));
            properties.setProperty("playerPath", playerPath);
            properties.setProperty("enemyPath", enemyPath);
            properties.setProperty("beamPath", beamPath);
            properties.setProperty("lastSkin", lastSkin);
            properties.setProperty("shotSound", shotSound);
            properties.setProperty("boughtStarWars", String.valueOf(boughtStarWars));
            properties.setProperty("boughtAlienEye", String.valueOf(boughtAlienEye));

            saveProperties(properties);
        }
        return properties;
    }

    public static void saveProperties(Properties properties) {
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, null);
        } catch (IOException e) {
            // Failed to save properties, ignore
        }
    }
}