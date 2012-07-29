package gdw.astroids.sound;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundPlayer
{
	private static SoundPlayer soundPlayer;
	
	public static final int SOUND_SHOOT = 0;
	public static final int SOUND_EXPLOSION  = 1;
	
	private ArrayList<Sound> sounds;
	
	private SoundPlayer()
	{
		String basePath = "astroids/assets/";
		String[] soundFiles = new String[] { "piu.wav", "bbwwww.wav" };
		
		Sound newSound = null;
		
		sounds = new ArrayList<Sound>();
		
		for (String s : soundFiles)
		{
			try
			{
				newSound = new Sound(basePath + s);
			} catch (SlickException e)
			{
			}
			
			if (newSound != null) sounds.add(newSound);
		}
	}
	
	public static SoundPlayer getInstance()
	{
		if (soundPlayer == null) soundPlayer = new SoundPlayer();
		return soundPlayer;
	}
	
	public void playSound(int i)
	{
		Sound sound = sounds.get(i);
		
//		if (sound != null) sound.play(Math.max(0.3f, (float)Math.random()-0.2f),1.0f);
	}
}
