package gdw.menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Physics.testbed.SimulationTest;

public class MenuTest extends BasicGame
{
	public MenuTest()
	{
		super("Hardcoded Menu! 20% cooler in 10 seconds flat!");
	}
	LobbyMenu lobby = new LobbyMenu();
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException
	{
		lobby.draw(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException
	{
		lobby.addToServerList("Rainbow Dash");
		lobby.addToServerList("Pinkie Pie");
		lobby.addToServerList("Fluttershy");
		lobby.addToServerList("Rarity");
		lobby.addToServerList("Applejack");
		lobby.addToServerList("Twilight Sparkle");
		lobby.addToServerList("Celestia");
		lobby.addToServerList("Luna");
		lobby.addToServerList("Scootaloo");
		lobby.addToServerList("Applebloom");
		lobby.addToServerList("Sweetie Belle");
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException
	{
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new MenuTest());
		app.setDisplayMode(800, 300, false);
		app.start();
	}

}
