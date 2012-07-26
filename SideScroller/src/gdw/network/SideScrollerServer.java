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
		WAITING ,LOBBY, START, PAUSE, RUNNING
	};
	
	private ServerGameStates curState;
	
	private ServerCoreLoop coreLoop;
	
	public SideScrollerServer(String infoText) throws IOException
	{
		super(1, infoText, true, false);
		this.curState = ServerGameStates.WAITING;
		NetSubSystem.initalise(1, true, this);
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
		if(this.getAmountOfConnections() != this.maxPlayer -1)
		{
			this.curState = ServerGameStates.START;
		}
		
		if(this.curState == ServerGameStates.WAITING)
		{
			this.curState = ServerGameStates.LOBBY;
		}
		return new PlayerConnection(info, this);
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
	
	public void startComplete()
	{
		if(curState== ServerGameStates.START)
		{
			curState = ServerGameStates.RUNNING;
		}
	}
}
