package gdw.menu;

import gdw.network.client.BasicClient;
import gdw.network.client.ServerInfo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Physics.testbed.SimulationTest;

public class MenuTest extends BasicGame
{
	public MenuTest()
	{
		super("Hardcoded Menu! 20% cooler in 10 seconds flat!");
	}

	private boolean shutdown = false;

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException
	{
		Menu.getInstance().draw(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException
	{
		Menu.getInstance().init(arg0);
		
		
//		LobbyMenu l = new LobbyMenu()
//		{
//			@Override
//			public void onOfflineModeClicked()
//			{
//				updateServerList = false;
//				CharacterSelectionMenu c = new CharacterSelectionMenu(true)
//				{
//					@Override
//					public void startOnline()
//					{
//					}
//					@Override
//					public void startOffline()
//					{
//					}
//					@Override
//					public void launchServer()
//					{
//					}
//				};
//
//				try
//				{
//					c.addHat(new Image("assets/hat/teddy_bandana.png"));
//					c.addHat(new Image("assets/hat/teddy_basecap.png"));
//					c.addHat(new Image("assets/hat/teddy_headband.png"));
//					c.addHat(new Image("assets/hat/teddy_sombrero.png"));
//					c.addHat(new Image("assets/hat/teddy_tophat.png"));
//					c.addHat(new Image("assets/hat/teddy_viking.png"));
//				} catch (SlickException e)
//				{
//					e.printStackTrace();
//				}
//				c.setPlayer1Hat(0);
//				c.setPlayer2Hat(0);
//				menu = c;
//			}
//		};
//		l.init(arg0);
//		BasicClient.setListener(l);
//		menu = l;
//		updateServerList = true;
//		BasicClient.refreshServerList();
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException
	{
		if (shutdown)
			arg0.exit();
//		
//		if (updateServerList)
//		{
//			serverUpdateTimer += arg1;
//			if (serverUpdateTimer > 5000)
//			{
//				serverUpdateTimer = 0;
//				BasicClient.refreshServerList();
//			}
//		}
		Menu.getInstance().update(arg0, arg1);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		Menu.getInstance().mouseClicked(button, x, y, clickCount);
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		Menu.getInstance().mousePressed(button, x, y);
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		Menu.getInstance().mouseReleased(button, x, y);
	}

	@Override
	public void keyPressed(int key, char c)
	{
		Menu.getInstance().keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_ESCAPE)
			shutdown = true;
		Menu.getInstance().keyReleased(key, c);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		Menu.getInstance().mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		change = Math.max(-1, Math.min(change, 1));
		Menu.getInstance().mouseWheelMoved(change);
	}

	/**
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new MenuTest());
		app.setDisplayMode(800, 600, false);
		app.setUpdateOnlyWhenVisible(false);
		app.start();
	}

}
