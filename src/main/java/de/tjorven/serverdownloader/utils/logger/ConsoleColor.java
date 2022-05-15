package de.tjorven.serverdownloader.utils.logger;

public class ConsoleColor {

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String DARK_RED = "\033[0;31m";     // RED
    public static final String DARK_GREEN = "\033[0;32m";   // GREEN
    public static final String GOLD = "\033[0;33m";  // YELLOW
    public static final String DARK_BLUE = "\033[0;34m";    // BLUE
    public static final String DARK_PURPLE = "\033[0;35m";  // PURPLE
    public static final String AQUA = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String GRAY = "\033[0;97m";       // GRAY
    public static final String DARK_GRAY = "\033[0;90m";  // DARK_GRAY
    public static final String GREEN = "\033[0;92m";
    public static final String BLUE = "\033[0;94m";
    public static final String RED = "\033[0;31m";
    public static final String PURPLE = "\033[0;95m";
    public static final String YELLOW = "\033[0;93m";

    public static String fromTag(String string) {
        return string
                .replace("§r", RESET)
                .replace("§f", WHITE)
                .replace("§0", BLACK)
                .replace("§1", DARK_BLUE)
                .replace("§2", DARK_GREEN)
                .replace("§3", AQUA)
                .replace("§4", DARK_RED)
                .replace("§5", DARK_PURPLE)
                .replace("§6", GOLD)
                .replace("§7", RESET)
                .replace("§8", DARK_GRAY)
                .replace("§9", GRAY)
                .replace("§a", GREEN)
                .replace("§b", BLUE)
                .replace("§c", RED)
                .replace("§d", PURPLE)
                .replace("§e", YELLOW);

    }
}