package gdwGraphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

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
	
	AnimatedSpriteComponent(ComponentTemplate ct)
	{
		
	}
	
	/**
	 *  draws the step-th frame of the animation.
	 */
	public void draw()
	{
		//TODO: verify this is correct
		Image img = spriteSheet.getSprite(step, 0);
		img.setCenterOfRotation(getPivotX(), getPivotY());
		//img.setRotation(owner.getOrientation());
		img.rotate(getOwner().getOrientation());
		img.draw(getOwner().getPosx(), getOwner().getPosy());
	}
	
	/**
	 * Advances the animation by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 */
	public void tick(float deltaTime)
	{
		step++;
		step %= cycleLength[cycle];
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
