package gdw.menu;

import gdw.network.server.ConnectionInfo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class MapSelecter
{
	private static Image invalidIDImage;
	private static boolean isInitialized = false;

	private Image baseImage = null;
	private String name = "Server";
//	private String mapName = "map/Karte.tmx";
	private int lineHeight = 16;
	private boolean nameModifiable = false;
	private boolean buttonClickable = false;
	private boolean isNameFieldActive = false;
	private int nameBoxStartX, nameBoxStartY, nameBoxSizeX, nameBoxSizeY;
	private int buttonStartX, buttonStartY, buttonSizeX, buttonSizeY;
	private static final int OPENED = 1, FULL = 2, OFFLINE = 3;
	private int state = 1;
	private static final int MAX_SERVERNAME = 32;

	public MapSelecter(boolean offline)
	{
		try
		{
			if (!isInitialized)
			{
				invalidIDImage = new Image("assets/menu/questionmark.png");
				isInitialized = true;
			}
			baseImage = invalidIDImage;
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
		if (offline)
		{
			state = OFFLINE;
			nameModifiable = false;
			buttonClickable = true;
			name = "Offline Server";
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setImage(Image img)
	{
		baseImage = img;
	}

	public void draw(GameContainer container, Graphics graphics, int offsetX,
			int offsetY, float scale)
	{
		lineHeight = graphics.getFont().getLineHeight();
		int textPadding = lineHeight / 8;

		Image drawImage = invalidIDImage;
		if (baseImage != null)
			drawImage = baseImage;

		// int nameWidth = graphics.getFont().getWidth();
		int nameWidth = getWidth();
		nameBoxStartX = (int) (offsetX + getWidth() * scale / 2 - nameWidth / 2 - textPadding);
		nameBoxStartY = (int) (offsetY + drawImage.getHeight() * scale)
				- textPadding;
		nameBoxSizeX = nameWidth + textPadding * 2;
		nameBoxSizeY = lineHeight + textPadding * 2;
		if (nameModifiable)
		{
			graphics.drawRect(nameBoxStartX, nameBoxStartY, nameBoxSizeX,
					nameBoxSizeY);
		}
		graphics.drawString(name, nameBoxStartX + textPadding, nameBoxStartY
				+ textPadding);

		if (isNameFieldActive)
		{
			int playerNameStringSizeX = graphics.getFont().getWidth(name);
			graphics.drawLine(nameBoxStartX + playerNameStringSizeX,
					nameBoxStartY + textPadding, nameBoxStartX
							+ playerNameStringSizeX, nameBoxStartY
							+ nameBoxSizeY - textPadding);
		}

		drawImage.draw(offsetX + getWidth() * scale / 2 - drawImage.getWidth()
				* scale / 2, offsetY, scale);

		String buttonMsg;
		switch (state)
		{
		case OPENED:
			buttonMsg = "waiting for players";
		break;
		case FULL:
			buttonMsg = "start game";
		break;
		case OFFLINE:
			buttonMsg = "start offline game";
		break;
		default:
			buttonMsg = "invalid state";
		break;
		}

		if (buttonClickable)
		{
			int buttonWidth = graphics.getFont().getWidth(buttonMsg);
			buttonStartX = (int) (offsetX + getWidth() * scale / 2 - buttonWidth
					/ 2 - textPadding);
			buttonStartY = (int) (offsetY + drawImage.getHeight() * scale
					+ lineHeight + textPadding * 2);
			buttonSizeX = buttonWidth + textPadding;
			buttonSizeY = lineHeight + textPadding * 2;
			graphics.drawRect(buttonStartX, buttonStartY, buttonSizeX, buttonSizeY);
			graphics.drawString(buttonMsg, buttonStartX + textPadding, buttonStartY
					+ textPadding);
		}
	}

	public int getWidth()
	{
		return Math.max(baseImage.getWidth(), buttonSizeX);
	}

	public int getHeight()
	{
		return baseImage.getHeight() + lineHeight * 2 + (lineHeight / 8) * 2;
	}

	public void setModifiable(boolean m)
	{
		nameModifiable = m;
	}
	public void setButtonClickable(boolean m)
	{
		buttonClickable = m;
	}

	public void handleClick(int button, int x, int y, int clickCount)
	{
		// if (x > leftArrowStartX && y > leftArrowStartY
		// && x < leftArrowStartX + leftArrowSizeX
		// && y < leftArrowStartY + leftArrowSizeY)
		// changeToPreviousMap();
		// if (x > rightArrowStartX && y > rightArrowStartY
		// && x < rightArrowStartX + rightArrowSizeX
		// && y < rightArrowStartY + rightArrowSizeY)
		// changeToNextMap();
		if (nameModifiable && x > nameBoxStartX && y > nameBoxStartY
				&& x < nameBoxStartX + nameBoxSizeX
				&& y < nameBoxStartY + nameBoxSizeY)
		{
			isNameFieldActive = true;
		}
		else
		{
			acceptName();
		}

		if (buttonClickable && x > buttonStartX && y > buttonStartY
				&& x < buttonStartX + buttonSizeX
				&& y < buttonStartY + buttonSizeY)
		{
			switch (state)
			{
			case OPENED:
			break;
			case FULL:
				startClicked();
			break;
			case OFFLINE:
				startClicked();
			break;
			}
		}
	}

	private void acceptName()
	{
		isNameFieldActive = false;
	}

	public abstract void startClicked();

	public void keyPressed(int key, char c)
	{
		if (nameModifiable && isNameFieldActive)
		{
			switch (key)
			{
			case Input.KEY_ENTER:
				isNameFieldActive = false;
			break;
			case Input.KEY_BACK:
				if (name.length() > 0)
					name = name.substring(0, name.length() - 1);
			break;
			default:
				if (name.length() < MAX_SERVERNAME && (int) c != 0)
					name += c;
			break;
			}
		}
	}

	public void keyReleased(int key, char c)
	{
	}
	
	private void setFull()
	{
		state = FULL;
	}
}
