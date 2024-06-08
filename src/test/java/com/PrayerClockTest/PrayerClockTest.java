package com.PrayerClockTest;

import com.PrayerClock.PrayerClockPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PrayerClockTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PrayerClockPlugin.class);
		RuneLite.main(args);
	}
}