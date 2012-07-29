package gdw;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.control.PlayerInputComponentManager;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Level;
import gdw.graphics.SpriteManager;
import gdw.network.NetSubSystem;
import gdw.network.client.BasicClient;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Client extends BasicGame {

	ClientListener l;
	boolean connected = false;
	boolean connecting = false;
	
	boolean loadLevel = false;
	boolean levelLoaded = false;
	
	boolean quit = false;
	
	
	public void setConnected(boolean c) {
		connected = c;
	}
	
	public void setLoadLevel(boolean ll)
	{
		loadLevel = ll;
	}
	
	public void setQuit(boolean q)
	{
		quit = q;
	}

	public Client(String title) {
		super(title);
		l = new ClientListener(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		SpriteManager.getInstance().render();
		if(connected)
			CollisionDetectionComponentManager.getInstance().render(arg1);

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		BasicClient.setListener(l);
		BasicClient.refreshServerList();
		/*
		 * try {
		 * BasicClient.connectToServer(InetAddress.getByName("192.168.1.1"),
		 * 1337, null); } catch (UnknownHostException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		if(quit)
			arg0.exit();
		if (!l.getServers().isEmpty() && !connecting) {
			BasicClient.connectToServer(l.getServers().get(0), null);
			connecting = true;
			System.out
					.println("connecting to " + l.getServers().get(0).address);
		}
		if(loadLevel && !levelLoaded)
		{
			Level.getInstance().start();
			try
			{
				EntityTemplateManager.getInstance().loadEntityTemplates("general.templates");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			levelLoaded = true;
		}
		if (connected) {
			PlayerInputComponentManager.getInstance().sendInputToPlayerInputComponents(arg0.getInput());
			NetSubSystem.getInstance().pollMessages();
			// CollisionDetectionComponentManager.getInstance().detectCollisionsAndNotifyEntities();
			EntityManager.getInstance().tick((float) arg1);
			NetSubSystem.getInstance().sendBufferedMessages();
			
			EntityManager.getInstance().cleanUpEntities();
		}
		

	}

	public static void main(String[] args) {
		Client test = new Client("Client");
		AppGameContainer app = null;
		try {
			app = new AppGameContainer(test);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
