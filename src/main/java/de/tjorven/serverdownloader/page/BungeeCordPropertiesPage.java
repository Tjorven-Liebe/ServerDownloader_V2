package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BungeeCordPropertiesPage extends SDPanel implements ActionListener {
    JTextField bungeePort;
    JSpinner spinner;
    public BungeeCordPropertiesPage(SDPanel last) {
        super("BungeeProperties", last);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        selectButton.setEnabled(false);
        add(selectButton);

        JLabel serverPortLabel = new JLabel("BungeeCord Port", SwingConstants.LEFT);
        serverPortLabel.setBounds(40, 100, 90, 30);
        add(serverPortLabel);
        bungeePort = new JTextField("25565");
        bungeePort.setBounds(140, 100, 300, 30);
        add(bungeePort);

        JLabel tabList = new JLabel("Tablist", SwingConstants.LEFT);
        tabList.setBounds(40, 150, 90, 30);
        add(tabList);
        JComboBox<String> jComboBox = new JComboBox<>(new String[] {"GLOBAL_PING", "GLOBAL", "SERVER"});
        jComboBox.setSelectedIndex(2);
        jComboBox.setBounds(140, 150, 300, 30);
        add(jComboBox);

        updateUI();
        Logger.getLogger().info("Loaded BungeeCordPropertiesPage");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
