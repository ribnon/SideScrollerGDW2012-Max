package gdw.menu;

import java.util.ArrayList;

import javax.print.attribute.standard.Severity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class LobbyMenu
{
	private static final String TITLE = "TEST";
	private static final int MAX_PLAYERNAME = 32;
	private String playerName = "Player";
	private ArrayList<String> serverList = new ArrayList<String>();
	private int serverListDisplayStartIndex = 0;
	private boolean isNameFieldActive = false;
	private int currentActiveServerField = 0;

	public LobbyMenu()
	{
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
		return serverList.get(currentActiveServerField);
	}

	private void onPlayerNameClick()
	{
		isNameFieldActive = true;
	}

	public void draw(GameContainer container, Graphics graphics)
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

		// Player name
		currentDrawY += lineHeight / 2;
		graphics.drawRect(0.01f * width, currentDrawY, 0.75f * width - 0.01f
				* width - 10, lineHeight + textPadding * 2);
		graphics.drawString(playerName, 0.01f * width + textPadding,
				currentDrawY + textPadding);

		// create server button
		graphics.drawRect(0.75f * width, currentDrawY, 0.99f * width - 0.75f
				* width, lineHeight + textPadding * 2);
		graphics.drawString("Create Server", 0.75f * width + textPadding,
				currentDrawY + textPadding);
		currentDrawY += lineHeight + textPadding * 2;

		// server list
		currentDrawY += lineHeight / 2;
		graphics.drawRect(0.01f * width, currentDrawY, 0.99f * width - 0.01f
				* width, 0.99f * height - currentDrawY);
		
		 // server list entries
		 for (int i = 0; i < serverList.size(); i++)
		 {
			 graphics.drawString(serverList.get(i), 0.01f * width + textPadding, currentDrawY + textPadding);
			 currentDrawY += lineHeight + textPadding;
		 }
	}
}
