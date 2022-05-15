package de.florianmichael.mcparsing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCParser {

    public final static Gson gson = new Gson();

    public static void main(String[] args) {
        final String input = "{\n" +
                "\t\"eula.txt\": {\n" +
                "\t\t\"completeOverwrite\": \"eula=true\"\n" +
                "\t},\n" +
                "\t\"server.properties\": {\n" +
                "\t\t\"property.onlinemode\": true\n" +
                "\t}\n" +
                "}\n";

        final List<Instrumentation> instructions = new ArrayList<>();

        final JsonObject theObject = gson.fromJson(input, JsonObject.class);

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : theObject.entrySet()) {
            for (Map.Entry<String, JsonElement> jsonElementEntry : stringJsonElementEntry.getValue().getAsJsonObject().entrySet()) {
                if (jsonElementEntry.getKey().equals("completeOverwrite")) {
                    final Instrumentation instrumentation = new Instrumentation(stringJsonElementEntry.getKey(), Instrumentation.Action.OVERWRITE_COMPLETE);

                    final HashMap<String, Object> data = new HashMap<>();

                    data.put("text", jsonElementEntry.getValue().getAsString());

                    instrumentation.data(data);
                } else {
                    final String propertyName = jsonElementEntry.getKey().substring("property.".length());

                    final Instrumentation instrumentation = new Instrumentation(stringJsonElementEntry.getKey(), Instrumentation.Action.CHANGE);

                    final HashMap<String, Object> data = new HashMap<>();

                    data.put(propertyName, jsonElementEntry.getValue().getAsString());

                    instrumentation.data(data);
                }
            }
        }
    }
}
