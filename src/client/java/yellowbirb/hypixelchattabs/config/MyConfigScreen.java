package yellowbirb.hypixelchattabs.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

public class MyConfigScreen {

    public static Screen create(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Hypixel Chat Tabs Settings"));

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(
                Text.literal("Enabled"),
                MyConfigManager.CONFIG.enabled)
                .setSaveConsumer(value -> MyConfigManager.CONFIG.enabled = value)
                .build());

        general.addEntry(entryBuilder.startIntSlider(
                Text.literal("Radius"),
                MyConfigManager.CONFIG.radius,
                1, 20)
                .setSaveConsumer(value -> MyConfigManager.CONFIG.radius = value)
                .build());

        // Save to JSON when the menu is closed
        builder.setSavingRunnable(MyConfigManager::save);

        return builder.build();
    }
}