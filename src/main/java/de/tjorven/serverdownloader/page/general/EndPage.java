package de.tjorven.serverdownloader.page.general;

import de.tjorven.serverdownloader.ServerDownloader;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.TextBubbleBorder;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.configuration.file.YamlConfiguration;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class EndPage extends SDPanel {

    public EndPage(SDPanel lastPage) {
        super("EndPage", lastPage);
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/ServerDownloader/settings.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        JTextField installationField = new JTextField();
        if (configuration.getString("downloadlocation") != null)
            installationField.setText(configuration.getString("downloadlocation"));
        else installationField.setText(System.getProperty("user.dir"));
        JButton chooseInstallationFolder = new JButton("Choose");
        chooseInstallationFolder.addActionListener(event -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setCurrentDirectory(new File(installationField.getText()));
            jFileChooser.setDialogTitle("Choose an installation folder");
            int returnVal = jFileChooser.showOpenDialog(installationField);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                installationField.setText(jFileChooser.getSelectedFile().toString());
            }
        });
        installationField.setBounds(40, 100, 300, 50);
        add(installationField);
        chooseInstallationFolder.setBounds(350, 100, 100, 50);
        add(chooseInstallationFolder);
        JButton accept = new JButton("Accept");
        accept.setSize(200, 100);
        accept.setBounds(
                ServerDownloader.frame.getWidth() / 2 - (200 / 2),
                ServerDownloader.frame.getHeight() / 2 - (100 / 2), 200, 100);
        accept.addActionListener(event -> {
            this.removeAll();
            Logger.getLogger().emergency(Util.versionType);
            if (Objects.equals(Util.versionType, "bungeecord") || Objects.equals(Util.versionType, "waterfall")) {
                Util.generateBungeeCord(installationField.getText());
            } else {
                Util.generate(installationField.getText());
            }
            JLabel label = new JLabel("<html><center>Thank you for using!<br/>You can close this window now..</center></html>", SwingConstants.CENTER);
            label.setBorder(new TextBubbleBorder(Color.BLACK, 5, 50, 0));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 25));
            label.setBounds(
                    ServerDownloader.frame.getWidth() / 2 - (300 / 2),
                    ServerDownloader.frame.getHeight() / 2 - (300 / 2), 300, 300);
            add(label);
            configuration.set("downloadlocation", installationField.getText());
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            updateUI();
        });
        add(accept);
        updateUI();
        Logger.getLogger().info("EndPage loaded");
    }
}
