package com.lambda.nopause;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NoPauseHud {
    private static final Identifier ICON_ENABLED = new Identifier("nopause", "textures/enabled.png");
    private static final Identifier ICON_DISABLED = new Identifier("nopause", "textures/disabled.png");

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        int x = 10; // X position of the HUD element
        int y = 10; // Y position of the HUD element

        Identifier icon = NoPause.isEnabled() ? ICON_ENABLED : ICON_DISABLED;
        context.drawTexture(icon, x, y, 0, 0, 16, 16, 16, 16);

        String statusText = NoPause.isEnabled() ? "Enabled" : "Disabled";
        context.drawText(client.textRenderer, Text.literal(statusText), x + 20, y + 4, 0xFFFFFF, true);
    }
}