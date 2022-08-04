package de.tjorven.serverdownloader.command;

import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

public class CreateCommand implements ICommandExecutor {
    @Override
    public void onCommand(String label, String[] args) {
        Util.javaVersionPath = System.getProperty("java.home");
        if (args.length == 0) {
            Logger.getLogger().error("Invalid arguments!", "Usage: create [-p, -d, -jvm, -v] <value>");
            return;
        }
        String downloadTo = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-p")) {
                downloadTo = args[i + 1].replace("_", "");
            }
            if (args[i].equalsIgnoreCase("-d")) {
                Util.downloadServer = args[i + 1].replace("_", " ");
            }
            if (args[i].equalsIgnoreCase("-jvm")) {
                Util.javaVersionPath = args[i + 1].replace("_", " ");
            }
            if (args[i].equalsIgnoreCase("-v")) {
                Util.versionSelected = args[i + 1].replace("_", " ");
            }
        }
        if (Util.downloadServer == null) {
            Util.downloadServer = "http://37.114.47.90/files/serverfiles";
        }
        if (Util.javaVersionPath == null) {
            Util.javaVersionPath = System.getProperty("java.home");
        }
        if (downloadTo == null) {
            downloadTo = System.getProperty("user.dir");
        }
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
