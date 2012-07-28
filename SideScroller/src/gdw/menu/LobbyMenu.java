package gdw.menu;

import gdw.network.client.BasicClient;
import gdw.network.client.IBasicClientListener;
import gdw.network.client.ServerInfo;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.print.attribute.standard.Severity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class LobbyMenu implements IMenuBase,
		IBasicClientListener
{
	private static final String TITLE = "TEST";
	private static final int MAX_PLAYERNAME = 32;
	private String playerName = "Player";
	private ArrayList<ServerInfo> serverList = new ArrayList<ServerInfo>();
	private boolean isNameFieldActive = false;
	private int currentActiveServerIndex = 0;
	private int currentIndexOnTopOfList = 0;
	private static int SERVER_UPDATE_RATE_IN_MS = 5000; 
	private int timeRemainingToUpdate = 0;

	// private int titleYPos;
	private int playerNameBoxPosX, playerNameBoxPosY, playerNameBoxSizeX,
			playerNameBoxSizeY;
	private int offlineButtonPosX, offlineButtonPosY, offlineButtonSizeX,
			offlineButtonSizeY;
	private int createServerButtonPosX, createServerButtonPosY,
			createServerButtonSizeX, createServerButtonSizeY;
	private int serverListPosY, serverListSizeY;
	private int serverListEntryHeight;
	private int bottomListEntry;
	private boolean isTopEntryArrow, isBottomEntryArrow;

	// private int playerNameStringPosX, playerNameStringPosY;

	public LobbyMenu()
	{
		BasicClient.setListener(this);
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public void updateServerInfo(ServerInfo info)
	{
		for (int i = 0; i < serverList.size(); i++)
		{
			if (serverList.get(i).address.equals(info))
			{
				serverList.set(i, info);
				break;
			}
		}
	}

	public InetAddress getSelectedServer()
	{
		return serverList.get(currentActiveServerIndex).address;
	}

	@Override
	public void update(GameContainer container, int deltaTime)
	{
		timeRemainingToUpdate -= deltaTime;
		if (timeRemainingToUpdate < 0)
		{
			BasicClient.refreshServerList();
			timeRemainingToUpdate = SERVER_UPDATE_RATE_IN_MS;
		}
	}

	public void draw(GameContainer container, Graphics graphics)
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

		// create server button
		String createServer = "Create Server";
		createServerButtonPosY = currentDrawY;
		createServerButtonSizeX = graphics.getFont().getWidth(createServer)
				+ textPadding * 2;
		createServerButtonSizeY = lineHeight + textPadding * 2;
		createServerButtonPosX = (int) (0.99 * width) - createServerButtonSizeX
				- textPadding * 2;

		graphics.drawRect(createServerButtonPosX, createServerButtonPosY,
				createServerButtonSizeX, createServerButtonSizeY);
		graphics.drawString(createServer, createServerButtonPosX + textPadding,
				createServerButtonPosY + textPadding);

		// create server button
		String playOffline = "Play Offline";
		offlineButtonPosY = currentDrawY;
		offlineButtonSizeX = graphics.getFont().getWidth(createServer)
				+ textPadding * 2;
		offlineButtonSizeY = lineHeight + textPadding * 2;
		offlineButtonPosX = createServerButtonPosX
				- createServerButtonSizeX - textPadding * 2;

		graphics.drawRect(offlineButtonPosX, offlineButtonPosY, offlineButtonSizeX, offlineButtonSizeY);
		graphics.drawString(playOffline, offlineButtonPosX + textPadding,
				offlineButtonPosY + textPadding);

		// Player name
		playerNameBoxPosX = (int) (0.01 * width);
		playerNameBoxPosY = currentDrawY;
		playerNameBoxSizeX = width - (int)(0.02 * width) - createServerButtonSizeX - offlineButtonSizeX - textPadding*6;
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
				ServerInfo info = serverList.get(i + currentIndexOnTopOfList);
				graphics.drawString(info.address + "\t" + info.infoMsg, 0.01f
						* width + textPadding, currentDrawY + textPadding);
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
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		if (x > playerNameBoxPosX && y > playerNameBoxPosY
				&& x < playerNameBoxPosX + playerNameBoxSizeX
				&& y < playerNameBoxPosY + playerNameBoxSizeY)
		{
			isNameFieldActive = true;
		} else
		{
			isNameFieldActive = false;
		}
		if (x > createServerButtonPosX && y > createServerButtonPosY
				&& x < createServerButtonPosX + createServerButtonSizeX
				&& y < createServerButtonPosY + createServerButtonSizeY)
		{
			onCreateNewServerClicked();
		}
		if (x > offlineButtonPosX && y > offlineButtonPosY
				&& x < offlineButtonPosX + offlineButtonSizeX
				&& y < offlineButtonPosY + offlineButtonSizeY)
		{
			onOfflineModeClicked();
		}

		boolean scrolled = false;
		// server list index:
		int entry = (y - serverListPosY) / serverListEntryHeight;
		if (entry == 0 && isTopEntryArrow)
		{
			--currentIndexOnTopOfList;
			scrolled = true;
		} else if (entry == bottomListEntry && isBottomEntryArrow)
		{
			++currentIndexOnTopOfList;
			scrolled = true;
		} else if (entry + currentIndexOnTopOfList < serverList.size())
		{
			currentActiveServerIndex = entry + currentIndexOnTopOfList;
		}

		if (clickCount == 2 && !scrolled)
			onJoinServerClicked(serverList.get(currentActiveServerIndex));
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
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		currentIndexOnTopOfList -= change;
		if (currentIndexOnTopOfList < 0)
			currentIndexOnTopOfList = 0;
		else if (currentIndexOnTopOfList > serverList.size() - 2)
			currentIndexOnTopOfList = serverList.size() - 2;
	}

	@Override
	public void serverResponce(ServerInfo info)
	{
		updateServerInfo(info);
	}

	@Override
	public void connectionUpdate(int msg)
	{
	}

	@Override
	public void connectionEstablished(BasicClient clientRef)
	{
	}

	@Override
	public void incomingMessage(ByteBuffer msg, boolean wasReliable)
	{
	}

	public abstract void onJoinServerClicked(ServerInfo info);
	public abstract void onCreateNewServerClicked();
	public abstract void onOfflineModeClicked();
}
