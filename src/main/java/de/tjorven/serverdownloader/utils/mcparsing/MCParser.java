package de.tjorven.serverdownloader.utils.mcparsing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.tjorven.serverdownloader.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCParser {

    public final static Gson gson = new Gson();

    public static List<Instrumentation> instrumentations = new ArrayList<>();

    public static void parse() {
        final JsonObject theObject = gson.fromJson(Util.jsonFromFile(Util.downloadServer + "/" + Util.versionSelected + "/data.json"), JsonObject.class);
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : theObject.entrySet()) {
            if (stringJsonElementEntry.getValue().isJsonObject())
                for (Map.Entry<String, JsonElement> jsonElementEntry : stringJsonElementEntry.getValue().getAsJsonObject().entrySet()) {
                    if (jsonElementEntry.getKey().equals("completeOverwrite")) {
                        final Instrumentation instrumentation = new Instrumentation(stringJsonElementEntry.getKey(), Instrumentation.Action.OVERWRITE_COMPLETE);
                        final HashMap<String, Object> data = new HashMap<>();
                        data.put("text", jsonElementEntry.getValue().getAsString());
                        instrumentation.data(data);
                        instrumentations.add(instrumentation);
                    } else {
                        final String propertyName = jsonElementEntry.getKey().substring("property.".length());
                        final Instrumentation instrumentation = new Instrumentation(stringJsonElementEntry.getKey(), Instrumentation.Action.CHANGE);
                        final HashMap<String, Object> data = new HashMap<>();
                        data.put(propertyName, jsonElementEntry.getValue().getAsString());
                        instrumentation.data(data);
                        instrumentations.add(instrumentation);
                    }
                }
        }
    }
}
