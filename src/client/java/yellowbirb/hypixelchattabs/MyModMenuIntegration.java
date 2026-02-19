package yellowbirb.hypixelchattabs;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import yellowbirb.hypixelchattabs.config.MyConfigScreen;

public class MyModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> MyConfigScreen.create(parent);
    }
}
