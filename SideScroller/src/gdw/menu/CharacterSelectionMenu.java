package gdw.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CharacterSelectionMenu extends MenuBase
{
	// TODO: character selection menu
	private Selecter charSelectPlayer1 = new Selecter(),
			charSelectPlayer2 = new Selecter();
	private Selecter serverSelecter = new Selecter();
	private boolean isServerNameFieldActive = false;
	private static final String TITLE = "Select your character";

	public CharacterSelectionMenu()
	{
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
	}

	@Override
	public void keyPressed(int key, char c)
	{
	}

	@Override
	public void keyReleased(int key, char c)
	{
	}

	@Override
	public void mouseWheelMoved(int change)
	{
	}

	@Override
	protected void implInit(GameContainer c)
	{
	}

	@Override
	protected void implDraw(GameContainer container, Graphics graphics)
	{
		int height = container.getHeight();
		int width = container.getWidth();

		int lineHeight = graphics.getFont().getLineHeight();
		int textPadding = lineHeight / 8;
		int currentDrawY = 0;
		// Title
		currentDrawY += 0.01f * height;
		int titlewidth = graphics.getFont().getWidth(TITLE);
		graphics.drawString(TITLE, 0.5f * width - titlewidth / 2, currentDrawY);
		currentDrawY += lineHeight;

		charSelectPlayer1.draw(container, graphics, (int) (0.01f * width),
				currentDrawY);
		charSelectPlayer2.draw(container, graphics, (int) (0.01f * width),
				currentDrawY + charSelectPlayer1.getHeight());
		serverSelecter.draw(container, graphics, charSelectPlayer1.getWidth(),
				currentDrawY + (charSelectPlayer1.getHeight()/2+charSelectPlayer2.getHeight()/2)/2);
	}

	public void setPlayer1Name(String name)
	{
		charSelectPlayer1.setName(name);
	}

	public void setPlayer2Name(String name)
	{
		charSelectPlayer2.setName(name);
	}

	public void setServerName(String name)
	{
		serverSelecter.setName(name);
	}

	public void addPlayerCharacterImage(Image img)
	{
		charSelectPlayer1.addImage(img);
		charSelectPlayer2.addImage(img);
	}

}
