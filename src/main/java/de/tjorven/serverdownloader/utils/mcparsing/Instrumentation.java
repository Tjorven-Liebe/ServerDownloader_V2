package de.tjorven.serverdownloader.utils.mcparsing;

import java.util.Map;

public class Instrumentation {

    public final String fileName;
    public final Action action;

    public Map<String, Object> data;

    public Instrumentation(final String fileName, final Action action) {
        this.fileName = fileName;
        this.action = action;
    }

    public void data(final Map<String, Object> values) {
        this.data = values;
    }

    enum Action {
        OVERWRITE_COMPLETE,
        CHANGE
    }

}
