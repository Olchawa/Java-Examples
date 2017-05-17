package sounds;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceLoader {
	
	public static ResourceLoader ResLoader = new ResourceLoader();
	
	
	
	public static void playSoundEffect(String soundToPlay) {

		// Pointer towards the resource to play
		URL soundLocation;

		try {

			soundLocation = new URL("file:./res/sounds"+soundToPlay);
			// Stores a predefined audio clip
			Clip clip = null;
			// Convert audio data to different playable formats
			clip = AudioSystem.getClip();
			// Holds a stream of a definite length
			AudioInputStream inputStream;
			inputStream = AudioSystem.getAudioInputStream(soundLocation);
			// Make audio clip available for play
			clip.open(inputStream);
			// Define how many times to loop
			clip.loop(0);
			// Play the clip
			clip.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
