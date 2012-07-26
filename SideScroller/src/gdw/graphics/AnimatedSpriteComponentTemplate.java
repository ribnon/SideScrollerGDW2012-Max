package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class AnimatedSpriteComponentTemplate extends ComponentTemplate
 {
	private SpriteSheet spriteSheet;
	private int[] cycleLength;
	private int cycle;
	private int step;

	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	public int[] getCycleLength() {
		return cycleLength;
	}

	public int getCycle() {
		return cycle;
	}

	public int getStep() {
		return step;
	}
	
	public AnimatedSpriteComponentTemplate(HashMap<String, String> params)
	{
		super(params);
		try
		{
			spriteSheet = new SpriteSheet(getStringParam("path", ""), getIntegerParam("tileWidth", 64), getIntegerParam("tileHeight", 64));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cycleLength = new int[spriteSheet.getVerticalCount()];
		for(int i = 0; i < cycleLength.length; ++i)
			cycleLength[i] = spriteSheet.getHorizontalCount(); // TODO detect unused slots in spritesheet
		cycle = getIntegerParam("cycle", 0);
		step = getIntegerParam("step", 0);
	}
	
	public Component createComponent()
	{
		return new AnimatedSpriteComponent(this);
	}
}
