package gdw.network;

import java.io.IOException;
import java.nio.ByteBuffer;

import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;
import gdw.network.server.ConnectionInfo;

public class SideScrollerServer extends BasicServer
{
	
	public SideScrollerServer(String infoText) throws IOException
	{
		super(2, infoText, true, false);
	}

	@Override
	protected void playerDisconnected(BasicClientConnection client)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void playerReconnected(BasicClientConnection client)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected BasicClientConnection incomingConnection(ConnectionInfo info,
			ByteBuffer data)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
