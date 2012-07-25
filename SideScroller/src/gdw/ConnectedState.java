package gdw;

import java.nio.ByteBuffer;

import gdw.network.client.BasicClient;
import gdw.network.client.IBasicClientListener;
import gdw.network.client.ServerInfo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ConnectedState extends BasicGameState {
	ServerInfo sInfo;
	int id;
	String rcvd;

	public ConnectedState(ServerInfo si, int id) {
		sInfo = si;
		this.id = id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		rcvd = new String();

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString(sInfo.infoMsg, 250, 0);
		g.drawString(rcvd, 0, 100);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}

	@Override
	public int getID() {
		return id;
	}

}
