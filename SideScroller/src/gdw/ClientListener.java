package gdw;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import collisionDetection.CollisionDetectionComponentManager;

import gdw.entityCore.Level;
import gdw.network.NetSubSystem;
import gdw.network.client.BasicClient;
import gdw.network.client.IBasicClientListener;
import gdw.network.client.ServerInfo;

public class ClientListener implements IBasicClientListener {

	LinkedList<ServerInfo> servers;
	
	public ClientListener(){
		servers = new LinkedList<ServerInfo>();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionEstablished(BasicClient clientRef) {
		System.out.println("Connection to " + clientRef.id);
		NetSubSystem.initalise(0, false, clientRef);
		Level.getInstance().start();
	}

	@Override
	public void incomingMessage(ByteBuffer msg, boolean wasReliable) {
		// TODO Auto-generated method stub

	}
	
	public LinkedList<ServerInfo> getServers()
	{
		return servers;
	}

}
