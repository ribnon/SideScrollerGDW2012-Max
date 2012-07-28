package tiled.test.entities;

import gamestates.PlayState;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class ColorBucket extends GameObject
{
	private Animation[] bucket = new Animation[2];
	private Vector2f position;
	
	public ColorBucket(String sheetRef, String overlaySheetRef, int[] cycles, int tileWidth, int tileHeigth, boolean flipped, Vector2f position, Color color) {
		super(null, color);
		this.position = position;
		this.color = color;
		
		// initialize Animation array
		for (int i = 0; i < bucket.length; ++i)
		{
			bucket[i] = new Animation();
			bucket[i].setAutoUpdate(false);
		}
		
		try {
			if ( (sheetRef != null ) && overlaySheetRef != null)
			{
				final SpriteSheet spritesheet  = new SpriteSheet(sheetRef, tileWidth, tileHeigth);
				final SpriteSheet overlaySheet = new SpriteSheet(overlaySheetRef, tileWidth, tileHeigth);
				
				for (int frame = 0; frame < spritesheet.getHorizontalCount(); ++frame)
				{
					if (flipped)
					{
						bucket[0].addFrame(spritesheet.getSprite(frame, 0).getFlippedCopy(true, false), 150);
						bucket[1].addFrame(overlaySheet.getSprite(frame, 0).getFlippedCopy(true, false), 150);
					}
					else
					{
						bucket[0].addFrame(spritesheet.getSprite(frame, 0), 150);
						bucket[1].addFrame(overlaySheet.getSprite(frame, 0), 150);
					}
				}
			}
			else
			{
				final Image defaultImage = new Image(PlayState.DEFAULT_IMAGE_PATH);
				bucket[0].addFrame(defaultImage, 1);
				bucket[1].addFrame(defaultImage, 1);
			}
			
			// collision geometry
			this.shape = new Rectangle(this.position.x, this.position.y, bucket[0].getWidth(), bucket[0].getHeight());
		}
		catch (SlickException e)
		{
			e.printStackTrace();
			for (int i = 0; i < bucket.length; ++i)
			{
				bucket[i] = null;
			}
			this.shape = new Rectangle(this.position.x, this.position.y, 10, 10);
		}
	}
	
	@Override
	public void update(long delta)
	{
		bucket[0].update(delta);
		bucket[1].update(delta);
	}
	
	@Override
	public void draw(Graphics g)
	{
		bucket[0].draw(this.position.x, this.position.y);
		bucket[1].draw(this.position.x, this.position.y, color);
	}
	
	public Color getColor() { return color; }
}