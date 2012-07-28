package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class HotButton extends GameObject {
	private Image[] button = new Image[2];
	private Vector2f position;
	private boolean isPressed;
	private String triggeredEntity;
	
	public HotButton(String baseImageRef, String overlayImageRef, Vector2f position, Color color, String triggeredEntity)
	{
		super(null, color);
		isPressed = false;
		this.position = position;
		
		try {
			if (triggeredEntity != null)
			{
				this.triggeredEntity = triggeredEntity;
			}
			else
			{
				throw new SlickException("Dieser Schalter triggert keine Entity!");
			}
			button[0] = new Image(baseImageRef);
			button[1] = new Image(overlayImageRef);
			shape = new Rectangle(this.position.x, this.position.y, button[0].getWidth(), button[0].getHeight());
		} catch (SlickException e) {
			e.printStackTrace();
			button[0] = null;
			button[1] = null;
			shape = new Rectangle(this.position.x, this.position.y, 10, 10);
		}
	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(Graphics g)
	{
		if ((button[0] != null) && (button[1] != null))
		{
			g.drawImage(button[0], position.x, position.y);
			g.drawImage(button[1], position.x, position.y, color);
		}
		else
		{
			g.draw(shape);
		}
	}

	public boolean isPressed() { return this.isPressed; }
	public void press()
	{
		if (!isPressed)
		{
			isPressed = true;
			//search for triggered Entity....
		}
	}
}
