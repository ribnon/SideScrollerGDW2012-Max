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
import gdw.network.server.BasicServer;

public class NetSubSystem
{
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
			EntityBusNetMessage ebnm = EntityBusNetMessage.getFromByteBuffer(buf);
			ref.getEntity(ebnm.entityID).message(ebnm.busMessage);			
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
	//constant - (messagecode,highlevelcode,size)/SideOFDeadReckMessage
	static int MAXAMOUNT_PERPACKET_DEADRECK = (NETCONSTANTS.PACKAGELENGTH - (Byte.SIZE*3))/(Integer.SIZE*2+4*Float.SIZE);
	static int MAXAMOUNT_PERPACKET_DESPAWN = (NETCONSTANTS.PACKAGELENGTH - (Byte.SIZE*3))/Integer.SIZE;
	
	
	//TODO umschreiben auf ohne konstanten
	
	//TODO roundtip fertig implementieren
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
				DeadReckoningNetMessage.fillInByteBuffer(listOfDeadReckonigMessages, buf, MAXAMOUNT_PERPACKET_DEADRECK);
				this.serverRef.sendToAll(buf, false);
			}
			//tunnel
			while(!this.listOfBusMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				EntityBusNetMessage.fillInByteBuffer(this.listOfBusMessages.poll(), buf);
				this.serverRef.sendToAll(buf, true);
			}
			//deSpawn
			while(!this.listOfDeSpawnMessages.isEmpty())
			{
				buf = this.serverRef.getMessageBuffer();
				EntityDeSpawnNetMessage.fillInByteBuffer(this.listOfDeSpawnMessages, buf,MAXAMOUNT_PERPACKET_DESPAWN);
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
				EntityBusNetMessage.fillInByteBuffer(this.listOfBusMessages.poll(), buf);
				this.clientRef.sendMessage(buf, true);
			}
		}
	}
	
	public float getRoundTipTime(int PlayerID)
	{
		
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
