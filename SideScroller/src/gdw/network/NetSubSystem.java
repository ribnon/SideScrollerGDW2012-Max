package gdw.network;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Message;
import gdw.network.client.BasicClient;
import gdw.network.messageType.DeadReckoningNetMessage;
import gdw.network.messageType.EntityBusNetMessage;
import gdw.network.messageType.EntityDeSpawnNetMessage;
import gdw.network.messageType.EntitySpawnNetMessage;
import gdw.network.messageType.TimeSyncMessage;
import gdw.network.server.BasicClientConnection;
import gdw.network.server.BasicServer;

public class NetSubSystem
{
	private static final long TIMESYNC_INTERVAL = 1000L;
	
	private static NetSubSystem singelton;
	
	/**
	 * 1 wenn Server. Sonst höhere Zahl
	 */
	private final int playerID;
	
	/**
	 * Bin ich Server, wenn ja verhalte ich mich anders usw.
	 */
	private final boolean serverFlag;
	
	/**
	 * Eine Referenz auf den Clientkomponente, nötig um Nachrichten als
	 * Client zu feuern. Null wenn Server
	 */
	private final BasicClient clientRef;
	
	/**
	 * Eine Referenz auf die Serverkomponenten, nötig um Nachrichten als
	 * Server zu feueren. NUll wenn client
	 */
	private final BasicServer serverRef;
	
	private long lastTimeSync;
	
	private final HashMap<Integer, Float> roundTripMap;
	
	
	private final LinkedList<NetComponent> listOfNetComponents;
	
	private final LinkedList<EntitySpawnNetMessage> listOfSpawnMessages;
	private final LinkedList<EntityBusNetMessage> listOfBusMessages;
	private final LinkedList<EntityDeSpawnNetMessage> listOfDeSpawnMessages;
	private final LinkedList<DeadReckoningNetMessage> listOfDeadReckonigMessages;
	private final LinkedList<TimeSyncMessage> listOfTimeSyncMessages;
	
	
	private NetSubSystem(int playerID, boolean serverFlag, BasicClient cRef, BasicServer sRef)
	{
		this.playerID = playerID;
		this.serverFlag = serverFlag;
		this.clientRef = cRef;
		this.serverRef = sRef;
		this.listOfNetComponents = new LinkedList<NetComponent>();
		this.listOfSpawnMessages = new LinkedList<EntitySpawnNetMessage>();
		this.listOfBusMessages = new LinkedList<EntityBusNetMessage>();
		this.listOfDeSpawnMessages = new LinkedList<EntityDeSpawnNetMessage>();
		this.listOfDeadReckonigMessages = new LinkedList<DeadReckoningNetMessage>();
		this.listOfTimeSyncMessages = new LinkedList<TimeSyncMessage>();
		
		this.roundTripMap = new HashMap<Integer,Float>();
		this.lastTimeSync = 0L;
		
	}
	
	public static void initalise(int playerID, boolean serverFlag, BasicClient cRef, BasicServer sRef)
	{
		if(NetSubSystem.singelton == null)
		{
			System.out.println("Alter ich wurde schon gebaut, ich ignorie das mal");
			return;
		}
		NetSubSystem.singelton = new NetSubSystem(playerID, serverFlag, cRef, sRef);
	}
	
	public static NetSubSystem getInstance()
	{
		if(singelton == null) {
			
//			singelton = new NetSubSystem(0, false, ,(BasicServer)new SideScrollerServer(""));

		}
		return NetSubSystem.singelton;
	}
	
	//singelton
	public int getPlayerID()
	{
		return this.playerID;
	}
	
	public boolean isServer()
	{
		return this.serverFlag;
	}
	
	public void pollMessages()
	{
		if(this.serverFlag)
		{
			this.serverRef.processInputData();
		}else
		{
			this.clientRef.pollInput();
		}
	}
	
	public void processMessage(ByteBuffer buf)
	{
		EntityManager ref = EntityManager.getInstance();
		switch (buf.get())
		{
		case NetMessageType.DeadReckoningMessageType:
			DeadReckoningNetMessage[] dmsg = DeadReckoningNetMessage.getFromByteBuffer(buf);
			for(int i=0;i< dmsg.length;++i)
			{
				Entity ente = ref.getEntity(dmsg[i].entityID);
				((NetComponent)ente.getComponent(NetComponent.COMPONENT_TYPE)).deadReckoningMessageReceive(dmsg[i]);
			}			
		break;
		
		case NetMessageType.EntityBusMessageType:
			EntityBusNetMessage[] ebnm = EntityBusNetMessage.getFromByteBuffer(buf);
			for(int i=0;i<ebnm.length;++i)
			{
				ref.getEntity(ebnm[i].entityID).message(ebnm[i].busMessage);	
			}		
		break;
		
		case NetMessageType.EntitySpawnMessageType:
			EntitySpawnNetMessage[] sqnms = EntitySpawnNetMessage.getFromByteBuffer(buf);
			for(int i=0;i<sqnms.length;++i)
			{
				EntitySpawnNetMessage item = sqnms[i];
				EntityTemplate enteT =  EntityTemplateManager.getInstance().getEntityTemplate(item.templateName);
				enteT.createEntity(item.id,item.posX,item.posY,item.orientation);	
			}		
		break;
		
		case NetMessageType.EntityDespawnMessageType:
			EntityDeSpawnNetMessage[] arrDNM = EntityDeSpawnNetMessage.getFromByteBuffer(buf);
			for(int i=0;i<arrDNM.length;++i)
			{
				ref.getEntity(arrDNM[i].entityID).markForDestroy();
			}
		break;
		
		case NetMessageType.TimeSyncMessageType:
			if(this.serverFlag)
			{
				TimeSyncMessage msg = TimeSyncMessage.getFromByteBuffer(buf);
				float calcedRoundtip = (System.currentTimeMillis() - msg.timeStamp)/2.0f;
				this.roundTripMap.put(msg.clientID, calcedRoundtip);
			}else
			{
				this.listOfTimeSyncMessages.add(TimeSyncMessage.getFromByteBuffer(buf));
			}

		break;	

		default:
		break;
		}
	}
	
