package de.tjorven.serverdownloader.page.bungee;

import de.tjorven.serverdownloader.page.general.EndPage;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BungeeCordPropertiesPage extends SDPanel implements ActionListener {
    JTextField bungeePort;

    public BungeeCordPropertiesPage(SDPanel last) {
        super("BungeeProperties", last);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);

        JLabel serverPortLabel = new JLabel("BungeeCord Port", SwingConstants.LEFT);
        serverPortLabel.setBounds(40, 100, 100, 30);
        add(serverPortLabel);
        bungeePort = new JTextField("25565");
        bungeePort.setBounds(160, 100, 320, 30);
        add(bungeePort);

        JLabel tabList = new JLabel("Tablist", SwingConstants.LEFT);
        tabList.setBounds(40, 150, 90, 30);
        add(tabList);

        JComboBox<String> jComboBox = new JComboBox<>(new String[]{"GLOBAL_PING", "GLOBAL", "SERVER"});
        jComboBox.setSelectedIndex(2);
        jComboBox.setBounds(160, 150, 320, 30);
        add(jComboBox);

        updateUI();
        Logger.getLogger().info("Loaded BungeeCordPropertiesPage");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Util.versionType = "bungeecord";
        new EndPage(this);
    }
}
