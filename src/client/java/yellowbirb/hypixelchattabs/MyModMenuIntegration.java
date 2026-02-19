package yellowbirb.hypixelchattabs;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import yellowbirb.hypixelchattabs.config.MyConfigScreen;

public class MyModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MyConfigScreen.create(parent);
    }
}