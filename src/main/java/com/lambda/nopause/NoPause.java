package com.lambda.nopause;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoPause implements ModInitializer {
	public static final String MOD_ID = "nopause";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	private static boolean enabled = true;
	private static KeyBinding toggleKey;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean value, boolean showMessage) {
		enabled = value;
		if (showMessage) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client != null && client.player != null) {
				client.player.sendMessage(
					Text.literal("§7[§bNoPause§7] §f" + (enabled ? "Enabled" : "Disabled")),
					false
				);
			}
		}
	}

	@Override
	public void onInitialize() {
		// Register keybinding
		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.nopause.toggle",
			InputUtil.Type.KEYSYM,
			InputUtil.GLFW_KEY_N, // N key
			"category.nopause.general"
		));

		// Register client-side command
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
				ClientCommandManager.literal("nopause")
					.then(ClientCommandManager.literal("enable")
						.executes(context -> {
							setEnabled(true, true);
							return 1;
						})
					)
					.then(ClientCommandManager.literal("disable")
						.executes(context -> {
							setEnabled(false, true);
							return 1;
						})
					)
			);
		});

		LOGGER.info("NoPause mod initialized!");
	}

	// Call this method every tick to check for keybinding
	public static void checkKeybinding() {
		while (toggleKey.wasPressed()) {
			setEnabled(!enabled, true);
		}
	}
}