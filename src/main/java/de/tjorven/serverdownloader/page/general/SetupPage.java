package de.tjorven.serverdownloader.page.general;

import de.tjorven.serverdownloader.page.bungee.BungeeCordSetupPage;
import de.tjorven.serverdownloader.page.server.ProptertiesPage;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetupPage extends SDPanel implements ActionListener {


    public SetupPage(SDPanel before) {
        super("Select Java Version", before);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        Util.javas = new ArrayList<>();
        selectButton.setBounds(40, 500, 400, 30);
        selectButton.setEnabled(false);
        add(selectButton);

        Map<String, String> fileList = new HashMap<>();
        try {
            File fileNew = new File(System.getProperty("java.home"));
            if (!fileNew.toString().contains(".jdks"))
                Files.list(new File(System.getProperty("java.home") + "/../").toPath()).forEach(file -> fileList.put(file.getFileName().toFile().getName(), file.toString()));
            else
                Files.list(new File("C:/Program Files/Java/").toPath()).forEach(file -> {
                    if (file.toFile().exists()) {
                        fileList.put(file.getFileName().toFile().getName(), file.toString());
                        Util.javas.add(file.toAbsolutePath().toString());
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (Files.isDirectory(new File(System.getProperty("user.home") + "/.jdks/").toPath())) {
                Files.list(new File(System.getProperty("user.home") + "/.jdks/").toPath()).forEach(file -> {
                    if (!file.toString().endsWith(".intellij"))
                        if (file.toFile().exists()) {
                            fileList.put(file.getFileName().toFile().getName(), file.toString());
                            Util.javas.add(file.toAbsolutePath().toString());
                        }
                });
            }
        } catch (IOException ignored) {
        }
        JList<String> list = new JList<>(fileList.keySet().toArray(new String[0]));
        list.addListSelectionListener(event -> {
            selectButton.setEnabled(true);
            Util.javaVersionName = list.getSelectedValue();
            Util.javaVersionPath = fileList.get(list.getSelectedValue());
            Logger.getLogger().info("Version selected: " + Util.javaVersionName, "Path: " + Util.javaVersionPath);
        });
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        list.setCellRenderer(renderer);
        JScrollPane pane = new JScrollPane(list);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setBounds(40, 100, 400, 375);
        add(pane);
        updateUI();
        Logger.getLogger().info("SetupPage loaded");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(Util.versionSelected);
        if (!(Util.versionSelected.equalsIgnoreCase("BungeeCord") || Util.versionSelected.equalsIgnoreCase("Waterfall"))) {
            if (e.getActionCommand().equals("Next")) {
                Util.versionType = "bungeecord";
                new ProptertiesPage(this);
            }
        } else
            new BungeeCordSetupPage(this);
    }
}
