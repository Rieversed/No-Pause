package com.lambda.nopause;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class NoPauseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the keybinding check
        ClientTickEvents.END_CLIENT_TICK.register(client -> NoPause.checkKeybinding());

        // Register the HUD renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            NoPauseHud.render(drawContext);
        });
    }
}