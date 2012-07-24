package gdw.network;

import java.nio.ByteBuffer;

import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;
import gdw.network.server.ConnectionInfo;

public class ClientConnection extends BasicClientConnection
{
	
	public ClientConnection(ConnectionInfo info, BasicServer ref)
	{
		super(info, ref);
		
	}

	@Override
	protected void incomingMessage(ByteBuffer buf, boolean wasReliable)
	{
		NetSubSystem.getInstance().processMessage(buf);
	}

}
