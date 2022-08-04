package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProptertiesPage extends SDPanel implements ActionListener {
    JTextField serverPort;
    JTextField maxPlayers;
    JTextField motd;
    JCheckBox onlineMode;
    JCheckBox enableCommandBlock;
    JCheckBox allowFlight;
    JCheckBox pvp;
    JCheckBox allowNether;
    JCheckBox spawnAnimals;
    JCheckBox hardcore;
    JCheckBox debug;
    JCheckBox spawnMonsters;

    public ProptertiesPage(SDPanel before) {
        super("Properties", before);

        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);

        JLabel label = new JLabel("<html><center>This is the properties page.<br/>You can change the properties here.<br/>There are not listed all properties<br/>Change more in: server.properties</center></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBounds(40, 50, 400, 120);
        add(label);

        JLabel serverPortLabel = new JLabel("Server Port", SwingConstants.LEFT);
        serverPortLabel.setBounds(40, 180, 90, 30);
        add(serverPortLabel);
        serverPort = new JTextField("25565");
        serverPort.setBounds(140, 180, 300, 30);
        add(serverPort);
        JLabel maxPlayersLabel = new JLabel("MaxPlayers", SwingConstants.LEFT);
        maxPlayersLabel.setBounds(40, 220, 90, 30);
        add(maxPlayersLabel);
        maxPlayers = new JTextField("20");
        maxPlayers.setBounds(140, 220, 300, 30);
        add(maxPlayers);

        JLabel motdLabel = new JLabel("MOTD", SwingConstants.LEFT);
        motdLabel.setBounds(40, 260, 90, 30);
        add(motdLabel);
        motd = new JTextField("A ServerDownloader created Server");
        motd.setBounds(140, 260, 300, 30);
        add(motd);

        onlineMode = new JCheckBox("OnlineMode", true);
        onlineMode.setBounds(60, 300, 120, 30);
        add(onlineMode);
        enableCommandBlock = new JCheckBox("CommandBlocks");
        enableCommandBlock.setBounds(190, 300, 120, 30);
        add(enableCommandBlock);
        allowFlight = new JCheckBox("Allow Flight");
        allowFlight.setBounds(320, 300, 150, 30);
        add(allowFlight);

        pvp = new JCheckBox("PvP", true);
        pvp.setBounds(60, 350, 120, 30);
        add(pvp);

        allowNether = new JCheckBox("Allow Nether", true);
        allowNether.setBounds(190, 350, 120, 30);
        add(allowNether);
        spawnAnimals = new JCheckBox("Spawn Animals", true);
        spawnAnimals.setBounds(320, 350, 120, 30);
        add(spawnAnimals);

        hardcore = new JCheckBox("Hardcore");
        hardcore.setBounds(60, 400, 120, 30);
        add(hardcore);
        debug = new JCheckBox("Debug");
        debug.setBounds(190, 400, 120, 30);
        add(debug);
        spawnMonsters = new JCheckBox("Spawn Monsters", true);
        spawnMonsters.setBounds(320, 400, 120, 30);
        add(spawnMonsters);

        updateUI();
        Logger.getLogger().info("PropertiesPage loaded");
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            Util.serverPort = Integer.parseInt(serverPort.getText());
            Util.maxPlayers = Integer.parseInt(maxPlayers.getText());
            Util.motd = motd.getText();
            Util.onlineMode = onlineMode.isSelected();
            Util.enableCommandBlock = enableCommandBlock.isSelected();
            Util.allowFlight = allowFlight.isSelected();
            Util.pvp = pvp.isSelected();
            Util.allowNether = allowNether.isSelected();
            Util.spawnAnimals = spawnAnimals.isSelected();
            Util.hardcore = hardcore.isSelected();
            Util.debug = debug.isSelected();
            Util.spawnMonsters = spawnMonsters.isSelected();
            new EndPage(this);
        }
    }
}
