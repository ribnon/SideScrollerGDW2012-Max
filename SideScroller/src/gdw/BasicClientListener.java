package gdw;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import gdw.network.client.BasicClient;
import gdw.network.client.IBasicClientListener;
import gdw.network.client.ServerInfo;



public class BasicClientListener implements gdw.network.client.IBasicClientListener{

	private ArrayList<gdw.network.client.ServerInfo> servers;
	private boolean registered = false;
	
	public void register()
	{
		if(!registered)
			BasicClient.setListener(this);
	}
	
	public void findServers()
	{
		if(!registered)
			register();
		
		servers = new ArrayList<ServerInfo>();
		BasicClient.refreshServerList();
	}
	
	ArrayList<ServerInfo> getServerList()
	{
		return (ArrayList<ServerInfo>) servers.clone();
	}
	
	@Override
	public void serverResponce(ServerInfo info) {
		for(int i = 0; i < servers.size(); ++i)
		{
			if (servers.get(i).id == info.id )
				return;
		}
		servers.add(info);
		
	}

	@Override
	public void connectionUpdate(int msg) {
		System.out.println("Message code recieved: " + msg);
		
	}

	@Override
	public void connectionEstablished(BasicClient clientRef) {
		System.out.println("Connection established");		
	}

	@Override
	public void incomingMessage(ByteBuffer msg, boolean wasReliable) {
		// TODO Auto-generated method stub
		
	}
}
