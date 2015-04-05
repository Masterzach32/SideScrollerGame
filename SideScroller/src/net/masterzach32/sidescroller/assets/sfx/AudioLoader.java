package net.masterzach32.sidescroller.assets.sfx;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.masterzach32.sidescroller.util.LogHelper;

public class AudioLoader {
	
	private String s = "[SFX] ";

	/**
	 * Loads the given audio file
	 * @param path
	 * @return ais
	 */
	public AudioInputStream load(String path) {
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			LogHelper.logInfo(s + "Loaded asset: " + path);
			return ais;
		} catch (UnsupportedAudioFileException e) {
			LogHelper.logError(s + "Unsupported Audio File: " + path);
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			LogHelper.logError(s + "Missing Audio File: " + path);
			e.printStackTrace();
			return null;
		}
	}
}
