package gdw.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ServerRunningMenu implements IMenuBase
{
	@Override
	public void draw(GameContainer container, Graphics graphics)
	{
		//container.setTargetFrameRate(1);
		int width = container.getWidth();
		int height = container.getHeight();

		String text = "Server is running!";
		int textWidth = graphics.getFont().getWidth(text);
		int textHeight = graphics.getFont().getHeight(text);

		graphics.drawString(text, width / 2 - textWidth / 2, height / 2
				- textHeight / 2);
	}

	@Override
	public void update(GameContainer container, int deltaTime)
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mouseWheelMoved(int change)
	{
	}

	@Override
	public void keyReleased(int key, char c)
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
	}

}
