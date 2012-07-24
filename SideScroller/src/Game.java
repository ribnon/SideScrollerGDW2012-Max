import java.util.ArrayList;

import gdwNet.client.ServerInfo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Game extends org.newdawn.slick.BasicGame {

	private ServerFinder sf;
	int waittime = 500;
	ArrayList<JoinButton> buttons;
	
	public Game(String title) {
		super("Blubb");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		ArrayList<ServerInfo> serverList = sf.getServerList();
		ServerInfo c;
		for (int i = 0; i < serverList.size(); ++i) {
			c = serverList.get(i);
			arg1.drawString(c.address + ":" + c.port + "|" + c.infoMsg + "("
					+ c.currentPlayer + "/" + c.maxPlayer + ")" + c.ping, 20,
					i * 15 + 100);
		}
		for(int i = 0; i < buttons.size(); ++i)
		{
			buttons.get(i).render(arg0, arg1);
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
			buttons = new ArrayList<JoinButton>();
			sf = new ServerFinder();
			sf.register();
			sf.findServers();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<ServerInfo> sl = sf.getServerList();
			for(int i = 0; i < sl.size(); ++i)
			{
				buttons.add(new JoinButton(0, i*15+100, 15, 15, new Image("button.png"), arg0, sl.get(i)));
			}
			
	}

	@Override
	public void update(GameContainer arg0, int deltaTime) throws SlickException {
		// TODO Auto-generated method stub
		if(waittime <= 0)
		{
			sf.findServers();
			waittime = 500;
		}
		else
		{
			waittime -= deltaTime;
		}

	}

	public static void main(String[] args) throws SlickException {
		Game test = new Game("0");
		AppGameContainer app = new AppGameContainer(test);
		app.start();
	}

}