	public void sendSpawn(String template, int id, float posX, float posY, float orientation)
	{
		if(!this.serverFlag)
			return;
		this.listOfSpawnMessages.add(new EntitySpawnNetMessage(template, id, posX, posY, orientation));
	}
	
	public void sendDeSpawn(int id)
	{
		if(!this.serverFlag)
			return;
		this.listOfDeSpawnMessages.add(new EntityDeSpawnNetMessage(id));
	}
	
	public void sendBusMessage(int entityID, Message msg)
	{
		this.listOfBusMessages.add(new EntityBusNetMessage(entityID, msg));
	}
	
	public void addNetComponentToList(NetComponent item)
	{
		this.listOfNetComponents.add(item);
	}
	
	public void removeNetComponentToList(NetComponent item)
	{
		this.listOfNetComponents.remove(item);
	}
	
	public void simulateGhosts(float deltaT)
	{
		for(NetComponent comp : this.listOfNetComponents)
		{
			comp.simulateGhost(deltaT);
		}
	}	
	
	private void sendDeadReckAndAddRoundtip(ByteBuffer buf, int writePositon)
	{
		LinkedList<BasicClientConnection> clients = this.serverRef.getAllConnected();
		int oldPostion = buf.position();
		while(!clients.isEmpty())
		{
			BasicClientConnection client = clients.poll();
			ByteBuffer sendBuf = buf.duplicate();
			sendBuf.position(writePositon);
			sendBuf.putFloat(this.roundTripMap.get(client.getId()));
			sendBuf.position(oldPostion);
			client.sendMSG(sendBuf, false);
		}
	}
	
	public void sendBufferedMessages()
	{
		ByteBuffer buf = null;
		if(this.serverFlag)
		{
			//spawn
			while(!this.listOfSpawnMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				EntitySpawnNetMessage.fillInByteBuffer(this.listOfSpawnMessages, buf);
				this.serverRef.sendToAll(buf, true);
			}
			//deadReck
			while(!this.listOfDeadReckonigMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				DeadReckoningNetMessage.fillInByteBuffer(listOfDeadReckonigMessages, buf);
				sendDeadReckAndAddRoundtip(buf, DeadReckoningNetMessage.ROUNDTIP_WRITE_POSITION);
			}
			//tunnel
			while(!this.listOfBusMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				EntityBusNetMessage.fillInByteBuffer(this.listOfBusMessages, buf);
				this.serverRef.sendToAll(buf, true);
			}
			//deSpawn
			while(!this.listOfDeSpawnMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				EntityDeSpawnNetMessage.fillInByteBuffer(this.listOfDeSpawnMessages, buf);
			}
		}else
		{
			while(!this.listOfTimeSyncMessages.isEmpty())
			{
				buf = this.clientRef.getMessageBuffer();
				TimeSyncMessage.fillInByteBuffer(this.listOfTimeSyncMessages.poll(), buf, playerID);
				this.clientRef.sendMessage(buf, false);
			}
			
			//nur tunnel
			while(!this.listOfBusMessages.isEmpty())
			{
				buf = this.clientRef.getMessageBuffer();
				EntityBusNetMessage.fillInByteBuffer(this.listOfBusMessages, buf);
				this.clientRef.sendMessage(buf, true);
			}
		}
		long currTimeStamp = System.currentTimeMillis();
		if(currTimeStamp > this.lastTimeSync +TIMESYNC_INTERVAL);
	}
	
	public float getRoundTipTime(int PlayerID)
	{
		return this.roundTripMap.get(playerID);
	}
	
	public void checkDeadReck()
	{
		if(!this.serverFlag)
		{
			return;
		}
		else
		{
			LinkedList<DeadReckoningNetMessage> list = new LinkedList<DeadReckoningNetMessage>();
			for(NetComponent comp : this.listOfNetComponents)
			{
				comp.addDeadReckoningNetMessageToList(list);
			}
		}
	}
	
}
