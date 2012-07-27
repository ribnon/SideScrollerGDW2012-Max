package gdw.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CharacterSelectionMenu extends MenuBase
{
	// TODO: character selection menu
	private Selecter charSelectPlayer1, charSelectPlayer2;
	private Selecter serverSelecter;
	private boolean isServerNameFieldActive = false;
	private static final String TITLE = "Select your character";

	public CharacterSelectionMenu()
	{
		charSelectPlayer1 = new Selecter()
		{
			@Override
			public void notifyPeerOfHatChange(int newHatID)
			{
				charSelectPlayer2.disableHatID(newHatID);
			}
		};
		charSelectPlayer2 = new Selecter()
		{
			@Override
			public void notifyPeerOfHatChange(int newHatID)
			{
				charSelectPlayer1.disableHatID(newHatID);
			}
		};
		// unneeded
		serverSelecter = new Selecter()
		{
			@Override
			public void notifyPeerOfHatChange(int newHatID)
			{
			}
		};
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		charSelectPlayer1.handleClick(button, x, y, clickCount);
		charSelectPlayer2.handleClick(button, x, y, clickCount);
		serverSelecter.handleClick(button, x, y, clickCount);
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
		currentDrawY += lineHeight + lineHeight / 2;

		int availableSpaceX = (int) (width - 0.02 * width);
		int availableSpaceY = (int) (height - currentDrawY - 0.01 * height);

		float scaleFactorPlayer1 = Math.min((availableSpaceX / 2f)
				/ charSelectPlayer1.getWidth(), (availableSpaceY / 2f)
				/ charSelectPlayer1.getHeight());

		float scaleFactorPlayer2 = Math.min((availableSpaceX / 2f)
				/ charSelectPlayer2.getWidth(), (availableSpaceY / 2f)
				/ charSelectPlayer2.getHeight());

		float scaleFactorServer = Math.min((availableSpaceX / 2f)
				/ serverSelecter.getWidth(), (availableSpaceY / 2f)
				/ serverSelecter.getHeight());

		int centerX = availableSpaceX / 2;
		int centerY = availableSpaceY / 2;

		charSelectPlayer1.draw(container, graphics,
				(int) (centerX - charSelectPlayer1.getWidth()
						* scaleFactorPlayer1),
				(int) (centerY - charSelectPlayer1.getHeight()
						* scaleFactorPlayer1), scaleFactorPlayer1);
		charSelectPlayer2.draw(container, graphics,
				(int) (centerX - charSelectPlayer2.getWidth()
						* scaleFactorPlayer2), centerY, scaleFactorPlayer2);
		// int serverStartY = (int) (currentDrawY
		// + (charSelectPlayer1.getHeight() * scaleFactorPlayer1 +
		// charSelectPlayer2
		// .getHeight() * scaleFactorPlayer2) / 2 - serverSelecter
		// .getHeight() / 2);
		serverSelecter
				.draw(container,
						graphics,
						centerX,
						(int) (centerY - (serverSelecter.getHeight() * scaleFactorServer) / 2),
						scaleFactorServer);
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

	public void setPlayer1Modifiable(boolean m)
	{
		charSelectPlayer1.setModifiable(m);
	}

	public void setPlayer2Modifiable(boolean m)
	{
		charSelectPlayer2.setModifiable(m);
	}

	public void setPlayer1Hat(int id)
	{
		charSelectPlayer1.setHatImageIndex(id);
	}

	public void setPlayer2Hat(int id)
	{
		charSelectPlayer2.setHatImageIndex(id);
	}

	public void setServerModifiable(boolean m)
	{
		serverSelecter.setModifiable(m);
	}

	public void addHat(Image img)
	{
		charSelectPlayer1.addImage(img);
		charSelectPlayer2.addImage(img);
	}

	public void setServerImage(Image img)
	{
		serverSelecter.setBaseImage(img);
	}

}
