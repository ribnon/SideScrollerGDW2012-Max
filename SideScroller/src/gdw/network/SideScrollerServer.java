package gdw.network;

import java.io.IOException;
import java.nio.ByteBuffer;

import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;
import gdw.network.server.ConnectionInfo;

public class SideScrollerServer extends BasicServer
{
	
	public static enum ServerGameStates
	{
		WAITING ,LOBBY, START, PAUSE
	};
	
	private ServerGameStates curState;
	
	private ServerCoreLoop coreLoop;
	
	public SideScrollerServer(String infoText) throws IOException
	{
		super(2, infoText, true, false);
		this.curState = ServerGameStates.WAITING;
		this.coreLoop = new ServerCoreLoop(this);
	}

	@Override
	protected void playerDisconnected(BasicClientConnection client)
	{
		//unhandled
		this.shutMeDown();
	}

	@Override
	protected void playerReconnected(BasicClientConnection client)
	{
		//kommt nicht vor
	}

	@Override
	protected BasicClientConnection incomingConnection(ConnectionInfo info,
			ByteBuffer data)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void killMe()
	{
		super.shutMeDown();
		this.coreLoop.interrupt();
	}
	
	public static void main(String[] args)
	{
		
		String infoText;
		//get info text
		if(args.length>1)
		{
			infoText = args[0];
		}else
		{
			infoText = NETCONSTANTS.DEFAULT_INFOTEXT;
		}
		try
		{
			new SideScrollerServer(infoText);
		} catch (IOException e)
		{
			e.printStackTrace();
		}	
	}

	public ServerGameStates getCurState()
	{
		return curState;
	}
}
