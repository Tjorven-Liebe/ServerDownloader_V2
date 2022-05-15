package de.tjorven.serverdownloader.page;

import de.tjorven.serverdownloader.ServerDownloader;
import de.tjorven.serverdownloader.utils.SDPanel;
import de.tjorven.serverdownloader.utils.TextBubbleBorder;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EndPage extends SDPanel {
    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     *
     * @param lastPage
     */
    public EndPage(SDPanel lastPage) {
        super("<html>EndPage<br/><hr></html>", lastPage);
        JTextField installationField = new JTextField(System.getProperty("user.dir"));
        JButton chooseInstallationFolder = new JButton("Choose");
        chooseInstallationFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                jFileChooser.setDialogTitle("Choose an installation folder");
                int returnVal = jFileChooser.showOpenDialog(installationField);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    installationField.setText(jFileChooser.getSelectedFile().toString());
                }
            }
        });
        installationField.setBounds(40, 100, 300, 50);
        add(installationField);
        chooseInstallationFolder.setBounds(350, 100, 100, 50);
        add(chooseInstallationFolder);
        JButton accept = new JButton("Accept");
        accept.setSize(200, 100);
        accept.setBounds(
                ServerDownloader.frame.getWidth() / 2 - (200 / 2),
                ServerDownloader.frame.getHeight() / 2 - (100 / 2), 200, 100);
        accept.addActionListener(event -> {
            this.removeAll();
            Util.generate(installationField.getText());
            JLabel label = new JLabel("<html><center>Thank you for using!<br/>You can close this window now..</center></html>", SwingConstants.CENTER);
            label.setBorder(new TextBubbleBorder(Color.BLACK, 5, 50, 0));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 25));
            label.setBounds(
                    ServerDownloader.frame.getWidth() / 2 - (300 / 2),
                    ServerDownloader.frame.getHeight() / 2 - (300 / 2), 300, 300);
            add(label);
            updateUI();

        });
        add(accept);
        updateUI();
        Logger.getLogger().info("EndPage loaded");
    }
}
