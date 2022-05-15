package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.*;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class SetupPage extends SDPanel implements ActionListener {


    public SetupPage(SDPanel before) {
        super("<html>Setup<br/><hr></html>", before);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        selectButton.setEnabled(false);
        add(selectButton);

        JLabel selectJavaVersion = new JLabel("Select Java Version", SwingConstants.CENTER);
        selectJavaVersion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        selectJavaVersion.setBounds(52, 100, 400, 30);
        add(selectJavaVersion);


        Map<String, String> fileList = new HashMap<>();
        try {
            File fileNew = new File(System.getProperty("java.home"));
            if (!fileNew.toString().contains(".jdks"))
                Files.list(new File(System.getProperty("java.home") + "/../").toPath()).forEach(file -> {
                    fileList.put(file.getFileName().toFile().getName(), file.toString());
                });
            else
                Files.list(new File("C:/Program Files/Java/").toPath()).forEach(file -> {
                    if (file.toFile().exists())
                        fileList.put(file.getFileName().toFile().getName(), file.toString());
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (Files.isDirectory(new File(System.getProperty("user.home") + "/.jdks/").toPath())) {
                Files.list(new File(System.getProperty("user.home") + "/.jdks/").toPath()).forEach(file -> {
                    if (!file.toString().endsWith(".intellij"))
                        if (file.toFile().exists())
                            fileList.put(file.getFileName().toFile().getName(), file.toString());
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
        Border border = new TextBubbleBorder(Color.BLACK, 1, 8, 0);
        pane.setBorder(border);
        pane.setBounds(40, 150, 400, 220);
        add(pane);
        updateUI();
        Logger.getLogger().info("SetupPage loaded");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(Util.versionSelected.equals("BungeeCord") || Util.versionSelected.equals("Waterfall"))) {
            if (e.getActionCommand().equals("Next"))
                new ProptertiesPage(this);
        } else
            new BungeeCordSetupPage(this);
    }
}
