package de.tjorven.serverdownloader.utils;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Util {
    public static String versionSelected;
    public static String javaVersionPath;
    public static String javaVersionName;
    public static HashMap properties;

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
            URL file = new URL("https://tjorven-liebe.de/files/serverfiles/" + versionSelected.toLowerCase().replace(" ", "-") + ".jar");
            FileUtils.copyURLToFile(file, new File(installationPath + "/server.jar"));

            File eula = new File(installationPath + "/eula.txt");
            eula.createNewFile();
            FileWriter fileWriter = new FileWriter(eula);
            fileWriter.append("eula=true");
            fileWriter.close();


            File start = new File(installationPath + "/start.bat");
            start.createNewFile();
            FileWriter startWriter = new FileWriter(start);
            startWriter.append("@echo off\n")
                    .append("SET version=" + versionSelected + "\n")
                    .append("color 0f\n")
                    .append("echo Starting on %version%\n")
                    .append("title %version%\n")
                    .append(":start\n").append("\"").append("" + javaVersionPath).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                    .append("goto start\n");
            startWriter.close();

            File server_properties = new File(installationPath + "/server.properties");
            FileWriter properties = new FileWriter(server_properties);
            properties.append("server-port=" + serverPort).append("\n");
            properties.append("max-players=" + maxPlayers).append("\n");
            properties.append("motd=" + motd).append("\n");
            properties.append("online-mode=" + onlineMode).append("\n");
            properties.append("enable-command-block=" + enableCommandBlock).append("\n");
            properties.append("allow-flight=" + allowFlight).append("\n");
            properties.append("pvp=" + pvp).append("\n");
            properties.append("allow-nether=" + allowNether).append("\n");
            properties.append("spawn-animals=" + spawnAnimals).append("\n");
            properties.append("hardcore=" + hardcore).append("\n");
            properties.append("debug=" + debug).append("\n");
            properties.append("spawn-monsters=" + spawnMonsters);
            properties.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
