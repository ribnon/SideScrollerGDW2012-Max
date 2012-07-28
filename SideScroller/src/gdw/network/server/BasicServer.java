package gdw.network.server;

import gdw.network.NETCONSTANTS;
import gdw.network.INetworkBridge;
import gdw.network.RESPONSECODES;
import gdw.utils.DefaultCharSet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BasicServer implements INetworkBridge
{
	// *threads*

	private MulticastresponseThread mThread;
	private final BroadcastresponseThread bThread;
	private final IncomingConnectionHandlerThread iThread;

	// *attributes*
	/** maximum allowed clients on server */
	protected int maxPlayer;
	/** current number of connected clients */
	private int currentConnections;
	/** hashmap from playerId to client */
	private final HashMap<Integer, BasicClientConnection> clientConnections;
	/** counter for playerIds */
	private int idCounter;
	/** current */
	protected String infoText;
	/** random long for serverId */
	protected final long serverId;
	/** bounded tcp Port */
	private final int tcpPort;

	/** the byte message */
	private ByteBuffer broadcastResponse;
	
	/** the byte message attached to the broadcastResponse buffer */
	private ByteBuffer broadcastResponseAttachment;
	

	// *to work with*
	/** queue that has all the joinrequest since last update*/
	private final ConcurrentLinkedQueue<JoinRequestWrapper> joinRequests;
	/**  */ 
	private final ConcurrentLinkedQueue<ReconnectRequestWrapper> recoRequests;
	/** older first */
	private final ConcurrentLinkedQueue<LeaverDataWrapper> leaverStack;
	
	// *flags*
	// let clients reconnect after a disconnect/timeout
	private boolean blockReconnector;
	// drop all joinrequest
	private boolean blockNewconncector;
	// a internal logic flag for no playerlimit
	private boolean noPlayerLimit;

	public BasicServer(int maxPlayer) throws IOException
	{
		this(maxPlayer, NETCONSTANTS.DEFAULT_INFOTEXT, false, false);
	}

	public BasicServer(int maxPlayer, String infoText,
			boolean blockReconnector, boolean blockNewconnector)
			throws IOException
	{
		// setting up
		this.maxPlayer = maxPlayer;
		currentConnections = 0;
		this.clientConnections = new HashMap<Integer, BasicClientConnection>();
		this.idCounter = 2;
		this.infoText = infoText;
		this.serverId = new Random().nextLong();
		this.broadcastResponse = ByteBuffer
				.allocate(NETCONSTANTS.BROADCAST_PACKET_LENGTH);
		
		this.recoRequests = new ConcurrentLinkedQueue<ReconnectRequestWrapper>();
		this.leaverStack = new ConcurrentLinkedQueue<LeaverDataWrapper>();

		// set flags
		this.blockReconnector = blockReconnector;
		this.blockNewconncector = blockNewconnector;

		this.noPlayerLimit = maxPlayer < 1;

		// to work with
		this.joinRequests = new ConcurrentLinkedQueue<JoinRequestWrapper>();
		GDWServerLogger.logMSG("Server loaded");

		// start Threads
		this.iThread = new IncomingConnectionHandlerThread(this);
		GDWServerLogger.logMSG("TCP thread started on port "
				+ this.iThread.getBoundPort());

		this.tcpPort = this.iThread.getBoundPort();
		updateBroadcastMessage(updatePart.ALL);

		this.bThread = new BroadcastresponseThread(this);

		try
		{
			this.mThread = new MulticastresponseThread(this);
		} catch (IOException e)
		{
			GDWServerLogger
					.logMSG("Es kann kein MulticastSocket erstellt werden, nicht weiter schlimm");
			this.mThread = null;
		}

		GDWServerLogger.logMSG("UDP thread started on port "
				+ this.bThread.getBoundedPort());
	}

	private static enum updatePart
	{
		ALL, CUR_PLAYER, MAX_PLAYER, INFO_TEXT, TCP_PORT, SERVER_ID, CUSTOM_ATTACHMENT
	}
	
	public LinkedList<BasicClientConnection> getAllConnected()
	{
		LinkedList<BasicClientConnection> result = new LinkedList<BasicClientConnection>();
		Iterator<Integer> iter = this.clientConnections.keySet().iterator();
		while(iter.hasNext())
		{
			BasicClientConnection client = this.clientConnections.get(iter.next());
			if(!client.isDisconnectFlaged())
			{
				result.add(client);
			}
		}
		return result;
	}

	final int CUR_PLAYER_POS = 0;
	final int MAX_PLAYER_POS = CUR_PLAYER_POS + Integer.SIZE;
	final int TCP_PORT_POS = MAX_PLAYER_POS + Integer.SIZE;
	final int SERVER_ID_POS = TCP_PORT_POS + Integer.SIZE;
	final int INFO_TEXT_POS = SERVER_ID_POS + Long.SIZE;

	protected void updateBroadcastMessage(updatePart part)
	{
		switch (part)
		{
		case ALL:
		{
			this.broadcastResponse.position(0);
			this.broadcastResponse.putInt(this.currentConnections);
			this.broadcastResponse.putInt(this.maxPlayer);
			this.broadcastResponse.putInt(this.tcpPort);
			this.broadcastResponse.putLong(this.serverId);
			byte[] arr = this.infoText.getBytes(DefaultCharSet
					.getDefaultCharset());
			this.broadcastResponse.put((byte) arr.length);
			this.broadcastResponse.put(arr);
		}
		break;

		case CUR_PLAYER:
		{
			this.broadcastResponse.position(CUR_PLAYER_POS);
			this.broadcastResponse.putInt(this.currentConnections);
		}
		break;

		case MAX_PLAYER:
		{
			this.broadcastResponse.position(MAX_PLAYER_POS);
			this.broadcastResponse.putInt(this.maxPlayer);
		}
		break;

		case INFO_TEXT:
		{
			this.broadcastResponse.position(INFO_TEXT_POS);
			byte[] arr = this.infoText.getBytes(DefaultCharSet
					.getDefaultCharset());
			this.broadcastResponse.put((byte) arr.length);
			this.broadcastResponse.put(arr);
			//net to add custom attachment
			if(this.broadcastResponseAttachment == null)
			{
				this.broadcastResponse.limit(this.broadcastResponseAttachment.position());
				return;
			}
			this.broadcastResponse.put(this.broadcastResponseAttachment);
			this.broadcastResponse.limit(this.broadcastResponse.position());
			
		}
		break;

		case TCP_PORT:
		{
			this.broadcastResponse.position(TCP_PORT_POS);
			this.broadcastResponse.putInt(this.tcpPort);
		}
		break;

		case SERVER_ID:
		{
			this.broadcastResponse.position(SERVER_ID_POS);
			this.broadcastResponse.putLong(this.serverId);
		}
		break;
		
		case CUSTOM_ATTACHMENT:
		{
			//get position to write on
			int pos = INFO_TEXT_POS + 1 + this.infoText.getBytes(DefaultCharSet.getDefaultCharset()).length;
			// postion from infotext + size info of followed bytes + byte for text
			
			if(this.broadcastResponseAttachment == null)
			{
				this.broadcastResponse.limit(pos);
				return;
			}	
			
			if((pos - this.broadcastResponse.capacity()) < this.broadcastResponseAttachment.limit())
			{
				throw new IndexOutOfBoundsException("Your attachment is to big to fit in message length of broadcastresponceMessage");
			}
			
			this.broadcastResponse.position(pos);
			this.broadcastResponse.put(this.broadcastResponseAttachment);
			
		}break;

		default:
		break;
		}
		this.broadcastResponse.position(0);
	}

	@Override
	public void pollNetInput()
	{
		this.processIncoming();
		Iterator<Integer> iter = this.clientConnections.keySet().iterator();
		LinkedList<Integer> toLeave = new LinkedList<Integer>();
		while (iter.hasNext())
		{
			Integer pos = iter.next();
			BasicClientConnection client = this.clientConnections.get(pos);
			if (client.checkForDisconnect(System.currentTimeMillis()))
			{
				toLeave.add(pos);				
			}
		}
		while (!toLeave.isEmpty())
		{
			disconnectPlayer(this.clientConnections.get(toLeave.pop()));
		}
		iter = this.clientConnections.keySet().iterator();
		while (iter.hasNext())
		{
			this.clientConnections.get(iter.next()).pollInput();
		}
	}
	
	
	protected void addJoinRequest(ConnectionInfo info, ByteBuffer data)
	{
		if(blockNewconncector)
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, info);
		}else
		{
			if((!noPlayerLimit)&&(this.currentConnections == maxPlayer))
			{
				sendErrorCodeToRequestAndClose(RESPONSECODES.SERVER_FULL, info);
			}else
			{
				joinRequests.add(new JoinRequestWrapper(info,data));
			}
		}
		
	}
	
	protected void addReconnectRequest(ConnectionInfo info)
	{
		if(blockReconnector)
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, info);
		}else
		{
			if((!noPlayerLimit)&&(this.currentConnections == maxPlayer))
			{
				sendErrorCodeToRequestAndClose(RESPONSECODES.SERVER_FULL, info);
			}else
			{
				recoRequests.add(new ReconnectRequestWrapper(info));
			}
		}
		
	}
		
	public int getAmountOfConnections()
	{
		return currentConnections;
	}
	
	private void processReconnectors()
	{
		//handle 
		while(!this.recoRequests.isEmpty())
		{
			ReconnectRequestWrapper rec = this.recoRequests.poll();
			LeaverDataWrapper found = null;
			Iterator<LeaverDataWrapper> iter = this.leaverStack.iterator();
			while (iter.hasNext())
			{
				LeaverDataWrapper temp = iter.next();
				if(temp.compareWithReco(rec))
				{
					found = temp;
					break;
				}
			}
			//you send me shit!
			if(found == null)
			{
				sendErrorCodeToRequestAndClose(RESPONSECODES.DATA_CORRUPTED, rec.info);
			}else
			{
				found.client.revive(rec.info);
				sendOKAndDataToRequest(rec.info,rec.info.id);
				//highlevel
				playerReconnected(found.client);
				
				//low level
				this.currentConnections++;
				this.leaverStack.remove(found);
			}
		}
		this.updateBroadcastMessage(updatePart.CUR_PLAYER);
	}

	private void processJoinRequests()
	{
		{
			//send to all that is full
			while(!this.joinRequests.isEmpty())
			{
				JoinRequestWrapper req = this.joinRequests.poll();
				//highlevel
				BasicClientConnection client = incomingConnection(req.info, req.data);
				if(client != null)
				{
					//lowlevel
					++this.currentConnections;
					client.id = this.idCounter++;
					this.clientConnections.put(client.getId(),client);
					sendOKAndDataToRequest(req.info,client.id);
				}
			}
			this.updateBroadcastMessage(updatePart.CUR_PLAYER);
		}
	}
	
	private void forgetOldLeaver()
	{
		long ttl = System.currentTimeMillis() + NETCONSTANTS.DISPOSE_LEAVERDATA_TTL;
		while (!this.leaverStack.isEmpty())
		{
			if(this.leaverStack.peek().timestamp > ttl)
			{
				this.leaverStack.remove();
			}else
			{
				break;
			}
			
		}
	}
	
	private void processIncoming()
	{
		//handle reconnector and leaver cleanup
		if(!blockReconnector)
		{
			if(!this.recoRequests.isEmpty())
			{
				processReconnectors();
			}
			if(!this.leaverStack.isEmpty())
			{
				forgetOldLeaver();
			}
						
		}
		//handle joinrequests
		if((!blockNewconncector)&&(!this.joinRequests.isEmpty()))
		{
			processJoinRequests();
		}else
		{
			this.joinRequests.clear();
		}
		
	}

	protected void sendErrorCodeToRequestAndClose(byte code, ConnectionInfo info)
	{
		sendErrorCodeToRequest(code, info.tcpConnection);
		info.closeOpenSockets();
	}

	private void sendErrorCodeToRequest(byte code, SocketChannel socket)
	{
		ByteBuffer send = ByteBuffer.allocate(1);
		send.put(code);
		send.flip();

		try
		{
			socket.write(send);
		} catch (IOException e)
		{
		}
	}
	
	private void sendOKAndDataToRequest(ConnectionInfo info, int id)
	{
		ByteBuffer buf = ByteBuffer.allocate(Byte.SIZE+Integer.SIZE+Integer.SIZE+Integer.SIZE);
		buf.put(RESPONSECODES.OK);
		buf.putInt(info.udpConnection.socket().getLocalPort());
		buf.putInt(id);
		buf.putInt(info.sharedSecret);
		buf.flip();
		try
		{
			info.tcpConnection.write(buf);
		} catch (IOException e)
		{
			info.closeOpenSockets();
		}
	}
	
	public void shutMeDown()
	{
		if (this.mThread != null)
			this.mThread.interrupt();
		this.bThread.interrupt();
		this.iThread.interrupt();
		
		this.leaverStack.clear();
		clearAllRequests();
		disconnetAll();
	}
	
	private void disconnetAll()
	{
		this.currentConnections = 0;
		Iterator<Integer> iter = this.clientConnections.keySet().iterator();
		while (iter.hasNext())
		{
			this.clientConnections.get(iter.next()).disconnect();
		}
		this.clientConnections.clear();
		this.idCounter = 0;
	}
	
	private void clearAllRequests()
	{
		while(!this.joinRequests.isEmpty())
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, this.joinRequests.poll().info);
		}
		while(!this.recoRequests.isEmpty())
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, this.recoRequests.poll().info);
		}
	}
	
	private void clearRecoRequests()
	{
		while(!this.recoRequests.isEmpty())
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, this.recoRequests.poll().info);
		}
	}
	
	private void clearJoinRequest()
	{
		while(!this.joinRequests.isEmpty())
		{
			sendErrorCodeToRequestAndClose(RESPONSECODES.CONNECT_REFUSE, this.joinRequests.poll().info);
		}
	}

	protected abstract void playerDisconnected(BasicClientConnection client);
	
	protected abstract void playerReconnected(BasicClientConnection client);
	
	protected abstract BasicClientConnection incomingConnection(
			ConnectionInfo info, ByteBuffer data);

	private void disconnectPlayer(BasicClientConnection client)
	{
		client.disconnect();
		playerDisconnected(client);
		//should be saved for reconnect
		if(!blockReconnector)
		{
			this.leaverStack.add(new LeaverDataWrapper(client));
		}else
		{
			this.clientConnections.remove(client.id);
		}
		--this.currentConnections;

	}

	public int getMaxPlayer()
	{
		return maxPlayer;
	}

	public void setMaxPlayer(int maxPlayer)
	{
		this.maxPlayer = maxPlayer;
		this.updateBroadcastMessage(updatePart.MAX_PLAYER);
		this.noPlayerLimit = maxPlayer <1;
	}

	public boolean isBlockReconnector()
	{
		return blockReconnector;
	}

	public void setBlockReconnector(boolean blockReconnector)
	{
		this.blockReconnector = blockReconnector;
		if(blockReconnector)
		{
			this.clearRecoRequests();
			this.leaverStack.clear();
		}
	}

	public boolean isBlockNewconncector()
	{
		return blockNewconncector;
	}

	public void setBlockNewconncector(boolean blockNewconncector)
	{
		this.blockNewconncector = blockNewconncector;
		if(blockNewconncector)
		{
			this.clearJoinRequest();
		}
	}

	public long getServerId()
	{
		return serverId;
	}

	public boolean isNoPlayerLimit()
	{
		return noPlayerLimit;
	}
	
	public String getInfoText()
	{
		return infoText;
	}

	public void setInfoText(String infoText)
	{
		this.infoText = infoText;
		this.updateBroadcastMessage(updatePart.INFO_TEXT);
	}
	
	public ByteBuffer getBroadcastResponseAttachment()
	{
		return broadcastResponseAttachment;
	}
	
	/**
	 * Adds the given ByteBuffer on broadcast Messages. Note the ByteBuffer will rewinded in this 
	 * methode. Null will be delete the attachment.
	 * 
	 * @param broadcastResponseAttachment the ByteBuffer to attache on brodcast messages
	 * 
	 * @throws IndexOutOfBoundsException When given Buffer ist too big to fit in.
	 */

	public void setBroadcastResponseAttachment(
			ByteBuffer broadcastResponseAttachment)
	{
		broadcastResponseAttachment.flip();
		this.broadcastResponseAttachment = broadcastResponseAttachment;
		try
		{
			this.updateBroadcastMessage(updatePart.CUSTOM_ATTACHMENT);
		}catch (IndexOutOfBoundsException e)
		{
			this.broadcastResponseAttachment = null;
			this.updateBroadcastMessage(updatePart.CUSTOM_ATTACHMENT);
			throw e;
		}
	}
	
	public BasicClientConnection getPlayerByID(int id)
	{
		return this.clientConnections.get(id);
	}

	/**
	 * Finger weg von der Methode und fasst den broadcastResponse nicht direkt
	 * an. Siehst du das ist druchgestrichen!!!
	 * 
	 * Dont use this methode or broadcastResponce.
	 * 
	 * @return
	 */
	@Deprecated
	protected ByteBuffer getBroadcastResponse()
	{
		return broadcastResponse;
	}
	
	public ByteBuffer getMessageBuffer()
	{
		ByteBuffer buf = ByteBuffer.allocate(NETCONSTANTS.PACKAGELENGTH);
		buf.clear();
		//buf.putShort((short)(NETCONSTANTS.PACKAGELENGTH-1));//placeholder
		buf.put(NETCONSTANTS.MESSAGE);
		return buf;
	}
	
	public void sendMessage(ByteBuffer msg, boolean reliable)
	{
		//ist send to all
		Iterator<Integer> iter = this.clientConnections.keySet().iterator();
		while (iter.hasNext())
		{
			BasicClientConnection client = this.clientConnections.get(iter.next());
			if(!client.isDisconnectFlaged())	
				client.sendMSG(msg, reliable);
		}
	}
}
