import gdwNet.client.ServerInfo;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LobbyState extends BasicGameState {

	private ServerFinder sf;
	int waittime = 500;
	ArrayList<JoinButton> buttons;
	int id;
	
	public LobbyState(int id)
	{
		this.id = id;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Lobby", 250, 0);
		ArrayList<ServerInfo> serverList = sf.getServerList();
		ServerInfo c;
		g.drawString("Servers:", 0, 85);
		for (int i = 0; i < serverList.size(); ++i) {
			c = serverList.get(i);
			g.drawString(c.address + ":" + c.port + "|" + c.infoMsg + "("
					+ c.currentPlayer + "/" + c.maxPlayer + ")" + c.ping, 20,
					i * 15 + 100);
		}
		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).render(gc, g);
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
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
		for (int i = 0; i < sl.size(); ++i) {
			buttons.add(new JoinButton(0, i * 15 + 100, 15, 15, new Image(
					"button.png"), gc, sl.get(i)));
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		// TODO Auto-generated method stub
		if (waittime <= 0) {
			sf.findServers();
			waittime = 500;
		} else {
			waittime -= deltaTime;
		}

	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return id;
	}

}
