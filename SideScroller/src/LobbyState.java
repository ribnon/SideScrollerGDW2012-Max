import gdwNet.client.BasicClient;
import gdwNet.client.IBasicClientListener;
import gdwNet.client.ServerInfo;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LobbyState extends BasicGameState implements IBasicClientListener {

	// private ServerFinder sf;
	int waittime = 500;
	ArrayList<JoinButton> buttons;
	int id;
	private ArrayList<ServerInfo> servers;
	private boolean registered = false;
	
	GameContainer gc;

	public LobbyState(int id) {
		this.id = id;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Lobby", 250, 0);
		ServerInfo c;
		g.drawString("Servers:", 0, 85);
		for (int i = 0; i < servers.size(); ++i) {
			c = servers.get(i);
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
		this.gc = gc;
		buttons = new ArrayList<JoinButton>();
		register();
		findServers();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		// TODO Auto-generated method stub
		if (waittime <= 0) {
			findServers();
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

	public void register() {
		if (!registered)
			BasicClient.setListener(this);
	}

	public void findServers() {
		if (!registered)
			register();

		servers = new ArrayList<ServerInfo>();
		BasicClient.refreshServerList();
	}

	@Override
	public void serverResponce(ServerInfo info) {
		for (int i = 0; i < servers.size(); ++i) {
			if (servers.get(i).id == info.id)
				return;
		}
		servers.add(info);
		try {
			buttons.add(new JoinButton(20, servers.size() * 15 + 100, 15, 15, new Image("button.png"), gc, info));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void connectionUpdate(int msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionEstablished(BasicClient clientRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incommingMessage(ByteBuffer msg, boolean wasReliable) {
		// TODO Auto-generated method stub

	}
}