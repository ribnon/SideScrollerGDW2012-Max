package gdw.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Path.Step;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Component;
import gdw.graphics.SpriteComponent;

/**
 * 
 * @author Dustin
 * 
 */
public class OverlayedAnimatedSpriteComponent extends SpriteComponent
{
	/**
	 * zwei Spritesheets die später übereinander gezeichnet werden
	 */
	private SpriteSheet baseSpritesheet;
	private SpriteSheet overlaySpritesheet1;
	private SpriteSheet overlaySpritesheet2;

	/**
	 * Color-Filter für den Overlay-Spritesheet
	 */
	private Color overlayColor1;
	private Color overlayColor2;
	/**
	 * number of Frames in per column of sprites in the spritesheets.
	 */
	private int[] baseCycleLength;
	private int[] overlayCycleLength1;
	private int[] overlayCycleLength2;
	/**
	 * current column (animation)
	 */
	private int baseCycle;
	private int overlayCycle1;
	private int overlayCycle2;
	/**
	 * current frame
	 */
	private int baseStep = 0;
	private int overlayStep1 = 0;
	private int overlayStep2 = 0;

	public SpriteSheet getImage()
	{
		return baseSpritesheet;
	}
	
	public SpriteSheet getBaseSpritesheet()
	{
		return baseSpritesheet;
	}

	public void setBaseSpritesheet(SpriteSheet baseSpritesheet)
	{
		this.baseSpritesheet = baseSpritesheet;
	}

	public SpriteSheet getOverlaySpritesheet1()
	{
		return overlaySpritesheet1;
	}
	
	public SpriteSheet getOverlaySpritesheet2()
	{
		return overlaySpritesheet2;
	}

	public void setOverlaySpritesheet1(SpriteSheet overlaySpritesheet)
	{
		this.overlaySpritesheet1 = overlaySpritesheet;
	}

	public void setOverlaySpritesheet2(SpriteSheet overlaySpritesheet)
	{
		this.overlaySpritesheet2 = overlaySpritesheet;
	}
	
	public Color getOverlayColor1()
	{
		return overlayColor1;
	}
	
	public Color getOverlayColor2()
	{
		return overlayColor2;
	}

	public void setOverlayColor1(Color overlayColor)
	{
		this.overlayColor1 = overlayColor;
	}
	
	public void setOverlayColor2(Color overlayColor)
	{
		this.overlayColor2 = overlayColor;
	}

	public int[] getBaseCycleLength()
	{
		return baseCycleLength;
	}

	public void setBaseCycleLength(int[] baseCycleLength)
	{
		this.baseCycleLength = baseCycleLength;
	}

	public int[] getOverlayCycle1Length()
	{
		return overlayCycleLength1;
	}
	
	public int[] getOverlayCycle2Length()
	{
		return overlayCycleLength2;
	}

	public void setOverlayCycle1Length(int[] overlayCycleLength)
	{
		this.overlayCycleLength1 = overlayCycleLength;
	}
	
	public void setOverlayCycle2Length(int[] overlayCycleLength)
	{
		this.overlayCycleLength2 = overlayCycleLength;
	}

	public int getBaseCycle()
	{
		return baseCycle;
	}

	public void setBaseCycle(int baseCycle)
	{
		this.baseCycle = baseCycle;
	}

	public int getOverlayCycle1()
	{
		return overlayCycle1;
	}
	
	public int getOverlayCycle2()
	{
		return overlayCycle2;
	}

	public void setOverlayCycle1(int overlayCycle)
	{
		this.overlayCycle1 = overlayCycle;
	}
	
	public void setOverlayCycle2(int overlayCycle)
	{
		this.overlayCycle2 = overlayCycle;
	}

	public int getBaseStep()
	{
		return baseStep;
	}

	public int getOverlayStep1()
	{
		return overlayStep1;
	}
	
	public int getOverlayStep2()
	{
		return overlayStep2;
	}

