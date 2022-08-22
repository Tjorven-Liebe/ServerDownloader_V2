package de.tjorven.serverdownloader.command;

import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

public class HelpCommand implements ICommandExecutor {
    @Override
    public void onCommand(String label, String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "info" -> Logger.getLogger().info("info [downloadserver]");
                case "create" -> Logger.getLogger().info(
                        "_ will be replaced with \" \"",
                        "create -p Pfad [value]",
                        "create -d Downloadserver [value]",
                        "create -jvm Java Virtual Machine [value]",
                        "create -v Version [value] <see: info>",
                        "create -port Port [value]",
                        "create -om Online Mode [value]",
                        "create -max Max. Spieler [value]",
                        "create -motd Motd [value]",
                        "create -pvp PvP [value]",
                        "create -sa Spawn Animals [value]",
                        "create -sm Spawn Monsters [value]",
                        "create -rcon [value]",
                        "create -rcon-port [value]",
                        "create -rcon-pass [value]",
                        "create -af Allow Flight [value]",
                        "create -an Allow Nether [value]");
                case "help" -> Logger.getLogger().info("help [command]");
            }
        } else {
            Logger.getLogger().info("Available commands:");
            Logger.getLogger().info("create [-p, -d, -jvm, -v, -port, -om, -max, -motd, -pvp, -sa, -sm, -af, -an] [value]");
            Logger.getLogger().info("info [downloadserver]");
            Logger.getLogger().info("help [command]");
        }
    }
}
