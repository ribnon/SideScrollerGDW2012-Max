package gdw.menu;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import javax.print.attribute.standard.Severity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class LobbyMenu extends MenuBase
{
	private static final String TITLE = "TEST";
	private static final int MAX_PLAYERNAME = 32;
	private String playerName = "Player";
	private ArrayList<String> serverList = new ArrayList<String>();
	private boolean isNameFieldActive = false;
	private int currentActiveServerIndex = 0;
	private int currentIndexOnTopOfList = 0;

	// private int titleYPos;
	private int playerNameBoxPosX, playerNameBoxPosY, playerNameBoxSizeX,
			playerNameBoxSizeY;
	private int createServerButtonPosX, createServerButtonPosY,
			createServerButtonSizeX, createServerButtonSizeY;
	private int serverListPosY, serverListSizeY;
	private int serverListEntryHeight;
	private int bottomListEntry;
	private boolean isTopEntryArrow, isBottomEntryArrow;

	// private int playerNameStringPosX, playerNameStringPosY;

	// TODO: scrolling

	public LobbyMenu()
	{
	}

	@Override
	public void implInit(GameContainer c)
	{
		// int height = c.getHeight();
		// int width = c.getWidth();
		// int lineheight = 16; //TODO: baah
		// int textPadding = lineheight / 8;
		//
		// int crntYPos = 0;
		//
		// crntYPos += 0.01f * height;
		// titleYPos = crntYPos;
		// crntYPos += lineheight;
		//
		// crntYPos += lineheight/2;
		// playerNameBoxPosX = (int) (0.01 * width);
		// playerNameBoxPosY = crntYPos;
		// playerNameBoxSizeX = (int) (0.75 * width - 0.01 * width - 10);
		// playerNameBoxSizeY = lineheight + textPadding;
		// playerNameStringPosX = playerNameBoxPosX + textPadding;
		// playerNameStringPosY = playerNameBoxPosY;
		//
		// createServerButtonPosX = (int) (0.75 * width);
		// createServerButtonPosY = crntYPos;
		//
		// crntYPos += lineheight + textPadding*2 + lineheight/2;
		// serverListPosX = (int) (0.01 * width);
		// serverListPosY = crntYPos;
		// serverListSizeX = (int) (0.99 * width - 0.01 * width);
		// serverListSizeY = (int) (0.99 * height - crntYPos);
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public void addToServerList(String serverName)
	{
		serverList.add(serverName);
	}

	public String getSelectedServer()
	{
		return serverList.get(currentActiveServerIndex);
	}

	public void implDraw(GameContainer container, Graphics graphics)
	{
		isTopEntryArrow = false;
		isBottomEntryArrow = false;

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

		// Player name
		currentDrawY += lineHeight / 2;
		playerNameBoxPosX = (int) (0.01f * width);
		playerNameBoxPosY = currentDrawY;
		playerNameBoxSizeX = (int) (0.75f * width - 0.01f * width - 10);
		playerNameBoxSizeY = lineHeight + textPadding * 2;
		graphics.drawRect(playerNameBoxPosX, playerNameBoxPosY,
				playerNameBoxSizeX, playerNameBoxSizeY);
		graphics.drawString(playerName, 0.01f * width + textPadding,
				currentDrawY + textPadding);
		if (isNameFieldActive)
		{
			int playerNameStringSizeX = graphics.getFont().getWidth(playerName);
			graphics.drawLine(playerNameStringSizeX + playerNameBoxPosX,
					currentDrawY + textPadding, playerNameStringSizeX
							+ playerNameBoxPosX, currentDrawY
							+ playerNameBoxSizeY - textPadding);
		}

		// create server button
		graphics.drawRect(0.75f * width, currentDrawY, 0.99f * width - 0.75f
				* width, lineHeight + textPadding * 2);
		graphics.drawString("Create Server", 0.75f * width + textPadding,
				currentDrawY + textPadding);
		currentDrawY += lineHeight + textPadding * 2;

		// server list
		currentDrawY += lineHeight / 2;
		serverListPosY = currentDrawY;
		serverListSizeY = (int) (0.99f * height - currentDrawY);
		graphics.drawRect(0.01f * width, serverListPosY, 0.99f * width - 0.01f
				* width, serverListSizeY);

		// server list entries
		serverListEntryHeight = lineHeight + textPadding;
		int maxEntries = maxNumServerListEntries((0.99f * height)
				- (currentDrawY + textPadding), lineHeight, textPadding);

		// upwards arrow
		if (currentIndexOnTopOfList != 0)
		{
			isTopEntryArrow = true;
			int widthOfDownwardCharacter = graphics.getFont().getWidth("^");
			graphics.drawString("^", width / 2.0f - widthOfDownwardCharacter,
					currentDrawY + textPadding);
			currentDrawY += lineHeight + textPadding;
		}

		for (int i = 0; i < maxEntries
				&& i < serverList.size() - currentIndexOnTopOfList; i++)
		{
			if (i == 0 && isTopEntryArrow)
				continue;
			
			if (i == maxEntries - 1
					&& i != serverList.size() - 1 - currentIndexOnTopOfList)
			{
				int widthOfDownwardCharacter = graphics.getFont().getWidth("v");
				graphics.drawString("v", width / 2.0f
						- widthOfDownwardCharacter, currentDrawY + textPadding);
				isBottomEntryArrow = true;
			} else
			{
				if (i + currentIndexOnTopOfList == currentActiveServerIndex)
				{
					graphics.drawRect(0.01f * width + textPadding / 2,
							currentDrawY + textPadding / 2, 0.99f * width
									- 0.01f * width - textPadding / 2,
							lineHeight);
				}
				graphics.drawString(
						serverList.get(i + currentIndexOnTopOfList), 0.01f
								* width + textPadding, currentDrawY
								+ textPadding);
			}
			bottomListEntry = i;
			currentDrawY += lineHeight + textPadding;
		}
	}

	int maxNumServerListEntries(float availableSpaceY, int lineheight,
			int padding)
	{
		return (int) (availableSpaceY / (lineheight + padding));
	}

	@Override
	public void keyPressed(int key, char c)
	{
		if (isNameFieldActive)
		{
			switch (key)
			{
			case Input.KEY_ENTER:
				isNameFieldActive = false;
			break;
			case Input.KEY_BACK:
				if (playerName.length() > 0)
					playerName = playerName.substring(0,
							playerName.length() - 1);
			break;
			default:
				if (playerName.length() < MAX_PLAYERNAME && (int) c != 0)
					playerName += c;
			break;
			}
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		if (x > playerNameBoxPosX && y > playerNameBoxPosY
				&& x < playerNameBoxPosX + playerNameBoxSizeX
				&& y < playerNameBoxPosY + playerNameBoxSizeY)
			isNameFieldActive = true;

		// server list index:
		int entry = (y - serverListPosY) / serverListEntryHeight;
		if (entry == 0 && isTopEntryArrow)
			--currentIndexOnTopOfList;
		else if (entry == bottomListEntry && isBottomEntryArrow)
			++currentIndexOnTopOfList;
		else if (entry + currentIndexOnTopOfList < serverList.size())
			currentActiveServerIndex = entry + currentIndexOnTopOfList;
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int change)
	{
		currentIndexOnTopOfList -= change;
		if (currentIndexOnTopOfList < 0)
			currentIndexOnTopOfList = 0;
		else if (currentIndexOnTopOfList > serverList.size()-2)
			currentIndexOnTopOfList = serverList.size()-2;
	}
}
