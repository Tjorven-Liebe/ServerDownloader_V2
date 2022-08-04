package de.tjorven.serverdownloader.utils;

import de.tjorven.serverdownloader.ServerDownloader;

import javax.swing.*;
import java.awt.*;

public class SDPanel extends JPanel {

    String pageTitle;
    SDPanel lastPage;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public SDPanel(String pageTitle, SDPanel lastPage) {
        if (lastPage != null) {
            ServerDownloader.frame.remove(lastPage);
            JButton button = new JButton();
            button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/circle-left-solid.png"))));
            button.setBackground(null);
            button.setForeground(null);
            button.setBorder(new TextBubbleBorder(null, 0, 50, 0));
            button.addActionListener(event -> {
                ServerDownloader.frame.remove(this);
                ServerDownloader.frame.add(lastPage);
                updateUI();
                lastPage.updateUI();
            });
            button.setBounds(20, 25, 50, 50);
            add(button);
        }
        this.pageTitle = pageTitle;
        this.lastPage = lastPage;
        setLayout(null);
        setSize(new Dimension(500, 600));
        JLabel label = new JLabel(pageTitle, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
//        label.getGraphics().getFontMetrics().stringWidth(label.getText());
        label.setBounds(-10, 5, 500, 100);
        add(label);
        ServerDownloader.frame.add(this);
        updateUI();
    }

}
