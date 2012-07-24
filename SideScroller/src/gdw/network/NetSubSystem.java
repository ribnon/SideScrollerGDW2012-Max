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
	
	
	private NetSubSystem()
	{
		this.
	}
	
	public void initalise()
	{
		
	}
	
	
	//geht über liste und prüft deadreg
	//singelton
	public int getPlayerID()
	{
		
	}
	public boolean isServer;
	
	public registerClinetObject;
	
	
	sendToAll(busMessag)//von server
	sendToServer(busMessage)//von client
	
	sendSpawn (String, int id, 3 floats(posXY ori))
	sendDeSpawn (int id)
	
	pollMessages()
	verarbeiteNachrichten()
	
	simGhosts(delta t)
	cheackDR()//wenn server dann boom
	
}
