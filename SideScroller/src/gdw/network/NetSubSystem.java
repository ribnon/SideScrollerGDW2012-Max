package gdw.network;

import java.awt.TrayIcon.MessageType;
import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.entityCore.Entity;
import gdw.entityCore.EntityTemplate;
import gdw.network.client.BasicClient;
import gdw.network.messageType.DeadReckoningNetMessage;
import gdw.network.messageType.EntityBusNetMessage;
import gdw.network.messageType.EntityDespawnNetMessage;
import gdw.network.messageType.EntitySpawnNetMessage;
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
	
	
	private final LinkedList<NetComponent> listOfNetComponents;
	
	private final LinkedList<EntitySpawnNetMessage> listOfSpawnMessages;
	private final LinkedList<EntityBusNetMessage> listOfBusMessages;
	private final LinkedList<EntityDespawnNetMessage> listOfDespawnMessages;

	
	
	private NetSubSystem(int playerID, boolean serverFlag, BasicClient cRef, BasicServer sRef)
	{
		this.playerID = playerID;
		this.serverFlag = serverFlag;
		this.clientRef = cRef;
		this.serverRef = sRef;
		this.listOfNetComponents = new LinkedList<NetComponent>();
		this.listOfSpawnMessages = new LinkedList<EntitySpawnNetMessage>();
		this.listOfBusMessages = new LinkedList<EntityBusNetMessage>();
		this.listOfDespawnMessages = new LinkedList<EntityDespawnNetMessage>();
	}
	
	public static void initalise(int playerID, boolean serverFlag, BasicClient cRef, BasicServer sRef)
	{
		if(NetSubSystem.singelton == null)
		{
			System.out.println("Alter ich wurde schon initiziert, ich ignorie das mal");
			return;
		}
		NetSubSystem.singelton = new NetSubSystem(playerID, serverFlag, cRef, sRef);
	}
	
	public static NetSubSystem getInstance()
	{
		return NetSubSystem.singelton;
	}
	
	
	//geht über liste und prüft deadreg
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
				((NetComponent) ente.getComponent(NetComponent.COMPONENT_TYPE)).deadReckoningMessageReceive(dmsg);
			}			
		break;
		
		case NetMessageType.EntityBusMessageType:
			EntityBusNetMessage ebnm = EntityBusNetMessage.getFromByteBuffer(buf);
			ref.getEntitiy(ebnm.entityID).message(ebnm.busMessage);			
		break;
		
		case NetMessageType.EntitySpawnMessageType:
			EntitySpawnNetMessage sqnm = EntitySpawnNetMessage.getFromByteBuffer(buf);
			EntityTemplate enteT =  EntityTemplateManager.getInstance().getEntityTemplate(sqnm.templateName);
			enteT.createEntity(sqnm.id,sqnm.posX,sqnm.posY,sqnm.orientation);			
		break;
		
		case NetMessageType.EntityDespawnMessageType:
			EntityDespawnNetMessage[] arrDNM = EntityDespawnNetMessage.getFromByteBuffer(buf);
			for(int i=0;i<arrDNM.length;++i)
			{
				ref.getEntity(arrDNM.entityID).markForDestroy();
			}
			
		break;
		
		case NetMessageType.TimeSyncMessageType:
		break;	

		default:
		break;
		}
	}
	
	public void sendSpawn(String template, int id, float posX, float posY, float orientation)
	{
		//TODO
	}
	
	public void sendDeSpawn(int id)
	{
		//TODO
	}
	
	public void addNetComponentToList(NetComponent item)
	{
		this.listOfNetComponents.add(item);
	}
	
	public void removeNetComponentToList(NetComponent item)
	{
		this.listOfNetComponents.remove(item);
	}
	
	private void sentToAll(NetMessageType msg)
	{
		
	}
	
	private void sendToServer(NetMessageType msg)
	{
		
	}
	
	public void simulateGhosts(float deltaT)
	{
		for(NetComponent comp : this.listOfNetComponents)
		{
			comp.simulateGhost(deltaT);
		}
	}
	
	public void sendBufferedMessages()
	{
		//spawn
		//tunnel
		//despawn
		if(this.serverFlag)
		{
			ByteBuffer buf = this.serverRef.getMessageBuffer();
			
		}else
		{
			
		}
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
