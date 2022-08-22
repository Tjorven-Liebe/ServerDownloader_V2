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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {
    public static List<String> files;
    public static List<String> javas;
    public static String versionSelected;
    public static String versionName;
    public static String javaVersionPath;
    public static String javaVersionName;
    public static String downloadServer;
    public static int serverPort = 25565;
    public static int maxPlayers = 20;
    public static String motd = "Just another ServerDownloader Server";
    public static boolean onlineMode = true;
    public static boolean enableCommandBlock = false;
    public static boolean allowFlight = false;
    public static boolean pvp = true;
    public static boolean allowNether = true;
    public static boolean spawnAnimals = true;
    public static boolean hardcore = false;
    public static boolean debug = false;
    public static boolean spawnMonsters = true;
    public static boolean rcon = false;
    public static int rconPort = 25575;
    public static String rconPassword = "";

    public static HashMap<Integer, Triple<String, Integer, Integer>> serverNameJavaAndVersion = new HashMap<>();
    public static String versionType;

    public static void generateBungeeCord(String installationPath, List<Triple<String, String, String>> triples) {
        AtomicInteger count = new AtomicInteger();
        triples.forEach(triple -> {
            count.getAndIncrement();
            String name = triple.getKey();
            String version = triple.getValue();
            String java = triple.getValue1();

            Logger.getLogger().emergency("Generating BungeeCord for " + name + " with " + java);

            File folder = new File(installationPath + "/" + name + "/");
            folder.mkdirs();

            if (!Objects.equals(version, "bungeecord")) {
                try {
                    try {
                        URL file = new URL(Util.downloadServer + "/" + version.toLowerCase()
                                .replace(" ", "-") + "/server.jar");
                        FileUtils.copyURLToFile(file, new File(installationPath + "/" + name + "/server.jar"));
                    } catch (IOException exception) {
                        File file = new File(Util.downloadServer + "/" + version.toLowerCase()
                                .replace(" ", "-") + "/server.jar");
                        FileUtils.copyFile(file, new File(installationPath + "/" + name + "/server.jar"));
                    }

                    if (System.getProperty("os.name").startsWith("Windows")) {
                        File start = new File(installationPath + "/" + name +  "/start.bat");
                        start.createNewFile();
                        FileWriter startWriter = new FileWriter(start);
                        startWriter.append("@echo off\n")
                                .append("SET version=").append(name).append("\n")
                                .append("echo Starting on %version%\n")
                                .append("title %version%\n")
                                .append(":start\n").append("\"").append("").append(java).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                                .append("goto start\n");
                        startWriter.close();
                    } else {
                        File start = new File(installationPath + "/" + name +  "/start.sh");
                        start.createNewFile();
                        FileWriter startWriter = new FileWriter(start);
                        startWriter.append("screen -S \"" + name + "\" java -Xmx1G -jar server.jar");
                        startWriter.close();
                        Files.setPosixFilePermissions(Path.of(new File(installationPath + "/" + name + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
                    }

                    File eula = new File(installationPath + "/" + name +  "/eula.txt");
                    eula.createNewFile();
                    FileWriter fileWriter = new FileWriter(eula);
                    fileWriter.append("eula=true");
                    fileWriter.close();

                    File server_properties = new File(installationPath + "/" + name + "/server.properties");
                    FileWriter properties = new FileWriter(server_properties);
                    properties.append("server-port=").append(String.valueOf(25565 + count.get())).append("\n");
                    properties.append("motd=").append("A ServerDownloader Subserver").append("\n");
                    properties.append("online-mode=").append("false").append("\n");
                    properties.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                try {
                    URL file = new URL(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyURLToFile(file, new File(installationPath + "/proxy/server.jar"));
                } catch (IOException exception) {
                    File file = new File(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyFile(file, new File(installationPath + "/proxy/server.jar"));
                }

                File start = new File(installationPath + "/proxy/start.bat");
                start.getParentFile().mkdirs();
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("@echo off\n")
                        .append("SET version=").append("Proxy").append("\n")
                        .append("echo Starting on %version%\n")
                        .append("title %version%\n")
                        .append(":start\n").append("\"").append("").append(javaVersionPath).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                        .append("goto start\n");
                startWriter.close();
            } else {
                try {
                    URL file = new URL(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyURLToFile(file, new File(installationPath + "/proxy/server.jar"));
                } catch (IOException exception) {
                    File file = new File(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyFile(file, new File(installationPath + "/proxy/server.jar"));
                }
                File start = new File(installationPath + "/proxy/start.sh");
                start.getParentFile().mkdirs();
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("screen -S Proxy java -Xmx1G -jar server.jar");
                startWriter.close();
                Files.setPosixFilePermissions(Path.of(new File(installationPath + "/proxy/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
            }
            FileWriter writer = new FileWriter(installationPath + "/proxy/config.yml");
            writer
                    .append("listeners:\n")
                    .append("- query_port: 25565\n")
                    .append("  motd: '&2A ServerDownloader Server'\n")
                    .append("  tab_list: GLOBAL_PING\n")
                    .append("  priorities:\n")
                    .append("  - " + triples.get(0).getKey().replace(" ", "_") + "\n")
                    .append("  host: 0.0.0.0:25565\n")
                    .append("  max_players: 1\n")
                    .append("  tab_size: 60\n")
                    .append("  force_default_server: false\n")
                    .append("player_limit: -1\n")
                    .append("ip_forward: false\n")
                    .append("online_mode: true\n")
                    .append("forge_support: false\n")
                    .append("servers:\n");
            AtomicInteger v = new AtomicInteger();
            triples.forEach(triple -> {
                v.getAndIncrement();
                try {
                    writer.append("   ").append(triple.getKey().replace(" ", "_")).append(":").append("\n")
                            .append("       motd: '&1Just another BungeeCord - Forced Host'").append("\n")
                            .append("       address: localhost:").append(String.valueOf(25565 + v.get())).append("\n")
                            .append("       restricted: false").append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();

            if (System.getProperty("os.name").startsWith("Windows")) {
                FileWriter startBat = new FileWriter(installationPath + "/start.bat");
                AtomicInteger i = new AtomicInteger();
                startBat.append("cd proxy\n");
                startBat.append("start cmd /k call \"start.bat\" 1000\n");
                startBat.append("cd ..\n");
                triples.forEach(triple -> {
                    i.getAndIncrement();
                    try {
                        startBat.append("cd ").append(triple.getKey()).append("\n")
                                .append("start cmd /k call \"start.bat\" ").append(String.valueOf(1000 + i.get())).append("\n")
                                .append("cd ..\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                startBat.close();
            } else {
                File start = new File(installationPath + "/start.sh");
                start.getParentFile().mkdirs();
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                AtomicInteger i = new AtomicInteger();
                startWriter.append("cd proxy\n");
                startWriter.append("screen -S Proxy java -Xmx1G -jar server.jar\n");
                startWriter.append("cd ..\n");
                triples.forEach(triple -> {
                    i.getAndIncrement();
                    try {
                        startWriter.append("cd ").append(triple.getKey()).append("\n")
                                .append("bash " + triple.getKey() + "/start.sh\n")
                                .append("cd ..\n");
                        Files.setPosixFilePermissions(Path.of(new File(installationPath + "/" + triple.getKey() + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                startWriter.close();
                Files.setPosixFilePermissions(Path.of(new File(installationPath + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateBungeeCord(String installationPath) {
        AtomicInteger count = new AtomicInteger();
        serverNameJavaAndVersion.forEach((integer, stringIntegerIntegerTriple) -> {
            count.getAndIncrement();
            String name = stringIntegerIntegerTriple.getKey();
            int versionInt = stringIntegerIntegerTriple.getValue();
            int javaInt = stringIntegerIntegerTriple.getValue1();
            String version = files.get(versionInt);
            System.out.println(version);
            String java = javas.get(javaInt);

            Logger.getLogger().emergency("Generating BungeeCord for " + name + " with " + java);

            File folder = new File(installationPath + "/" + name + "/");
            folder.mkdirs();

            if (!Objects.equals(version, "bungeecord")) {
                try {
                    try {
                        URL file = new URL(Util.downloadServer + "/" + version.toLowerCase()
                                .replace(" ", "-") + "/server.jar");
                        FileUtils.copyURLToFile(file, new File(installationPath + "/" + name + "/server.jar"));
                    } catch (IOException exception) {
                        File file = new File(Util.downloadServer + "/" + version.toLowerCase()
                                .replace(" ", "-") + "/server.jar");
                        FileUtils.copyFile(file, new File(installationPath + "/" + name + "/server.jar"));
                    }

                    if (System.getProperty("os.name").startsWith("Windows")) {
                        File start = new File(installationPath + "/" + name +  "/start.bat");
                        start.createNewFile();
                        FileWriter startWriter = new FileWriter(start);
                        startWriter.append("@echo off\n")
                                .append("SET version=").append(name).append("\n")
                                .append("echo Starting on %version%\n")
                                .append("title %version%\n")
                                .append(":start\n").append("\"").append("").append(java).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                                .append("goto start\n");
                        startWriter.close();
                    } else {
                        File start = new File(installationPath + "/" + name +  "/start.sh");
                        start.createNewFile();
                        FileWriter startWriter = new FileWriter(start);
                        startWriter.append("screen -S \"" + name + "\" java -Xmx1G -jar server.jar");
                        startWriter.close();
                        Files.setPosixFilePermissions(Path.of(new File(installationPath + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
                    }

                    File eula = new File(installationPath + "/" + name +  "/eula.txt");
                    eula.createNewFile();
                    FileWriter fileWriter = new FileWriter(eula);
                    fileWriter.append("eula=true");
                    fileWriter.close();

                    File server_properties = new File(installationPath + "/" + name + "/server.properties");
                    FileWriter properties = new FileWriter(server_properties);
                    properties.append("server-port=").append(String.valueOf(25565 + count.get())).append("\n");
                    properties.append("motd=").append("A ServerDownloader Subserver").append("\n");
                    properties.append("online-mode=").append("false").append("\n");
                    properties.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                try {
                    URL file = new URL(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyURLToFile(file, new File(installationPath + "/proxy/server.jar"));
                } catch (IOException exception) {
                    File file = new File(Util.downloadServer + "/bungeecord/server.jar");
                    FileUtils.copyFile(file, new File(installationPath + "/proxy/server.jar"));
                }

                File start = new File(installationPath + "/proxy/start.bat");
                start.getParentFile().mkdirs();
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("@echo off\n")
                        .append("SET version=").append("Proxy").append("\n")
                        .append("echo Starting on %version%\n")
                        .append("title %version%\n")
                        .append(":start\n").append("\"").append("").append(javaVersionPath).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                        .append("goto start\n");
                startWriter.close();
            } else {
                File start = new File(installationPath + "/proxy/start.sh");
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("screen -S Proxy java -Xmx1G -jar server.jar");
                startWriter.close();
                Files.setPosixFilePermissions(Path.of(new File(installationPath + "/proxy/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
            }
            FileWriter writer = new FileWriter(installationPath + "/proxy/config.yml");
            writer
                    .append("listeners:\n")
                    .append("- query_port: 25565\n")
                    .append("  motd: '&2A ServerDownloader Server'\n")
                    .append("  tab_list: GLOBAL_PING\n")
                    .append("  priorities:\n")
                    .append("  - " + serverNameJavaAndVersion.values().stream().toList().get(0).getKey().replace(" ", "_") + "\n")
                    .append("  host: 0.0.0.0:25565\n")
                    .append("  max_players: 1\n")
                    .append("  tab_size: 60\n")
                    .append("  force_default_server: false\n")
                    .append("player_limit: -1\n")
                    .append("ip_forward: false\n")
                    .append("online_mode: true\n")
                    .append("forge_support: false\n")
                    .append("servers:\n");

            AtomicInteger v = new AtomicInteger();
            serverNameJavaAndVersion.forEach((integer1, stringIntegerIntegerTriple1) -> {
                v.getAndIncrement();
                try {
                    String versionName = stringIntegerIntegerTriple1.getKey();
                    writer.append("   ").append(versionName.replace(" ", "_")).append(":").append("\n")
                            .append("       motd: '&1Just another BungeeCord - Forced Host'").append("\n")
                            .append("       address: localhost:").append(String.valueOf(25565 + v.get())).append("\n")
                            .append("       restricted: false").append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();

            FileWriter startBat = new FileWriter(installationPath + "/start.bat");
            AtomicInteger i = new AtomicInteger();
            startBat.append("cd proxy\n");
            startBat.append("start cmd /k call \"start.bat\" 1000\n");
            startBat.append("cd ..\n");
            serverNameJavaAndVersion.forEach((integer1, stringIntegerIntegerTriple1) -> {
                i.getAndIncrement();
                try {
                    startBat.append("cd ").append(stringIntegerIntegerTriple1.getKey()).append("\n")
                            .append("start cmd /k call \"start.bat\" ").append(String.valueOf(1000 + i.get())).append("\n")
                            .append("cd ..\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            startBat.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generate(String installationPath, String version, String java) {
        try {
            try {
                URL file = new URL(Util.downloadServer + "/" + version.toLowerCase()
                        .replace(" ", "-") + "/server.jar");
                FileUtils.copyURLToFile(file, new File(installationPath + "/server.jar"));
            } catch (IOException exception) {
                File file = new File(Util.downloadServer + "/" + version.toLowerCase()
                        .replace(" ", "-") + "/server.jar");
                FileUtils.copyFile(file, new File(installationPath + "/server.jar"));
            }

            if (System.getProperty("os.name").startsWith("Windows")) {
                File start = new File(installationPath + "/start.bat");
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("@echo off\n")
                        .append("SET version=").append(version).append("\n")
                        .append("echo Starting on %version%\n")
                        .append("title %version%\n")
                        .append(":start\n").append("\"").append("").append(java).append("\\bin\\java.exe\" -Xmx1024M -jar server.jar nogui\n")
                        .append("goto start\n");
                startWriter.close();
            } else {
                File start = new File(installationPath + "/start.sh");
                start.createNewFile();
                FileWriter startWriter = new FileWriter(start);
                startWriter.append("screen -S \"" + version + "\" java -Xmx1G -jar server.jar");
                startWriter.close();
                Files.setPosixFilePermissions(Path.of(new File(installationPath + "/start.sh").getPath()), PosixFilePermissions.fromString("rwxr-xr-x"));
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
            properties.append("spawn-monsters=").append(String.valueOf(spawnMonsters)).append("\n");
            properties.append("enable-rcon=").append(String.valueOf(rcon)).append("\n");
            properties.append("rcon.port=").append(String.valueOf(rconPort)).append("\n");
            properties.append("rcon.password=").append(rconPassword).append("\n");
            properties.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void generate(String installationPath) {
        generate(installationPath, versionSelected, javaVersionPath);
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

    public static void setTheme(JFrame frame) throws
            UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
