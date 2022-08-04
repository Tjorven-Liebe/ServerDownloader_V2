package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.TextBubbleBorder;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.configuration.file.YamlConfiguration;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartPage extends SDPanel {

    public StartPage() {
        super("ServerDownloader by Cericx_", null);

        updateUI();
        JButton selectButton = new JButton("Select");

        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/ServerDownloader/settings.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        JButton button = new JButton();
        button.setBackground(null);
        button.setForeground(null);
        button.setBorder(new TextBubbleBorder(null, 0, 50, 0));
        if (configuration.get("gui.theme") == ("light-owl-contrast"))
            button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/gear-solid-dark.png"))));
        else
            button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/gear-solid.png"))));
        button.addActionListener(event -> new SettingsPage(this));
        button.setBounds(20, 25, 50, 50);
        add(button);

        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);

        JCheckBox checkBox = new JCheckBox("Use default Downloadlocation", configuration.getBoolean("useDownloadserver"));
        checkBox.setBounds(40, 120, 400, 30);

        add(checkBox);
        JLabel downloadLabel = new JLabel("<html>Download URL or folder</html>");
        downloadLabel.setBounds(40, 160, 130, 30);
        add(downloadLabel);
        JTextField downloadServer = new JTextField();
        if (configuration.getString("downloadserver") != null)
            downloadServer.setText(configuration.getString("downloadserver"));
        downloadServer.setBounds(180, 160, 260, 30);
        add(downloadServer);
        JButton searchButton = new JButton("Choose");
        searchButton.setBounds(180, 190, 260, 30);
        add(searchButton);
        searchButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (downloadServer.getText() == null)
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            else
                jFileChooser.setCurrentDirectory(new File(downloadServer.getText()));
            jFileChooser.setDialogTitle("Choose an installation folder");
            int returnVal = jFileChooser.showOpenDialog(downloadServer);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                downloadServer.setText(jFileChooser.getSelectedFile().toString());
            }
        });

        checkBox.addActionListener(event -> {
            if (checkBox.isSelected()) {
                downloadLabel.setEnabled(false);
                downloadServer.setEnabled(false);
                searchButton.setEnabled(false);
            } else {
                downloadLabel.setEnabled(true);
                downloadServer.setEnabled(true);
                searchButton.setEnabled(true);
            }
        });
        if (checkBox.isSelected()) {
            downloadLabel.setEnabled(false);
            downloadServer.setEnabled(false);
            searchButton.setEnabled(false);
        }
        selectButton.addActionListener(event -> {
            if (checkBox.isSelected()) {
                Util.downloadServer = "http://37.114.47.90/files/serverfiles";
            } else {
                Util.downloadServer = downloadServer.getText();
                configuration.set("downloadserver", Util.downloadServer);
            }
            new VersionPage(this);
            configuration.set("useDownloadserver", checkBox.isSelected());
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Logger.getLogger().info("Using downloadserver: " + Util.downloadServer);
        });
        updateUI();
        Logger.getLogger().info(" StartPage loaded");
    }

}