	public OverlayedAnimatedSpriteComponent(ComponentTemplate template)
	{
		super(template);		
		OverlayedAnimatedSpriteComponentTemplate t = (OverlayedAnimatedSpriteComponentTemplate) template;
		
		setScale(t.getScale());
		setFilter(t.getFilter());
		setPivotX(t.getPivotX());
		setPivotY(t.getPivotY());
		setLayer(t.getLayer());
		setFlipped(t.isFlipped());
		
		baseSpritesheet = t.getBaseSpritesheet();
		overlaySpritesheet1 = t.getOverlaySpritesheet1();
		overlaySpritesheet2 = t.getOverlaySpritesheet2();
		
		baseCycleLength = t.getBaseCycleLength();
		overlayCycleLength1 = t.getOverlayCycleLength1();
		overlayCycleLength2 = t.getOverlayCycleLength2();
		
		baseCycle = t.getBaseCycle();
		overlayCycle1 = t.getOverlayCycle1();
		overlayCycle2 = t.getOverlayCycle2();
		
		overlayColor1 = t.getOverlayColor1();
		overlayColor2 = t.getOverlayColor2();
		
		baseStep = t.getBaseStep();
		overlayStep1 = t.getOverlayStep1();
		overlayStep2 = t.getOverlayStep2();
		
		SpriteManager.getInstance().addSprite(this);
 	}

	
	/**
	 * method to draw the current frame of the animation
	 */ 
	public void draw(float camPosX,float camPosY)
	{
		Image baseimg = baseSpritesheet.getSprite(baseStep, baseCycle);
		if(getFlipped())
			baseimg = baseimg.getFlippedCopy(false, true);
		baseimg.setCenterOfRotation(getPivotX(), getPivotY());
		baseimg.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));

		Image overlayimg1 = overlaySpritesheet1.getSprite(overlayStep1,
				overlayCycle1);
		if(getFlipped())
			overlayimg1 = overlayimg1.getFlippedCopy(false, true);
		overlayimg1.setCenterOfRotation(getPivotX(), getPivotY());
		overlayimg1.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));
		
		Image overlayimg2 = overlaySpritesheet2.getSprite(overlayStep2,
				overlayCycle2);
		if(getFlipped())
			overlayimg2 = overlayimg2.getFlippedCopy(false, true);
		overlayimg2.setCenterOfRotation(getPivotX(), getPivotY());
		overlayimg2.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));


		if (getFilter() != null)
		{
			baseimg.draw(getOwner().getPosX()- camPosX - (baseimg.getWidth() / 2),
					getOwner().getPosY() - camPosY - (baseimg.getHeight() / 2),
					getScale(), getFilter());
		} else
		{
			baseimg.draw(getOwner().getPosX() -camPosX- (baseimg.getWidth() / 2),
					getOwner().getPosY()-camPosY - (baseimg.getHeight() / 2),
					getScale());
		}

		if (overlayColor1 != null)
		{
			overlayimg1.draw(getOwner().getPosX()-camPosX - (overlayimg1.getWidth() / 2),
					getOwner().getPosY()-camPosY - (overlayimg1.getHeight() / 2),
					getScale(), overlayColor1);
		} else
		{
			overlayimg1.draw(getOwner().getPosX()-camPosX - (overlayimg1.getWidth() / 2),
					getOwner().getPosY()-camPosY - (overlayimg1.getHeight() / 2),
					getScale());
		}
		
		if (overlayColor2 != null)
		{
			overlayimg2.draw(getOwner().getPosX()-camPosX - (overlayimg2.getWidth() / 2),
					getOwner().getPosY()-camPosY - (overlayimg2.getHeight() / 2),
					getScale(), overlayColor2);
		} else
		{
			overlayimg2.draw(getOwner().getPosX()-camPosX - (overlayimg2.getWidth() / 2),
					getOwner().getPosY()- camPosY - (overlayimg2.getHeight() / 2),
					getScale());
		}

	}

	protected void destroy()
	{
		SpriteManager.getInstance().removeSprite(this);
		
		try
		{
			baseSpritesheet.destroy();
		} catch (SlickException e)
		{
			e.printStackTrace();
			System.out.println("BaseSpriteSheet konnte nicht zerstört werden!");
		}

		try
		{
			overlaySpritesheet1.destroy();
		} catch (SlickException e)
		{
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet1 konnte nicht zerstört werden!");
		}
		
		try
		{
			overlaySpritesheet2.destroy();
		} catch (SlickException e)
		{
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet2 konnte nicht zerstört werden!");
		}

	}

	public void resetBaseCycle()
	{
		baseCycle = 0;
	}

	public void resetOverlayCycle1()
	{
		overlayCycle1 = 0;
	}
	
	public void resetOverlayCycle2()
	{
		overlayCycle2 = 0;
	}
	
	/**
	 * Advances the animations by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 */
	public void tick(float deltaTime)
	{
		baseStep++;
		baseStep %= baseCycleLength[baseCycle]; // loop back to frame 0
		
		overlayStep1++;
		overlayStep1 %= overlayCycleLength1[overlayCycle1]; // loop back to frame 0
		
		overlayStep2++;
		overlayStep2 %= overlayCycleLength2[overlayCycle2]; // loop back to frame 0
	}
	
	/**
	 * Advances the base-animation by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 * @deprecated use tick() instead
	 */
	public void baseTick(float deltaTime)
	{
		baseStep++;
		baseStep %= baseCycleLength[baseCycle]; // loop back to frame 0
	}
	
	/**
	 * Advances the overlay1-animation1 by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 * @deprecated use tick() instead
	 */
	public void overlayTick1(float deltaTime)
	{
		overlayStep1++;
		overlayStep1 %= overlayCycleLength1[overlayCycle1]; // loop back to frame 0
	}
	
	/**
	 * Advances the overlay2-animation2 by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 * @deprecated use tick() instead
	 */
	public void overlayTick2(float deltaTime)
	{
		overlayStep2++;
		overlayStep2 %= overlayCycleLength2[overlayCycle2]; // loop back to frame 0
	}

}
