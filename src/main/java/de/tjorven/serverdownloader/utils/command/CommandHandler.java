package de.tjorven.serverdownloader.utils.command;

import de.tjorven.serverdownloader.command.CreateBungeeCommand;
import de.tjorven.serverdownloader.command.CreateCommand;
import de.tjorven.serverdownloader.command.HelpCommand;
import de.tjorven.serverdownloader.command.InfoCommand;
import de.tjorven.serverdownloader.utils.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CommandHandler {

    String label;
    String[] args;
    ArrayList<String> commands = new ArrayList<>();

    public CommandHandler() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Logger.getLogger().info("Type a command (type \"help\" for help)...");
        while (scanner.hasNext()) {
            String commandLine = scanner.nextLine();
            String[] commandOriginal = commandLine.split(" ");
            label = commandLine.split(" ")[0];
            args = Arrays.copyOfRange(commandOriginal, 1, commandOriginal.length);
            register(new CreateCommand(), "create");
            register(new InfoCommand(), "info");
            register(new HelpCommand(), "help");
            register(new CreateBungeeCommand(), "createbungee");
            if (!commands.contains(label)) {
                Logger.getLogger().error("Unknown command! Type \"help\" for help.");
            }
        }
    }

    public void register(ICommandExecutor commandExecutor, String command) {
        if (label.equalsIgnoreCase(command)) {
            commandExecutor.onCommand(command, args);
            commands.add(command);
        }
    }

}
