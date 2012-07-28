package gdw.network.server;

import gdw.network.GenericSocketThread;
import gdw.network.IDiscoFlagAble;
import gdw.network.NETCONSTANTS;
import gdw.network.NetMessageWrapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BasicClientConnection implements IDiscoFlagAble
{
	private static final int messagesPerUpdate = 5;

	private DatagramChannel udpConnection;

	private SocketChannel tcpConnection;
	
	private	GenericSocketThread myThread;
	
	protected final int sharedSecret;

	protected int id;

	private long lastHeartbeat;

	private long pongRequest;

	private boolean discoFlag;

	protected final BasicServer ref;

	public BasicClientConnection(ConnectionInfo info, BasicServer ref) 
	{
		this.ref = ref;
		this.id = info.id;
		this.lastHeartbeat = System.currentTimeMillis();
		this.discoFlag = false;
		this.pongRequest = -1L;
		this.udpConnection = info.udpConnection;
		this.tcpConnection = info.tcpConnection;
		this.sharedSecret = info.sharedSecret;
		
		this.myThread = new GenericSocketThread(udpConnection, tcpConnection,this);		
		
	}
	
	public boolean checkForDisconnect(long current)
	{
		return false;
		/*
		if ((this.lastHeartbeat + NETCONSTANTS.HEARTBEAT_REQUESTIME) < current)
		{
			// check if ping request is needed
			if (pongRequest > -1L)
			{
				if ((pongRequest + NETCONSTANTS.PONG_TIMEOUT) < current)
				{
					// not alive
					this.discoFlag = true;
					return true;
				}
			} else
			{
				// send ping
				sendPing(current);
			}
		}
		return false;*/
	}

	public boolean isDisconnectFlaged()
	{
		return discoFlag;
	}

	private void sendPing(long currentTimeStamp)
	{
		ByteBuffer buf = ByteBuffer.allocate(1);
		buf.put(NETCONSTANTS.PING);
		buf.flip();
		try
		{
			this.udpConnection.write(buf);
			this.pongRequest = currentTimeStamp;
		} catch (IOException e)
		{
			// somethings wrong shutdown the connection
			e.printStackTrace();
			discoFlag = true;
		}
	}

	private void sendPong()
	{
		ByteBuffer buf = ByteBuffer.allocate(1);
		buf.put(NETCONSTANTS.PONG);
		buf.flip();

		try
		{
			this.tcpConnection.write(buf);
		} catch (IOException e)
		{
			// somethings wrong shutdown the connection
			e.printStackTrace();
			discoFlag = true;
		}
	}

	public void disconnect()
	{
		try
		{
			this.tcpConnection.close();
			this.udpConnection.close();
		} catch (IOException e)
		{

		}
	}

	public void sendMSG(ByteBuffer msg, boolean reliable)
	{
		
		
		int oldPos = msg.position();
		short size =(short) (oldPos-1);
		msg.position(0);
		msg.putShort(size);
		msg.position(oldPos);
		
		this.myThread.outMessages.add(new NetMessageWrapper(reliable, msg));
	}
		

	public void pollInput()
	{
		while (!this.myThread.inMessages.isEmpty())
		{
			NetMessageWrapper wrap = this.myThread.inMessages.poll();
			this.incomingMsg(wrap.msg,wrap.reliable);
		}
	}

	private void incomingMsg(ByteBuffer buf, boolean wasReliable)
	{
		switch (buf.get())
		{
		case NETCONSTANTS.PING:
			sendPong();
			break;
		case NETCONSTANTS.PONG:
			this.pongRequest = -1L;
			break;

		case NETCONSTANTS.MESSAGE:
			this.incomingMessage(buf, wasReliable);

		default:
			break;
		}
	}

	protected void revive(ConnectionInfo info)
	{
		this.tcpConnection = info.tcpConnection;
		this.udpConnection = info.udpConnection;
		this.discoFlag = false;	
		this.myThread = new GenericSocketThread(udpConnection, tcpConnection,this);
	}
	
	protected abstract void incomingMessage(ByteBuffer buf, boolean wasReliable);

	public int getId()
	{
		return id;
	}
	
	@Override
	public void discoFlag()
	{
		this.discoFlag = true;
		
	}
}
