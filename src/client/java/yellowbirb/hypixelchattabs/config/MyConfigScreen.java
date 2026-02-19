package yellowbirb.hypixelchattabs.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class MyConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Hypixel Chat Tabs Config"))
                .setSavingRunnable(MyConfigManager::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enable Tabs"), MyConfigManager.CONFIG.enabled)
                .setSaveConsumer(value -> MyConfigManager.CONFIG.enabled = value)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Text.literal("Tab Radius"), MyConfigManager.CONFIG.radius, 0, 100)
                .setSaveConsumer(value -> MyConfigManager.CONFIG.radius = value)
                .build());

        return builder.build();
    }

}
