package de.tjorven.serverdownloader.command;

import de.tjorven.serverdownloader.utils.Triple;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateBungeeCommand implements ICommandExecutor {

    int port;
    String jvm;
    String version;
    boolean onlineMode;
    int maxPlayers;
    String path;
    List<Triple<String, String, String>> server;

    @Override
    public void onCommand(String label, String[] args) {
        server = new ArrayList<>();
        Util.javaVersionPath = System.getProperty("java.home");
        if (args.length == 0) {
            Logger.getLogger().error("Invalid arguments!", "Usage: createbungee [-p, -d, -jvm, -port, -om, -max]");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-p"))
                path = args[i + 1].replace("_", "");
            if (args[i].equalsIgnoreCase("-d"))
                Util.downloadServer = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-jvm"))
                Util.javaVersionPath = args[i + 1].replace("_", " ");
            if (args[i].equalsIgnoreCase("-port"))
                port = Integer.parseInt(args[i + 1]);
            if (args[i].equalsIgnoreCase("-om"))
                onlineMode = Boolean.parseBoolean(args[i + 1]);
            if (args[i].equalsIgnoreCase("-max"))
                maxPlayers = Integer.parseInt(args[i + 1]);
        }
        if (port == 0)
            port = 25565;
        if (maxPlayers == 0)
            maxPlayers = 20;
        if (path == null)
            path = System.getProperty("user.dir");
        if (Util.downloadServer == null)
            Util.downloadServer = "http://37.114.47.90/files/serverfiles/";
        if (jvm == null)
            jvm = Util.javaVersionPath;
        Logger.getLogger().info("Creating Proxy..");
        scan();
    }

    public void scan() {
        Logger.getLogger().info("Type a name for your next Server, you can alternatively exit by typing \"exit\"", "waiting for input..");
        Scanner scanner = new Scanner(System.in);
        Triple<String, String, String> serverTriple = new Triple<>();
        serverTriple.setKey(scanner.nextLine());
        Logger.getLogger().info("Now provide a version for your Server", "waiting for input..");
        serverTriple.setValue(scanner.nextLine());
        Logger.getLogger().info("Now provide a JavaVirtualMachine [JVM] for your Server", "waiting for input, Press enter to use default java..");
        if (scanner.nextLine().isEmpty()) {
            serverTriple.setValue1(System.getProperty("java.home"));
        } else
            serverTriple.setValue1(scanner.nextLine());
        server.add(serverTriple);
        Logger.getLogger().info("Type exit to quit or type another key to continue", "waiting for input..");
        if (scanner.nextLine().equals("exit")) {
            Logger.getLogger().info("Creating Serverfiles..");
            Util.generateBungeeCord(path, server);
            System.exit(0);
        } else
            scan();
    }

}
