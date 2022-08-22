package de.tjorven.serverdownloader.page.bungee;

import de.tjorven.serverdownloader.utils.Triple;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BungeeCordSetupPage extends SDPanel implements ActionListener {

    public BungeeCordSetupPage(SDPanel last) {
        super("BungeeSetup", last);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);
        
        Util.files.remove(0);

        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(1);
        model.setMaximum(10);
        JSpinner spinner = new JSpinner(model);
        spinner.setValue(2);
        spinner.setBounds(240, 100, 150, 30);
        add(spinner);

        JLabel minecraftVersion= new JLabel("Select Minecraft Version", SwingConstants.CENTER);
        minecraftVersion.setForeground(new Color(217, 76, 76));
        minecraftVersion.setFont(new Font("Arial", Font.PLAIN, 20));
        minecraftVersion.setBounds(40, 200, 350, 20);
        add(minecraftVersion);

        //Server versions
        JList<String> list = new JList<>(Util.files.toArray(new String[0]));
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        list.setCellRenderer(renderer);
        list.setSelectedIndex(0);
        JScrollPane pane = new JScrollPane(list);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.setBounds(40, 225, 350, 115);
        pane.updateUI();
        add(pane);

        JLabel javaVersion = new JLabel("Select Java Version", SwingConstants.CENTER);
        javaVersion.setForeground(new Color(217, 76, 76));
        javaVersion.setFont(new Font("Arial", Font.PLAIN, 20));
        javaVersion.setBounds(40, 345, 350, 20);
        add(javaVersion);
        //Java versions
        JList<String> javas = new JList<>(Util.javas.toArray(new String[0]));
        javas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        javas.setFont(new Font("Arial", Font.PLAIN, 20));
        DefaultListCellRenderer rendererj = (DefaultListCellRenderer) javas.getCellRenderer();
        rendererj.setHorizontalAlignment(SwingConstants.CENTER);
        javas.setCellRenderer(rendererj);
        JScrollPane panej = new JScrollPane(javas);
        panej.setBorder(BorderFactory.createEmptyBorder());
        panej.setBounds(40, 375, 350, 125);
        panej.updateUI();
        javas.setSelectedIndex(0);
        add(panej);

        Util.serverNameJavaAndVersion.put(1, new Triple<>("Server 1", 0, 0));
        Util.serverNameJavaAndVersion.put(2, new Triple<>("Server 2", 0, 0));

        SpinnerNumberModel maxModel = new SpinnerNumberModel();
        maxModel.setMinimum(1);
        maxModel.setMaximum(2);
        JSpinner serverSpinner = new JSpinner(maxModel);
        serverSpinner.setValue(2);

        spinner.addChangeListener(event -> {
            int value = Integer.parseInt(spinner.getValue().toString());

            if (!Util.serverNameJavaAndVersion.containsKey(value)) {
                Util.serverNameJavaAndVersion.put(value, new Triple<>("Server " + value, 0, 0));
            } else {
                Util.serverNameJavaAndVersion.remove(value + 1);
            }
            maxModel.setMaximum(Util.serverNameJavaAndVersion.size());
            serverSpinner.setModel(maxModel);
            if (Integer.parseInt(serverSpinner.getValue().toString())>value) {
                serverSpinner.setValue(value);
            }
            Util.serverNameJavaAndVersion.forEach((integer, triple) -> {
                Logger.getLogger().emergency(integer + ":" + triple.getKey() + ":" + triple.getValue());
            });
        });

        serverSpinner.setBounds(240, 150, 90, 30);
        add(serverSpinner);

        JTextField field = new JTextField(Util.serverNameJavaAndVersion.get(2).getKey());
        field.setBounds(40, 150, 180, 30);
        add(field);

        serverSpinner.addChangeListener(event -> {
            field.setText(Util.serverNameJavaAndVersion.get(Integer.parseInt(serverSpinner.getValue().toString())).getKey());
            list.setSelectedIndex(Util.serverNameJavaAndVersion.get((int) serverSpinner.getValue()).getValue());
            javas.setSelectedIndex(Util.serverNameJavaAndVersion.get((int) serverSpinner.getValue()).getValue1());
        });

        JButton set = new JButton("Set");
        set.setBounds(340, 150, 50, 30);
        add(set);
        set.addActionListener(event -> {
            Util.serverNameJavaAndVersion.put(Integer.parseInt(serverSpinner.getValue().toString()), new Triple<>(field.getText(), list.getSelectedIndex(), javas.getSelectedIndex()));
        });

        JLabel label = new JLabel("Subserver count");
        label.setBounds(40, 100, 200, 30);
        add(label);
        updateUI();
        Logger.getLogger().info("Loaded BungeeCordSetupPage");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new BungeeCordPropertiesPage(this);
    }
}
