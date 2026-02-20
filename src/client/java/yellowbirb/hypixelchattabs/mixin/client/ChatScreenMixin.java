package yellowbirb.hypixelchattabs.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yellowbirb.hypixelchattabs.HypixelChatTabsClient;
import yellowbirb.hypixelchattabs.HypixelChatTabsClient.Tab;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

    @Shadow protected TextFieldWidget chatField;

    @Unique
    private final List<ButtonWidget> tabButtons = new ArrayList<>();

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ChatHud hud = client.inGameHud.getChatHud();

        tabButtons.clear();

        int y = this.height - 28; // clean position just above chat input

        for (Tab chatTab : Tab.values()) {

            String label = switch (chatTab) {
                case ALL -> "A";
                case PARTY -> "P";
                case GUILD -> "G";
                case PRIVATE -> "PM";
                case COOP -> "CC";
            };

            ButtonWidget button = ButtonWidget.builder(Text.literal(label), btn -> {
                HypixelChatTabsClient.tab = chatTab;
                hud.reset();
                client.send(() -> setFocused(chatField));
            }).dimensions(
                    5 + chatTab.ordinal() * 24,
                    y,
                    22,
                    20
            ).build();

            tabButtons.add(button);        // ✅ IMPORTANT
            addDrawableChild(button);
        }
    }

    @Inject(method = "resize", at = @At("TAIL"))
    private void onResize(MinecraftClient client, int width, int height, CallbackInfo ci) {
        int y = this.height - 28;

        for (int i = 0; i < tabButtons.size(); i++) {
            ButtonWidget button = tabButtons.get(i);
            button.setX(5 + i * 24);
            button.setY(y);
        }
    }
}