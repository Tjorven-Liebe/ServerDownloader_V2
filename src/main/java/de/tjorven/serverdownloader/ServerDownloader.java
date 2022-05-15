package de.tjorven.serverdownloader;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlIJTheme;
import de.tjorven.serverdownloader.page.StartPage;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ServerDownloader extends JFrame {

    @Getter
    @Setter
    public static JFrame frame;

    public ServerDownloader() {
        setVisible(true);
        setSize(500, 600);
        setTitle("ServerDownloader V2");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/spigot-og.png")));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame = this;
        new StartPage();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        FlatNightOwlIJTheme.setup();
        try {
            UIManager.setLookAndFeel(new FlatNightOwlIJTheme());
        } catch (UnsupportedLookAndFeelException ignored) {
        }
        new ServerDownloader();
    }

}
