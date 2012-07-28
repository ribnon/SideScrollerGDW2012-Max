package gdw.network;

import gdw.network.server.GDWServerLogger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GenericSocketThread extends Thread
{
	private DatagramChannel udpConnection;

	private SocketChannel tcpConnection;

	private Selector udpSelector;

	private Selector tcpSelector;

	private final IDiscoFlagAble bridge;

	public final ConcurrentLinkedQueue<NetMessageWrapper> inMessages;

	public final ConcurrentLinkedQueue<NetMessageWrapper> outMessages;

	public GenericSocketThread(DatagramChannel udpConnection,
			SocketChannel tcpConnection, IDiscoFlagAble bridge)
	{
		super();
		this.udpConnection = udpConnection;
		this.tcpConnection = tcpConnection;
		this.inMessages = new ConcurrentLinkedQueue<NetMessageWrapper>();
		this.outMessages = new ConcurrentLinkedQueue<NetMessageWrapper>();
		this.bridge = bridge;

		try
		{
			this.udpConnection.configureBlocking(false);
			this.tcpConnection.configureBlocking(false);

			this.tcpSelector = Selector.open();
			this.tcpConnection.register(this.tcpSelector,
					tcpConnection.validOps());

			this.udpSelector = Selector.open();
			this.udpConnection.register(this.udpSelector,
					this.udpConnection.validOps());

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		this.start();
	}

	private boolean deBufferNetwork(ByteBuffer netRead, ByteBuffer targetMessage)
	{
		boolean wasComplete = true;
		int toRead = targetMessage.remaining();
		if (netRead.remaining() < toRead)
		{
			wasComplete = false;
		} else
		{
			netRead.limit(netRead.position() + toRead);
		}
		targetMessage.put(netRead);
		return wasComplete;
	}

	private void proccessSelctionKey(SelectionKey tcpKey, SelectionKey udpKey)
			throws IOException
	{
		ByteBuffer reader = ByteBuffer.allocate(NETCONSTANTS.PACKAGELENGTH * 3);

		while ((tcpKey.isValid()) && (udpKey.isValid()))
		{

			int readBytes;
			// read

			reader.clear();
			reader.position(0);
			readBytes = this.tcpConnection.read(reader);
			if(readBytes != 0)
			{
				if (readBytes == -1)
				{
					GDWServerLogger.logMSG("-1 bytes wuren gelsen");
					return;
				}
					
				// tcp
				while(readBytes < 3)
				{
					readBytes += this.tcpConnection.read(reader);
				}
				reader.flip();
				while (reader.hasRemaining())
				{
					int target = reader.getShort();
					GDWServerLogger.logMSG(target + " ist das Ziel");
					ByteBuffer message = ByteBuffer.allocate(target);
					message.clear();
					while (!deBufferNetwork(reader, message))
					{
						reader.position(0);
						this.tcpConnection.read(reader);
						reader.flip();
					}
					this.inMessages.add(new NetMessageWrapper(true, message));
				}
			}
			
			
			reader.clear();
			reader.position(0);
			readBytes = this.udpConnection.read(reader);
			if(readBytes != 0)
			{
				if (readBytes == -1)
					return;
				while(readBytes < 3)
				{
					readBytes += this.udpConnection.read(reader);
				}
				
				reader.flip();
				// udp
				while (reader.hasRemaining())
				{
					int target = reader.getShort();
					ByteBuffer message = ByteBuffer.allocate(target);
					message.clear();
					while (!deBufferNetwork(reader, message))
					{
						reader.position(0);
						this.tcpConnection.read(reader);
						reader.flip();
					}
					this.inMessages.add(new NetMessageWrapper(false, message));
				}
			}

			// write
			while (!this.outMessages.isEmpty())
			{
				NetMessageWrapper wrap = outMessages.poll();
				ByteBuffer buf = wrap.msg;
				if (wrap.reliable)
				{
					// tcp
					if (udpKey.isWritable())
					{
						while (buf.hasRemaining())
						{
							this.tcpConnection.write(buf);
						}
					} else
					{
						// pushback
						this.outMessages.add(wrap);
					}
				} else
				{
					// udp
					if (tcpKey.isWritable())
					{
						while (buf.hasRemaining())
						{
							this.udpConnection.write(buf);
						}
					} else
					{
						this.outMessages.add(wrap);
					}
				}
			}

		}
	}

	@Override
	public void run()
	{
		while (!this.isInterrupted())
		{
			SelectionKey tcpKey, udpKey;
			try
			{
				this.tcpSelector.select();
				this.udpSelector.select();
			} catch (IOException e)
			{

				e.printStackTrace();
			}
			Iterator<SelectionKey> itTcp = this.tcpSelector.keys().iterator();
			Iterator<SelectionKey> itUdp = this.udpSelector.keys().iterator();

			tcpKey = itTcp.next();

			udpKey = itUdp.next();

			try
			{
				proccessSelctionKey(tcpKey, udpKey);
				itTcp.remove();
				itUdp.remove();
			} catch (IOException e)
			{
				e.printStackTrace();
				this.bridge.discoFlag();
				break;
			}
		}
	}
}