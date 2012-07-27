package gdw.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class MenuBase
{
	private static Image background;
	private static Image mouseCursor;
	private int mousePosX, mousePosY;

	public MenuBase()
	{
	}
	public void draw(GameContainer container, Graphics graphics)
	{
		float widthScale = (float)container.getWidth() / background.getWidth();
		float heightScale = (float)container.getHeight() / background.getHeight();
		float scale = widthScale > heightScale ? widthScale : heightScale;
		background.draw(0, 0, scale);
		
		implDraw(container, graphics);
		mouseCursor.draw(mousePosX, mousePosY);
	}
	public abstract void mouseClicked(int button, int x, int y, int clickCount);
	public abstract void mousePressed(int button, int x, int y);
	public abstract void mouseReleased(int button, int x, int y);
	public abstract void keyPressed(int key, char c);
	public abstract void keyReleased(int key, char c);
	public abstract void mouseWheelMoved(int change);
	public void init(GameContainer c)
	{
		try
		{
			background = new Image("assets/menu/menu_background.png");
			mouseCursor = new Image("assets/menu/menu_cursor.png");
			//Somehow this does not work with a real image
			c.setMouseCursor("assets/menu/transparent_pixel.png", 0, 0);
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
		implInit(c);
	}
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		mousePosX = newx;
		mousePosY = newy;
	}
	
	protected abstract void implInit(GameContainer c);
	protected abstract void implDraw(GameContainer container, Graphics graphics);
	
}
