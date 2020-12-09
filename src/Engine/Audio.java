package Engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Audio {
	protected static AudioInputStream audio;
	protected static Clip clip;

	public static void playMusic(String fileName) {
		
		try {
			audio = AudioSystem.getAudioInputStream(new File(fileName));
		 clip = AudioSystem.getClip();
		clip.open(audio);
		clip.start();
		//needs to be fixed so that it stops when player dies
//		clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException error) {
			error.printStackTrace();
		}

	}
	public static void stopMusic() {

		clip.close();
		clip.stop();

	}

}
