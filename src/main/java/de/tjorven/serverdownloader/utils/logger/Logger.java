package de.tjorven.serverdownloader.utils.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    /***
     * Logs Text to console
     * Each arg will end in new line ex.
     * info ("First line", "Second line");
     * info ("Just output one line);
     * @since 1.0 | 17.09.2021
     */

    private static String date;

    public Logger() {
        date = ConsoleColor.fromTag("§a") + new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static Logger getLogger() {
        return new Logger();
    }

    @Deprecated
    public void raw(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §7" + toLog));
    }

    public void object(Object... objects) {
        for (Object object : objects)
            System.out.println(ConsoleColor.fromTag(date + " §r[§3OBJECT§r] §r" + object.toString()));
    }

    public void info(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §r[§2INFO§r] §r" + toLog));
    }

    public void warn(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §r[§eWARN§r] §r" + toLog));
    }

    public void error(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §r[§4ERROR§r] §c" + toLog));
    }

    public void error(Throwable... throwable) {
        for (Throwable exceptionMessage : throwable) {
            error("" + exceptionMessage.getClass().getName());
            error("" + exceptionMessage.getMessage());
            for (StackTraceElement element : exceptionMessage.getStackTrace()) {
                error(element.toString());
            }

        }
    }

    public void error(Exception... exception) {
        for (Exception exceptionMessage : exception) {
            error("" + exceptionMessage.getClass().getName());
            error("" + exceptionMessage.getMessage());
            for (StackTraceElement element : exceptionMessage.getStackTrace()) {
                error(element.toString());
            }
        }
    }

    public void alert(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §r[§3ALERT§r] §r" + toLog));
    }

    public void emergency(String... string) {
        for (String toLog : string)
            System.out.println(ConsoleColor.fromTag(date + " §r[§5EMERGENCY§r] §r" + toLog));

    }

}