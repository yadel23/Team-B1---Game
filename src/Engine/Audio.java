package Engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	public static void playMusic(String fileName) {
		
		try {
		AudioInputStream audio = AudioSystem.getAudioInputStream(new File(fileName));
		Clip clip = AudioSystem.getClip();
		clip.open(audio);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException error) {
			error.printStackTrace();
		}

	}
}
