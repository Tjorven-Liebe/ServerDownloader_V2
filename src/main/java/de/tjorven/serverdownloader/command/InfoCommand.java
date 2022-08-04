package de.tjorven.serverdownloader.command;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.tjorven.serverdownloader.utils.Util;
import de.tjorven.serverdownloader.utils.command.ICommandExecutor;
import de.tjorven.serverdownloader.utils.logger.Logger;

import java.util.ArrayList;
import java.util.Map;

public class InfoCommand implements ICommandExecutor {

    @Override
    public void onCommand(String label, String[] args) {
        if (args.length == 0) {
            Logger.getLogger().info("Showing info from default downloadserver..", "you can use following files as download file:");
            Util.downloadServer = "http://37.114.47.90/files/serverfiles";
        } else if (args.length == 1) {
            Logger.getLogger().info("Showing info from downloadserver " + args[0], "you can use following files as download file:");
            Util.downloadServer = args[0];
        }
        Logger.getLogger().info(getFiles());
    }

    public String[] getFiles() {
        Gson gson = new Gson();
        ArrayList<String> files = new ArrayList<>();
        JsonObject jsonObject = gson.fromJson(Util.jsonFromFile(Util.downloadServer + "/versions.json"), JsonObject.class);
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            JsonArray arr = stringJsonElementEntry.getValue().getAsJsonArray();
            for (int i = 0; i < arr.size(); i++) {
                String jsonElement = arr.get(i).getAsString();
                JsonObject nameObject = gson.fromJson(Util.jsonFromFile(Util.downloadServer + "/" + jsonElement + "/data.json"), JsonObject.class);
                files.add(nameObject.get("version-name").getAsString());
            }
        }
        return files.toArray(new String[0]);
    }

}
