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
	private SpriteSheet overlaySpritesheet;

	/**
	 * Color-Filter für den Overlay-Spritesheet
	 */
	private Color overlayColor;
	/**
	 * number of Frames in per column of sprites in the spritesheets.
	 */
	private int[] baseCycleLength;
	private int[] overlayCycleLength;
	/**
	 * current column (animation)
	 */
	private int baseCycle;
	private int overlayCycle;
	/**
	 * current frame
	 */
	private int baseStep = 0;
	private int overlayStep = 0;

	public SpriteSheet getBaseSpritesheet()
	{
		return baseSpritesheet;
	}

	public void setBaseSpritesheet(SpriteSheet baseSpritesheet)
	{
		this.baseSpritesheet = baseSpritesheet;
	}

	public SpriteSheet getOverlaySpritesheet()
	{
		return overlaySpritesheet;
	}

	public void setOverlaySpritesheet(SpriteSheet overlaySpritesheet)
	{
		this.overlaySpritesheet = overlaySpritesheet;
	}

	public Color getOverlayColor()
	{
		return overlayColor;
	}

	public void setOverlayColor(Color overlayColor)
	{
		this.overlayColor = overlayColor;
	}

	public int[] getBaseCycleLength()
	{
		return baseCycleLength;
	}

	public void setBaseCycleLength(int[] baseCycleLength)
	{
		this.baseCycleLength = baseCycleLength;
	}

	public int[] getOverlayCycleLength()
	{
		return overlayCycleLength;
	}

	public void setOverlayCycleLength(int[] overlayCycleLength)
	{
		this.overlayCycleLength = overlayCycleLength;
	}

	public int getBaseCycle()
	{
		return baseCycle;
	}

	public void setBaseCycle(int baseCycle)
	{
		this.baseCycle = baseCycle;
	}

	public int getOverlayCycle()
	{
		return overlayCycle;
	}

	public void setOverlayCycle(int overlayCycle)
	{
		this.overlayCycle = overlayCycle;
	}

	public int getBaseStep()
	{
		return baseStep;
	}

	public int getOverlayStep()
	{
		return overlayStep;
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
			overlaySpritesheet = new SpriteSheet(
					template.getStringParam("OverlayPath"),
					template.getIntegerParam("OverlayTileWidth"),
					template.getIntegerParam("OverlayTileHeight"));
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet konnte nicht erstellt werden!");
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

		overlayCycleLength = new int[overlaySpritesheet.getVerticalCount()];
		for (int i = 0; i < overlayCycleLength.length; ++i)
			overlayCycleLength[i] = overlaySpritesheet.getHorizontalCount(); // TODO
																				// detect
																				// unused
																				// slots
																				// in
																				// spritesheet
		overlayCycle = template.getIntegerParam("OverlayCycle");
	}

	
	/**
	 * method to draw the current frame of the animation
	 */
	public void draw()
	{
		Image baseimg = baseSpritesheet.getSprite(baseStep, baseCycle);
		baseimg.setCenterOfRotation(getPivotX(), getPivotY());
		baseimg.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));

		Image overlayimg = overlaySpritesheet.getSprite(overlayStep,
				overlayCycle);
		overlayimg.setCenterOfRotation(getPivotX(), getPivotY());
		overlayimg.setRotation((float) (getOwner().getOrientation() * (180 / Math.PI)));

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

		if (overlayColor != null)
		{
			overlayimg.draw(getOwner().getPosX() - (overlayimg.getWidth() / 2),
					getOwner().getPosY() - (overlayimg.getHeight() / 2),
					getScale(), overlayColor);
		} else
		{
			overlayimg.draw(getOwner().getPosX() - (overlayimg.getWidth() / 2),
					getOwner().getPosY() - (overlayimg.getHeight() / 2),
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
			overlaySpritesheet.destroy();
		} catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out
					.println("OverlaySpriteSheet konnte nicht zerstört werden!");
		}

	}

	public void resetBaseCycle()
	{
		baseCycle = 0;
	}

	public void resetOverlayCycle()
	{
		overlayCycle = 0;
	}
	
	/**
	 * Advances the base-animation by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 */
	public void baseTick(float deltaTime)
	{
		baseStep++;
		baseStep %= baseCycleLength[baseCycle]; // loop back to frame 0
	}
	/**
	 * Advances the overlay-animation by one frame, loops around if end of animation is reached
	 * 
	 * @param time passed since last tick
	 */
	public void overlayTick(float deltaTime)
	{
		overlayStep++;
		overlayStep %= overlayCycleLength[overlayCycle]; // loop back to frame 0
	}

}
