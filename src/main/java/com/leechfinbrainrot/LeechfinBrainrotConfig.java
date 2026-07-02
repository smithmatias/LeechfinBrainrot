package com.leechfinbrainrot;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("leechfinbrainrot")
public interface LeechfinBrainrotConfig extends Config
{
	@Range(
			max = 100,
			min =  0
	)
	@ConfigItem(
			keyName = "volume",
			name = "Volume",
			description = "Sound volume (0-100)"
	)
	default int volume()
	{
		return 100;
	}
}