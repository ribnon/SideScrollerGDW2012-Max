package gdw.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

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
		try
		{
			baseSpritesheet = new SpriteSheet(
					template.getStringParam("BasePath"),
					template.getIntegerParam("BaseTileWidth"),
					template.getIntegerParam("BaseTileHeight"));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("BaseSpriteSheet konnte nicht erstellt werden!");
		}

		try
		{
			overlaySpritesheet1 = new SpriteSheet(
					template.getStringParam("OverlayPath"),
					template.getIntegerParam("OverlayTileWidth"),
					template.getIntegerParam("OverlayTileHeight"));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet1 konnte nicht erstellt werden!");
		}
		
		try
		{
			overlaySpritesheet2 = new SpriteSheet(
					template.getStringParam("OverlayPath"),
					template.getIntegerParam("OverlayTileWidth"),
					template.getIntegerParam("OverlayTileHeight"));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet2 konnte nicht erstellt werden!");
		}

		baseCycleLength = new int[baseSpritesheet.getVerticalCount()];
		for (int i = 0; i < baseCycleLength.length; ++i)
			baseCycleLength[i] = baseSpritesheet.getHorizontalCount(); // TODO
																		// detect
																		// unused
																		// slots
																		// in
																		// spritesheet
		baseCycle = template.getIntegerParam("BaseCycle");

		overlayCycleLength1 = new int[overlaySpritesheet1.getVerticalCount()];
		for (int i = 0; i < overlayCycleLength1.length; ++i)
			overlayCycleLength1[i] = overlaySpritesheet1.getHorizontalCount(); // TODO
																				// detect
																				// unused
																				// slots
																				// in
																				// spritesheet
		overlayCycle2 = template.getIntegerParam("OverlayCycle");
		
		overlayCycleLength2 = new int[overlaySpritesheet2.getVerticalCount()];
		for (int i = 0; i < overlayCycleLength2.length; ++i)
			overlayCycleLength2[i] = overlaySpritesheet2.getHorizontalCount(); // TODO
																				// detect
																				// unused
																				// slots
																				// in
																				// spritesheet
		overlayCycle2 = template.getIntegerParam("OverlayCycle");
	}

	
	/**
	 * method to draw the current frame of the animation
	 */
	public void draw()
	{
		Image baseimg = baseSpritesheet.getSprite(baseStep, baseCycle);
		baseimg.setCenterOfRotation(getPivotX(), getPivotY());
		baseimg.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));

		Image overlayimg1 = overlaySpritesheet1.getSprite(overlayStep1,
				overlayCycle1);
		overlayimg1.setCenterOfRotation(getPivotX(), getPivotY());
		overlayimg1.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));
		
		Image overlayimg2 = overlaySpritesheet2.getSprite(overlayStep2,
				overlayCycle2);
		overlayimg2.setCenterOfRotation(getPivotX(), getPivotY());
		overlayimg2.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));


		if (getFilter() != null)
		{
			baseimg.draw(getOwner().getPosX() - (baseimg.getWidth() / 2),
					getOwner().getPosY() - (baseimg.getHeight() / 2),
					getScale(), getFilter());
		} else
		{
			baseimg.draw(getOwner().getPosX() - (baseimg.getWidth() / 2),
					getOwner().getPosY() - (baseimg.getHeight() / 2),
					getScale());
		}

		if (overlayColor1 != null)
		{
			overlayimg1.draw(getOwner().getPosX() - (overlayimg1.getWidth() / 2),
					getOwner().getPosY() - (overlayimg1.getHeight() / 2),
					getScale(), overlayColor1);
		} else
		{
			overlayimg1.draw(getOwner().getPosX() - (overlayimg1.getWidth() / 2),
					getOwner().getPosY() - (overlayimg1.getHeight() / 2),
					getScale());
		}
		
		if (overlayColor2 != null)
		{
			overlayimg2.draw(getOwner().getPosX() - (overlayimg2.getWidth() / 2),
					getOwner().getPosY() - (overlayimg2.getHeight() / 2),
					getScale(), overlayColor2);
		} else
		{
			overlayimg2.draw(getOwner().getPosX() - (overlayimg2.getWidth() / 2),
					getOwner().getPosY() - (overlayimg2.getHeight() / 2),
					getScale());
		}

	}

	protected void destroy()
	{
		try
		{
			baseSpritesheet.destroy();
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("BaseSpriteSheet konnte nicht zerstört werden!");
		}

		try
		{
			overlaySpritesheet1.destroy();
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet1 konnte nicht zerstört werden!");
		}
		
		try
		{
			overlaySpritesheet2.destroy();
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
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
	 * Advances the base-animation by one frame, loops around if end of animation is reached
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
	 * Advances the overlay-animation1 by one frame, loops around if end of animation is reached
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
	 * Advances the overlay-animation2 by one frame, loops around if end of animation is reached
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
