package gdw.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import gdw.entityCore.ComponentTemplate;
import gdw.graphics.SpriteComponent;
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
	
	/* not in uml-diagram
	public void setStep(int s)
	{
		step = s;
	}
	*/
	
	public AnimatedSpriteComponent(ComponentTemplate template)
	{
		super(template);
		AnimatedSpriteComponentTemplate t = (AnimatedSpriteComponentTemplate) template;
		
		spriteSheet = t.getSpriteSheet();
		cycleLength = t.getCycleLength();
		cycle = t.getCycle();
		step = t.getStep();
		
		SpriteManager.getInstance().addSprite(this);
	}
	
	
	/**
	 *  draws the current frame of the animation
	 */
	public void draw(float camPosX,float camPosY)
	{
		//TODO: verify this is correct, image might have to be drawn with an offset to be centered at the entity
		Image img = spriteSheet.getSprite(step, cycle);
		if(getFlipped())
			img = img.getFlippedCopy(false, true); //TODO: 
		img.setCenterOfRotation(getPivotX(), getPivotY());
		img.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));
		if(getFilter() != null)
			img.draw(getOwner().getPosX()-camPosX, getOwner().getPosY()-camPosY, getScale(), getFilter());
		else
			img.draw(getOwner().getPosX()-camPosX, getOwner().getPosY()-camPosY, getScale());
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
	 * sets step back to 0
	 */
	public void resetCycle()
	{
		step = 0;
	}
	
	/**
	 * destroys the slick spritesheet
	 */
	protected void destroy()
	{
		SpriteManager.getInstance().removeSprite(this);
		
		try {
			spriteSheet.destroy();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("destroying spritesheet failed :-(");
		}
	}

}
