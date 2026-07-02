package com.leechfinbrainrot;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import javax.sound.sampled.*;
import java.io.InputStream;
import net.runelite.api.events.StatChanged;

@Slf4j
@PluginDescriptor(
		name = "Leechfin Brainrot"
)
public class LeechfinBrainrotPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private LeechfinBrainrotConfig config;

	@Override
	protected void startUp()
	{
		log.info("Leechfin Brainrot started");
	}

	@Override
	protected void shutDown()
	{
		log.info("Leechfin Brainrot stopped");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			log.info("Leechfin Brainrot ready");
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		if (event.getSkill() != Skill.FISHING)
		{
			return;
		}

		onLeechfinCatch();
	}

	private void onLeechfinCatch()
	{
		playGoodSound();
	}

	private void playGoodSound()
	{
		try
		{
			InputStream audioSrc = getClass().getResourceAsStream("/sounds/goodsound.wav");
			if (audioSrc == null)
			{
				log.warn("Sound file not found");
				return;
			}

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioSrc);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);

			applyVolume(clip);

			clip.start();
		}
		catch (Exception e)
		{
			log.warn("Failed to play sound", e);
		}
	}

	private void applyVolume(Clip clip)
	{
		try
		{
			if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN))
			{
				return;
			}

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			int volume = config.volume(); // 0 - 100

			float min = gainControl.getMinimum();
			float max = gainControl.getMaximum();

			float gain = (max - min) * (volume / 100f) + min;

			gainControl.setValue(gain);
		}
		catch (Exception e)
		{
			log.warn("Failed to apply volume", e);
		}
	}

	@Provides
	LeechfinBrainrotConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LeechfinBrainrotConfig.class);
	}
}