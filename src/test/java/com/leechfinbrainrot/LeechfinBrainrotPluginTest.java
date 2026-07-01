package com.leechfinbrainrot;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class LeechfinBrainrotPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(LeechfinBrainrotPlugin.class);
		RuneLite.main(args);
	}
}