package display;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class MusicPlayer {
	private static AudioInputStream audioIn;
	private static Clip clip;
	private static String currentMusic = null;

	public static void playSoundEffect(String file) {
		try {
		AudioInputStream tempAudioIn = AudioSystem.getAudioInputStream(new File(file));
		Clip tempClip = AudioSystem.getClip();
		tempClip.open(tempAudioIn);
		tempClip.setFramePosition(0);
		tempClip.loop(0);
		tempClip.start();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.err.println("Unable to find music file " + file);
		}
	}

	private static void loopSound(String file)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioIn = AudioSystem.getAudioInputStream(new File(file));
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void changePlayingMusic(String newMusic) {
		if (newMusic.equals(currentMusic)) {
			return;
		} else {
			try {
				if (clip != null)
					clip.stop();
				loopSound(newMusic);
				currentMusic = newMusic;
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
				System.err.println("Error playing music");
			}
		}
	}

	/** @return is music playing or not */
	public static boolean isMusicPlaying() {
		if (clip != null) {
			return true;
		}
		return false;
	}

	/**
	 * * Get the currently playing music. If no music is playing, returns
	 * null. @return the currently playing music
	 */
	public String getPlayingMusic() {
		return currentMusic;
	}

	/** MusicPlayer may not be instantiated. */
	private MusicPlayer() {
	}
}