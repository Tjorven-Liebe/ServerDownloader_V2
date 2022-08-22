package de.tjorven.serverdownloader.command;

import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

public class CreateCommand implements ICommandExecutor {
    @Override
    public void onCommand(String label, String[] args) {
        Util.javaVersionPath = System.getProperty("java.home");
        if (args.length == 0) {
            Logger.getLogger().error("Invalid arguments!", "create [-p, -d, -jvm, -v, -port, -om, -max, -motd, -pvp, -sa, -sm, -af, -an] [value]");
            return;
        }
        String downloadTo = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-p"))
                downloadTo = args[i + 1].replace("_", "");
            if (args[i].equalsIgnoreCase("-d"))
                Util.downloadServer = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-jvm"))
                Util.javaVersionPath = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-v"))
                Util.versionSelected = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-port"))
                Util.serverPort = Integer.parseInt(args[i + 1]);
            if (args[i].equalsIgnoreCase("-om"))
                Util.onlineMode = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-max"))
                Util.maxPlayers = Integer.parseInt(args[i + 1]);
            if (args[i].equalsIgnoreCase("-motd"))
                Util.motd = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-pvp"))
                Util.pvp = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-sa"))
                Util.spawnAnimals = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-sm"))
                Util.spawnMonsters = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-rcon"))
                Util.rcon = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-rcon-port"))
                Util.rconPort = Integer.parseInt(args[i + 1]);
            if (args[i].equalsIgnoreCase("-rcon-pass"))
                Util.rconPassword = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-af"))
                Util.allowFlight = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-an"))
                Util.allowNether = Boolean.parseBoolean(args[i + 1]);
        }
        if (Util.serverPort == 0)
            Util.serverPort = 25565;
        if (Util.maxPlayers == 0)
            Util.maxPlayers = 20;
        if (Util.downloadServer == null)
            Util.downloadServer = "http://37.114.47.90/files/serverfiles";
        if (Util.javaVersionPath == null)
            Util.javaVersionPath = System.getProperty("java.home");
        if (downloadTo == null)
            downloadTo = System.getProperty("user.dir");
        if (Util.versionSelected == null) {
            Logger.getLogger().error("Invalid arguments!", "You need to select a version, using: -v <version>");
            return;
        }
        Logger.getLogger().info("Downloading server...");
        Util.generate(downloadTo);
        Logger.getLogger().info("done!");
        System.exit(0);
    }
}
