package de.tjorven.serverdownloader.page.general;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;
import de.tjorven.serverdownloader.utils.mcparsing.MCParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersionPage extends SDPanel implements ActionListener {
    public VersionPage(SDPanel lastPage) {
        super("Select a Version", lastPage);

        Util.files = new ArrayList<>();
        List<String> files = Util.files;
        HashMap<String, String> keys = new HashMap<>();
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(Util.jsonFromFile(Util.downloadServer + "/versions.json"), JsonObject.class);
            for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
                JsonArray arr = stringJsonElementEntry.getValue().getAsJsonArray();

                for (int i = 0; i < arr.size(); i++) {
                    String jsonElement = arr.get(i).getAsString();
                    JsonObject nameObject = gson.fromJson(Util.jsonFromFile(Util.downloadServer + "/" + jsonElement + "/data.json"), JsonObject.class);
                    files.add(nameObject.get("version-name").getAsString());
                    keys.put(nameObject.get("version-name").getAsString(), jsonElement);
                }
            }
        } catch (Exception exception) {
            JLabel errorLabel = new JLabel("<html>You've made an error<br/>in your download URL", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            errorLabel.setBounds(40, 150, 400, 60);
            errorLabel.setForeground(Color.red);
            add(errorLabel);
            updateUI();
            exception.printStackTrace();
            return;
        }
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);
        selectButton.setEnabled(false);
        JList<String> list = new JList<>(files.toArray(new String[0]));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectButton.setEnabled(true);
                Util.versionName = list.getSelectedValue();
                Util.versionSelected = keys.get(list.getSelectedValue());
                Logger.getLogger().info("Version selected: " + list.getSelectedValue());
                MCParser.parse();
                MCParser.instrumentations.forEach(instrumentation -> System.out.println(instrumentation.fileName + ":" + instrumentation.action + ":" + instrumentation.data.entrySet()));
                super.mouseClicked(e);
            }
        });
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        list.setCellRenderer(renderer);
        JScrollPane pane = new JScrollPane(list);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setBounds(40, 100, 400, 375);
        pane.updateUI();
        add(pane);
        updateUI();
        Logger.getLogger().info("VersionPage loaded");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Select")) {
            new SetupPage(this);
        }
    }

}
