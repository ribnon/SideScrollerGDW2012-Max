package gdw.menu;

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

	MenuBase menu;
	private boolean shutdown = false;

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException
	{
		menu.draw(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException
	{
		LobbyMenu l = new LobbyMenu()
		{
			@Override
			public void onJoinServerClicked(int indexInList)
			{
				CharacterSelectionMenu c = new CharacterSelectionMenu();
				c.setPlayer1Name("Foo");
				c.setPlayer2Name("Bar");
				c.setServerName("20% cooler");
				c.setPlayer1Modifiable(true);
				c.setPlayer2Modifiable(true);
				// c.addPlayerCharacterImage("")
				try
				{
					c.setServerImage(new Image(
							"assets/spritesheets/singleImages/enemy_brocoli_bad.png"));
					c.addHat(new Image("assets/hat/teddy_bandana.png"));
					c.addHat(new Image("assets/hat/teddy_basecap.png"));
					c.addHat(new Image("assets/hat/teddy_headband.png"));
					c.addHat(new Image("assets/hat/teddy_sombrero.png"));
					c.addHat(new Image("assets/hat/teddy_tophat.png"));
					c.addHat(new Image("assets/hat/teddy_viking.png"));
				} catch (SlickException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.setPlayer1Hat(0);
				c.setPlayer2Hat(0);
				menu = c;
			}

			@Override
			public void onCreateNewServerClicked()
			{
			}
		};
		l.init(arg0);
		l.addToServerList("Rainbow Dash");
		l.addToServerList("Pinkie Pie");
		l.addToServerList("Fluttershy");
		l.addToServerList("Rarity");
		l.addToServerList("Applejack");
		l.addToServerList("Twilight Sparkle");
		l.addToServerList("Celestia");
		l.addToServerList("Luna");
		l.addToServerList("Scootaloo");
		l.addToServerList("Applebloom");
		l.addToServerList("Sweetie Belle");
		l.addToServerList("Chimcherry");
		l.addToServerList("Cherrychanga");
		l.addToServerList("Chimcherry");
		l.addToServerList("Cherrychanga");
		l.addToServerList("Chimcherry");
		l.addToServerList("Cherrychanga");
		l.addToServerList("Last Entry");
		menu = l;
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
		menu.mouseClicked(button, x, y, clickCount);
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		menu.mousePressed(button, x, y);
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		menu.mouseReleased(button, x, y);
	}

	@Override
	public void keyPressed(int key, char c)
	{
		menu.keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_ESCAPE)
			shutdown = true;
		menu.keyReleased(key, c);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		menu.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		change = Math.max(-1, Math.min(change, 1));
		menu.mouseWheelMoved(change);
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
