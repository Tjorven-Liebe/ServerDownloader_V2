package de.tjorven.serverdownloader;

import de.tjorven.serverdownloader.page.StartPage;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.command.CommandHandler;
import de.tjorven.serverdownloader.utils.logger.Logger;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServerDownloader extends JFrame {

    @Getter
    @Setter
    public static JFrame frame;

    public ServerDownloader() throws IOException {
        frame = this;
        try {
            Util.setTheme(frame);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setSize(500, 600);
        setTitle("ServerDownloader");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spigot-og.png")));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        new StartPage();
        new CommandHandler();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws IOException {
        if (!System.getProperty("os.name").startsWith("Windows")) {
            ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/bash", "-c", "command -v screen");
            if (new String(processBuilder.start().getInputStream().readAllBytes()).isEmpty()) {
                Runtime.getRuntime().exec("sudo apt-get install screen");
            }
            new CommandHandler();
        } else {
            new ServerDownloader();
        }
    }
}
