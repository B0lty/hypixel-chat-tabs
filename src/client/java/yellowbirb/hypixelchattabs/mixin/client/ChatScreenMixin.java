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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yellowbirb.hypixelchattabs.HypixelChatTabsClient;
import yellowbirb.hypixelchattabs.HypixelChatTabsClient.Tab;
import yellowbirb.hypixelchattabs.config.MyConfigManager;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

    @Shadow protected TextFieldWidget chatField;

    // Keep a reference to the tab buttons
    private final List<ButtonWidget> tabButtons = new ArrayList<>();

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    // Add buttons after init
    @Inject(at = @At("TAIL"), method = "init")
    private void onInit(CallbackInfoReturnable<Void> ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ChatHud hud = client.inGameHud.getChatHud();

        // Clear previous buttons (if any)
        tabButtons.clear();

        // Create buttons
        for (Tab chatTab : Tab.values()) {
            String message = switch (chatTab) {
                case ALL -> "A";
                case PARTY -> "P";
                case GUILD -> "G";
                case PRIVATE -> "PM";
                case COOP -> "CC";
            };

            ButtonWidget tabButton = ButtonWidget.builder(Text.literal(message), btn -> {
                HypixelChatTabsClient.tab = chatTab;
                hud.reset();
                client.send(() -> setFocused(chatField));
            }).dimensions(0, 0, 20, 20).build(); // x/y will be updated dynamically

            tabButtons.add(tabButton);
            addDrawableChild(tabButton);
        }

        // Position buttons for the first time
        updateTabButtonPositions();
    }

    // Called automatically after screen size changes
    @Inject(at = @At("TAIL"), method = "resize(Lnet/minecraft/client/MinecraftClient;II)V")
    private void onResize(MinecraftClient client, int width, int height, CallbackInfoReturnable<Screen> cir) {
        updateTabButtonPositions();
    }

    // Update button positions based on screen size and config radius
    private void updateTabButtonPositions() {
        MinecraftClient client = MinecraftClient.getInstance();
        ChatHud hud = client.inGameHud.getChatHud();

        for (int i = 0; i < tabButtons.size(); i++) {
            ButtonWidget button = tabButtons.get(i);
            int x = 5 + i * 22; // horizontal spacing
            int y = this.height - hud.getHeight() - MyConfigManager.CONFIG.radius - 20; // dynamic vertical position
            button.setX(x);
            button.setY(y);
        }
    }
}
