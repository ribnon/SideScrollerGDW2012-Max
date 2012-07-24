package gdwGraphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

/**
 * 
 * @author Dustin
 * 
 */

public class StaticSpriteComponent extends SpriteComponent
{
	/**
	 * Image for sprite
	 */
	private Image image;


	/**
	 * 
	 * @param template
	 */
	public StaticSpriteComponent(ComponentTemplate template)
	{
		super(template);

		try
		{
			image = new Image(template.getStringParam("Path"));
		} catch (SlickException e)
		{
			e.printStackTrace();
			System.out.println("Image konnte nicht erzeugt werden!");
		}
	}

	protected void destroy()
	{
		try
		{
			image.destroy();
		} catch (SlickException e)
		{
			e.printStackTrace();
			System.out.println("Image konnte nicht zerstört werden!");
		}
	}

	/**
	 * Getter for image of the sprite
	 * 
	 * @return Image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Setter for image of the sprite
	 * 
	 * @param i
	 *            Image
	 */
	public void setImage(Image i)
	{
		image = i;
	}

	
	/**
	 * Method to draw the sprite
	 * 
	 */
	public void draw()
	{
		image.setCenterOfRotation(getPivotX(), getPivotY());
		image.setRotation(getOwner().getOrientation() * (180 / Math.PI));
		if (getFilter() != null)
		{
			image.draw(getOwner().getPosX() - (image.getWidth() / 2),
					getOwner().getPosY() - (image.getHeight() / 2), getScale(),
					getFilter());
		} else
		{
			image.draw(getOwner().getPosX() - (image.getWidth() / 2),
					getOwner().getPosY() - (image.getHeight() / 2), getScale());
		}
	}
}
