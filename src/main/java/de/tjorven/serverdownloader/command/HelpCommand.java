package de.tjorven.serverdownloader.command;

import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

public class HelpCommand implements ICommandExecutor {
    @Override
    public void onCommand(String label, String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "info" -> Logger.getLogger().info("info [downloadserver]");
                case "create" -> Logger.getLogger().info("create -p Pfad [value]", "create -d Downloadserver [value]", "create -jvm Java Virtual Machine [value]", "create -v Version [value] <see: info>");
                case "help" -> Logger.getLogger().info("help [command]");
            }
        } else {
            Logger.getLogger().info("Available commands:");
            Logger.getLogger().info("create [-p, -d, -jvm, -v] [value]");
            Logger.getLogger().info("info [downloadserver]");
            Logger.getLogger().info("help [command]");
        }
    }
}
