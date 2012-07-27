package gdw.network;

import java.nio.ByteBuffer;

import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;
import gdw.network.server.ConnectionInfo;

public class PlayerConnection extends BasicClientConnection
{

	public PlayerConnection(ConnectionInfo info, BasicServer ref)
	{
		super(info, ref);

	}

	@Override
	protected void incomingMessage(ByteBuffer buf, boolean wasReliable)
	{
		if(((SideScrollerServer)this.ref).getCurState() == SideScrollerServer.ServerGameStates.RUNNING)
		{
			NetSubSystem.getInstance().processMessage(buf);
		}
	}

}
