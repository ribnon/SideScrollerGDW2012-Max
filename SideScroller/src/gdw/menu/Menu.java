package gdw.menu;

import gdw.network.client.ServerInfo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Menu
{
	private static Image background;
	private static Image mouseCursor;
	private int mousePosX, mousePosY;
	private IMenuBase currentScreen = null;
	private static Menu singletonInstance = null;

	public static Menu getInstance()
	{
		if (singletonInstance == null)
			singletonInstance = new Menu();
		return singletonInstance;
	}
	
	private Menu()
	{
	}
	
	public void draw(GameContainer container, Graphics graphics)
	{
		float widthScale = (float)container.getWidth() / background.getWidth();
		float heightScale = (float)container.getHeight() / background.getHeight();
		float scale = widthScale > heightScale ? widthScale : heightScale;
		background.draw(0, 0, scale);
		
		if (currentScreen != null)
			currentScreen.draw(container, graphics);
			
		mouseCursor.draw(mousePosX, mousePosY);
	}
	public void update(GameContainer container, int deltaTime)
	{
		if (currentScreen != null)
			currentScreen.update(container, deltaTime);
	}
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		mousePosX = x;
		mousePosY = y;
		if (currentScreen != null)
			currentScreen.mouseClicked(button, x, y, clickCount);
	}
	public void mousePressed(int button, int x, int y)
	{
		mousePosX = x;
		mousePosY = y;
		if (currentScreen != null)
			currentScreen.mousePressed(button, x, y);
	}
	public void mouseReleased(int button, int x, int y)
	{
		mousePosX = x;
		mousePosY = y;
		if (currentScreen != null)
			currentScreen.mouseReleased(button, x, y);
	}
	public void keyPressed(int key, char c)
	{
		if (currentScreen != null)
			currentScreen.keyPressed(key, c);
	}
	public void keyReleased(int key, char c)
	{
		if (currentScreen != null)
			currentScreen.keyReleased(key, c);
	}
	public void mouseWheelMoved(int change)
	{
		if (currentScreen != null)
			currentScreen.mouseWheelMoved(change);
	}
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		mousePosX = newx;
		mousePosY = newy;
		if (currentScreen != null)
			currentScreen.mouseMoved(oldx, oldy, newx, newy);
	}
	
	public void init(GameContainer c)
	{
		try
		{
			background = new Image("assets/menu/menu_background.png");
			mouseCursor = new Image("assets/menu/menu_cursor.png");
			//Somehow this does not work with a real image
			c.setMouseCursor("assets/menu/transparent_pixel.png", 0, 0);
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
		
		LobbyMenu l = new LobbyMenu()
		{
			@Override
			public void onOfflineModeClicked()
			{
				offlineGame(this);
			}
			@Override
			public void onJoinServerClicked(ServerInfo info)
			{
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onCreateNewServerClicked()
			{
				createServer(this);
			}
		};
		setScreen(l);
	}
	
	public void setScreen(IMenuBase b)
	{
		currentScreen = b;
	}
	public IMenuBase getScreen()
	{
		return currentScreen;
	}

	private void offlineGame(LobbyMenu lobbyMenu)
	{
		CharacterSelectionMenu c = new CharacterSelectionMenu(true)
		{
			@Override
			public void start()
			{
			}
			@Override
			public void launchServer()
			{
			}
		};

		addHatsToCharSelectionMenu(c);
		c.setPlayer1Hat(0);
		c.setPlayer2Hat(0);
		setScreen(c);
	}
	private void createServer(LobbyMenu lobbyMenu)
	{
		String playerName = lobbyMenu.getPlayerName();
		CharacterSelectionMenu c = new CharacterSelectionMenu(false)
		{
			@Override
			public void start()
			{
			}
			
			@Override
			public void launchServer()
			{
			}
		};

		addHatsToCharSelectionMenu(c);
		c.setPlayer1Name(playerName);
		c.setPlayer1Modifiable(true);
		c.setServerModifiable(true);
		c.setPlayer1Hat(0);
		c.setPlayer2Hat(-1);
		setScreen(c);
	}

	private void addHatsToCharSelectionMenu(CharacterSelectionMenu c)
	{
		try
		{
			c.addHat(new Image("assets/hat/teddy_bandana.png"));
			c.addHat(new Image("assets/hat/teddy_basecap.png"));
			c.addHat(new Image("assets/hat/teddy_headband.png"));
			c.addHat(new Image("assets/hat/teddy_sombrero.png"));
			c.addHat(new Image("assets/hat/teddy_tophat.png"));
			c.addHat(new Image("assets/hat/teddy_viking.png"));
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
}
