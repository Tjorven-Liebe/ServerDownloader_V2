package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BungeeCordSetupPage extends SDPanel implements ActionListener {

    public BungeeCordSetupPage(SDPanel last) {
        super("BungeeSetup", last);
        JButton selectButton = new JButton("Next");
        selectButton.addActionListener(this);
        selectButton.setBounds(40, 500, 400, 30);
        add(selectButton);

        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(1);
        model.setMaximum(10);
        JSpinner spinner = new JSpinner(model);
        spinner.setValue(2);
        spinner.setBounds(240, 100, 150, 30);
        add(spinner);

        Util.serverAndName.put(1, "Server 1");
        Util.serverAndName.put(2, "Server 2");

        SpinnerNumberModel maxModel = new SpinnerNumberModel();
        maxModel.setMinimum(1);
        maxModel.setMaximum(2);
        JSpinner serverSpinner = new JSpinner(maxModel);
        serverSpinner.setValue(2);

        spinner.addChangeListener(event -> {
            int value = Integer.parseInt(spinner.getValue().toString());

            if (!Util.serverAndName.containsKey(value)) {
                Util.serverAndName.put(value, "Server " + value);
            } else {
                Util.serverAndName.remove(value + 1);
            }
            maxModel.setMaximum(Util.serverAndName.size());
            serverSpinner.setModel(maxModel);
            if (Integer.parseInt(serverSpinner.getValue().toString()) > value) {
                serverSpinner.setValue(value);
            }
            Util.serverAndName.forEach((integer, string) -> Logger.getLogger().emergency(integer + ":" + string));
        });

        serverSpinner.setBounds(240, 150, 90, 30);
        add(serverSpinner);

        JTextField field = new JTextField(Util.serverAndName.get(2));
        field.setBounds(40, 150, 180, 30);
        add(field);

        serverSpinner.addChangeListener(event -> field.setText(Util.serverAndName.get(Integer.parseInt(serverSpinner.getValue().toString()))));

        JButton set = new JButton("Set");
        set.setBounds(340, 150, 50, 30);
        add(set);
        set.addActionListener(event -> Util.serverAndName.put(Integer.parseInt(serverSpinner.getValue().toString()), field.getText()));

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
