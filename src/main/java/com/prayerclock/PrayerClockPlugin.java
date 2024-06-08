package com.prayerclock;

import com.google.inject.Inject;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "Prayer Clock",
		description = "Counts the time since a protection prayer was activated",
		tags = {"prayer", "counter", "overlay"}
)
public class PrayerClockPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ClockOverlay overlay;

	private boolean previousProtectFromMagic;
	private boolean previousProtectFromMissiles;
	private boolean previousProtectFromMelee;

	@Provides
	PrayerClockConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(PrayerClockConfig.class);
	}

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameTick(GameTick tick) {
		boolean currentProtectFromMagic = client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC);
		boolean currentProtectFromMissiles = client.isPrayerActive(Prayer.PROTECT_FROM_MISSILES);
		boolean currentProtectFromMelee = client.isPrayerActive(Prayer.PROTECT_FROM_MELEE);


		if (currentProtectFromMagic != previousProtectFromMagic ||
				currentProtectFromMissiles != previousProtectFromMissiles ||
				currentProtectFromMelee != previousProtectFromMelee) {

			overlay.resetTicks();
			resetPrayerState();
		}

		if (currentProtectFromMagic || currentProtectFromMissiles || currentProtectFromMelee) {
			overlay.incrementTicks();
		}
	}

	private void resetPrayerState() {
		previousProtectFromMagic = client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC);
		previousProtectFromMissiles = client.isPrayerActive(Prayer.PROTECT_FROM_MISSILES);
		previousProtectFromMelee = client.isPrayerActive(Prayer.PROTECT_FROM_MELEE);
	}
}