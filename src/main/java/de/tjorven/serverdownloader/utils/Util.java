package de.tjorven.serverdownloader.utils;

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme;
import de.tjorven.serverdownloader.utils.command.CommandHandler;
import de.tjorven.serverdownloader.utils.configuration.file.YamlConfiguration;
import de.tjorven.serverdownloader.utils.logger.Logger;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;

public class Util {
    public static String versionSelected;
    public static String versionName;
    public static String javaVersionPath;
    public static String javaVersionName;
    public static String downloadServer;
    public static int serverPort;
    public static int maxPlayers;
    public static String motd;
    public static boolean onlineMode;
    public static boolean enableCommandBlock;
    public static boolean allowFlight;
    public static boolean pvp;
    public static boolean allowNether;
    public static boolean spawnAnimals;
    public static boolean hardcore;
    public static boolean debug;
    public static boolean spawnMonsters;

    public static HashMap<Integer, String> serverAndName = new HashMap<>();

    public static void generate(String installationPath) {
        try {
            try {
                URL file = new URL(Util.downloadServer + "/" + Util.versionSelected.toLowerCase()
                        .replace(" ", "-") + "/server.jar");
                FileUtils.copyURLToFile(file, new File(installationPath + "/server.jar"));
            } catch (IOException exception) {
                File file = new File(Util.downloadServer + "/" + Util.versionSelected.toLowerCase()
                        .replace(" ", "-") + "/server.jar");
                FileUtils.copyFile(file, new File(installationPath + "/server.jar"));
            }

            if (System.getProperty("os.name").startsWith("Windows")) {
                File start = new File(installationPath + "/start.bat");
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("@echo off\n")
                        .append("SET version=").append(versionSelected).append("\n")
                        .append("echo Starting on %version%\n")
                        .append("title %version%\n")
                        .append(":start\n").append("\"").append("").append(javaVersionPath).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                        .append("goto start\n");
                startWriter.close();
            } else {
                File start = new File(installationPath + "/start.sh");
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("screen -S \"" + versionSelected + "\" java -Xmx1G -jar server.jar");
                startWriter.close();
                Files.setPosixFilePermissions(Path.of(new File(System.getProperty("user.dir") + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
            }

            File eula = new File(installationPath + "/eula.txt");
            eula.createNewFile();
            FileWriter fileWriter = new FileWriter(eula);
            fileWriter.append("eula=true");
            fileWriter.close();

            File server_properties = new File(installationPath + "/server.properties");
            FileWriter properties = new FileWriter(server_properties);
            properties.append("server-port=").append(String.valueOf(serverPort)).append("\n");
            properties.append("max-players=").append(String.valueOf(maxPlayers)).append("\n");
            properties.append("motd=").append(motd).append("\n");
            properties.append("online-mode=").append(String.valueOf(onlineMode)).append("\n");
            properties.append("enable-command-block=").append(String.valueOf(enableCommandBlock)).append("\n");
            properties.append("allow-flight=").append(String.valueOf(allowFlight)).append("\n");
            properties.append("pvp=").append(String.valueOf(pvp)).append("\n");
            properties.append("allow-nether=").append(String.valueOf(allowNether)).append("\n");
            properties.append("spawn-animals=").append(String.valueOf(spawnAnimals)).append("\n");
            properties.append("hardcore=").append(String.valueOf(hardcore)).append("\n");
            properties.append("debug=").append(String.valueOf(debug)).append("\n");
            properties.append("spawn-monsters=").append(String.valueOf(spawnMonsters));
            properties.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String jsonFromFile(String path) {
        String line;
        StringBuilder buff = new StringBuilder();
        URL url;
        try {
            url = new URL(path);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                while ((line = in.readLine()) != null) {
                    buff.append(line)
                            .append(System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(Files.newInputStream(new File(path).toPath())));
                while ((line = reader.readLine()) != null) {
                    buff.append(line).append(System.lineSeparator());
                }
            } catch (IOException ex) {
                Logger.getLogger().error("Downloadserver " + Util.downloadServer + " is not reachable", "Try to use another one..");
                try {
                    new CommandHandler();
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
        return buff.toString();
    }

    public static void setTheme(JFrame frame) throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/ServerDownloader/settings.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if (configuration.getString("gui.theme") == null) {
            configuration.set("gui.theme", "gradianto-midnight-blue");
            configuration.save(file);
        }
        switch (configuration.getString("gui.theme").toLowerCase()) {
            case "material-deep-ocean": {
                FlatMaterialDeepOceanIJTheme.setup();
                UIManager.setLookAndFeel(new FlatMaterialDeepOceanIJTheme());
                frame.setVisible(true);
                break;
            }
            case "night-owl": {
                FlatNightOwlIJTheme.setup();
                UIManager.setLookAndFeel(new FlatNightOwlIJTheme());
                frame.setVisible(true);
                break;
            }
            case "gradianto-deep-ocean": {
                FlatGradiantoMidnightBlueIJTheme.setup();
                UIManager.setLookAndFeel(new FlatGradiantoDeepOceanIJTheme());
                frame.setVisible(true);
                break;
            }
            case "light-owl-contrast": {
                FlatLightOwlContrastIJTheme.setup();
                UIManager.setLookAndFeel(new FlatLightOwlContrastIJTheme());
                frame.setVisible(true);
                break;
            }
            case "os-theme": {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                break;
            }
            default:
            case "gradianto-midnight-blue": {
                FlatGradiantoMidnightBlueIJTheme.setup();
                UIManager.setLookAndFeel(new FlatGradiantoMidnightBlueIJTheme());
                frame.setVisible(true);
                break;
            }
        }
    }

}
