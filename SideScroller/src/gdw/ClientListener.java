package gdw;

import java.nio.ByteBuffer;
import java.util.LinkedList;


import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.entityCore.Level;
import gdw.network.NetSubSystem;
import gdw.network.client.BasicClient;
import gdw.network.client.IBasicClientListener;
import gdw.network.client.ServerInfo;

public class ClientListener implements IBasicClientListener {

	LinkedList<ServerInfo> servers;
	Client owner;
	
	public ClientListener(Client o){
		servers = new LinkedList<ServerInfo>();
		owner = o;
	}
	
	@Override
	public void serverResponce(ServerInfo info) {
		for(int i = 0; i < servers.size(); ++i)
		{
			System.out.println("found Server");
			if(info.id == servers.get(i).id)
				return;
		}
		servers.add(info);
		System.out.println("Server " + info.address + " found");
	}

	@Override
	public void connectionUpdate(int msg) {
		System.out.println("con update " + msg);
		if(msg == -1)
			owner.setQuit(true);
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionEstablished(BasicClient clientRef) {
		System.out.println("Connection to " + clientRef.id);
		NetSubSystem.initalise(clientRef.id, false, clientRef);
		owner.setConnected(true);
		owner.setLoadLevel(true);
		//Level.getInstance().start();
	}

	@Override
	public void incomingMessage(ByteBuffer msg, boolean wasReliable) {
		NetSubSystem.getInstance().processMessage(msg);

	}
	
	public LinkedList<ServerInfo> getServers()
	{
		return servers;
	}

}
