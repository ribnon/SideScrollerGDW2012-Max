package gdw.graphics;

import java.awt.BufferCapabilities.FlipContents;

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
		StaticSpriteComponentTemplate t = (StaticSpriteComponentTemplate) template;

		image = t.getImage();
		
		setScale(t.getScale());
		setFilter(t.getFilter());
		setPivotX(t.getPivotX());
		setPivotY(t.getPivotY());
		setLayer(t.getLayer());
		setFlipped(t.isFlipped());
		
		SpriteManager.getInstance().addSprite(this);
	}

	protected void destroy()
	{
		SpriteManager.getInstance().removeSprite(this);
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

	public void setFlipped(boolean b)
	{
		if (getFlipped() != b)
		{
			image = image.getFlippedCopy(true, false);
		}
		super.setFlipped(b);
	}

	/**
	 * Method to draw the sprite
	 * 
	 */
	public void draw(float camPosX,float camPosY)
	{
		
		image.setCenterOfRotation(getPivotX(), getPivotY());
		image.setRotation(getOwner().getOrientation());
		if (getFilter() != null)
		{
			image.draw(camPosX + getOwner().getPosX() - ((image.getWidth() / 2f)*getScale()),
					camPosY + getOwner().getPosY() - ((image.getHeight() / 2f)*getScale()), getScale(),
					getFilter());
		} else
		{
			image.draw(camPosX + getOwner().getPosX() - ((image.getWidth() / 2f)*getScale()),
					camPosY + getOwner().getPosY() - ((image.getWidth() / 2f)*getScale()), getScale());
		}
	}
}
