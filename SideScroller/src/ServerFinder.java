import java.nio.ByteBuffer;
import java.util.ArrayList;

import gdwNet.client.BasicClient;
import gdwNet.client.IBasicClientListener;
import gdwNet.client.ServerInfo;



public class ServerFinder implements IBasicClientListener{

	private ArrayList<ServerInfo> servers;
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
	public void incommingMessage(ByteBuffer msg, boolean wasReliable) {
		System.out.println("Message recieved: " + msg);
		
	}

}
