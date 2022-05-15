package de.florianmichael.mcparsing;

import java.util.Map;

public class Instrumentation {

    private final String fileName;
    private final Action action;

    private Map<String, Object> data;

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
