package gdw;

import java.io.IOException;
import java.nio.ByteBuffer;

import gdw.network.ClientConnection;
import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;
import gdw.network.server.ConnectionInfo;

public class TestServer extends BasicServer
{
	private TestServerCoreLoop loop;
	

	public TestServer() throws IOException
	{
		super(1, "test zwecke",false, false);
		this.loop = new TestServerCoreLoop(this);
	}

	@Override
	protected void playerDisconnected(BasicClientConnection client)
	{
		this.loop.killMe();

	}

	@Override
	protected void playerReconnected(BasicClientConnection client)
	{
		
	}

	@Override
	protected BasicClientConnection incomingConnection(ConnectionInfo info,
			ByteBuffer data)
	{
		this.loop.startGame();
		return new ClientConnection(info, this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new TestServer();
		} catch (IOException e)
		{
			
		}
	}

}
