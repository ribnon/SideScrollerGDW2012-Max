package gdw.graphics;

import org.lwjgl.opengl.GL11;
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
	private boolean isBackground;
	/**
	 * 
	 * @param template
	 */
	public StaticSpriteComponent(ComponentTemplate template)
	{
		super(template);
		StaticSpriteComponentTemplate t = (StaticSpriteComponentTemplate) template;
		
		setScale(t.getScale());
		setFilter(t.getFilter());
		setPivotX(t.getPivotX());
		setPivotY(t.getPivotY());
		setLayer(t.getLayer());
		setFlipped(t.isFlipped());
		isBackground = t.isBackground;
		
		image = t.getImage();
		
		SpriteManager.getInstance().addSprite(this);
	}

	protected void destroy()
	{
		SpriteManager.getInstance().removeSprite(this);
		
//		try
//		{
//			image.destroy();
//		} catch (SlickException e)
//		{
//			e.printStackTrace();
//			System.out.println("Image konnte nicht zerstört werden!");
//		}
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
		if (!(getFlipped() == b))
		{
			image = image.getFlippedCopy(false, true);
		}
	}

	/**
	 * Method to draw the sprite
	 * 
	 */
	public void draw(float camPosX,float camPosY)
	{
		
		image.setCenterOfRotation(getPivotX(), getPivotY());
		image.setRotation(getOwner().getOrientation());
		image.setRotation(getOwner().getOrientation());
		if (getFilter() != null)
		{
			image.draw(getOwner().getPosX() - ((image.getWidth() / 2f)*getScale()),
					getOwner().getPosY()- ((image.getHeight() / 2f)*getScale()), getScale(),
					getFilter());
		} else
		{
			image.draw(getOwner().getPosX() - ((image.getWidth() / 2f)*getScale()),
					getOwner().getPosY() - ((image.getWidth() / 2f)*getScale()), getScale());
		}
	}
}
