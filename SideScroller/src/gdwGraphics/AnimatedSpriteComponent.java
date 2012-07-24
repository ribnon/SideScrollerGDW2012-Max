package gdwGraphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import gdw.entityCore.ComponentTemplate;
import gdwGraphics.SpriteComponent;
/**
 * Animated Sprite Component
 * for animated sprites
 * 
 * @author max
 *
 */
public class AnimatedSpriteComponent extends SpriteComponent {
	/**
	 * Sprite sheet where animation frames are read from. Frames of an animation are stored in row.
	 * Multiple animations can be stored beneath each other in the spritesheet. 
	 */
	private SpriteSheet spriteSheet;
	
	/**
	 * number of Frames in per column of sprites in the spritesheet.
	 */
	private int [] cycleLength;
	
	/**
	 * current column (animation)
	 */
	private int cycle;
	
	/**
	 * current frame
	 */
	private int step;
	
	public SpriteSheet getSpriteSheet()
	{
		return spriteSheet;
	}
	
	public void setSpriteSheet(SpriteSheet s)
	{
		spriteSheet = s;
	}
	
	public int[] getCycleLength()
	{
		return cycleLength;
	}
	
	public void setCycleLength(int [] c)
	{
		cycleLength = c;
	}
	
	public int getStep()
	{
		return step;
	}
	
	public void setStep(int s)
	{
		step = s;
	}
	
	AnimatedSpriteComponent(ComponentTemplate template)
	{
		super(template);
		try
		{
			spriteSheet = new SpriteSheet(template.getStringParam("Path"), template.getIntegerParam("TileWidth"), template.getIntegerParam("TileHeight"));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cycleLength = new int[spriteSheet.getVerticalCount()];
		for(int i = 0; i < cycleLength.length; ++i)
			cycleLength[i] = spriteSheet.getHorizontalCount(); // TODO detect unused slots in spritesheet
		cycle = template.getIntegerParam("Cycle");
	}
	
	/**
	 *  draws the current frame of the animation
	 */
	public void draw()
	{
		//TODO: verify this is correct, image might have to be drawn with an offset to be centered at the entity
		Image img = spriteSheet.getSprite(step, cycle);
		img.setCenterOfRotation(getPivotX(), getPivotY());
		//img.rotate(getOwner().getOrientation());
		if(getFilter() != null)
			img.draw(getOwner().getPosX(), getOwner().getPosY(), getScale(), getFilter());
		else
			img.draw(getOwner().getPosX(), getOwner().getPosY(), getScale());
	}
	
	/**
	 * Advances the animation by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 */
	public void tick(float deltaTime)
	{
		step++;
		step %= cycleLength[cycle]; //loop back to frame 0 
	}
	
	/**
	 * sets cycle back to 0
	 */
	public void resetCycle()
	{
		cycle = 0;
	}
	
	/**
	 * destroys the slick spritesheet
	 */
	protected void destroy()
	{
		try {
			spriteSheet.destroy();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("destroying spritesheet failed :-(");
		}
	}

}
