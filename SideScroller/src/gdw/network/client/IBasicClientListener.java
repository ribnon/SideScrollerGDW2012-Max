package gdw.network.client;

import java.nio.ByteBuffer;

public interface IBasicClientListener
{
	public void serverResponce(ServerInfo info);

	public void connectionUpdate(int msg);

	public void connectionEstablished(BasicClient clientRef);

	public void incomingMessage(ByteBuffer msg, boolean wasReliable);

}
