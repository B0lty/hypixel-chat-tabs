package yellowbirb.hypixelchattabs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir().resolve("hypixel-chat-tabs.json");

    public static MyConfig CONFIG = new MyConfig();

    // Load config from file or create defaults
    public static void load() {
        try {
            if (Files.exists(FILE)) {
                CONFIG = GSON.fromJson(Files.readString(FILE), MyConfig.class);
            } else {
                save(); // create file with defaults
            }
        } catch (Exception e) {
            System.err.println("Failed to load config");
            e.printStackTrace();
        }
    }

    // Save current config to JSON
    public static void save() {
        try {
            Files.writeString(FILE, GSON.toJson(CONFIG));
        } catch (IOException e) {
            System.err.println("Failed to save config");
            e.printStackTrace();
        }
    }
}