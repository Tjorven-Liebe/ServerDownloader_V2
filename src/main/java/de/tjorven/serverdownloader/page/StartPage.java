package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.TextBubbleBorder;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage extends SDPanel implements ActionListener {
    public StartPage() {
        super("<html>ServerDownloader<br/><hr><center>by Cericx_</center></html>", null);
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);
        selectButton.setEnabled(false);
        JList<String> list = new JList<>(new String[]{
                "Spigot 1.8",
                "Spigot 1.9",
                "Spigot 1.10",
                "Spigot 1.11",
                "Spigot 1.12",
                "Spigot 1.13",
                "Spigot 1.14",
                "Spigot 1.15",
                "Spigot 1.16",
                "Spigot 1.17",
                "Spigot 1.18",
                "Paper 1.8",
                "Paper 1.9",
                "Paper 1.10",
                "Paper 1.11",
                "Paper 1.12",
                "Paper 1.13",
                "Paper 1.14",
                "Paper 1.15",
                "Paper 1.16",
                "Paper 1.17",
                "Paper 1.18",
                "Vanilla 1.8",
                "Vanilla 1.9",
                "Vanilla 1.10",
                "Vanilla 1.11",
                "Vanilla 1.12",
                "Vanilla 1.13",
                "Vanilla 1.14",
                "Vanilla 1.15",
                "Vanilla 1.16",
                "Vanilla 1.17",
                "Vanilla 1.18",
                "BungeeCord",
                "Waterfall"});
        list.addListSelectionListener(event -> {
            selectButton.setEnabled(true);
            Util.versionSelected = list.getSelectedValue();
            Logger.getLogger().info("Version selected: " + list.getSelectedValue());
        });
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        list.setCellRenderer(renderer);
        JScrollPane pane = new JScrollPane(list);
        Border border = new TextBubbleBorder(Color.BLACK, 1, 8, 0);
        pane.setBorder(border);
        pane.setBounds(40, 150, 400, 220);
        pane.updateUI();
        add(pane);
        JCheckBox jCheckBox = new JCheckBox("Advanced Setup");
        jCheckBox.setBounds(40, 300, 400, 30);
        add(jCheckBox);
        updateUI();
        Logger.getLogger().info("StartPage loaded");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Select")) {
            new SetupPage(this);
        }
    }

}
