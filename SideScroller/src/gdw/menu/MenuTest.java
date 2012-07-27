package gdw.menu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Physics.testbed.SimulationTest;

public class MenuTest extends BasicGame
{
	public MenuTest()
	{
		super("Hardcoded Menu! 20% cooler in 10 seconds flat!");
	}
	LobbyMenu lobby = new LobbyMenu();
	private boolean shutdown = false;
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException
	{
		lobby.draw(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException
	{
		lobby.init(arg0);
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
		lobby.addToServerList("Chimcherry");
		lobby.addToServerList("Cherrychanga");
		lobby.addToServerList("Chimcherry");
		lobby.addToServerList("Cherrychanga");
		lobby.addToServerList("Chimcherry");
		lobby.addToServerList("Cherrychanga");
		lobby.addToServerList("Last Entry");
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException
	{
		if (shutdown)
			arg0.exit();
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		lobby.mouseClicked(button, x, y, clickCount);
	}
	@Override
	public void mousePressed(int button, int x, int y)
	{
		lobby.mousePressed(button, x, y);
	}
	@Override
	public void mouseReleased(int button, int x, int y)
	{
		lobby.mouseReleased(button, x, y);
	}
	@Override
	public void keyPressed(int key, char c)
	{
		lobby.keyPressed(key, c);
	}
	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_ESCAPE)
			shutdown  = true;
		lobby.keyReleased(key, c);
	}
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		lobby.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		change = Math.max(-1, Math.min(change, 1));
		lobby.mouseWheelMoved(change);
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new MenuTest());
		app.setDisplayMode(600, 300, false);
		app.setUpdateOnlyWhenVisible(false);
		app.start();
	}

}
