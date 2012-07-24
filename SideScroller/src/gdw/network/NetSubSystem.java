package gdw.network;

import java.util.LinkedList;

import gdw.network.client.BasicClient;
import gdw.network.server.BasicServer;

public class NetSubSystem
{
	private final static NetSubSystem singelton;
	
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
	
	
	private NetSubSystem(int playerID, boolean serverFlag, BasicClient cRef, BasicServer sRef)
	{
		this.playerID = playerID;
		this.serverFlag = serverFlag;
		this.clientRef = cRef;
		this.serverRef = sRef;
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
	
	public static NetSubSystem instance()
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
	
	
	
	sendToAll(busMessag)//von server
	sendToServer(busMessage)//von client
	
	sendSpawn (String, int id, 3 floats(posXY ori))
	sendDeSpawn (int id)
	
	pollMessages()
	verarbeiteNachrichten()
	
	public void simulateGhosts(float delta)
	{
		for(NetComponent comp : this.listOfNetComponents)
		{
			comp.s
		}
	}
	checkDR()//wenn server dann boom
	
}
