package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class StaticSprite extends GameObject {
	private Image image;
	private Vector2f position;
	
	public StaticSprite(String imageRef, Vector2f position, Color color) {
		super(null, color);
		this.position = position;
		this.color = color;
		
		try {
			this.image = new Image(imageRef);
			this.shape = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
			this.image = null;
			this.shape = new Rectangle(this.position.x, this.position.y, 10, 10);
		}
	}
	
	@Override
	public void update(long delta) { }
	
	@Override
	public void draw(Graphics g)
	{
		if (image != null)
		{
			g.drawImage(image, position.x, position.y);
		}
		else
		{
			g.draw(shape);
		}
	}
}
