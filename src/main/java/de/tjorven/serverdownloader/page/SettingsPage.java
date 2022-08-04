package de.tjorven.serverdownloader.page;

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme;
import de.tjorven.serverdownloader.ServerDownloader;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.configuration.file.YamlConfiguration;

import javax.swing.*;
import java.io.File;

public class SettingsPage extends SDPanel {

    public SettingsPage(SDPanel lastPage) {
        super("Optionen", lastPage);
        updateUI();
        JComboBox<String> jComboBox = new JComboBox<>(new String[]{"Material DeepOcean", "NightOwl", "Gradianto DeepOcean", "Gradianto MidnightBlue", "LightOwl", "OSTheme"});
        jComboBox.setBounds(100, 150, 300, 30);
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/ServerDownloader/settings.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        jComboBox.addActionListener(event -> {
            try {
                switch (jComboBox.getSelectedItem().toString().toLowerCase()) {
                    case "material deepocean": {
                        configuration.set("gui.theme", "material-deep-ocean");
                        configuration.save(file);
                        FlatMaterialDeepOceanIJTheme.setup();
                        UIManager.setLookAndFeel(new FlatMaterialDeepOceanIJTheme());
                        updateUI();
                        lastPage.updateUI();
                        break;
                    }
                    case "nightowl": {
                        configuration.set("gui.theme", "night-owl");
                        configuration.save(file);
                        FlatNightOwlIJTheme.setup();
                        UIManager.setLookAndFeel(new FlatNightOwlIJTheme());
                        updateUI();
                        lastPage.updateUI();
                        break;
                    }
                    case "gradianto deepocean": {
                        configuration.set("gui.theme", "gradianto-deep-ocean");
                        configuration.save(file);
                        FlatGradiantoMidnightBlueIJTheme.setup();
                        UIManager.setLookAndFeel(new FlatGradiantoDeepOceanIJTheme());
                        updateUI();
                        lastPage.updateUI();
                        break;
                    }
                    case "gradianto midnightblue": {
                        configuration.set("gui.theme", "gradianto-midnight-blue");
                        configuration.save(file);
                        FlatGradiantoMidnightBlueIJTheme.setup();
                        UIManager.setLookAndFeel(new FlatGradiantoMidnightBlueIJTheme());
                        updateUI();
                        lastPage.updateUI();
                        break;
                    }
                    case "ostheme": {
                        configuration.set("gui.theme", "os-theme");
                        configuration.save(file);
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        break;
                    }
                    case "lightowl": {
                        configuration.set("gui.theme", "light-owl-contrast");
                        configuration.save(file);
                        FlatLightOwlContrastIJTheme.setup();
                        UIManager.setLookAndFeel(new FlatLightOwlContrastIJTheme());
                        updateUI();
                        lastPage.updateUI();
                        break;
                    }
                }
                updateUI();
                lastPage.updateUI();
                configuration.save(file);
                ServerDownloader.frame.setVisible(true);
                SwingUtilities.updateComponentTreeUI(ServerDownloader.frame);
                SwingUtilities.updateComponentTreeUI(lastPage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        add(jComboBox);
    }
}
