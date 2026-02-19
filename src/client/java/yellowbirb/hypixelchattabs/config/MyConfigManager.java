package yellowbirb.hypixelchattabs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyConfigManager {

    private static final File FILE = new File("config/hypixel-chat-tabs.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static MyConfig CONFIG = new MyConfig();

    // Load config from file
    public static void load() {
        try {
            if (FILE.exists()) {
                CONFIG = GSON.fromJson(Files.readString(FILE.toPath()), MyConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save config to file
    public static void save() {
        try {
            FILE.getParentFile().mkdirs();
            Files.writeString(FILE.toPath(), GSON.toJson(CONFIG));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
